
package com.example.cvealert.api.model.cves;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo ")
public class Cves {

    @SerializedName("resultsPerPage")
    @Expose
    private Integer resultsPerPage;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("result")
    @Expose
    private Result result;

    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
