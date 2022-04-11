package sa.med.imc.myimc.HealthSummary.presenter;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import sa.med.imc.myimc.HealthSummary.model.AllergyResponse;
import sa.med.imc.myimc.HealthSummary.model.ReadingResponse;
import sa.med.imc.myimc.HealthSummary.view.HealthViews;
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Heath Allergies API implementation class.
 * Get All Allergies
 * Get All Readings
 */

public class HealthImpl implements HealthPresenter {

    private Activity activity;
    private HealthViews views;

    public HealthImpl(HealthViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public void callGetAllAllergies(String token, String mrn, int hosp) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);

        Call<AllergyResponse> xxx = webService.fetchAllergies(mrn, hosp);
        xxx.enqueue(new Callback<AllergyResponse>() {
            @Override
            public void onResponse(Call<AllergyResponse> call, Response<AllergyResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    AllergyResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onSuccess(response1);
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
            public void onFailure(Call<AllergyResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
//                    views.onFail(message);
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
    public void callGetAllReadings(String token, String patId, String episodeNo, String fromDate, String toDate, String item_count, String page, int hosp) {
        views.showLoading();
        JSONObject object = new JSONObject();
        try {
            object.put("patId", patId);
            if (episodeNo.length() > 0)
                object.put("episodeNo", Long.parseLong(episodeNo));
            object.put("fromDate", fromDate);
            object.put("toDate", toDate);
            object.put("item_count", item_count);
            object.put("page", page);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<ReadingResponse> xxx = webService.fetchAllReadings(body);

        xxx.enqueue(new Callback<ReadingResponse>() {
            @Override
            public void onResponse(Call<ReadingResponse> call, Response<ReadingResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    ReadingResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onSuccessReadings(response1);
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
            public void onFailure(Call<ReadingResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
//                    views.onFail(message);
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
