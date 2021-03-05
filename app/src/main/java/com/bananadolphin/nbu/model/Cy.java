package com.bananadolphin.nbu.model;

public class Cy {

    private String currencyName;
    private String rate;
    private String cc;
    private String exchangeDate;

    public Cy() {
    }

    public Cy(String currencyName, String rate, String cc, String exchangeDate) {
        this.currencyName = currencyName;
        this.rate = rate;
        this.cc = cc;
        this.exchangeDate = exchangeDate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    @Override
    public String toString() {
        return "Cy{" +
                "currencyName='" + currencyName + '\'' +
                ", rate='" + rate + '\'' +
                ", cc='" + cc + '\'' +
                ", exchangeDate='" + exchangeDate + '\'' +
                '}';
    }
}
