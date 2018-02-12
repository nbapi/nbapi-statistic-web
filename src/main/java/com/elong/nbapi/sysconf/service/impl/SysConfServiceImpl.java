package com.elong.nbapi.sysconf.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.elong.nbapi.common.dao.IBaseDao;
import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.Dimension;
import com.elong.nbapi.common.po.DimensionMetricRelation;
import com.elong.nbapi.common.po.Metric;
import com.elong.nbapi.common.po.Module;
import com.elong.nbapi.sysconf.service.SysConfService;

@Service
public class SysConfServiceImpl implements SysConfService {

    @Autowired
    private IBaseDao baseDao;

    public List<BusinessSystem> getSystemList() {
        return baseDao.findAll(BusinessSystem.class);
    }

    public void deleteSystemById(BusinessSystem system) {

        Criteria criteria = Criteria.where(Module.FIELD_SYSTEMID).is(system.getId());
        Query modules = Query.query(criteria);
        List<Module> moduleList = baseDao.find(modules, Module.class);

        Set<String> moduleIdSet = new HashSet<String>();
        for (Module module : moduleList) {
            moduleIdSet.add(module.getId());
        }

        Criteria dimensions = Criteria.where(Dimension.FIELD_MODULE_ID).in(moduleIdSet);
        Query dimensionQuery = Query.query(dimensions);
        List<Dimension> dimensionList = baseDao.find(dimensionQuery, Dimension.class);
        Set<String> dimensionIdSet = new HashSet<String>();
        for (Dimension dimen : dimensionList) {
            dimensionIdSet.add(dimen.getId());
        }

        // 删除dimension metric的关联关系
        Query metricRelations = Query.query(Criteria.where(
                DimensionMetricRelation.FIELD_DIMENSION_ID).in(dimensionIdSet));
        baseDao.remove(metricRelations, DimensionMetricRelation.class);
        
        // 删除dimension
        baseDao.remove(dimensionQuery, Dimension.class);
        
        // 删除关联的module
        baseDao.remove(Query.query(criteria), Module.class);

        // 删除
        baseDao.remove(system);
    }

    public void addSystem(BusinessSystem system) {
        baseDao.save(system);
    }

    public List<Module> getModuleListBySystemId(BusinessSystem system) {
        Criteria criteria = Criteria.where(Module.FIELD_SYSTEMID).is(system.getId());

        return baseDao.find(Query.query(criteria), Module.class);
    }

    public BusinessSystem getBusinessSystemById(BusinessSystem system) {
        Criteria criteria = Criteria.where(BusinessSystem.FIELD_ID).is(system.getId());
        Query query = Query.query(criteria);

        return baseDao.findOne(query, BusinessSystem.class);
    }

    public void addModule(Module module) {
        baseDao.save(module);
    }

    public Module getModuleByModuleId(Module module) {
        Criteria criteria = Criteria.where(Module.FIELD_ID).is(module.getId());
        Query query = Query.query(criteria);

        return baseDao.findOne(query, Module.class);
    }

    public List<Dimension> getDimensionListByModuleId(Module module) {
        Criteria criteria = Criteria.where(Dimension.FIELD_MODULE_ID).is(module.getId());
        Query query = Query.query(criteria);

        return baseDao.find(query, Dimension.class);
    }

    public void addDimension(Dimension dimension) {
        baseDao.save(dimension);
    }

    public void deleteDimensionByDimensionId(Dimension dimension) {
        baseDao.remove(dimension);
        
        // 删除和度量值关联关系
        Criteria criteria = Criteria.where(DimensionMetricRelation.FIELD_DIMENSION_ID).is(dimension.getId());
        Query query = Query.query(criteria);
        baseDao.remove(query, DimensionMetricRelation.class);
        
    }

    public void deleteModuleByModuleId(Module module) {
        Criteria criteria = Criteria.where(Dimension.FIELD_MODULE_ID).is(module.getId());
        Query query = Query.query(criteria);

        Set<String> dimensionSet = new HashSet<String>();
        List<Dimension> dimensionList = baseDao.find(query, Dimension.class);
        for (Dimension item : dimensionList) {
            dimensionSet.add(item.getId());
        }

        // 删除和dimension关联的 metric
        Criteria inCriteeria = Criteria.where(DimensionMetricRelation.FIELD_DIMENSION_ID).in(
                dimensionSet);
        baseDao.remove(Query.query(inCriteeria), DimensionMetricRelation.class);

        // 删除 dimension
        baseDao.remove(query, Dimension.class);

        // 删除module本身
        baseDao.remove(module);
    }

