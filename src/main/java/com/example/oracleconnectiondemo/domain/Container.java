package com.example.oracleconnectiondemo.domain;

import java.util.Date;

public class Container {

        String shippingLine;
        String containerNumber;
        String type;
        String length;
        String invoiceCategory;
        String fullOrEmpty;
        String reef;
        String imdg;
        String oog;
        String dmg;
        Date incDate;
        Date outDate;

    public Container() {
    }

    public Container(String shippingLine, String containerNumber, String type, String length, String invoiceCategory, String fullOrEmpty, String reef, String imdg, String oog, String dmg,Date incDate, Date outDate) {
        this.shippingLine = shippingLine;
        this.containerNumber = containerNumber;
        this.type = type;
        this.length = length;
        this.invoiceCategory = invoiceCategory;
        this.fullOrEmpty = fullOrEmpty;
        this.reef = reef;
        this.imdg = imdg;
        this.oog = oog;
        this.dmg = dmg;
        this.incDate = incDate;
        this.outDate = outDate;
    }

    public String getShippingLine() {
        return shippingLine;
    }

    public void setShippingLine(String shippingLine) {
        this.shippingLine = shippingLine;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(String invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public String getFullOrEmpty() {
        return fullOrEmpty;
    }

    public void setFullOrEmpty(String fullOrEmpty) {
        this.fullOrEmpty = fullOrEmpty;
    }

    public String getReef() {
        return reef;
    }

    public void setReef(String reef) {
        this.reef = reef;
    }

    public String getImdg() {
        return imdg;
    }

    public void setImdg(String imdg) {
        this.imdg = imdg;
    }

    public String getOog() {
        return oog;
    }

    public void setOog(String oog) {
        this.oog = oog;
    }

    public String getDmg() {
        return dmg;
    }

    public void setDmg(String dmg) {
        this.dmg = dmg;
    }

    public Date getIncDate() {
        return incDate;
    }

    public void setIncDate(Date incDate) {
        this.incDate = incDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }
}
