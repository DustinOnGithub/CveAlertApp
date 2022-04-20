
package com.example.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class References {

    @SerializedName("reference_data")
    @Expose
    private List<ReferenceDatum> referenceData = null;

    public List<ReferenceDatum> getReferenceData() {
        return referenceData;
    }

    public void setReferenceData(List<ReferenceDatum> referenceData) {
        this.referenceData = referenceData;
    }

}
