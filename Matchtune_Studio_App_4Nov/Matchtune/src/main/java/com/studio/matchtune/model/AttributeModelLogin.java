package com.studio.matchtune.model;

public class AttributeModelLogin {

    String  UNIXTIME;
    String UUID;
    String APPId;
    Boolean tos;
    String signature;


    public String getUNIXTIME() {
        return UNIXTIME;
    }

    public void setUNIXTIME(String UNIXTIME) {
        this.UNIXTIME = UNIXTIME;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getAPPId() {
        return APPId;
    }

    public void setAPPId(String APPId) {
        this.APPId = APPId;
    }

    public Boolean getTos() {
        return tos;
    }

    public void setTos(Boolean tos) {
        this.tos = tos;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
