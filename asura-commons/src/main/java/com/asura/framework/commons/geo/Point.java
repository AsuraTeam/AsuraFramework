/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.geo;

/**
 * <p>经纬度坐标</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2015/8/11 14:49
 * @since 1.0
 */
public class Point {

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 构造器
     *
     * @param latitude
     *         纬度
     * @param longitude
     *         经度
     */
    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
