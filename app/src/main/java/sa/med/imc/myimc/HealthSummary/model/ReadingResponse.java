package sa.med.imc.myimc.HealthSummary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadingResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_pages")
    @Expose
    private String total_pages;
    @SerializedName("total_items")
    @Expose
    private String total_items;
    @SerializedName("data")
    @Expose
    private List<ReadingModel> readingModels = null;

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

    public List<ReadingModel> getReadingModels() {
        return readingModels;
    }

    public void setReadingModels(List<ReadingModel> readingModels) {
        this.readingModels = readingModels;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_items() {
        return total_items;
    }

    public void setTotal_items(String total_items) {
        this.total_items = total_items;
    }

    public class ReadingModel {

        @SerializedName("readingId")
        @Expose
        private Integer readingId;
        @SerializedName("patId")
        @Expose
        private String patId;
        @SerializedName("episodeNo")
        @Expose
        private Integer episodeNo;
        @SerializedName("height")
        @Expose
        private Double height;
        @SerializedName("weight")
        @Expose
        private Double weight;
        @SerializedName("bodyTempture")
        @Expose
        private Double bodyTempture;
        @SerializedName("bloodPressure")
        @Expose
        private String bloodPressure;
        @SerializedName("respiratoryRate")
        @Expose
        private String respiratoryRate;
        @SerializedName("pluseRate")
        @Expose
        private String pluseRate;
        @SerializedName("oxSaturation")
        @Expose
        private String oxSaturation;
        @SerializedName("notes")
        @Expose
        private String notes;
        @SerializedName("readingDate")
        @Expose
        private String readingDate;
        @SerializedName("ibw")
        @Expose
        private Double ibw;
        @SerializedName("bsa")
        @Expose
        private Double bsa;
        @SerializedName("bee")
        @Expose
        private Double bee;
        @SerializedName("bmi")
        @Expose
        private Double bmi;
        @SerializedName("bloodGlucose")
        @Expose
        private Double bloodGlucose;
        @SerializedName("epsiodeLocationEn")
        @Expose
        private String epsiodeLocationEn;
        @SerializedName("epsiodeLocationAr")
        @Expose
        private String epsiodeLocationAr;

        public Integer getReadingId() {
            return readingId;
        }

        public void setReadingId(Integer readingId) {
            this.readingId = readingId;
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


        public Double getBodyTempture() {
            return bodyTempture;
        }

        public void setBodyTempture(Double bodyTempture) {
            this.bodyTempture = bodyTempture;
        }

        public String getBloodPressure() {
            return bloodPressure;
        }

        public void setBloodPressure(String bloodPressure) {
            this.bloodPressure = bloodPressure;
        }

        public String getRespiratoryRate() {
            return respiratoryRate;
        }

        public void setRespiratoryRate(String respiratoryRate) {
            this.respiratoryRate = respiratoryRate;
        }

        public String getPluseRate() {
            return pluseRate;
        }

        public void setPluseRate(String pluseRate) {
            this.pluseRate = pluseRate;
        }

        public String getOxSaturation() {
            return oxSaturation;
        }

        public void setOxSaturation(String oxSaturation) {
            this.oxSaturation = oxSaturation;
        }


        public String getReadingDate() {
            return readingDate;
        }

        public void setReadingDate(String readingDate) {
            this.readingDate = readingDate;
        }

        public Double getBloodGlucose() {
            return bloodGlucose;
        }

        public void setBloodGlucose(Double bloodGlucose) {
            this.bloodGlucose = bloodGlucose;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public Double getIbw() {
            return ibw;
        }

        public void setIbw(Double ibw) {
            this.ibw = ibw;
        }

        public Double getBsa() {
            return bsa;
        }

        public void setBsa(Double bsa) {
            this.bsa = bsa;
        }

        public Double getBee() {
            return bee;
        }

        public void setBee(Double bee) {
            this.bee = bee;
        }

        public Double getBmi() {
            return bmi;
        }

        public void setBmi(Double bmi) {
            this.bmi = bmi;
        }

        public String getEpsiodeLocationEn() {
            return epsiodeLocationEn;
        }

        public void setEpsiodeLocationEn(String epsiodeLocationEn) {
            this.epsiodeLocationEn = epsiodeLocationEn;
        }

        public String getEpsiodeLocationAr() {
            return epsiodeLocationAr;
        }

        public void setEpsiodeLocationAr(String epsiodeLocationAr) {
            this.epsiodeLocationAr = epsiodeLocationAr;
        }
    }
}
