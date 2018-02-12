package com.elong.nbapi.sysconf.service;

import java.util.List;

import com.elong.nbapi.common.po.BusinessSystem;
import com.elong.nbapi.common.po.Dimension;
import com.elong.nbapi.common.po.DimensionMetricRelation;
import com.elong.nbapi.common.po.Metric;
import com.elong.nbapi.common.po.Module;

public interface SysConfService {

    public List<BusinessSystem> getSystemList();

    public void deleteSystemById(BusinessSystem system);

    public void addSystem(BusinessSystem system);

    public List<Module> getModuleListBySystemId(BusinessSystem system);

    public BusinessSystem getBusinessSystemById(BusinessSystem system);

    public void addModule(Module module);

    public Module getModuleByModuleId(Module module);

    public List<Dimension> getDimensionListByModuleId(Module module);

    public void addDimension(Dimension dimension);

    public void deleteDimensionByDimensionId(Dimension dimension);

    public void deleteModuleByModuleId(Module module);

    public List<Metric> findMetricList();

    public void deleteMetricById(Metric metric);

    public void addMetric(Metric metric);

    public void updateMetric(Metric metric);

    public List<DimensionMetricRelation> getDimensionMetricRelations(
            DimensionMetricRelation relation);

    public void updateDimensionMetriRelation(DimensionMetricRelation relation);
    
    public void updateBussinessSystem(BusinessSystem system);
    
    public void updateModule(Module module);
    
    public void updateDimension(Dimension dimension);
}
