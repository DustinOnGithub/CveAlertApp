
package com.cvealert.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Impact {

    @SerializedName("baseMetricV3")
    @Expose
    private BaseMetricV3 baseMetricV3;
    @SerializedName("baseMetricV2")
    @Expose
    private BaseMetricV2 baseMetricV2;

    public BaseMetricV3 getBaseMetricV3() {
        return baseMetricV3;
    }

    public void setBaseMetricV3(BaseMetricV3 baseMetricV3) {
        this.baseMetricV3 = baseMetricV3;
    }

    public BaseMetricV2 getBaseMetricV2() {
        return baseMetricV2;
    }

    public void setBaseMetricV2(BaseMetricV2 baseMetricV2) {
        this.baseMetricV2 = baseMetricV2;
    }

}
