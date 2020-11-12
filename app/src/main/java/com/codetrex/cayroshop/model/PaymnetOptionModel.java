package com.codetrex.cayroshop.model;

public class PaymnetOptionModel {
    private String paymentoption;
    private String option_id;

    public String getPaymentoption() {
        return paymentoption;
    }

    public void setPaymentoption(String paymentoption) {
        this.paymentoption = paymentoption;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public PaymnetOptionModel(String paymentoption, String option_id) {
        this.paymentoption = paymentoption;
        this.option_id = option_id;
    }
}
