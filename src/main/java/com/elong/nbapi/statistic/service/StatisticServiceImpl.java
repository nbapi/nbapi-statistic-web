package com.elong.nbapi.statistic.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elong.nbapi.common.constants.Constant;
import com.elong.nbapi.common.dao.IBaseDao;
import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.Dimension;
import com.elong.nbapi.common.po.DimensionMetricRelation;
import com.elong.nbapi.common.po.Metric;
import com.elong.nbapi.common.po.Module;
import com.elong.nbapi.common.po.ReportSystem;
import com.elong.nbapi.common.utils.DateUtils;
import com.elong.nbapi.statistic.po.LeftNavParemeter;
import com.elong.nbapi.statistic.po.CommDimension;
import com.elong.nbapi.statistic.po.CommDimensionTime;
import com.elong.nbapi.statistic.po.MinuteDimension;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

@Service
public class StatisticServiceImpl{

	@Autowired
	private IBaseDao baseDao;

	private static final Logger logger = Logger.getLogger("businessMinitorLogger");

	public List<BusinessSystem> findRegisterSystemAll() {
		List<BusinessSystem> registerSystemList = baseDao.findAll(BusinessSystem.class);
		return registerSystemList;
	}

	public List<CommDimension> findCommDimension(LeftNavParemeter parameters) {
		Criteria criteria = Criteria.where(CommDimension.FIELD_DATE).is(parameters.getDate());

		criteria.and(CommDimension.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(CommDimension.FIELD_METRIC).is(parameters.getMetric());

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.ASC, CommDimension.FIELD_HOURRANGE));
		logger.info("findCommDimension query:" + JSON.toJSONString(criteria));

		List<CommDimension> resultList = baseDao.find(query, CommDimension.class, parameters.getBusinessType());

