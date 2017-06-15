package com.elong.nbapi.statistic.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elong.nbapi.common.constants.Constant;
import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.Dimension;
import com.elong.nbapi.common.po.Module;
import com.elong.nbapi.common.utils.DateUtils;
import com.elong.nbapi.statistic.constants.Const;
import com.elong.nbapi.statistic.po.LeftNavParemeter;
import com.elong.nbapi.statistic.po.CommDimensionTime;
import com.elong.nbapi.statistic.po.TotalDataEntity;
import com.elong.nbapi.statistic.po.TotalItemEntity;
import com.elong.nbapi.statistic.service.StatisticServiceImpl;

@Controller
@EnableScheduling
public class StatisticController {

	private final Set<LeftNavParemeter> subscribeSet = new HashSet<LeftNavParemeter>();
	private static final Logger logger = Logger.getLogger("businessMinitorLogger");

	@Autowired
	private StatisticServiceImpl service;

	private String currentSysName;

	@RequestMapping(value = "/system", method = RequestMethod.GET)
	public String showSystemUI(Model model) {
		model.addAttribute("menuItem", "system");
		return "system";
	}

	@RequestMapping(value = "/addDimesion", method = RequestMethod.GET)
	public String showDimensionUI(Model model) {
		model.addAttribute("menuItem", "addDimesion");
		return "dimension";
	}

	@RequestMapping(value = Constant.LEFT_NAV_PREFIX + "/{businessType}/{dimensionName}", method = RequestMethod.GET)
	public ModelAndView gotoPageByNavParameter(LeftNavParemeter navParameters, String sysid) {

		Dimension dimension = service.getDimensionById(navParameters.getDimensionId());
		navParameters.setDimension(dimension);

		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("dimension", dimension);
		modelMap.addAttribute("navParametersPo", navParameters);

		if (Constant.DIMENSION_DISPLAY_TYPE.equals(dimension.getDisplayType())) {
			JSONArray dimensionValues = JSON.parseArray(dimension.getDimensionValues());
			modelMap.addAttribute("dimensionValues", dimensionValues);
		}

		modelMap.addAttribute("navParameters", JSONArray.toJSONString(navParameters));
		modelMap.addAttribute("currentSysName", currentSysName);
		String pageUrl = getCmmPageUrl("commondimension");

		Date currDate = new Date(); // 当前时间
		Date prevDate = new Date();

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(currDate);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		prevDate = calendar.getTime(); // 得到前一天的时间
		modelMap.put("currDate", DateUtils.format(currDate, DateUtils.YYYY_MM_DD_HH_MM));
		modelMap.put("prevDate", DateUtils.format(prevDate, DateUtils.YYYY_MM_DD_HH_MM));
		modelMap.put("startMonthDate", DateUtils.getTodayOfLastMonth());
		modelMap.put("endMonthDate", DateUtils.format(new Date(), DateUtils.DATE_YYYY_MM_DD));

		// 由于集群的问题左侧导航菜单不能保存在session中，要保存在request中
		BusinessSystem businessSystem = new BusinessSystem();
		businessSystem.setId(sysid);
		List<Module> moduleList = service.findModuleBySysId(businessSystem);
		modelMap.addAttribute("sysModuleList", moduleList);

		ModelAndView modelAndView = new ModelAndView(pageUrl, modelMap);
		return modelAndView;
	}

