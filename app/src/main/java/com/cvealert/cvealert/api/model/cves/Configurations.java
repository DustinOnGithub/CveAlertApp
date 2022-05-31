
package com.cvealert.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Configurations {

    @SerializedName("CVE_data_version")
    @Expose
    private String cVEDataVersion;
    @SerializedName("nodes")
    @Expose
    private List<Node> nodes = null;

    public String getCVEDataVersion() {
        return cVEDataVersion;
    }

    public void setCVEDataVersion(String cVEDataVersion) {
        this.cVEDataVersion = cVEDataVersion;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

}
