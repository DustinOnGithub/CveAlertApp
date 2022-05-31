
package com.cvealert.cvealert.api.model.cves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Cve {

    @SerializedName("data_type")
    @Expose
    private String dataType;
    @SerializedName("data_format")
    @Expose
    private String dataFormat;
    @SerializedName("data_version")
    @Expose
    private String dataVersion;
    @SerializedName("CVE_data_meta")
    @Expose
    private CVEDataMeta cVEDataMeta;
    @SerializedName("problemtype")
    @Expose
    private Problemtype problemtype;
    @SerializedName("references")
    @Expose
    private References references;
    @SerializedName("description")
    @Expose
    private Description__1 description;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public CVEDataMeta getCVEDataMeta() {
        return cVEDataMeta;
    }

    public void setCVEDataMeta(CVEDataMeta cVEDataMeta) {
        this.cVEDataMeta = cVEDataMeta;
    }

    public Problemtype getProblemtype() {
        return problemtype;
    }

    public void setProblemtype(Problemtype problemtype) {
        this.problemtype = problemtype;
    }

    public References getReferences() {
        return references;
    }

    public void setReferences(References references) {
        this.references = references;
    }

    public Description__1 getDescription() {
        return description;
    }

    public void setDescription(Description__1 description) {
        this.description = description;
    }

}
