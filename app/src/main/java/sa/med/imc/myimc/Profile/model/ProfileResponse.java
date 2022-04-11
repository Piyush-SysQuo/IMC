package sa.med.imc.myimc.Profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import sa.med.imc.myimc.Login.Validate.DataModel.ValidateResponse;

public class ProfileResponse implements Serializable {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("depandant")
    @Expose
    private List<ValidateResponse.UserDetails> depandant;

    @SerializedName("userDetails")
    @Expose
    private ValidateResponse.UserDetails userDetails;

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

    public ValidateResponse.UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(ValidateResponse.UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<ValidateResponse.UserDetails> getDepandant() {
        return depandant;
    }

    public void setDepandant(List<ValidateResponse.UserDetails> depandant) {
        this.depandant = depandant;
    }
}
