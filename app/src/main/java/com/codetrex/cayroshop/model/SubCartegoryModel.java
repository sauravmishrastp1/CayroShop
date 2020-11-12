package com.codetrex.cayroshop.model;

public class SubCartegoryModel {
    private String subcatid;
    private String cat_id;
    private String subcat_name;
    private String status;

    public String getSubcatid() {
        return subcatid;
    }

    public void setSubcatid(String subcatid) {
        this.subcatid = subcatid;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubCartegoryModel(String subcatid, String cat_id, String subcat_name, String status) {
        this.subcatid = subcatid;
        this.cat_id = cat_id;
        this.subcat_name = subcat_name;
        this.status = status;
    }
}
