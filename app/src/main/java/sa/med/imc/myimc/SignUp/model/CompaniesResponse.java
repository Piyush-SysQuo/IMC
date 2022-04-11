package sa.med.imc.myimc.SignUp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompaniesResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;

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

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }


    public class Company {

        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("companyName")
        @Expose
        private String companyName;
        @SerializedName("companyShortName")
        @Expose
        private String companyShortName;
        @SerializedName("companyNameAr")
        @Expose
        private String companyNameAr;

        @SerializedName("companyShortNameAr")
        @Expose
        private String companyShortNameAr;
        @SerializedName("debtorType")
        @Expose
        private String debtorType;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyShortName() {
            return companyShortName;
        }

        public void setCompanyShortName(String companyShortName) {
            this.companyShortName = companyShortName;
        }

        public String getCompanyNameAr() {
            return companyNameAr;
        }

        public void setCompanyNameAr(String companyNameAr) {
            this.companyNameAr = companyNameAr;
        }

        public String getCompanyShortNameAr() {
            return companyShortNameAr;
        }

        public void setCompanyShortNameAr(String companyShortNameAr) {
            this.companyShortNameAr = companyShortNameAr;
        }

        public String getDebtorType() {
            return debtorType;
        }

        public void setDebtorType(String debtorType) {
            this.debtorType = debtorType;
        }

    }

}
