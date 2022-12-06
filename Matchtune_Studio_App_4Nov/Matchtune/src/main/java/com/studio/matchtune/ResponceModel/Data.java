package com.studio.matchtune.ResponceModel;

import java.util.ArrayList;

public class Data {

    public String type;
    public ArrayList<Attributedata> attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Attributedata> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attributedata> attributes) {
        this.attributes = attributes;
    }
}
