package sa.med.imc.myimc.Questionaires.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultModel implements Serializable {
    @SerializedName("UID")
    @Expose
    private String uID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("stdError")
    @Expose
    private String stdError;
    @SerializedName("theta")
    @Expose
    private String theta;
    @SerializedName("T-Score")
    @Expose
    private String tScore;
    @SerializedName("sError")
    @Expose
    private String sError;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Interpretations")
    @Expose
    private List<Object> interpretations = null;

    @SerializedName("Items")
    @Expose
    private List<AssessmentModel.Item> items = null;


    public List<AssessmentModel.Item> getItems() {
        return items;
    }

    public void setItems(List<AssessmentModel.Item> items) {
        this.items = items;
    }

    public String getUID() {
        return uID;
    }

    public void setUID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStdError() {
        return stdError;
    }

    public void setStdError(String stdError) {
        this.stdError = stdError;
    }

    public String getTheta() {
        return theta;
    }

    public void setTheta(String theta) {
        this.theta = theta;
    }

    public String getTScore() {
        return tScore;
    }

    public void setTScore(String tScore) {
        this.tScore = tScore;
    }

    public String getSError() {
        return sError;
    }

    public void setSError(String sError) {
        this.sError = sError;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getInterpretations() {
        return interpretations;
    }

    public void setInterpretations(List<Object> interpretations) {
        this.interpretations = interpretations;
    }


}
