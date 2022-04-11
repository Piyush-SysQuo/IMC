package sa.med.imc.myimc.Questionaires.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResultResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("score")
    @Expose
    private ResultModel ResultModel = null;


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

    public sa.med.imc.myimc.Questionaires.model.ResultModel getResultModel() {
        return ResultModel;
    }

    public void setResultModel(sa.med.imc.myimc.Questionaires.model.ResultModel resultModel) {
        ResultModel = resultModel;
    }
}
