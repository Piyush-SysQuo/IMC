package sa.med.imc.myimc.Questionaires.presenter;

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
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.SimpleResponse;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.Network.WebUrl;
import sa.med.imc.myimc.Questionaires.model.AssessmentResponse;
import sa.med.imc.myimc.Questionaires.model.CompletedResponse;
import sa.med.imc.myimc.Questionaires.view.AssessmentViews;
import sa.med.imc.myimc.Questionaires.model.AssessmentModel;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Utils.Common;

/**
 * Get Assessment API implementation class.
 * Get Tokens
 * Completed list of assessments
 * Start and save result APIs
 */

public class AssessmentImpl implements AssessmentPresenter {
    //TODO: validate hosp Changes
    private Activity activity;
    private AssessmentViews views;

    public AssessmentImpl(AssessmentViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public void callGetNextAssessment(String token, String id, String itemOID, String response) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class, token);
        JSONObject object = new JSONObject();
        try {
            object.put("itemResponseOid", itemOID);
            object.put("response", response);
            object.put("assessmentId", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        Call<AssessmentResponse> xxx = webService.nextAssessment(body);

        xxx.enqueue(new Callback<AssessmentResponse>() {
            @Override
            public void onResponse(Call<AssessmentResponse> call, Response<AssessmentResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    AssessmentResponse response1 = response.body();
                    if (response1 != null) {
                        views.onGetAssessmentNext(response1);
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<AssessmentResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    Common.noInternet(activity);
                } else {
                    message = "Unknown";
                    views.onFail(message);

                }
            }
        });
    }

//    TODO check hosp is implemented in API are not
    @Override
    public void callGetListCompletedAssessment(String token, String patId, String item_count, String page, int hosp) {
        views.showLoading();
        JSONObject object = new JSONObject();
        try {
            object.put("patId", patId);
            object.put("item_count", item_count);
            object.put("page", page);
            object.put("hosp", hosp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<CompletedResponse> xxx = webService.getListCompletedAssessment(body);

        xxx.enqueue(new Callback<CompletedResponse>() {
            @Override
            public void onResponse(Call<CompletedResponse> call, Response<CompletedResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    CompletedResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onGetListAssessments(response1);
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
            public void onFailure(Call<CompletedResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    views.onNoInternet();
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));

                }
            }
        });
    }

    @Override
    public void callGetFirstQuestion(String token, String formId, String patId, String episode, String assessmentId) {
        views.showLoading();
        JSONObject object = new JSONObject();
        try {
            object.put("formId", formId);
            object.put("patId", patId);
            object.put("episode", episode);
            object.put("assessmentId", assessmentId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());


        WebService webService = ServiceGenerator.createService(WebService.class, token);
        Call<AssessmentResponse> xxx = webService.startAssessment(body);


        xxx.enqueue(new Callback<AssessmentResponse>() {
            @Override
            public void onResponse(Call<AssessmentResponse> call, Response<AssessmentResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    AssessmentResponse response1 = response.body();
                    if (response1 != null) {
                        views.onGetAssessmentFirst(response1);
                    } else {
                        views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                    }
                } else {
                    views.onFail(activity.getResources().getString(R.string.plesae_try_again));
                }
            }

            @Override
            public void onFailure(Call<AssessmentResponse> call, Throwable t) {
                views.hideLoading();
                String message = "";

                if (t instanceof SocketTimeoutException) {//ClassNotFoundException
                    message = "timeout";
                    views.onFail(message);
                } else if (t instanceof ConnectException || t instanceof UnknownHostException) {
                    Common.noInternet(activity);
                } else {
                    message = "Unknown";
                    views.onFail(message);
                }
            }
        });
    }
}
