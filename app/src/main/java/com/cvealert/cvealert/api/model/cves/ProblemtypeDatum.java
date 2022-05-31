
package com.cvealert.cvealert.api.model.cves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ProblemtypeDatum {

    @SerializedName("description")
    @Expose
    private List<Description> description = null;

    public List<Description> getDescription() {
        return description;
    }

    public void setDescription(List<Description> description) {
        this.description = description;
    }

}
