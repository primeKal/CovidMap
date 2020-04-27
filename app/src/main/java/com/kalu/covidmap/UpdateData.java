package com.kalu.covidmap;

public class UpdateData {
   private String confiremed;
   private String recovered;

   private String inland;

    public UpdateData() {
    }

    public UpdateData(String confiremed, String recovered, String inland) {
        this.confiremed = confiremed;
        this.recovered = recovered;
        this.inland = inland;
    }

    public String getConfiremed() {
        return confiremed;
    }

    public void setConfiremed(String confiremed) {
        this.confiremed = confiremed;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getInland() {
        return inland;
    }

    public void setInland(String inland) {
        this.inland = inland;
    }
}
