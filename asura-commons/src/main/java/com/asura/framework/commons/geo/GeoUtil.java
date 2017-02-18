/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.geo;

/**
 * <p>经纬度计算工具类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2015/8/11 14:47
 * @since 1.0
 */
public class GeoUtil {

    /**
     * 地球半径
     */
    private static final double EARTH_RADIUS = 6378137;

    /**
     * 私有化构造
     */
    private GeoUtil() {

    }

    /**
     * 获取给定经纬度，计算半径范围内矩形的四个顶点经纬度
     *
     * @param distance
     *         距离半径（单位：米）
     * @param lat
     *         纬度
     * @param lng
     *         经度
     *
     * @return 矩形的四个顶点经纬度
     */
    private static Node getRectangleNode(double distance, double lat, double lng) {

        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(lat));
        dlng = Math.toDegrees(dlng);

        double dlat = distance / EARTH_RADIUS;
        dlat = Math.toDegrees(dlat); // # 弧度转换成角度

        Point left_top = new Point(lat + dlat, lng - dlng);
        Point right_top = new Point(lat + dlat, lng + dlng);
        Point left_bottom = new Point(lat - dlat, lng - dlng);
        Point right_bottom = new Point(lat - dlat, lng + dlng);

        Node node = new Node(left_top, right_top, left_bottom, right_bottom);
        return node;
    }

    public static double hav(double theta) {
        double s = Math.sin(theta / 2);
        return s * s;
    }

    /**
     * 计算给定两个经纬度计算距离
     *
     * @param lat0
     *         纬度1
     * @param lng0
     *         经度1
     * @param lat1
     *         纬度2
     * @param lng1
     *         经度2
     *
     * @return 两点距离
     */
    public static double getDistance(double lat0, double lng0, double lat1, double lng1) {
        lat0 = Math.toRadians(lat0);
        lat1 = Math.toRadians(lat1);
        lng0 = Math.toRadians(lng0);
        lng1 = Math.toRadians(lng1);

        double dlng = Math.abs(lng0 - lng1);
        double dlat = Math.abs(lat0 - lat1);
        double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));

        return distance;
    }

}
