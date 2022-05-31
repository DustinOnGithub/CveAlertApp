
package com.cvealert.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Result {

    @SerializedName("CVE_data_type")
    @Expose
    private String cVEDataType;
    @SerializedName("CVE_data_format")
    @Expose
    private String cVEDataFormat;
    @SerializedName("CVE_data_version")
    @Expose
    private String cVEDataVersion;
    @SerializedName("CVE_data_timestamp")
    @Expose
    private String cVEDataTimestamp;
    @SerializedName("CVE_Items")
    @Expose
    private List<CVEItem> cVEItems = null;

    public String getCVEDataType() {
        return cVEDataType;
    }

    public void setCVEDataType(String cVEDataType) {
        this.cVEDataType = cVEDataType;
    }

    public String getCVEDataFormat() {
        return cVEDataFormat;
    }

    public void setCVEDataFormat(String cVEDataFormat) {
        this.cVEDataFormat = cVEDataFormat;
    }

    public String getCVEDataVersion() {
        return cVEDataVersion;
    }

    public void setCVEDataVersion(String cVEDataVersion) {
        this.cVEDataVersion = cVEDataVersion;
    }

    public String getCVEDataTimestamp() {
        return cVEDataTimestamp;
    }

    public void setCVEDataTimestamp(String cVEDataTimestamp) {
        this.cVEDataTimestamp = cVEDataTimestamp;
    }

    public List<CVEItem> getCVEItems() {
        return cVEItems;
    }

    public void setCVEItems(List<CVEItem> cVEItems) {
        this.cVEItems = cVEItems;
    }

}
