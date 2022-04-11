package sa.med.imc.myimc.Clinics.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_items")
    @Expose
    private String total_items;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("physicians")
    @Expose
    private List<ClinicModel> clinicModels = null;

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

    public List<ClinicModel> getClinicModels() {
        return clinicModels;
    }

    public void setClinicModels(List<ClinicModel> clinicModels) {
        this.clinicModels = clinicModels;
    }

    public String getTotal_items() {
        return total_items;
    }

    public void setTotal_items(String total_items) {
        this.total_items = total_items;
    }

    public class ClinicModel {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("descEn")
        @Expose
        private String descEn;
        @SerializedName("descAr")
        @Expose
        private String descAr;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescEn() {
            return descEn;
        }

        public void setDescEn(String descEn) {
            this.descEn = descEn;
        }

        public String getDescAr() {
            return descAr;
        }

        public void setDescAr(String descAr) {
            this.descAr = descAr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
