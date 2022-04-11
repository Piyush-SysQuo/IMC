package sa.med.imc.myimc.Settings.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportTypeResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("reports")
    @Expose
    private List<Reports> reports = null;

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

    public List<Reports> getReports() {
        return reports;
    }

    public void setReports(List<Reports> data) {
        this.reports = data;
    }


    public class Reports {

        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("reportType")
        @Expose
        private String reportType;
        @SerializedName("isTranslaionReport")
        @Expose
        private String isTranslaionReport;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getIsTranslaionReport() {
            return isTranslaionReport;
        }

        public void setIsTranslaionReport(String isTranslaionReport) {
            this.isTranslaionReport = isTranslaionReport;
        }
    }


}
