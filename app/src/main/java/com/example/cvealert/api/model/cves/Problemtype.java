
package com.example.cvealert.api.model.cves;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Problemtype {

    @SerializedName("problemtype_data")
    @Expose
    private List<ProblemtypeDatum> problemtypeData = null;

    public List<ProblemtypeDatum> getProblemtypeData() {
        return problemtypeData;
    }

    public void setProblemtypeData(List<ProblemtypeDatum> problemtypeData) {
        this.problemtypeData = problemtypeData;
    }

}
