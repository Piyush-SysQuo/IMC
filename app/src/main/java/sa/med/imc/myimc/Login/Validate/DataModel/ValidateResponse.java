package sa.med.imc.myimc.Login.Validate.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ValidateResponse implements Serializable {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("notif_count")
    @Expose
    private String notif_count;
    @SerializedName("userDetails")
    @Expose
    private UserDetails userDetails;

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getNotif_count() {
        return notif_count;
    }

    public void setNotif_count(String notif_count) {
        this.notif_count = notif_count;
    }

    public class UserDetails implements Serializable {

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("isEmailVerified")
        @Expose
        private int isEmailVerified;

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobilenum")
        @Expose
        private String mobilenum;
        @SerializedName("national_id")
        @Expose
        private String nationalId;

        @SerializedName("nationality")
        @Expose
        private String nationality;
        @SerializedName("nationalityAr")
        @Expose
        private String nationalityAr;
        @SerializedName("national_id_type")
        @Expose
        private String national_id_type;

        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("middle_name")
        @Expose
        private String middleName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("family_name")
        @Expose
        private String familyName;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("mrnumber")
        @Expose
        private String mrnumber;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("addressAr")
        @Expose
        private String addressAr;
        @SerializedName("notification_status")
        @Expose
        private String notification_staus;

        @SerializedName("first_name_ar")
        @Expose
        private String first_name_ar;
        @SerializedName("middle_name_ar")
        @Expose
        private String middle_name_ar;
        @SerializedName("last_name_ar")
        @Expose
        private String last_name_ar;
        @SerializedName("family_name_ar")
        @Expose
        private String family_name_ar;
        @SerializedName("marital_status")
        @Expose
        private String marital_status;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("gender")
        @Expose
        private String gender;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        public int getIsEmailVerified() {
            return isEmailVerified;
        }

        public void setIsEmailVerified(int isEmailVerified) {
            this.isEmailVerified = isEmailVerified;
        }
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobilenum() {
            return mobilenum;
        }

        public void setMobilenum(String mobilenum) {
            this.mobilenum = mobilenum;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMrnumber() {
            return mrnumber;
        }

        public void setMrnumber(String mrnumber) {
            this.mrnumber = mrnumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNotification_staus() {
            return notification_staus;
        }

        public void setNotification_staus(String notification_staus) {
            this.notification_staus = notification_staus;
        }

        public String getNational_id_type() {
            return national_id_type;
        }

        public void setNational_id_type(String national_id_type) {
            this.national_id_type = national_id_type;
        }

        public String getFirst_name_ar() {
            return first_name_ar;
        }

        public void setFirst_name_ar(String first_name_ar) {
            this.first_name_ar = first_name_ar;
        }

        public String getMiddle_name_ar() {
            return middle_name_ar;
        }

        public void setMiddle_name_ar(String middle_name_ar) {
            this.middle_name_ar = middle_name_ar;
        }

        public String getLast_name_ar() {
            return last_name_ar;
        }

        public void setLast_name_ar(String last_name_ar) {
            this.last_name_ar = last_name_ar;
        }

        public String getFamily_name_ar() {
            return family_name_ar;
        }

        public void setFamily_name_ar(String family_name_ar) {
            this.family_name_ar = family_name_ar;
        }

        public String getMarital_status() {
            return marital_status;
        }

        public void setMarital_status(String marital_status) {
            this.marital_status = marital_status;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getNationalityAr() {
            return nationalityAr;
        }

        public void setNationalityAr(String nationalityAr) {
            this.nationalityAr = nationalityAr;
        }

        public String getAddressAr() {
            return addressAr;
        }

        public void setAddressAr(String addressAr) {
            this.addressAr = addressAr;
        }
    }


}
