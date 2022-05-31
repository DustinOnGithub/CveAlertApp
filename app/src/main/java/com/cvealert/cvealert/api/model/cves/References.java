
package com.cvealert.cvealert.api.model.cves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

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
