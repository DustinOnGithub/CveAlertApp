
package com.example.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Child {

    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("children")
    @Expose
    private List<Object> children = null;
    @SerializedName("cpe_match")
    @Expose
    private List<CpeMatch> cpeMatch = null;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    public List<CpeMatch> getCpeMatch() {
        return cpeMatch;
    }

    public void setCpeMatch(List<CpeMatch> cpeMatch) {
        this.cpeMatch = cpeMatch;
    }

}