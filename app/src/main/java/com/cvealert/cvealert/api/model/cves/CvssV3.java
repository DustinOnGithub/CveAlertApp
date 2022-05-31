
package com.cvealert.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CvssV3 {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("vectorString")
    @Expose
    private String vectorString;
    @SerializedName("attackVector")
    @Expose
    private String attackVector;
    @SerializedName("attackComplexity")
    @Expose
    private String attackComplexity;
    @SerializedName("privilegesRequired")
    @Expose
    private String privilegesRequired;
    @SerializedName("userInteraction")
    @Expose
    private String userInteraction;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("confidentialityImpact")
    @Expose
    private String confidentialityImpact;
    @SerializedName("integrityImpact")
    @Expose
    private String integrityImpact;
    @SerializedName("availabilityImpact")
    @Expose
    private String availabilityImpact;
    @SerializedName("baseScore")
    @Expose
    private Double baseScore;
    @SerializedName("baseSeverity")
    @Expose
    private String baseSeverity;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVectorString() {
        return vectorString;
    }

    public void setVectorString(String vectorString) {
        this.vectorString = vectorString;
    }

    public String getAttackVector() {
        return attackVector;
    }

    public void setAttackVector(String attackVector) {
        this.attackVector = attackVector;
    }

    public String getAttackComplexity() {
        return attackComplexity;
    }

    public void setAttackComplexity(String attackComplexity) {
        this.attackComplexity = attackComplexity;
    }

    public String getPrivilegesRequired() {
        return privilegesRequired;
    }

    public void setPrivilegesRequired(String privilegesRequired) {
        this.privilegesRequired = privilegesRequired;
    }

    public String getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(String userInteraction) {
        this.userInteraction = userInteraction;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getConfidentialityImpact() {
        return confidentialityImpact;
    }

    public void setConfidentialityImpact(String confidentialityImpact) {
        this.confidentialityImpact = confidentialityImpact;
    }

    public String getIntegrityImpact() {
        return integrityImpact;
    }

    public void setIntegrityImpact(String integrityImpact) {
        this.integrityImpact = integrityImpact;
    }

    public String getAvailabilityImpact() {
        return availabilityImpact;
    }

    public void setAvailabilityImpact(String availabilityImpact) {
        this.availabilityImpact = availabilityImpact;
    }

    public Double getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(Double baseScore) {
        this.baseScore = baseScore;
    }

    public String getBaseSeverity() {
        return baseSeverity;
    }

    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

}