	@RequestMapping(value = "/sysoption", method = RequestMethod.GET)
	public String gotoBusinessSystemPage(BusinessSystem businessSystem, Model model) {
		List<Module> moduleList = service.findModuleBySysId(businessSystem);
		model.addAttribute("sysModuleList", moduleList);
		currentSysName = service.getBusinessSystemBySystemId(businessSystem).getSystemName();
		model.addAttribute("currentSysName", currentSysName);

		if (moduleList.size() == 0) {
			return "/common/index";
		}
		String businessLine = moduleList.get(0).getBusinessType();
		Dimension dimension = moduleList.get(0).getOneDimensionList().get(0);
		String dimensionName = dimension.getDimensionName();
		String dimensionId = dimension.getId();

		return "redirect:" + Constant.LEFT_NAV_PREFIX + "/" + businessLine + "/" + dimensionName + "?sysid=" + moduleList.get(0).getSystemId()
				+ "&dimensionId=" + dimensionId;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView gotoIndexPage(Model model) {
		model.addAttribute(Constant.HEADER_ACTIVE_ITEM, new String[] { "index" });
		model.addAttribute("sysModuleList", new ArrayList<Module>());
		model.addAttribute("businessList", service.getBusinessSystemList());

		String page = getCmmPageUrl("index");
		ModelAndView modelAndView = new ModelAndView(page);
		return modelAndView;
	}

	protected String getCmmPageUrl(String url) {
		return "/common/" + url;
	}
	
	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/showdata")
	public void showDataByCondition(LeftNavParemeter parameters) {
		subscribeSet.add(parameters);
		printRealtimeDataWithAll();
	}

	public TotalItemEntity getRealtimeDODData(LeftNavParemeter parameter, DateTime queryDateTime) {
		List<CommDimensionTime> minuteDimensionList = service.findCommDimensionDOD(parameter, queryDateTime);
		paddingList(minuteDimensionList, queryDateTime.plusDays(-1));
		TotalItemEntity totalItemEntity = new TotalItemEntity();
		totalItemEntity.setName(Const.DOD_DISPLAY);
		totalItemEntity.setType(Const.LINE_TYPE);
		if (null != minuteDimensionList && minuteDimensionList.size() > 0) {
			List<TotalDataEntity> totalDataEntities = new ArrayList<>();
			DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
			for (CommDimensionTime commDimension : minuteDimensionList) {
				DateTime dateTime = dateTimeFormatter.parseDateTime(commDimension.getDateTime());
				String displayTime = dateTime.plusDays(1).toString("yyyy-MM-dd HH:mm");
				TotalDataEntity totalDataEntity = new TotalDataEntity().setDateTime(commDimension.getDateTime())
						.setDisplayTime(displayTime).setDimensionValue(commDimension.getDimensionValue());
				totalDataEntity.setTotalValue(commDimension.getTotal() == null ? 0 : commDimension.getTotal().intValue());
				totalDataEntities.add(totalDataEntity);
			}
			totalItemEntity.setTotalDataEntityList(totalDataEntities);
			return totalItemEntity;
		}
		return null;
	}

	public TotalItemEntity getRealtimeWOWData(LeftNavParemeter parameter, DateTime queryDateTime) {
		List<CommDimensionTime> minuteDimensionList = service.findCommDimensionWOW(parameter, queryDateTime);
		paddingList(minuteDimensionList, queryDateTime.plusDays(-7));
		TotalItemEntity totalItemEntity = new TotalItemEntity();
		totalItemEntity.setName(Const.WOW_DISPLAY);
		totalItemEntity.setType(Const.LINE_TYPE);
		if (null != minuteDimensionList && minuteDimensionList.size() > 0) {
			List<TotalDataEntity> totalDataEntities = new ArrayList<>();
			DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
			for (CommDimensionTime commDimension : minuteDimensionList) {
				DateTime dateTime = dateTimeFormatter.parseDateTime(commDimension.getDateTime());
				String displayTime = dateTime.plusDays(7).toString("yyyy-MM-dd HH:mm");
				TotalDataEntity totalDataEntity = new TotalDataEntity().setDateTime(commDimension.getDateTime())
						.setDisplayTime(displayTime).setDimensionValue(commDimension.getDimensionValue());
				totalDataEntity.setTotalValue(commDimension.getTotal() == null ? 0 : commDimension.getTotal().intValue());
				totalDataEntities.add(totalDataEntity);
			}
			totalItemEntity.setTotalDataEntityList(totalDataEntities);
			return totalItemEntity;
		}
		return null;
	}
	

	@Scheduled(fixedRate = 10 * 1000)
	public void printRealtimeDataWithAll() {

		long beginMills = System.currentTimeMillis();
		DateTime dateTime = DateTime.now();
		for (LeftNavParemeter parameter : subscribeSet) {

			parameter.setDate(getCurrentTime());

			List<CommDimensionTime> results = service.findCommDimensionTime(parameter, dateTime);

			paddingList(results, dateTime);

			String realSubscribe = getSubscribe(Const.BROKER_REALTIME, parameter);
			template.convertAndSend(realSubscribe, results);

			// total
			List<TotalItemEntity> totalItemEntities = new ArrayList<>();
			TotalItemEntity totalItemEntityDOD = getRealtimeDODData(parameter, dateTime);
			TotalItemEntity totalItemEntityWOW = getRealtimeWOWData(parameter, dateTime);
			if (null != totalItemEntityDOD) {
				totalItemEntities.add(totalItemEntityDOD);
			}
			if (null != totalItemEntityWOW) {
				totalItemEntities.add(totalItemEntityWOW);
			}

			String totalSubscribe = getTotalSubscribe(Const.BROKER_REALTIME, Const.TOTAL_SUBSCRIBE, parameter);
			template.convertAndSend(totalSubscribe, totalItemEntities);

		}
		logger.info("执行耗时: " + (System.currentTimeMillis() - beginMills) + "ms");

	}
	
	
	private void paddingList(List<CommDimensionTime> list, DateTime dateTime) {
		int size = Constant.X_AXIS_CATEGORIES_SIZE - list.size();
		if (size > 0) {
			Map<String, CommDimensionTime> tempMap = new HashMap<>();
			for (CommDimensionTime commDimensionTime : list) {
				tempMap.put(commDimensionTime.getDateTime(), commDimensionTime);
			}
			dateTime = dateTime.plusMinutes(1);
			for (int i = 0; i < Constant.X_AXIS_CATEGORIES_SIZE; i++) {
				dateTime = dateTime.plusMinutes(-1);
				if (!tempMap.containsKey(dateTime.toString(DateUtils.YYYY_MM_DD_HH_MM))) {
					CommDimensionTime item = new CommDimensionTime();
					String time = DateUtils.format(dateTime.toDate(), DateUtils.HHMM);
					item.setTime(time);
					item.setDateTime(dateTime.toString(DateUtils.YYYY_MM_DD_HH_MM));
					list.add(item);
				}
			}
			Collections.sort(list);
		}
	}

	private String getSubscribe(String prefix, LeftNavParemeter parameter) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		buffer.append("/");
		buffer.append(parameter.getBusinessType());
		buffer.append("/");
		buffer.append(parameter.getDimensionName());

		buffer.append("/");
		buffer.append(parameter.getMetric());

		return buffer.toString();
	}

