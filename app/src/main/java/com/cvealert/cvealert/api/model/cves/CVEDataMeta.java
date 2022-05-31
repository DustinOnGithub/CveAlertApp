
package com.cvealert.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CVEDataMeta {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("ASSIGNER")
    @Expose
    private String assigner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

}
