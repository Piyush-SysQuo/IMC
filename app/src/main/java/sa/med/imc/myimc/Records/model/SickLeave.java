package sa.med.imc.myimc.Records.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class SickLeave {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sysCode")
    @Expose
    private String sysCode;
    @SerializedName("staffId")
    @Expose
    private String staffId;
    @SerializedName("leaveType")
    @Expose
    private String leaveType;
    @SerializedName("leaveExpectStartDate")
    @Expose
    private String leaveExpectStartDate;
    @SerializedName("leaveExpectReturnDate")
    @Expose
    private String leaveExpectReturnDate;
    @SerializedName("enteredByName")
    @Expose
    private String enteredByName;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("leaveActualLeaveDate")
    @Expose
    private String leaveActualLeaveDate;
    @SerializedName("leaveActualReturnDate")
    @Expose
    private String leaveActualReturnDate;
    @SerializedName("leaveNotes")
    @Expose
    private String leaveNotes;
    @SerializedName("leaveStatus")
    @Expose
    private String leaveStatus;
    @SerializedName("amendBy")
    @Expose
    private String amendBy;
    @SerializedName("amendDate")
    @Expose
    private String amendDate;
    @SerializedName("hosp")
    @Expose
    private Integer hosp;
    @SerializedName("episodeNo")
    @Expose
    private Integer episodeNo;
    @SerializedName("episodeType")
    @Expose
    private String episodeType;
    @SerializedName("episodeConsultant")
    @Expose
    private String episodeConsultant;
    @SerializedName("episodeDate")
    @Expose
    private String episodeDate;
    @SerializedName("episodeLocation")
    @Expose
    private String episodeLocation;
    @SerializedName("sickLeaveDayFlag")
    @Expose
    private String sickLeaveDayFlag;
    @SerializedName("followupFlag")
    @Expose
    private String followupFlag;
    @SerializedName("referralFlag")
    @Expose
    private String referralFlag;
    @SerializedName("reqApprovalFlag")
    @Expose
    private String reqApprovalFlag;
    @SerializedName("untreatedFlag")
    @Expose
    private String untreatedFlag;
    @SerializedName("disabilityFlag")
    @Expose
    private String disabilityFlag;
    @SerializedName("treatingConsultant")
    @Expose
    private String treatingConsultant;
    @SerializedName("treatingDate")
    @Expose
    private String treatingDate;
    @SerializedName("approvingConsultant")
    @Expose
    private String approvingConsultant;
    @SerializedName("approvingDate")
    @Expose
    private String approvingDate;
    @SerializedName("sickLeaveDay")
    @Expose
    private Integer sickLeaveDay;
    @SerializedName("referralReason")
    @Expose
    private String referralReason;
    @SerializedName("otherRecFlag")
    @Expose
    private String otherRecFlag;
    @SerializedName("otherRecommend")
    @Expose
    private String otherRecommend;
    @SerializedName("patid")
    @Expose
    private String patid;
    @SerializedName("mrPatDocId")
    @Expose
    private String mrPatDocId;
    @SerializedName("admitDate")
    @Expose
    private String admitDate;
    @SerializedName("dischDate")
    @Expose
    private String dischDate;
    @SerializedName("noVerifiers")
    @Expose
    private String noVerifiers;
    @SerializedName("verifiedBy1")
    @Expose
    private String verifiedBy1;
    @SerializedName("verifiedBy1Date")
    @Expose
    private String verifiedBy1Date;
    @SerializedName("verifiedBy2")
    @Expose
    private String verifiedBy2;
    @SerializedName("verifiedBy2Date")
    @Expose
    private String verifiedBy2Date;
    @SerializedName("ver1Flag")
    @Expose
    private String ver1Flag;
    @SerializedName("ver2Flag")
    @Expose
    private String ver2Flag;
    @SerializedName("ver1AmendBy")
    @Expose
    private String ver1AmendBy;
    @SerializedName("ver2AmendBy")
    @Expose
    private String ver2AmendBy;
    @SerializedName("addAmendBy")
    @Expose
    private String addAmendBy;
    @SerializedName("addAmendDate")
    @Expose
    private String addAmendDate;
    @SerializedName("addNotes")
    @Expose
    private String addNotes;
    @SerializedName("incomingNotes")
    @Expose
    private String incomingNotes;
    @SerializedName("incomingDates")
    @Expose
    private String incomingDates;
    @SerializedName("addRefId")
    @Expose
    private String addRefId;
    @SerializedName("approvedAdminBy")
    @Expose
    private String approvedAdminBy;
    @SerializedName("approvedAdminDate")
    @Expose
    private String approvedAdminDate;
    @SerializedName("outDoc")
    @Expose
    private String outDoc;
    @SerializedName("outClinic")
    @Expose
    private String outClinic;
    @SerializedName("outDiagnosis")
    @Expose
    private String outDiagnosis;
    @SerializedName("extendPatLeave")
    @Expose
    private String extendPatLeave;
    @SerializedName("printCount")
    @Expose
    private Integer printCount;
    @SerializedName("cancelReason")
    @Expose
    private String cancelReason;
    @SerializedName("deptCode")
    @Expose
    private String deptCode;

    private String localPath = "";

    private String MRN;

    public String getMRN() {
        return MRN;
    }

    public void setMRN(String MRN) {
        this.MRN = MRN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveExpectStartDate() {
        return leaveExpectStartDate;
    }

    public void setLeaveExpectStartDate(String leaveExpectStartDate) {
        this.leaveExpectStartDate = leaveExpectStartDate;
    }

    public String getLeaveExpectReturnDate() {
        return leaveExpectReturnDate;
    }

    public void setLeaveExpectReturnDate(String leaveExpectReturnDate) {
        this.leaveExpectReturnDate = leaveExpectReturnDate;
    }

    public String getEnteredByName() {
        return enteredByName;
    }

    public void setEnteredByName(String enteredByName) {
        this.enteredByName = enteredByName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLeaveActualLeaveDate() {
        return leaveActualLeaveDate;
    }

    public void setLeaveActualLeaveDate(String leaveActualLeaveDate) {
        this.leaveActualLeaveDate = leaveActualLeaveDate;
    }

    public String getLeaveActualReturnDate() {
        return leaveActualReturnDate;
    }

    public void setLeaveActualReturnDate(String leaveActualReturnDate) {
        this.leaveActualReturnDate = leaveActualReturnDate;
    }

    public String getLeaveNotes() {
        return leaveNotes;
    }

    public void setLeaveNotes(String leaveNotes) {
        this.leaveNotes = leaveNotes;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getAmendBy() {
        return amendBy;
    }

    public void setAmendBy(String amendBy) {
        this.amendBy = amendBy;
    }

    public String getAmendDate() {
        return amendDate;
    }

    public void setAmendDate(String amendDate) {
        this.amendDate = amendDate;
    }

    public Integer getHosp() {
        return hosp;
    }

    public void setHosp(Integer hosp) {
        this.hosp = hosp;
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

    public String getEpisodeDate() {
        return episodeDate;
    }

    public void setEpisodeDate(String episodeDate) {
        this.episodeDate = episodeDate;
    }

    public String getEpisodeLocation() {
        return episodeLocation;
    }

    public void setEpisodeLocation(String episodeLocation) {
        this.episodeLocation = episodeLocation;
    }

    public String getSickLeaveDayFlag() {
        return sickLeaveDayFlag;
    }

    public void setSickLeaveDayFlag(String sickLeaveDayFlag) {
        this.sickLeaveDayFlag = sickLeaveDayFlag;
    }

    public String getFollowupFlag() {
        return followupFlag;
    }

    public void setFollowupFlag(String followupFlag) {
        this.followupFlag = followupFlag;
    }

    public String getReferralFlag() {
        return referralFlag;
    }

    public void setReferralFlag(String referralFlag) {
        this.referralFlag = referralFlag;
    }

    public String getReqApprovalFlag() {
        return reqApprovalFlag;
    }

    public void setReqApprovalFlag(String reqApprovalFlag) {
        this.reqApprovalFlag = reqApprovalFlag;
    }

    public String getUntreatedFlag() {
        return untreatedFlag;
    }

    public void setUntreatedFlag(String untreatedFlag) {
        this.untreatedFlag = untreatedFlag;
    }

    public String getDisabilityFlag() {
        return disabilityFlag;
    }

    public void setDisabilityFlag(String disabilityFlag) {
        this.disabilityFlag = disabilityFlag;
    }

    public String getTreatingConsultant() {
        return treatingConsultant;
    }

    public void setTreatingConsultant(String treatingConsultant) {
        this.treatingConsultant = treatingConsultant;
    }

    public String getTreatingDate() {
        return treatingDate;
    }

    public void setTreatingDate(String treatingDate) {
        this.treatingDate = treatingDate;
    }

    public String getApprovingConsultant() {
        return approvingConsultant;
    }

    public void setApprovingConsultant(String approvingConsultant) {
        this.approvingConsultant = approvingConsultant;
    }

    public String getApprovingDate() {
        return approvingDate;
    }

    public void setApprovingDate(String approvingDate) {
        this.approvingDate = approvingDate;
    }

    public Integer getSickLeaveDay() {
        return sickLeaveDay;
    }

    public void setSickLeaveDay(Integer sickLeaveDay) {
        this.sickLeaveDay = sickLeaveDay;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public String getOtherRecFlag() {
        return otherRecFlag;
    }

    public void setOtherRecFlag(String otherRecFlag) {
        this.otherRecFlag = otherRecFlag;
    }

    public String getOtherRecommend() {
        return otherRecommend;
    }

    public void setOtherRecommend(String otherRecommend) {
        this.otherRecommend = otherRecommend;
    }

    public String getPatid() {
        return patid;
    }

    public void setPatid(String patid) {
        this.patid = patid;
    }

    public String getMrPatDocId() {
        return mrPatDocId;
    }

    public void setMrPatDocId(String mrPatDocId) {
        this.mrPatDocId = mrPatDocId;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public String getDischDate() {
        return dischDate;
    }

    public void setDischDate(String dischDate) {
        this.dischDate = dischDate;
    }

    public String getNoVerifiers() {
        return noVerifiers;
    }

    public void setNoVerifiers(String noVerifiers) {
        this.noVerifiers = noVerifiers;
    }

    public String getVerifiedBy1() {
        return verifiedBy1;
    }

    public void setVerifiedBy1(String verifiedBy1) {
        this.verifiedBy1 = verifiedBy1;
    }

    public String getVerifiedBy1Date() {
        return verifiedBy1Date;
    }

    public void setVerifiedBy1Date(String verifiedBy1Date) {
        this.verifiedBy1Date = verifiedBy1Date;
    }

    public String getVerifiedBy2() {
        return verifiedBy2;
    }

    public void setVerifiedBy2(String verifiedBy2) {
        this.verifiedBy2 = verifiedBy2;
    }

    public String getVerifiedBy2Date() {
        return verifiedBy2Date;
    }

    public void setVerifiedBy2Date(String verifiedBy2Date) {
        this.verifiedBy2Date = verifiedBy2Date;
    }

    public String getVer1Flag() {
        return ver1Flag;
    }

    public void setVer1Flag(String ver1Flag) {
        this.ver1Flag = ver1Flag;
    }

    public String getVer2Flag() {
        return ver2Flag;
    }

    public void setVer2Flag(String ver2Flag) {
        this.ver2Flag = ver2Flag;
    }

    public String getVer1AmendBy() {
        return ver1AmendBy;
    }

    public void setVer1AmendBy(String ver1AmendBy) {
        this.ver1AmendBy = ver1AmendBy;
    }

    public String getVer2AmendBy() {
        return ver2AmendBy;
    }

    public void setVer2AmendBy(String ver2AmendBy) {
        this.ver2AmendBy = ver2AmendBy;
    }

    public String getAddAmendBy() {
        return addAmendBy;
    }

    public void setAddAmendBy(String addAmendBy) {
        this.addAmendBy = addAmendBy;
    }

    public String getAddAmendDate() {
        return addAmendDate;
    }

    public void setAddAmendDate(String addAmendDate) {
        this.addAmendDate = addAmendDate;
    }

    public String getAddNotes() {
        return addNotes;
    }

    public void setAddNotes(String addNotes) {
        this.addNotes = addNotes;
    }

    public String getIncomingNotes() {
        return incomingNotes;
    }

    public void setIncomingNotes(String incomingNotes) {
        this.incomingNotes = incomingNotes;
    }

    public String getIncomingDates() {
        return incomingDates;
    }

    public void setIncomingDates(String incomingDates) {
        this.incomingDates = incomingDates;
    }

    public String getAddRefId() {
        return addRefId;
    }

    public void setAddRefId(String addRefId) {
        this.addRefId = addRefId;
    }

    public String getApprovedAdminBy() {
        return approvedAdminBy;
    }

    public void setApprovedAdminBy(String approvedAdminBy) {
        this.approvedAdminBy = approvedAdminBy;
    }

    public String getApprovedAdminDate() {
        return approvedAdminDate;
    }

    public void setApprovedAdminDate(String approvedAdminDate) {
        this.approvedAdminDate = approvedAdminDate;
    }

    public String getOutDoc() {
        return outDoc;
    }

    public void setOutDoc(String outDoc) {
        this.outDoc = outDoc;
    }

    public String getOutClinic() {
        return outClinic;
    }

    public void setOutClinic(String outClinic) {
        this.outClinic = outClinic;
    }

    public String getOutDiagnosis() {
        return outDiagnosis;
    }

    public void setOutDiagnosis(String outDiagnosis) {
        this.outDiagnosis = outDiagnosis;
    }

    public String getExtendPatLeave() {
        return extendPatLeave;
    }

    public void setExtendPatLeave(String extendPatLeave) {
        this.extendPatLeave = extendPatLeave;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
