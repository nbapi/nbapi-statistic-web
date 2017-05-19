package com.elong.nbapi.statistic.po;

import java.util.List;

/**
 * Created by minji on 16/2/25.
 */
public class TotalItemEntity {


  public TotalItemEntity()
  {
    //this.totalDataEntityList=new ArrayList<>();
  }
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<TotalDataEntity> getTotalDataEntityList() {
    return totalDataEntityList;
  }

  public void setTotalDataEntityList(List<TotalDataEntity> totalDataEntityList) {
    this.totalDataEntityList = totalDataEntityList;
  }

  private String name;
  private String type;
  private List<TotalDataEntity> totalDataEntityList;
}
