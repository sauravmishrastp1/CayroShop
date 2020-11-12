package com.codetrex.cayroshop.model;

public class LoginUser {
    private String clientid;
    private String accescode;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getAccescode() {
        return accescode;
    }

    public void setAccescode(String accescode) {
        this.accescode = accescode;
    }

    public LoginUser(String clientid, String accescode) {
        this.clientid = clientid;
        this.accescode = accescode;
    }


}