    public List<Metric> findMetricList() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, Metric.FIELD_ORDER));
        return baseDao.find(query, Metric.class);
    }

    public void deleteMetricById(Metric metric) {
        Criteria criteria = Criteria.where(Metric.FIELD_ID).is(metric.getId());
        baseDao.deleteOne(Query.query(criteria), Metric.class);
        baseDao.remove(Query.query(criteria), DimensionMetricRelation.class);
    }

    public void addMetric(Metric metric) {
        baseDao.save(metric);
    }

    public void updateMetric(Metric metric) {
        Criteria criteria = Criteria.where(Metric.FIELD_ID).is(metric.getId());
        Metric findOne = baseDao.findOne(Query.query(criteria), Metric.class);

        if (StringUtils.isNotEmpty(metric.getDisplayName())) {
            findOne.setDisplayName(metric.getDisplayName());
        }

        if (StringUtils.isNotEmpty(metric.getFormula())) {
            findOne.setFormula(metric.getFormula());
        }

        if (StringUtils.isNotEmpty(metric.getOrder())) {
            findOne.setOrder(metric.getOrder());
        }

        baseDao.save(findOne);
    }

    public List<DimensionMetricRelation> getDimensionMetricRelations(
            DimensionMetricRelation relation) {
        Criteria criteria = Criteria.where(DimensionMetricRelation.FIELD_DIMENSION_ID).is(
                relation.getDimensionId());
        return baseDao.find(Query.query(criteria), DimensionMetricRelation.class);
    }

    public void updateDimensionMetriRelation(DimensionMetricRelation relation) {
        Criteria criteria = Criteria.where(DimensionMetricRelation.FIELD_DIMENSION_ID).is(
                relation.getDimensionId());
        Query query = Query.query(criteria);

        baseDao.remove(query, DimensionMetricRelation.class);

        for (String metricId : relation.getMetricIds()) {

            DimensionMetricRelation item = new DimensionMetricRelation();
            item.setDimensionId(relation.getDimensionId());

            item.setMetricId(metricId);

            baseDao.save(item);
        }

    }
    
    public void updateBussinessSystem(BusinessSystem system) {
        Criteria criteria = Criteria.where(BusinessSystem.FIELD_ID).is(system.getId());
        BusinessSystem findOne = baseDao.findOne(Query.query(criteria), BusinessSystem.class);

        if (StringUtils.isNotEmpty(system.getSystemName())) {
            findOne.setSystemName(system.getSystemName());
        }

        if (StringUtils.isNotEmpty(system.getBusinessLine())) {
            findOne.setBusinessLine(system.getBusinessLine());
        }

        if (StringUtils.isNotEmpty(system.getContactName())) {
            findOne.setContactName(system.getContactName());
        }
        
        if (StringUtils.isNotEmpty(system.getDepartment())) {
            findOne.setDepartment(system.getDepartment());
        }

        baseDao.save(findOne);
    }
    
    public void updateModule(Module module) {
        Criteria criteria = Criteria.where(Module.FIELD_ID).is(module.getId());
        Module findOne = baseDao.findOne(Query.query(criteria), Module.class);

        if (StringUtils.isNotEmpty(module.getItemName())) {
            findOne.setItemName(module.getItemName());
        }

        if (StringUtils.isNotEmpty(module.getBusinessType())) {
            findOne.setBusinessType(module.getBusinessType());
        }

        baseDao.save(findOne);
    }
    
    public void updateDimension(Dimension dimension) {
        Criteria criteria = Criteria.where(Dimension.FIELD_ID).is(dimension.getId());
        Dimension findOne = baseDao.findOne(Query.query(criteria), Dimension.class);
        
        if(StringUtils.isNotEmpty(dimension.getLocalName())) {
            findOne.setLocalName(dimension.getLocalName());
        }
        
        if (StringUtils.isNotEmpty(dimension.getDimensionCount())) {
            findOne.setDimensionCount(dimension.getDimensionCount());
        }

        if (StringUtils.isNotEmpty(dimension.getDimensionName()
                )) {
            findOne.setDimensionName(dimension.getDimensionName());
        }
        
        if(StringUtils.isNotEmpty(dimension.getDimensionValues())) {
            findOne.setDimensionValues(dimension.getDimensionValues());
        }
        
        if(StringUtils.isNotEmpty(dimension.getDisplayType())) {
            findOne.setDisplayType(dimension.getDisplayType());
        }
        
        if(dimension.getDisplayOrder() != null) {
            findOne.setDisplayOrder(dimension.getDisplayOrder());
        }

        baseDao.save(findOne);
    }
}