		return resultList;
	}

	public List<DBObject> findMinuteData(LeftNavParemeter parameters) {

		String minDate = parameters.getDate().split(" ")[0];
		Criteria criteria = Criteria.where("dateTime").gte(minDate).andOperator(Criteria.where("dateTime").lte(parameters.getDate()));

		criteria.and(CommDimension.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(CommDimension.FIELD_METRIC).is(parameters.getMetric());

		Query query = Query.query(criteria);

		final List<DBObject> dbObjectList = new ArrayList<DBObject>();
		String collectionName = parameters.getBusinessType() + Constant.COLLECTION_SUFFIX_MINUTE;
		baseDao.executeQuery(query, collectionName, new DocumentCallbackHandler() {
			@Override
			public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
				dbObjectList.add(dbObject);
			}
		});

		return dbObjectList;
	}

	public List<Module> findModuleBySysId(BusinessSystem businessSystem) {
		Criteria criteria = Criteria.where(Module.FIELD_SYSTEMID).is(businessSystem.getId());

		Query query = Query.query(criteria);
		List<Module> moduleList = baseDao.find(query, Module.class);

		for (Module module : moduleList) {
			Criteria tempCri = Criteria.where(Dimension.FIELD_MODULE_ID).is(module.getId());
			Query tempQuery = Query.query(tempCri);
			tempQuery.with(new Sort(Sort.Direction.ASC, Dimension.FIELD_DISPLAY_ORDER));

			List<Dimension> dimensionList = baseDao.find(tempQuery, Dimension.class);

			module.setDimensionList(dimensionList);
		}

		return moduleList;
	}

	public List<CommDimensionTime> findCommDimensionTime(LeftNavParemeter parameters, DateTime dateTime) {
		Criteria criteria = Criteria.where(CommDimensionTime.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(CommDimensionTime.FIELD_METRIC).is(parameters.getMetric());

		// 取当天日期和前一天日期, 避免凌晨时 图上只有几个点
		List<String> dates = new ArrayList<String>();

		dates.add(dateTime.toString(DateUtils.DATE_YYYY_MM_DD));
		dates.add(dateTime.plusDays(-1).toString(DateUtils.DATE_YYYY_MM_DD));
		criteria.and(CommDimensionTime.FIELD_DATE).in(dates);

		Query query = Query.query(criteria);
		query.limit(Constant.X_AXIS_CATEGORIES_SIZE);
		query.with(new Sort(Sort.Direction.DESC, CommDimensionTime.FIELD_DATE_TIME));
		logger.info("findCommDimensionTime" + JSON.toJSONString(query));// 打印
		List<CommDimensionTime> resultList = baseDao.find(query, CommDimensionTime.class, parameters.getBusinessType()
				+ Constant.COLLECTION_SUFFIX_MINUTE);

		Collections.reverse(resultList);

		return resultList;
	}

	/**
	 * DOD 数据
	 * @param parameters
	 * @return
	 */
	public List<CommDimensionTime> findCommDimensionDOD(LeftNavParemeter parameters, DateTime dateTime) {
		Criteria criteria = Criteria.where(CommDimensionTime.FIELD_DIMENSION).is(parameters.getDimensionName());
		criteria.and(CommDimensionTime.FIELD_METRIC).is(parameters.getMetric());

		// 此处必须改成大于小于号
		criteria.and(CommDimensionTime.FIELD_DATE_TIME).lte(dateTime.plusDays(-1).toString(DateUtils.YYYY_MM_DD_HH_MM))
				.gt(dateTime.plusDays(-1).plusMinutes(0 - Constant.X_AXIS_CATEGORIES_SIZE).toString(DateUtils.YYYY_MM_DD_HH_MM));

		Query query = Query.query(criteria);
		query.limit(Constant.X_AXIS_CATEGORIES_SIZE);
		query.with(new Sort(Sort.Direction.DESC, CommDimensionTime.FIELD_DATE_TIME));

		List<CommDimensionTime> resultList = baseDao.find(query, CommDimensionTime.class, parameters.getBusinessType()
				+ Constant.COLLECTION_SUFFIX_MINUTE);

		Collections.reverse(resultList);

		return resultList;
	}

	/**
	 * WOW数据
	 * @param parameters
	 * @return
	 */
	public List<CommDimensionTime> findCommDimensionWOW(LeftNavParemeter parameters, DateTime dateTime) {
		Criteria criteria = Criteria.where(CommDimensionTime.FIELD_DIMENSION).is(parameters.getDimensionName());
		criteria.and(CommDimensionTime.FIELD_METRIC).is(parameters.getMetric());

		criteria.and(CommDimensionTime.FIELD_DATE_TIME).lte(dateTime.plusDays(-7).toString(DateUtils.YYYY_MM_DD_HH_MM))
				.gt(dateTime.plusDays(-7).plusMinutes(0 - Constant.X_AXIS_CATEGORIES_SIZE).toString(DateUtils.YYYY_MM_DD_HH_MM));

		Query query = Query.query(criteria);
		query.limit(Constant.X_AXIS_CATEGORIES_SIZE);
		query.with(new Sort(Sort.Direction.DESC, CommDimensionTime.FIELD_DATE_TIME));

		List<CommDimensionTime> resultList = baseDao.find(query, CommDimensionTime.class, parameters.getBusinessType()
				+ Constant.COLLECTION_SUFFIX_MINUTE);

		Collections.reverse(resultList);

		return resultList;
	}


	public List<DBObject> findTwoDimensionData(LeftNavParemeter parameter) {
		Criteria criteria = Criteria.where("date").is(parameter.getDate());
		criteria.and(Dimension.FIELD_DIMENSION_NAME).is(parameter.getDimension().getDimensionName());

		criteria.and("metric").is(parameter.getMetric());
		logger.info("findTwoDimensionData query:" + JSON.toJSONString(criteria));

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "hourRange"));

		String collectionName = parameter.getBusinessType() + Constant.COLLECTION_SUFFIX_TWODIMEN;

		final List<DBObject> dbObjectList = new ArrayList<DBObject>();
		baseDao.executeQuery(query, collectionName, new DocumentCallbackHandler() {

			@Override
			public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
				dbObjectList.add(dbObject);
			}
		});

		return dbObjectList;
	}

	public List<DBObject> findTwoDimensionsDayData(LeftNavParemeter parameters) {
		try {
			String startDateStr = parameters.getStartDate();
			String endDateStr = parameters.getEndDate();
			Date startDate = DateUtils.parse(startDateStr, DateUtils.DATE_YYYY_MM_DD);
			Date endDate = DateUtils.parse(endDateStr, DateUtils.DATE_YYYY_MM_DD);

			List<DBObject> resultList = new ArrayList<DBObject>();
			while (startDate.compareTo(endDate) <= 0) {
				startDateStr = DateUtils.format(startDate);
				parameters.setDate(startDateStr);
				logger.info("findTwoDimensionsDayData,parameter = " + JSON.toJSONString(parameters));
				List<DBObject> twoDimensionSumDataList = findTwoDimensionSumData(parameters);
				if (CollectionUtils.isNotEmpty(twoDimensionSumDataList)) {
					DBObject dayData = twoDimensionSumDataList.get(0);
					if (dayData != null) {
						dayData.put("date", startDateStr);
						resultList.add(dayData);
					}
				}
				startDate = DateUtils.addDate(startDate, 1);
			}
			logger.info("findTwoDimensionsDayData,result size = " + resultList.size());
			Collections.reverse(resultList);
			return resultList;
		} catch (Exception e) {
			logger.error("findTwoDimensionsDayData,error = " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<DBObject> findTwoDimensionSumData(LeftNavParemeter parameter) {
		List<DBObject> list = findTwoDimensionData(parameter);
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyList();

		Integer startHourrange = StringUtils.isEmpty(parameter.getStartHourRange()) ? NumberUtils.INTEGER_ZERO : Integer.parseInt(parameter
				.getStartHourRange());
		Integer endHourrange = StringUtils.isEmpty(parameter.getEndHourRange()) ? NumberUtils.INTEGER_ZERO : Integer.parseInt(parameter
				.getEndHourRange());
		logger.info("findTwoDimensionSumData,startHourrange = " + startHourrange + ",endHourrange = " + endHourrange);

		Map<Integer, Map<String, Map<String, Integer>>> partTotalMap = new TreeMap<Integer, Map<String, Map<String, Integer>>>();
		Map<Integer, Integer> totalMap = new HashMap<Integer, Integer>();
		try {
			Dimension dimension = parameter.getDimension();
			String dimensionNameStr = dimension.getDimensionName();
			String dimensionValueStr = dimension.getDimensionValuesJson();
			String[] dimensionNames = StringUtils.split(dimensionNameStr, "&");
			JSONObject dimensionValueJsonObj = JSONObject.parseObject(dimensionValueStr);

			for (int idx = list.size() - 1; idx >= 0; idx--) {
				DBObject dbObject = list.get(idx);
				JSONObject jsonObject = JSON.parseObject(dbObject.toString());
				String hourRange = jsonObject.getString("hourRange");
				Integer hourRangeInt = Integer.valueOf(hourRange);
				String lastHourRange = "";
				if (hourRangeInt != null && hourRangeInt > 0) {
					lastHourRange = String.valueOf(hourRangeInt - 1);
				}
				logger.info("findTwoDimensionSumData,hourRangeInt = " + hourRangeInt + ",lastHourRange = " + lastHourRange);

				if (StringUtils.isNotEmpty(parameter.getStartHourRange()) && StringUtils.isNotEmpty(parameter.getEndHourRange())) {
					Integer currentHourrange = hourRangeInt == null ? NumberUtils.INTEGER_ZERO : hourRangeInt;
					if (currentHourrange.intValue() < startHourrange.intValue() || currentHourrange.intValue() > endHourrange.intValue()) {
						logger.info("findTwoDimensionSumData,ignore hourRange = " + currentHourrange);
						continue;
					}
				}

				Integer thisTotal = jsonObject.getInteger("total");

				Map<String, Map<String, Integer>> oneMap = new HashMap<String, Map<String, Integer>>();

				JSONArray agentNameJsonArray = null;
				JSONArray statusJsonArray = null;
				if (dimensionValueJsonObj == null) {
					jsonObject.remove("_id");
					jsonObject.remove("date");
					jsonObject.remove("dimension");
					jsonObject.remove("hourRange");
					jsonObject.remove("metric");
					jsonObject.remove("total");
					agentNameJsonArray = JSON.parseArray(JSON.toJSONString(jsonObject.keySet()));
					statusJsonArray = new JSONArray();
					Set<String> statusSet = new HashSet<String>();
					for (Object agentName : agentNameJsonArray) {
						statusSet.addAll(jsonObject.getJSONObject(agentName.toString()).keySet());
					}
					statusJsonArray.addAll(statusSet);
				} else {
					agentNameJsonArray = dimensionValueJsonObj.getJSONArray(dimensionNames[0]);
					statusJsonArray = dimensionValueJsonObj.getJSONArray(dimensionNames[1]);
				}

				for (Object agentName : agentNameJsonArray) {
					Map<String, Integer> twoMap = new HashMap<String, Integer>();
					String statusJsonStr = jsonObject.getString(agentName.toString());

					if (StringUtils.isNotEmpty(statusJsonStr)) {
						if (StringUtils.isEmpty(lastHourRange)) {
							JSONObject statusJsonObj = JSON.parseObject(statusJsonStr);
							for (Object status : statusJsonArray) {
								Integer count1 = statusJsonObj.getInteger(status.toString());
								twoMap.put(status.toString(), count1 == null ? 0 : count1);
							}
						} else {
							Map<String, Map<String, Integer>> lastOneMap = partTotalMap.get(hourRangeInt - 1);
							if (lastOneMap == null) {
								lastOneMap = new HashMap<String, Map<String, Integer>>();
							}
							Map<String, Integer> lastTwoMap = lastOneMap.get(agentName.toString());
							if (lastTwoMap == null) {
								lastTwoMap = new HashMap<String, Integer>();
							}

							JSONObject statusJsonObj = JSON.parseObject(statusJsonStr);
							for (Object status : statusJsonArray) {
								Integer count1 = statusJsonObj.getInteger(status.toString());
								Integer count2 = lastTwoMap.get(status.toString());
								twoMap.put(status.toString(), (count1 == null ? 0 : count1) + (count2 == null ? 0 : count2));
							}
						}
					} else {
						if (StringUtils.isEmpty(lastHourRange)) {
							for (Object status : statusJsonArray) {
								twoMap.put(status.toString(), 0);
							}
						} else {
							Map<String, Map<String, Integer>> lastOneMap = partTotalMap.get(hourRangeInt - 1);
							if (lastOneMap == null) {
								lastOneMap = new HashMap<String, Map<String, Integer>>();
							}
							Map<String, Integer> lastTwoMap = lastOneMap.get(agentName.toString());
							if (lastTwoMap == null) {
								lastTwoMap = new HashMap<String, Integer>();
							}
							for (Object status : statusJsonArray) {
								Integer count2 = lastTwoMap.get(status.toString());
								twoMap.put(status.toString(), 0 + (count2 == null ? 0 : count2));
							}
						}
					}
					oneMap.put(agentName.toString(), twoMap);
				}
				partTotalMap.put(hourRangeInt, oneMap);

				if (StringUtils.isEmpty(lastHourRange)) {
					totalMap.put(hourRangeInt, thisTotal);
				} else {
					Integer lastTotal = totalMap.get(hourRangeInt - 1);
					lastTotal = (lastTotal == null) ? 0 : lastTotal;
					totalMap.put(hourRangeInt, lastTotal + thisTotal);
				}
			}
		} catch (Exception e) {
			logger.error("findTwoDimensionSumData " + e.getMessage(), e);
		}

		List<DBObject> dbObjectList = new ArrayList<DBObject>();
		for (Map.Entry<Integer, Map<String, Map<String, Integer>>> entry : partTotalMap.entrySet()) {
			BasicDBObject jsonObject = new BasicDBObject();
			jsonObject.put("hourRange", entry.getKey());
			Map<String, Map<String, Integer>> oneMap = entry.getValue();
			for (Map.Entry<String, Map<String, Integer>> twoEntry : oneMap.entrySet()) {
				jsonObject.put(twoEntry.getKey(), twoEntry.getValue());
			}
			jsonObject.put("total", totalMap.get(entry.getKey()));
			dbObjectList.add(jsonObject);
		}
		Collections.reverse(dbObjectList);
		logger.info("findTwoDimensionSumData,result = " + JSON.toJSONString(dbObjectList));
		return dbObjectList;
	}

	public Dimension getDimensionById(String id) {
		Criteria criteria = Criteria.where(Dimension.FIELD_ID).is(id);
		Query query = Query.query(criteria);

		Dimension dimension = baseDao.findOne(query, Dimension.class);

		try {
			Criteria criteria1 = Criteria.where(DimensionMetricRelation.FIELD_DIMENSION_ID).is(id);
			List<DimensionMetricRelation> relations = baseDao.find(Query.query(criteria1), DimensionMetricRelation.class);

			List<Metric> metricList = new ArrayList<Metric>();
			for (DimensionMetricRelation item : relations) {
				Criteria criteria2 = Criteria.where(Metric.FIELD_ID).is(item.getMetricId());
				Metric metric = baseDao.findOne(Query.query(criteria2), Metric.class);
				metricList.add(metric);
			}

			dimension.setMetricList(metricList);
		} catch (Exception exx) {
			logger.error(exx);
		}

		return dimension;
	}

	public List<CommDimension> findCommDimensionRange(LeftNavParemeter parameters) {

		Criteria criteria = Criteria.where(CommDimension.FIELD_DATE).gte(parameters.getStartDate())
				.andOperator(Criteria.where(CommDimension.FIELD_DATE).lte(parameters.getEndDate()));

		criteria.and(CommDimension.FIELD_DIMENSION).is(parameters.getDimensionName());
		criteria.and(CommDimension.FIELD_METRIC).is(parameters.getMetric());

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.ASC, CommDimension.FIELD_DATE, CommDimension.FIELD_HOURRANGE));

		List<CommDimension> resultList = baseDao.find(query, CommDimension.class, parameters.getBusinessType());

		return resultList;
	}

	public List<DBObject> findTwoDimensionHistoryData(LeftNavParemeter parameter) {
		Criteria criteria = Criteria.where("date").gte(parameter.getStartDate())
				.andOperator(Criteria.where("date").lte(parameter.getEndDate()));

		criteria.and(Dimension.FIELD_DIMENSION_NAME).is(parameter.getDimension().getDimensionName());

		criteria.and("metric").is(parameter.getMetric());

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.ASC, "date", "hourRange"));
		// query.with(new Sort(Sort.Direction.ASC, "date", "hourRange"));

		String collectionName = parameter.getBusinessType() + Constant.COLLECTION_SUFFIX_TWODIMEN;

		final List<DBObject> dbObjectList = new ArrayList<DBObject>();
		baseDao.executeQuery(query, collectionName, new DocumentCallbackHandler() {

			@Override
			public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
				dbObjectList.add(dbObject);
			}
		});

		return dbObjectList;
	}

	public MinuteDimension findSumMinuteDimension(LeftNavParemeter parameters) {
		List<MinuteDimension> minuteDimensionList = findMinuteDimensionRange(parameters);
		if (minuteDimensionList.size() == 0) {
			return null;
		}
		Map<String, Integer> valueTotal = new HashMap<String, Integer>();

		long total = 0;
		for (MinuteDimension minuteDimension : minuteDimensionList) {
			String dimensionValue = minuteDimension.getDimensionValue();
			JSONObject jsobj = JSON.parseObject(dimensionValue);
			Set<Entry<String, Object>> entrySet = jsobj.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Integer value = (Integer) entry.getValue();
				if (valueTotal.get(key) == null) {
					valueTotal.put(key, value);
				} else {
					valueTotal.put(key, valueTotal.get(key) + value);
				}
			}
			total += minuteDimension.getTotal();
		}

		JSONObject json = new JSONObject();

		MinuteDimension dimensionone = minuteDimensionList.get(0);
		for (Entry<String, Integer> entry : valueTotal.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			json.put(key, value);
		}

		String dimensionVal = JSONObject.toJSONString(json);
		dimensionone.setDateTime(parameters.getStartDate());
		dimensionone.setTime(parameters.getTime());
		dimensionone.setDimensionValue(dimensionVal);
		dimensionone.setTotal(total);
		return dimensionone;
	}

	public MinuteDimension findMinuteDimension(LeftNavParemeter parameters) {

		String startDateTime = parameters.getStartDate();
		Date startDate = DateUtils.parse(startDateTime, DateUtils.DATE_YYYY_MM_DD);
		startDateTime = DateUtils.format(startDate, DateUtils.YYYY_MM_DD_HH_MM);

		Criteria criteria = Criteria.where(MinuteDimension.FIELD_DATETIME).gte(startDateTime)
				.andOperator(Criteria.where(MinuteDimension.FIELD_DATETIME).lte(parameters.getStartDate()));

		criteria.and(MinuteDimension.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(MinuteDimension.FIELD_METRIC).is(parameters.getMetric());
		logger.info("findMinuteDimension query:" + JSON.toJSONString(criteria));

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.DESC, MinuteDimension.FIELD_DATETIME));

		List<MinuteDimension> minuteDimensionList = baseDao.find(query, MinuteDimension.class, parameters.getBusinessType()
				+ "_minute");

		if (minuteDimensionList.size() == 0) {
			return null;
		}
		Map<String, Integer> valueTotal = new HashMap<String, Integer>();

		long total = 0;
		for (MinuteDimension minuteDimension : minuteDimensionList) {
			String dimensionValue = minuteDimension.getDimensionValue();
			JSONObject jsobj = JSON.parseObject(dimensionValue);
			Set<Entry<String, Object>> entrySet = jsobj.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Integer value = (Integer) entry.getValue();
				if (valueTotal.get(key) == null) {
					valueTotal.put(key, value);
				} else {
					valueTotal.put(key, valueTotal.get(key) + value);
				}
			}
			total += minuteDimension.getTotal();
		}

		JSONObject json = new JSONObject();

		MinuteDimension dimensionone = minuteDimensionList.get(0);
		for (Entry<String, Integer> entry : valueTotal.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			json.put(key, value);
		}

		String dimensionVal = JSONObject.toJSONString(json);
		dimensionone.setDateTime(parameters.getStartDate());
		dimensionone.setTime(parameters.getTime());
		dimensionone.setDimensionValue(dimensionVal);
		dimensionone.setTotal(total);
		return dimensionone;
	}

	public List<MinuteDimension> findDayDimensions(LeftNavParemeter navParameter) {
		try {
			String startDateStr = navParameter.getStartDate();
			String endDateStr = navParameter.getEndDate();
			Date startDate = DateUtils.parse(startDateStr, DateUtils.DATE_YYYY_MM_DD);
			Date endDate = DateUtils.parse(endDateStr, DateUtils.DATE_YYYY_MM_DD);

			List<MinuteDimension> resultList = new ArrayList<MinuteDimension>();
			while (startDate.compareTo(endDate) <= 0) {
				startDateStr = DateUtils.format(startDate);
				navParameter.setStartDate(startDateStr + " 23:59");
				logger.info("findDayDimensions,parameter = " + JSON.toJSONString(navParameter));
				MinuteDimension minuteDimension = findMinuteDimension(navParameter);
				if (minuteDimension != null) {
					minuteDimension.setDate(startDateStr);
					resultList.add(minuteDimension);
				}
				startDate = DateUtils.addDate(startDate, 1);
			}
			logger.info("findDayDimensions,result size = " + resultList.size());
			Collections.reverse(resultList);
			return resultList;
		} catch (Exception e) {
			logger.error("findDayDimensions,error = " + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public List<CommDimension> findRangeDimensions(LeftNavParemeter parameters) {

		Map<Integer, Map<String, Integer>> hourRange = new LinkedHashMap<Integer, Map<String, Integer>>();
		LinkedHashMap<Integer, Long> totalMap = new LinkedHashMap<Integer, Long>();
		String datestr = parameters.getDate();
		List<Integer> allTime = timeUtils(parameters);
		for (Integer str : allTime) {
			hourRange.put(str, null);
		}

		int hourange = allTime.size();
		Criteria criteria = Criteria.where(CommDimension.FIELD_HOURRANGE).lt(allTime.get(hourange - 1) + 1);

		criteria.and(CommDimension.FIELD_DATE).is(datestr);

		criteria.and(CommDimension.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(CommDimension.FIELD_METRIC).is(parameters.getMetric());

		Query query = Query.query(criteria);

		query.with(new Sort(Sort.Direction.ASC, CommDimension.FIELD_HOURRANGE));

		List<CommDimension> resultList = baseDao.find(query, CommDimension.class, parameters.getBusinessType());
		if (resultList.size() == 0) {
			return null;
		}

		Map<String, Integer> valueTotal = new HashMap<String, Integer>();
		int currentHour = 0;
		for (CommDimension commDimension : resultList) {

			String dimensionValue = commDimension.getDimensionValue();
			int hasHour = commDimension.getHourRange();

			if (currentHour == 0) {
				currentHour = hasHour;
			}

			JSONObject jsobj = JSON.parseObject(dimensionValue);
			Set<Entry<String, Object>> entrySet = jsobj.entrySet();
			for (Entry<String, Object> entry : entrySet) {

				String key = entry.getKey();
				Integer value = (Integer) entry.getValue();

				while (currentHour != hasHour) {
					hourRange.put(currentHour, new HashMap<String, Integer>());
					for (Entry<String, Integer> ent : valueTotal.entrySet()) {
						String valKey = ent.getKey();
						hourRange.get(currentHour).put(valKey, valueTotal.get(valKey));
					}

					Long currentTotal = 0l;
					for (Entry<Integer, Long> en : totalMap.entrySet()) {
						currentTotal = en.getValue();
					}
					totalMap.put(currentHour, currentTotal);
					currentHour++;
				}

				if (valueTotal.get(key) == null) {
					valueTotal.put(key, value);
				} else {
					valueTotal.put(key, valueTotal.get(key) + value);
				}

				if (hourRange.get(hasHour) == null) {
					hourRange.put(hasHour, new HashMap<String, Integer>());
				}
				for (Entry<String, Integer> ent : valueTotal.entrySet()) {
					String valKey = ent.getKey();
					hourRange.get(hasHour).put(valKey, valueTotal.get(valKey));
				}
			}
			if (totalMap.size() == 0) {
				totalMap.put(hasHour, commDimension.getTotal());
			} else {
				Long totalLast = 0l;
				for (Entry<Integer, Long> en : totalMap.entrySet()) {
					totalLast = en.getValue();
				}
				totalMap.put(hasHour, totalLast + commDimension.getTotal());
			}
			currentHour++;
		}

		int index = 0;
		for (Entry<Integer, Map<String, Integer>> entry : hourRange.entrySet()) {
			if (entry.getValue() != null) {
				index = entry.getKey();
			}
		}
		List<CommDimension> finalList = new ArrayList<CommDimension>();
		for (int i = 0; i < hourange; i++) {
			CommDimension commonDimension = new CommDimension();
			commonDimension.setDate(parameters.getDate());
			commonDimension.setDimension(parameters.getDimensionName());
			commonDimension.setHourRange(i);
			commonDimension.setMetric(parameters.getMetric());

			Map<String, Integer> valueMap = hourRange.get(i);
			if (valueMap == null && i < index) {
				commonDimension.setDimensionValue(null);
				finalList.add(commonDimension);
				continue;
			} else if (valueMap == null && i > index) {
				valueMap = hourRange.get(index);
				commonDimension.setTotal(totalMap.get(index));
			} else {
				commonDimension.setTotal(totalMap.get(i));
			}
			JSONObject jsonobj = parseMapToJSON(valueMap);
			String jsonString = JSONObject.toJSONString(jsonobj);

			commonDimension.setDimensionValue(jsonString);

			finalList.add(commonDimension);
		}

		Collections.reverse(finalList);
		return finalList;
	}

	private JSONObject parseMapToJSON(Map<String, Integer> map) {
		JSONObject json = new JSONObject();
		for (Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			json.put(key, value);
		}

		return json;
	}

	private List<Integer> timeUtils(LeftNavParemeter parameters) {
		String startTime = parameters.getStartDate();

		String[] times = startTime.split(" ");
		String hourAndMin = times[1];

		String[] hourMins = hourAndMin.split(":");
		String hourS = hourMins[0];
		Integer hour = Integer.parseInt(hourS);

		Integer[] hourRange = new Integer[hour + 1];
		List<Integer> allTime = new LinkedList<Integer>();

		for (int i = 0; i < hour; i++) {
			hourRange[i] = i;
			String hours = String.valueOf(hourRange[i]);
			allTime.add(Integer.parseInt(hours));
		}
		return allTime;
	}

	public Map<String, Integer> findCommDimensionByMin(LeftNavParemeter parameters, String currentDate) {

		StringBuffer start = new StringBuffer(currentDate.split(" ")[0]);
		start.append(" 00:00");

		Criteria criteria = Criteria.where(MinuteDimension.FIELD_DATETIME).lte(currentDate);
		criteria.gte(start.toString());

		criteria.and(MinuteDimension.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(MinuteDimension.FIELD_METRIC).is(parameters.getMetric());

		Query query = Query.query(criteria);

		long startLong = System.currentTimeMillis();
		List<MinuteDimension> resultList = baseDao.find(query, MinuteDimension.class, parameters.getBusinessType()
				+ Constant.COLLECTION_SUFFIX_MINUTE);
		long endLong = System.currentTimeMillis();
		System.out.println("相隔时间是" + (endLong - startLong));
		// key 供应商 名称 value 供应商从零点到这个时间点的总数
		Map<String, Integer> valueMap = new LinkedHashMap<String, Integer>();

		for (MinuteDimension item : resultList) {
			String dimensionValue = item.getDimensionValue();
			JSONObject jsobj = JSON.parseObject(dimensionValue);
			Set<Entry<String, Object>> entrySet = jsobj.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey().trim();
				Integer value = (Integer) entry.getValue();
				if (valueMap.get(key) == null) {
					valueMap.put(key, value);
				} else {
					valueMap.put(key, valueMap.get(key) + value);
				}
			}
		}

		return valueMap;
	}

	public List<MinuteDimension> findMinuteDimensionRange(LeftNavParemeter parameters) {

		String start = parameters.getStartDate();
		String end = parameters.getEndDate();

		Date startDate = null;
		Date endDate = null;
		try {
			startDate = new Date(Long.parseLong(start));
			endDate = new Date(Long.parseLong(end));
		} catch (Exception e) {
			startDate = DateUtils.parse(start, DateUtils.YYYY_MM_DD_HH_MM);
			endDate = DateUtils.parse(end, DateUtils.YYYY_MM_DD_HH_MM);
		}

		start = DateUtils.format(startDate, DateUtils.YYYY_MM_DD_HH_MM);
		end = DateUtils.format(endDate, DateUtils.YYYY_MM_DD_HH_MM);

		Criteria criteria = Criteria.where(MinuteDimension.FIELD_DATETIME).gte(start)
				.andOperator(Criteria.where(MinuteDimension.FIELD_DATETIME).lte(end));

		criteria.and(MinuteDimension.FIELD_DIMENSION).is(parameters.getDimensionName());

		criteria.and(MinuteDimension.FIELD_METRIC).is(parameters.getMetric());

		Query query = Query.query(criteria);

		query.with(new Sort(Sort.Direction.ASC, MinuteDimension.FIELD_DATETIME));
		List<MinuteDimension> resultList = baseDao.find(query, MinuteDimension.class, parameters.getBusinessType() + "_minute");

		return resultList;

	}

	public List<CommDimension> hourDataDownload(LeftNavParemeter parameters, Dimension dimension) {
		// Dimension dimension = getDimensionById(parameters.getDimensionId());

		Criteria criteria = Criteria.where(CommDimension.FIELD_DATE).is(parameters.getDate());

		criteria.and(CommDimension.FIELD_DIMENSION).is(dimension.getDimensionName());

		List<String> metricList = new ArrayList<String>();
		for (Metric metric : dimension.getMetricList()) {
			metricList.add(metric.getName());
		}

		criteria.and(CommDimension.FIELD_METRIC).in(metricList);

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.ASC, CommDimension.FIELD_HOURRANGE));

		List<CommDimension> resultList = baseDao.find(query, CommDimension.class, parameters.getBusinessType());

		return resultList;
	}

	public List<BusinessSystem> getBusinessSystemList() {
		return baseDao.findAll(BusinessSystem.class);
	}

	public List<CommDimension> findCommByDimensionValue(LeftNavParemeter paremeter) {
		String start = paremeter.getStartDate();
		String end = paremeter.getEndDate();

		if (!start.contains("-")) {
			Date startDate = new Date(Long.parseLong(start));
			Date endDate = new Date(Long.parseLong(end));

			start = DateUtils.format(startDate, DateUtils.DATE_YYYY_MM_DD);
			end = DateUtils.format(endDate, DateUtils.DATE_YYYY_MM_DD);
			paremeter.setStartDate(start);
			paremeter.setEndDate(end);
		}
		Criteria criteria = Criteria.where(CommDimension.FIELD_DATE).gte(paremeter.getStartDate())
				.andOperator(Criteria.where(CommDimension.FIELD_DATE).lte(paremeter.getEndDate()));

		criteria.and(CommDimension.FIELD_DIMENSION).is(paremeter.getDimensionName());

		criteria.and(CommDimension.FIELD_METRIC).is(paremeter.getMetric());

		Query query = new Query(criteria);

		query.with(new Sort(Sort.Direction.ASC, CommDimension.FIELD_DATE).and(new Sort(Sort.Direction.ASC,
				CommDimension.FIELD_HOURRANGE)));

		List<CommDimension> resultList = baseDao.find(query, CommDimension.class, paremeter.getBusinessType());
		return resultList;
	}

	public List<CommDimensionTime> getComparisonData(LeftNavParemeter parameters) {

		Criteria criteria = Criteria.where(CommDimensionTime.FIELD_DIMENSION).is(parameters.getDimensionName());
		criteria.and(CommDimensionTime.FIELD_METRIC).is(parameters.getMetric());

		Object[] dates = parameters.getDate().split(",");

		criteria.and(CommDimensionTime.FIELD_DATE).in(dates);

		Query query = Query.query(criteria);
		query.with(new Sort(Sort.Direction.ASC, CommDimensionTime.FIELD_DATE_TIME));

		List<CommDimensionTime> resultList = baseDao.find(query, CommDimensionTime.class, parameters.getBusinessType()
				+ Constant.COLLECTION_SUFFIX_MINUTE);

		return resultList;

	}

	public BusinessSystem getBusinessSystemBySystemId(BusinessSystem businessSystem) {
		Criteria criteria = Criteria.where(BusinessSystem.FIELD_ID).is(businessSystem.getId());

		return baseDao.findOne(Query.query(criteria), BusinessSystem.class);
	}
	
	public List<ReportSystem>getReportSystemList(){
		return baseDao.findAll(ReportSystem.class);
	}

}
