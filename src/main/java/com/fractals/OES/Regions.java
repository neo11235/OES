package com.fractals.OES;

public class Regions {
    int regionId;
    String regionName;
    Regions(){

    }
    Regions(int regionId,String regionName)
    {
        this.regionId=regionId;
        this.regionName=regionName;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Regions{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
