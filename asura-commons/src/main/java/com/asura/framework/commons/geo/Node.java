/*
 * Copyright (c) 2017 Asura
 */
package com.asura.framework.commons.geo;

/**
 * <p>坐标正方体范围</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sunzhenlei
 * @version 1.0
 * @date 2015/8/11 14:52
 * @since 1.0
 */
public class Node {

    /**
     * 左上角
     */
    private Point left_top = null;
    /**
     * 右上角
     */
    private Point right_top = null;
    /**
     * 左下角
     */
    private Point left_bottom = null;
    /**
     * 右下角
     */
    private Point right_bottom = null;

    /**
     * 构造器
     *
     * @param left_top
     *         左上角
     * @param right_top
     *         右上角
     * @param left_bottom
     *         左下角
     * @param right_bottom
     *         右下角
     */
    public Node(Point left_top, Point right_top, Point left_bottom, Point right_bottom) {
        this.left_top = left_top;
        this.right_top = right_top;
        this.left_bottom = left_bottom;
        this.right_bottom = right_bottom;
    }

    public Point getLeft_top() {
        return left_top;
    }

    public void setLeft_top(Point left_top) {
        this.left_top = left_top;
    }

    public Point getRight_top() {
        return right_top;
    }

    public void setRight_top(Point right_top) {
        this.right_top = right_top;
    }

    public Point getLeft_bottom() {
        return left_bottom;
    }

    public void setLeft_bottom(Point left_bottom) {
        this.left_bottom = left_bottom;
    }

    public Point getRight_bottom() {
        return right_bottom;
    }

    public void setRight_bottom(Point right_bottom) {
        this.right_bottom = right_bottom;
    }
}
