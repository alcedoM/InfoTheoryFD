package com.ITFD.components;

public class Result {
    private Object[][] tableData;
    private double l;
    private double h;
    private double e;

    public Result(Object[][] tableData, double l, double h,double e) {
        this.tableData = tableData;
        this.l = l;
        this.h = h;
        this.e = e;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public Object[][] getTableData() {
        return tableData;
    }

    public void setTableData(Object[][] tableData) {
        this.tableData = tableData;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