	private String getTotalSubscribe(String prefix, String totalName, LeftNavParemeter parameter) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		buffer.append("/");
		buffer.append(parameter.getBusinessType());
		buffer.append("/");
		buffer.append(totalName);
		buffer.append("/");
		buffer.append(parameter.getDimensionName());
		buffer.append("/");
		buffer.append(parameter.getMetric());

		return buffer.toString();
	}


	private String getCurrentTime() {
		return DateUtils.format(Calendar.getInstance().getTime());
	}


	@RequestMapping(value = "/comm/getcomparisondata", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Map<String, List<Object[]>>> getComparisonData(String parameter) {
		LeftNavParemeter navParameter = JSON.parseObject(parameter, LeftNavParemeter.class);

		List<CommDimensionTime> resultList = service.getComparisonData(navParameter);
		JSONArray dimensionValues = JSON.parseArray(navParameter.getDimension().getDimensionValues());
		// 总和
		dimensionValues.add("total");

		Map<String, Map<String, List<Object[]>>> resultMap = new HashMap<String, Map<String, List<Object[]>>>();

		for (int i = 0; i < resultList.size(); i++) {
			CommDimensionTime item = resultList.get(i);

			JSONObject parseObject = JSONObject.parseObject(item.getDimensionValue());

			for (Object value : dimensionValues) {
				Map<String, List<Object[]>> map = resultMap.get(value);
				if (map == null) {
					map = new HashMap<String, List<Object[]>>();
					resultMap.put(value.toString(), map);
				}

				List<Object[]> list = map.get(item.getDate());
				if (list == null) {
					list = new ArrayList<Object[]>();
					map.put(item.getDate(), list);
				}

				Object[] xyAxis = new Object[2];
				xyAxis[0] = item.getTime();

				Object yPoint = null;
				if ("total".equals(value)) {
					yPoint = item.getTotal();
				} else {
					if (parseObject.containsKey(value)) {
						yPoint = parseObject.get(value);
					} else {
						yPoint = 0;
					}
				}

				xyAxis[1] = yPoint;

				list.add(xyAxis);
			}
		}

		// 补足缺少的日期
		List<String> hhmmList = getHHMMOneDay();
		Set<Entry<String, Map<String, List<Object[]>>>> entrySet = resultMap.entrySet();
		for (Entry<String, Map<String, List<Object[]>>> entry : entrySet) {
			Map<String, List<Object[]>> value = entry.getValue();

			Set<Entry<String, List<Object[]>>> entrySet2 = value.entrySet();
			for (Entry<String, List<Object[]>> entry2 : entrySet2) {
				List<Object[]> value2 = entry2.getValue();

				for (int i = 0; i < hhmmList.size(); i++) {
					if (i + 1 > value2.size() || !hhmmList.get(i).equals(value2.get(i)[0])) {
						Object[] temp = new Object[2];
						temp[0] = hhmmList.get(i);
						temp[1] = 0;
						value2.add(i, temp);
					}

					// 转化成日期
					value2.get(i)[0] = xCoordinate(value2.get(i)[0].toString());
				}

			}
		}

		return resultMap;
	}

	private Date xCoordinate(String time) {
		StringBuilder builder = new StringBuilder();
		// 由于是比较的不同日期， 同一个时间段的值（00-24点的数据）,所以将不同日期在同一坐标里面显示值
		builder.append("2100-01-01 ");
		builder.append(time);

		Date x = DateUtils.parse(builder.toString(), DateUtils.YYYY_MM_DD_HH_MM);
		return x;
	}

	private List<String> getHHMMOneDay() {
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.HOUR_OF_DAY, 0);
		instance.set(Calendar.MINUTE, 0);

		List<String> hhmmList = new ArrayList<String>();
		hhmmList.add(DateUtils.format(instance.getTime(), DateUtils.HHMM));
		for (int i = 0; i < 1439; i++) {
			instance.add(Calendar.MINUTE, 1);
			hhmmList.add(DateUtils.format(instance.getTime(), DateUtils.HHMM));
		}
		return hhmmList;
	}


}
