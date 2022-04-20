
package com.example.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class BaseMetricV2 {

    @SerializedName("cvssV2")
    @Expose
    private CvssV2 cvssV2;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("exploitabilityScore")
    @Expose
    private Double exploitabilityScore;
    @SerializedName("impactScore")
    @Expose
    private Double impactScore;
    @SerializedName("acInsufInfo")
    @Expose
    private Boolean acInsufInfo;
    @SerializedName("obtainAllPrivilege")
    @Expose
    private Boolean obtainAllPrivilege;
    @SerializedName("obtainUserPrivilege")
    @Expose
    private Boolean obtainUserPrivilege;
    @SerializedName("obtainOtherPrivilege")
    @Expose
    private Boolean obtainOtherPrivilege;
    @SerializedName("userInteractionRequired")
    @Expose
    private Boolean userInteractionRequired;

    public CvssV2 getCvssV2() {
        return cvssV2;
    }

    public void setCvssV2(CvssV2 cvssV2) {
        this.cvssV2 = cvssV2;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
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

    public Boolean getAcInsufInfo() {
        return acInsufInfo;
    }

    public void setAcInsufInfo(Boolean acInsufInfo) {
        this.acInsufInfo = acInsufInfo;
    }

    public Boolean getObtainAllPrivilege() {
        return obtainAllPrivilege;
    }

    public void setObtainAllPrivilege(Boolean obtainAllPrivilege) {
        this.obtainAllPrivilege = obtainAllPrivilege;
    }

    public Boolean getObtainUserPrivilege() {
        return obtainUserPrivilege;
    }

    public void setObtainUserPrivilege(Boolean obtainUserPrivilege) {
        this.obtainUserPrivilege = obtainUserPrivilege;
    }

    public Boolean getObtainOtherPrivilege() {
        return obtainOtherPrivilege;
    }

    public void setObtainOtherPrivilege(Boolean obtainOtherPrivilege) {
        this.obtainOtherPrivilege = obtainOtherPrivilege;
    }

    public Boolean getUserInteractionRequired() {
        return userInteractionRequired;
    }

    public void setUserInteractionRequired(Boolean userInteractionRequired) {
        this.userInteractionRequired = userInteractionRequired;
    }

}
