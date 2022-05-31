
package com.cvealert.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CpeMatch {

    @SerializedName("vulnerable")
    @Expose
    private Boolean vulnerable;
    @SerializedName("cpe23Uri")
    @Expose
    private String cpe23Uri;
    @SerializedName("versionEndExcluding")
    @Expose
    private String versionEndExcluding;
    @SerializedName("cpe_name")
    @Expose
    private List<Object> cpeName = null;

    public Boolean getVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public String getCpe23Uri() {
        return cpe23Uri;
    }

    public void setCpe23Uri(String cpe23Uri) {
        this.cpe23Uri = cpe23Uri;
    }

    public String getVersionEndExcluding() {
        return versionEndExcluding;
    }

    public void setVersionEndExcluding(String versionEndExcluding) {
        this.versionEndExcluding = versionEndExcluding;
    }

    public List<Object> getCpeName() {
        return cpeName;
    }

    public void setCpeName(List<Object> cpeName) {
        this.cpeName = cpeName;
    }

}
