package sa.med.imc.myimc.Records.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class MedicalReport {
    @Embedded
    @SerializedName("id")
    @Expose
    private IdModel id;
    @NonNull
    @SerializedName("patId")
    @Expose
    private String patId = "";
    @NonNull
    @PrimaryKey
    @SerializedName("prodcutCode")
    @Expose
    private String prodcutCode;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("deliveringDept")
    @Expose
    private String deliveringDept;
    @SerializedName("orderingDept")
    @Expose
    private String orderingDept;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("orderFlag")
    @Expose
    private String orderFlag;
    @SerializedName("resultAvailable")
    @Expose
    private Integer resultAvailable;
    @SerializedName("orderStatusWeb")
    @Expose
    private Integer orderStatusWeb;

    private String localPath = "";
    private String MRN;

    public String getMRN() {
        return MRN;
    }

    public void setMRN(String MRN) {
        this.MRN = MRN;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public IdModel getId() {
        return id;
    }

    public void setId(IdModel id) {
        this.id = id;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getProdcutCode() {
        return prodcutCode;
    }

    public void setProdcutCode(String prodcutCode) {
        this.prodcutCode = prodcutCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveringDept() {
        return deliveringDept;
    }

    public void setDeliveringDept(String deliveringDept) {
        this.deliveringDept = deliveringDept;
    }

    public String getOrderingDept() {
        return orderingDept;
    }

    public void setOrderingDept(String orderingDept) {
        this.orderingDept = orderingDept;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public Integer getResultAvailable() {
        return resultAvailable;
    }

    public void setResultAvailable(Integer resultAvailable) {
        this.resultAvailable = resultAvailable;
    }

    public Integer getOrderStatusWeb() {
        return orderStatusWeb;
    }

    public void setOrderStatusWeb(Integer orderStatusWeb) {
        this.orderStatusWeb = orderStatusWeb;
    }

}

