package com.elong.nbapi.statistic.po;

/**
 * Created by minji on 16/2/25.
 */
public class TotalDataEntity implements Comparable<TotalDataEntity> {

  public String getDateTime() {
    return dateTime;
  }

  public TotalDataEntity setDateTime(String dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public String getDisplayTime() {
    return displayTime;
  }

  public TotalDataEntity setDisplayTime(String displayTime) {
    this.displayTime = displayTime;
    return this;
  }

  public int getTotalValue() {
    return totalValue;
  }

  public TotalDataEntity setTotalValue(int totalValue) {
    this.totalValue = totalValue;
    return this;
  }

  public String getContext() {
    return context;
  }

  public void setContext(String special) {
    this.context = special;
  }

  public String getDimensionValue() {
    return dimensionValue;
  }

  public TotalDataEntity setDimensionValue(String dimensionValue) {
    this.dimensionValue = dimensionValue;
    return this;
  }

  private String dateTime;
  private String displayTime;
  private int totalValue;
  private String context;
  private String dimensionValue;

  @Override public int compareTo(TotalDataEntity o) {

    return this.dateTime.compareTo(o.getDateTime());
  }
}
