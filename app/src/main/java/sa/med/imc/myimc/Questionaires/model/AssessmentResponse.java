package sa.med.imc.myimc.Questionaires.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AssessmentResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("assessmentId")
    @Expose
    private String assessmentId;
    @SerializedName("assessment")
    @Expose
    private AssessmentModel assessments = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public AssessmentModel getAssessments() {
        return assessments;
    }

    public void setAssessments(AssessmentModel assessments) {
        this.assessments = assessments;
    }
}
