package sa.med.imc.myimc.Login;

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
import sa.med.imc.myimc.Login.DataModel.LoginResponse;
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.R;

/**
 * Login API implementation class.
 * login API implemented.
 */

public class LoginImpl implements LoginPresenter {

    Activity activity;
    LoginViews views;

    public LoginImpl(LoginViews views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }


    @Override
    public void callLoginApi(String id_type, String id_value, String user_type, int hosp) {
        views.showLoading();
        WebService webService = ServiceGenerator.createService(WebService.class);

        JSONObject object = new JSONObject();
        try {
            object.put("id_type", id_type);
            object.put("id_value", id_value);
            object.put("user_type", user_type);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (object).toString());

        Call<LoginResponse> xxx = webService.login(body);
        xxx.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                views.hideLoading();
                if (response.isSuccessful()) {
                    LoginResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            views.onSuccessLogin(response1);
//                          MCPatient.getInstance().get().refreshData();
//                            MCPatient.updateCurrentPatient();
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

//    fun loginWithPin(activity: Activity, onlinePatient: OnlinePatient, name: String,
//                     pin: String) {            onlinePatient.loginWithPinAsync(name, pin,
//            Locale.getDefault().language,
//            object : LoginWithPinDelegate() {
//        override fun onLoginWithPinSuccess(patient: Patient?) {
//            activity.runOnUiThread {
//                MCPatient.setCurrent(patient)
//                MCPatient.getInstance().get().syncWithServer(object : SyncWithServerDelegate() {
//                    override fun onSyncWithServerSuccess() {
//                        activity.runOnUiThread(Runnable {
//                            MCPatient.getInstance().get().refreshData()
//                            MCPatient.updateCurrentPatient()
//                            GeneralFireIntents.fireMainActivityForLabUser(
//                                    activity,
//                                    MCPatient.getInstance().patientRecord.hasPassword)
//                        })
//                    }                                    override fun onSyncWithServerFailure(errorMessage: String) {                                    }
//                })
//            }
//        }                        override fun onLoginWithPinFailure(errors: LoginWithPinErrors?) {
//            activity.runOnUiThread {                            }
//        }                    })
//    }



//    public static void updateCurrentPatient()
//    {
//        int patientId = patient_id;        if (patientId != -1)
//        {            Patient updatedPatient = Patient.get(patientId);            if (updatedPatient != null) {
//        updatedPatient.refreshData();
//        instance.current = updatedPatient;
//        instance.current.syncWithServerAsync();
//        instance.patientRecord = updatedPatient.getPatientRecord();
//        SmartReport.patient = MCPatient.getInstance().get();
//        MedicusInsights.setPatient(MCPatient.getInstance().get());
//        MedicusUtilities.setPatient(MCPatient.getInstance().get());
//    }            instance.patientRecord = null;        }    }
}
