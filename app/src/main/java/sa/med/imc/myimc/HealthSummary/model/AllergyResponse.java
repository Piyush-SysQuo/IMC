package sa.med.imc.myimc.HealthSummary.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllergyResponse {

    public class AllergyModel {

        @SerializedName("patientAlertId")
        @Expose
        private Integer patientAlertId;
        @SerializedName("fullDescp")
        @Expose
        private String fullDescp;
        @SerializedName("alertDescp")
        @Expose
        private String alertDescp;
        @SerializedName("alertType")
        @Expose
        private String alertType;
        @SerializedName("alertDisplay")
        @Expose
        private String alertDisplay;
        @SerializedName("alertNotes")
        @Expose
        private Object alertNotes;
        @SerializedName("alertOnSiteDate")
        @Expose
        private String alertOnSiteDate;
        @SerializedName("patId")
        @Expose
        private String patId;
        @SerializedName("episodeNo")
        @Expose
        private Integer episodeNo;
        @SerializedName("episodeType")
        @Expose
        private String episodeType;
        @SerializedName("episodeConsultant")
        @Expose
        private String episodeConsultant;
        @SerializedName("episodeClinicLocation")
        @Expose
        private String episodeClinicLocation;

        public Integer getPatientAlertId() {
            return patientAlertId;
        }

        public void setPatientAlertId(Integer patientAlertId) {
            this.patientAlertId = patientAlertId;
        }

        public String getFullDescp() {
            return fullDescp;
        }

        public void setFullDescp(String fullDescp) {
            this.fullDescp = fullDescp;
        }

        public String getAlertDescp() {
            return alertDescp;
        }

        public void setAlertDescp(String alertDescp) {
            this.alertDescp = alertDescp;
        }

        public String getAlertType() {
            return alertType;
        }

        public void setAlertType(String alertType) {
            this.alertType = alertType;
        }

        public String getAlertDisplay() {
            return alertDisplay;
        }

        public void setAlertDisplay(String alertDisplay) {
            this.alertDisplay = alertDisplay;
        }

        public Object getAlertNotes() {
            return alertNotes;
        }

        public void setAlertNotes(Object alertNotes) {
            this.alertNotes = alertNotes;
        }

        public String getAlertOnSiteDate() {
            return alertOnSiteDate;
        }

        public void setAlertOnSiteDate(String alertOnSiteDate) {
            this.alertOnSiteDate = alertOnSiteDate;
        }

        public String getPatId() {
            return patId;
        }

        public void setPatId(String patId) {
            this.patId = patId;
        }

        public Integer getEpisodeNo() {
            return episodeNo;
        }

        public void setEpisodeNo(Integer episodeNo) {
            this.episodeNo = episodeNo;
        }

        public String getEpisodeType() {
            return episodeType;
        }

        public void setEpisodeType(String episodeType) {
            this.episodeType = episodeType;
        }

        public String getEpisodeConsultant() {
            return episodeConsultant;
        }

        public void setEpisodeConsultant(String episodeConsultant) {
            this.episodeConsultant = episodeConsultant;
        }

        public String getEpisodeClinicLocation() {
            return episodeClinicLocation;
        }

        public void setEpisodeClinicLocation(String episodeClinicLocation) {
            this.episodeClinicLocation = episodeClinicLocation;
        }

    }

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<AllergyModel> data = null;

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

        public List<AllergyModel> getData() {
            return data;
        }

        public void setData(List<AllergyModel> data) {
            this.data = data;
        }

}
