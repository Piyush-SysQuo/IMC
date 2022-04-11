package sa.med.imc.myimc.Managebookings.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FormIdNameModel implements Serializable {
    @SerializedName("formId")
    @Expose
    private String formId;
    @SerializedName("formName")
    @Expose
    private String formName;
    @SerializedName("assessmentId")
    @Expose
    private String assessmentId;


    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }
}
