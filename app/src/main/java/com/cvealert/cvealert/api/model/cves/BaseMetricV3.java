
package com.cvealert.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BaseMetricV3 {

    @SerializedName("cvssV3")
    @Expose
    private CvssV3 cvssV3;
    @SerializedName("exploitabilityScore")
    @Expose
    private Double exploitabilityScore;
    @SerializedName("impactScore")
    @Expose
    private Double impactScore;

    public CvssV3 getCvssV3() {
        return cvssV3;
    }

    public void setCvssV3(CvssV3 cvssV3) {
        this.cvssV3 = cvssV3;
    }

    public Double getExploitabilityScore() {
        return exploitabilityScore;
    }

    public void setExploitabilityScore(Double exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    public Double getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(Double impactScore) {
        this.impactScore = impactScore;
    }

}
