package sa.med.imc.myimc.Profile.presenter;

import android.app.Activity;

import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.SimpleResponse;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.Profile.model.LastBookingResponse;
import sa.med.imc.myimc.Profile.model.MedicationRespone;
import sa.med.imc.myimc.Profile.model.ProfileResponse;
import sa.med.imc.myimc.Profile.view.ProfileViews;
import sa.med.imc.myimc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Profile API implementation class.
 * Get Profile data
 * Update Profile data
 */

public class ProfileImpl implements ProfilePresenter {

    private Activity activity;
    private ProfileViews views;

    public ProfileImpl(ProfileViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    @Override
    public void callGetUserProfileApi(String user_id, String token, int hosp) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<ProfileResponse> xxx = webService.getUserInfo(user_id, hosp);

        xxx.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    ProfileResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetProfile(response1);
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
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
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
    public void callGetMedication(String token, String mrNumber, String episodeId, String itemCount, String page, int hosp) {
        if (!page.equalsIgnoreCase("0"))
            views.showLoading();

        JSONObject object = new JSONObject();
        try {
            object.put("mrNumber", mrNumber);
            object.put("episodeId", episodeId);
            object.put("itemCount", itemCount);
            object.put("page", page);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<MedicationRespone> xxx = webService.fetchMedication(body);

        xxx.enqueue(new Callback<MedicationRespone>() {
            @Override
            public void onResponse(Call<MedicationRespone> call, Response<MedicationRespone> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    MedicationRespone response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetMedication(response1);
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
            public void onFailure(Call<MedicationRespone> call, Throwable t) {
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
    public void callUpdateProfileApi(String mrNumber, String token, String email, String address, String addressAr, int hosp) {
        views.showLoading();

        JSONObject object = new JSONObject();
        try {
            object.put("mrNumber", mrNumber);
            object.put("email", email);
            object.put("address", address);
            object.put("addressAr", addressAr);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<SimpleResponse> xxx = webService.updateUserInfo(body);

        xxx.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    SimpleResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onUpdateProfile(response1);
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

}
