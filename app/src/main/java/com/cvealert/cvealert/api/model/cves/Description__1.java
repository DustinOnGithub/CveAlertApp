
package com.cvealert.cvealert.api.model.cves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Description__1 {

    @SerializedName("description_data")
    @Expose
    private List<DescriptionDatum> descriptionData = null;

    public List<DescriptionDatum> getDescriptionData() {
        return descriptionData;
    }

    public void setDescriptionData(List<DescriptionDatum> descriptionData) {
        this.descriptionData = descriptionData;
    }

}
