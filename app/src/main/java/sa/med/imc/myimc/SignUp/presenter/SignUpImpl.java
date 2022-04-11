package sa.med.imc.myimc.SignUp.presenter;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.Login.DataModel.LoginResponse;
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.SimpleResponse;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.Network.WebUrl;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.SignUp.model.CityResponse;
import sa.med.imc.myimc.SignUp.model.CompaniesResponse;
import sa.med.imc.myimc.SignUp.model.DistrictResponse;
import sa.med.imc.myimc.SignUp.model.NationalityResponse;
import sa.med.imc.myimc.SignUp.model.UserDetailModel;
import sa.med.imc.myimc.SignUp.view.SignUpViews;
import sa.med.imc.myimc.Utils.FileUtils;

/**
 * Sign Up API implementation class.
 */

public class SignUpImpl implements SignUpPresenter {

    private Activity activity;
    private SignUpViews views;

    public SignUpImpl(SignUpViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    @Override
    public void callSinUpAPI(String token, String docNumber,
                             String docType,
                             String docExpiryDate,
                             String patName, String patNameAr,
                             String familyName, String familyNameAr,
                             String fatherName,
                             String fatherNameAr,
                             String grandfatherName,
                             String grandfatherNameAr, String gender,
                             String telMobile, String telHome,
                             String addressDistrict,
                             String addressCity,
                             String addressCountry,
                             String nationality,
                             String dob, String lang,
                             String erFirstName,
                             String erFamilyName, String erPhone,
                             String comments,
                             String hasInsurance,
                             String insuranceCompany,
                             String insuranceCompanyCode,
                             String idCard,
                             String policyNumber,
                             String insuranceMemberId,
                             String insuranceExpDate,
                             String insuranceClass,
                             String insuranceAttachment,
                             String submitterName,
                             String relationToRegistrant,
                             String idCard_path,
                             String insuranceAttachmentPath,
                             String matritalStatus, int hosp) {
        views.showLoading();
        telMobile = WebUrl.COUNTRY_CODE + telMobile;

        if (telHome.length() > 0)
            telHome = WebUrl.COUNTRY_CODE + telHome;

        JSONObject object = new JSONObject();
        try {
            object.put("docNumber", docNumber);
            object.put("docType", docType);
            object.put("docExpiryDate", docExpiryDate);

            object.put("firstName", patName);
            object.put("firstNameAr", patNameAr);
            object.put("grandfatherName", grandfatherName);
            object.put("grandfatherNameAr", grandfatherNameAr);
            object.put("familyName", familyName);
            object.put("familyNameAr", familyNameAr);
            object.put("fatherName", fatherName);
            object.put("fatherNameAr", fatherNameAr);

            object.put("gender", gender);
            object.put("language", lang);
            object.put("dob", dob);

            object.put("addressDistrict", addressDistrict);
            object.put("addressCity", addressCity);
            object.put("addressCountry", addressCountry);
            object.put("nationality", nationality);

            object.put("visitReason", comments);
            object.put("telMobile", telMobile);
            object.put("telHome", telHome);

            object.put("erFirstName", erFirstName);
            object.put("erFamilyName", erFamilyName);
            object.put("erPhone", erPhone);

            object.put("hasInsurance", hasInsurance);
            object.put("idCard", idCard);
            object.put("insuranceCompany", insuranceCompany);
            object.put("insuranceCompanyCode", insuranceCompanyCode);
            object.put("insurancePolicyNumber", policyNumber);
            object.put("insuranceMemberId", insuranceMemberId);
            object.put("insuranceExpDate", insuranceExpDate);
            object.put("insuranceClass", insuranceClass);
            object.put("insuranceAttachment", insuranceAttachment);
            object.put("relationToRegistrant", relationToRegistrant);
            object.put("submitterName", submitterName);
            object.put("maritalStatus", matritalStatus);

            object.put("accept", true);
            object.put("consentChecked", true);
            object.put("hosp", hosp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MultipartBody.Part idCardPart = prepareImgFilePart(idCard_path, "idCard");
        RequestBody user = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, token);
        MultipartBody.Part insurancePart = null;
        Call<SimpleResponse> xxx;

        if (insuranceAttachmentPath.length() > 0) {
            insurancePart = prepareImgFilePart(insuranceAttachmentPath, "insuranceAttachment");
            xxx = webService.registerPatientWithIsurance(user, idCardPart, insurancePart);

        } else {
            xxx = webService.registerPatient(user, idCardPart);
        }

        xxx.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    SimpleResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onSignUp(response1);
                        } else
                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                views.hideLoading();
                String message = t.getMessage();

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    // message = "Unknown";
                    views.onFail(message);
                }
            }
        });
    }

    @Override
    public void callGetOtpApi(String phoneNumber, String iqama_id, String typ, int hosp) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class);
        phoneNumber = WebUrl.COUNTRY_CODE + phoneNumber;

        JSONObject object = new JSONObject();
        try {
            object.put("telMobile", phoneNumber);
            object.put("docNumber", iqama_id);
            object.put("docType", typ);
            object.put("hosp", hosp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        Call<LoginResponse> xxx = webService.regLogin(body);
        xxx.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    LoginResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onSuccessOTP(response1);
                        }

                        views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    message = "Unknown";
                    views.onFail(message);
                }
            }
        });

    }

    @Override
    public void callGetNationalityApi() {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class);

        Call<NationalityResponse> xxx = webService.getNationalityCompanies();
        xxx.enqueue(new Callback<NationalityResponse>() {
            @Override
            public void onResponse(Call<NationalityResponse> call, Response<NationalityResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    NationalityResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetNationality(response1);
                        } else

                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<NationalityResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    message = "Unknown";
                    views.onFail(message);
                }
            }
        });

    }

    @Override
    public void callGetCompaniesApi(int hosp) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class);

        Call<CompaniesResponse> xxx = webService.getInsuranceCompanies(hosp);
        xxx.enqueue(new Callback<CompaniesResponse>() {
            @Override
            public void onResponse(Call<CompaniesResponse> call, Response<CompaniesResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    CompaniesResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetComapanies(response1);
                        } else

                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<CompaniesResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    message = "Unknown";
                    views.onFail(message);
                }
            }
        });

    }

    @Override
    public void callGetCityApi() {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class);

        Call<CityResponse> xxx = webService.getCity();
        xxx.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    CityResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetCity(response1);
                        } else

                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    message = "Unknown";
                    views.onFail(message);
                }
            }
        });
    }

    @Override
    public void callGetDistrictApi() {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class);

        Call<DistrictResponse> xxx = webService.getDistrict();
        xxx.enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    DistrictResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetDistrict(response1);
                        } else

                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<DistrictResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    message = "Unknown";
                    views.onFail(message);
                }
            }
        });
    }

    @Override
    public void callGetInformationApi(String token, String id, String dob) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class, WebUrl.TEMP_BASE_URL, token);

        Call<UserDetailModel> xxx = webService.getInformationFromID(id, dob);
        xxx.enqueue(new Callback<UserDetailModel>() {
            @Override
            public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    UserDetailModel response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetInfo(response1);
                        } else
                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<UserDetailModel> call, Throwable t) {
                views.hideLoading();
                String message = t.getMessage();

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = activity.getString(R.string.time_out_messgae);
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    // message = "Unknown";
                    views.onFail(message);
                }
            }
        });
    }

    @NonNull
    private MultipartBody.Part prepareImgFilePart(String filee, String name) {
        File file = new File(filee);
        String mime = FileUtils.getMimeType(file);
        Log.e("mime", "" + mime);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mime), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);

    }
}
