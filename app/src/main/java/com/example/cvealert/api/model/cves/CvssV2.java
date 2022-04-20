
package com.example.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CvssV2 {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("vectorString")
    @Expose
    private String vectorString;
    @SerializedName("accessVector")
    @Expose
    private String accessVector;
    @SerializedName("accessComplexity")
    @Expose
    private String accessComplexity;
    @SerializedName("authentication")
    @Expose
    private String authentication;
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

    public String getAccessVector() {
        return accessVector;
    }

    public void setAccessVector(String accessVector) {
        this.accessVector = accessVector;
    }

    public String getAccessComplexity() {
        return accessComplexity;
    }

    public void setAccessComplexity(String accessComplexity) {
        this.accessComplexity = accessComplexity;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
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

}
