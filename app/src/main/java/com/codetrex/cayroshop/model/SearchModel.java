package com.codetrex.cayroshop.model;

public class SearchModel {
    private String id;
    private String searchkeyword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchkeyword() {
        return searchkeyword;
    }

    public void setSearchkeyword(String searchkeyword) {
        this.searchkeyword = searchkeyword;
    }

    public SearchModel(String id, String searchkeyword) {
        this.id = id;
        this.searchkeyword = searchkeyword;
    }
}
