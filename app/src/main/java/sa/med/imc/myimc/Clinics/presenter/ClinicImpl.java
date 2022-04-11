package sa.med.imc.myimc.Clinics.presenter;

import android.app.Activity;

import sa.med.imc.myimc.Clinics.model.ClinicResponse;
import sa.med.imc.myimc.Clinics.view.ClinicViews;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clinic API implementation class.
 * Get Clinic data
 */

public class ClinicImpl implements ClinicPresenter {

    private Activity activity;
    private ClinicViews views;

    public ClinicImpl(ClinicViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public void callGetAllClinics(String token, String physician_id, String isActive, String search_txt, String item_count, String page, int hosp) {
        if (page.equalsIgnoreCase("0")) {
            if (search_txt.length() == 0)
                views.showLoading();
        } else
            views.showLoading();

        JSONObject object = new JSONObject();
        try {
            object.put("physicianId", "");
            object.put("isActive", "Y");
            object.put("search_txt", search_txt);
            object.put("item_count", item_count);
            object.put("page", page);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());
        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<ClinicResponse> xxx = webService.getAllWebClinics(body);

        xxx.enqueue(new Callback<ClinicResponse>() {
            @Override
            public void onResponse(Call<ClinicResponse> call, Response<ClinicResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    ClinicResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetClinicsList(response1);
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
            public void onFailure(Call<ClinicResponse> call, Throwable t) {
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
    public void callGetSearchClinics(String token, String physician_id, String isActive, String search_txt, String item_count, String page, int hosp) {
        if (search_txt.length() == 0 && !page.equalsIgnoreCase("0"))
            views.showLoading();

        JSONObject object = new JSONObject();
        try {
            object.put("physicianId", "");
            object.put("isActive", "Y");
            object.put("search_txt", search_txt.toLowerCase());
            object.put("item_count", item_count);
            object.put("page", page);
            object.put("hosp", hosp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());
        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<ClinicResponse> xxx = webService.getAllClinics(body);

        xxx.enqueue(new Callback<ClinicResponse>() {
            @Override
            public void onResponse(Call<ClinicResponse> call, Response<ClinicResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    ClinicResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetSearchClinicsList(response1);
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
            public void onFailure(Call<ClinicResponse> call, Throwable t) {
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

}
