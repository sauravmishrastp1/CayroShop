package com.codetrex.cayroshop.model;

public class Category_Model {
    private String cat_id;
    private String cat_name;
    private String group_id;
    private String status;

    public Category_Model(String cat_id, String cat_name, String group_id, String status) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.group_id = group_id;
        this.status = status;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
