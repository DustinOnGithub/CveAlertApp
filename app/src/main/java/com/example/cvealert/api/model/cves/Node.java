
package com.example.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Node {

    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("children")
    @Expose
    private List<Child> children = null;
    @SerializedName("cpe_match")
    @Expose
    private List<Object> cpeMatch = null;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<Object> getCpeMatch() {
        return cpeMatch;
    }

    public void setCpeMatch(List<Object> cpeMatch) {
        this.cpeMatch = cpeMatch;
    }

}
