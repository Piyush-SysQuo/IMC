package sa.med.imc.myimc.Physicians.presenter;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.Appointmnet.model.SessionDatesResponse;
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.SimpleResponse;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.Network.WebUrl;
import sa.med.imc.myimc.Physicians.model.NextTimeResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianCompleteDetailCMSResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianDetailResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianResponse;
import sa.med.imc.myimc.Physicians.view.PhysicianViews;
import sa.med.imc.myimc.R;

/**
 * Physician API implementation class.
 * Get Physician data
 * Get next available Time slot of a doctor
 */

public class PhysicianImpl implements PhysicianPresenter {

    private Activity activity;
    private PhysicianViews views;

    public PhysicianImpl(PhysicianViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public void callGetAllDoctors(String token, String rat, String search_txt, String lang, String item_count, String page, int hosp) {
        if (page.equalsIgnoreCase("0")) {
            if (search_txt.length() == 0 && (lang.length() > 0 || rat.length() > 0 || lang.length() == 0 || rat.length() == 0))
                views.showLoading();
        } else
            views.showLoading();
        String clinic_code;
        if (rat.length() != 0) {
            clinic_code = rat;
        } else {
            clinic_code = "0";
        }
        WebService webService = ServiceGenerator.createService(WebService.class);
        JSONObject object = new JSONObject();
        try {
            object.put("mrnumber", "");
            object.put("clinic_code", clinic_code);
            object.put("search_txt", search_txt);
            object.put("rating", "");
            object.put("serviceid", "");
            object.put("deptCode", "");
            object.put("lang", lang);
            object.put("item_count", item_count);
            object.put("page", page);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());


        Call<PhysicianResponse> xxx = webService.getAllDoctorsWeb(body);

        xxx.enqueue(new Callback<PhysicianResponse>() {
            @Override
            public void onResponse(Call<PhysicianResponse> call, Response<PhysicianResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    PhysicianResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetPhysicianList(response1);
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
            public void onFailure(Call<PhysicianResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
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
    public void callGetSearchDoctors(String token, String mrn, String clinic_id, String search_txt, String lang,
                                     String rating, String item_count, String page, String type, int hosp) {
        if (page.equalsIgnoreCase("0")) {
            if (search_txt.length() == 0 && (clinic_id.length() > 0 || rating.length() > 0 || clinic_id.length() == 0 || rating.length() == 0))
                views.showLoading();
        } else
            views.showLoading();

        if (clinic_id.length() == 0) {
            clinic_id = "0";
        }
        if (rating.length() != 0) {
            clinic_id = rating;
        }

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        JSONObject object = new JSONObject();
        try {


            object.put("mrnumber", mrn);
            object.put("clinic_code", clinic_id);
            object.put("search_txt", search_txt.toLowerCase());
            object.put("rating", "");
            object.put("serviceid", "");
            object.put("search_word", "-");
            object.put("deptCode", "");
            object.put("lang", lang);
            object.put("item_count", item_count);
            object.put("page", page);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        Call<PhysicianResponse> xxx;
        if (type.length() > 0)
            xxx = webService.getAllDoctorsWeb(body);
        else
            xxx = webService.getAllDoctors(body);

        xxx.enqueue(new Callback<PhysicianResponse>() {
            @Override
            public void onResponse(Call<PhysicianResponse> call, Response<PhysicianResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    PhysicianResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetPhysicianList(response1);
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
            public void onFailure(Call<PhysicianResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
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
    public void callGetDoctorsInfo(String token, String doctor_id, String mrn, String clinic_id, int hosp) {
        views.showLoading();
        JSONObject object = new JSONObject();
        try {
            if (token.length() != 0)
                object.put("mrnumber", mrn);
            else
                object.put("mrnumber", "");

            object.put("clinicid", clinic_id);
            object.put("phyid", doctor_id);
            object.put("hosp", hosp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<PhysicianDetailResponse> xxx = null;

        if (token.length() != 0)
            xxx = webService.getDoctorInfo(body);
        else
            xxx = webService.getDoctorInfoWeb(body);


        xxx.enqueue(new Callback<PhysicianDetailResponse>() {
            @Override
            public void onResponse(Call<PhysicianDetailResponse> call, Response<PhysicianDetailResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    PhysicianDetailResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetPhysicianProfile(response1);
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
            public void onFailure(Call<PhysicianDetailResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    // message = "Unknown";
                    //  views.onFail(message);

                }
            }
        });
    }

    @Override
    public void callGetCMSDoctorProfileData(String doctor_id) {
        views.showLoading();

        WebService webService = ServiceGenerator.createService(WebService.class, WebUrl.BASE_URL_LINKS, "");
        Call<PhysicianCompleteDetailCMSResponse> xxx = webService.getDoctorFullDetailCMS(doctor_id);

        xxx.enqueue(new Callback<PhysicianCompleteDetailCMSResponse>() {
            @Override
            public void onResponse(Call<PhysicianCompleteDetailCMSResponse> call, Response<PhysicianCompleteDetailCMSResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    PhysicianCompleteDetailCMSResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus()) {
                            views.onGetCMSPhysician(response1);
                        }
//                        else
//                            views.onFail(response1.getMessage());
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<PhysicianCompleteDetailCMSResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    // message = "Unknown";
                    //  views.onFail(message);
                }
            }
        });
    }

    @Override
    public void callGetNextAvailableDateTime(String token, String physicianId, String clinicId, String serviceId, String deptCode, int pos, int hosp) {
//        views.showLoading();
        JSONObject object = new JSONObject();
        try {
            object.put("physicianId", physicianId);
            object.put("clinicId", clinicId);
            object.put("serviceId", serviceId);
            object.put("deptCode", deptCode);
            object.put("sessionType", "0");
            object.put("hosp", hosp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<NextTimeResponse> xxx = webService.getNextAvailableTimeSlot(body);


        xxx.enqueue(new Callback<NextTimeResponse>() {
            @Override
            public void onResponse(Call<NextTimeResponse> call, Response<NextTimeResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    NextTimeResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetNextAvailableTIme(response1, pos);
                        }
//                        else
//                            views.onFail(response1.getMessage());
                    } else {
//                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
//                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<NextTimeResponse> call, Throwable t) {
                views.hideLoading();
//                String message = "";
//
//                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
//                    message = "timeout";
//                    views.onFail(message);
//                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
//                    views.onNoInternet();
//                } else {
//                    // message = "Unknown";
//                    //  views.onFail(message);
//
//                }
            }
        });
    }

}
