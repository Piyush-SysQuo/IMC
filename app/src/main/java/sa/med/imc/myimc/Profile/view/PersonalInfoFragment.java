package sa.med.imc.myimc.Profile.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.select.Evaluator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sa.med.imc.myimc.Login.Validate.DataModel.ValidateResponse;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.IDNameModel;
import sa.med.imc.myimc.Network.ServiceGenerator;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.Network.SimpleResponse;
import sa.med.imc.myimc.Network.WebService;
import sa.med.imc.myimc.Profile.model.MedicationRespone;
import sa.med.imc.myimc.Profile.model.ProfileResponse;
import sa.med.imc.myimc.Profile.presenter.ProfileImpl;
import sa.med.imc.myimc.Profile.presenter.ProfilePresenter;
import sa.med.imc.myimc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import sa.med.imc.myimc.Settings.ContactUsActivity;
import sa.med.imc.myimc.Utils.Common;

import static sa.med.imc.myimc.Settings.ContactUsActivity.isValidEmail;

/**
 * Personal information of user like date of birth, blood group, allergy, height, weight.
 */
public class PersonalInfoFragment extends Fragment implements ProfileViews {
    ProfileResponse profileresponse;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.ed_value_dob)
    EditText edValueDob;
    @BindView(R.id.ed_value_blood_group)
    EditText edValueBloodGroup;
    @BindView(R.id.ed_value_marital_status)
    EditText edValueMaritalStatus;
    @BindView(R.id.ed_value_height)
    EditText edValueHeight;
    @BindView(R.id.ed_value_weight)
    EditText edValueWeight;
    @BindView(R.id.ed_value_allergy)
    EditText edValueAllergy;
    @BindView(R.id.ed_value_address)
    EditText edValueAddress;
    @BindView(R.id.tv_value_mrn)
    TextView tvValueMrn;
    @BindView(R.id.tv_value_id_name)
    TextView tvValueIdName;
    @BindView(R.id.tv_value_id_value)
    TextView tvValueIdValue;
    @BindView(R.id.tv_value_nationality)
    TextView tv_value_nationality;
    //txt_verify
    @BindView(R.id.txtVerify)
    TextView txtVerify;
    //tick
    @BindView(R.id.tick)
    ImageView tick;
    Dialog dialog;
    //tvEmail
    @BindView(R.id.tvEmail)
    TextView tvEmail;

    String emailID = "";
    int oTP;
    int is_email_valified;
    ProfilePresenter profilePresenter;
    BottomDependentListDialog bottomDependentListDialog;
    ProfileResponse profileResponse;
    Dialog sucDialog;
    String email = "";
    String update_email = "";

    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    public static PersonalInfoFragment newInstance(String param1, String param2) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        ButterKnife.bind(this, view);

        profilePresenter = new ProfileImpl(this, getActivity());

        ValidateResponse.UserDetails userDetails = SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER);

        edValueDob.setText(Common.parseDate(userDetails.getDob()));
        tvValueMrn.setText(userDetails.getMrnumber());
        tvValueIdValue.setText(userDetails.getNationalId());
        tvEmail.setText(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER).getEmail());
        String email_vale = SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER).getEmail();
        tick.setVisibility(View.VISIBLE);
        txtVerify.setVisibility(View.GONE);
        if (email_vale != null) {
            tick.setVisibility(View.VISIBLE);
            txtVerify.setVisibility(View.GONE);
        }

        if (SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_LANGUAGE, "").equalsIgnoreCase(Constants.ARABIC)) {
            tv_value_nationality.setText(userDetails.getNationalityAr());
            edValueAddress.setText(userDetails.getAddressAr());
        } else {
            tv_value_nationality.setText(userDetails.getNationality());
            edValueAddress.setText(userDetails.getAddress());
        }
        txtVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(requireContext(), "");
            }
        });

        is_email_valified = SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER).getIsEmailVerified();
        // Toast.makeText(getContext(), "CONTACT_US_EMAIL" + is_email_valified, Toast.LENGTH_LONG).show();

        if (is_email_valified == 1) {
            tvEmail.setText(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER).getEmail());
            tick.setVisibility(View.VISIBLE);
            txtVerify.setVisibility(View.GONE);
        } else if (is_email_valified == 0) {
            tick.setVisibility(View.GONE);
            txtVerify.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void showDialog(Context activity, String msg) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_email);

        TextView txtVerify = (TextView) dialog.findViewById(R.id.txtVerify);
        TextView txt_close = (TextView) dialog.findViewById(R.id.txt_close);
        TextView txt_submit = (TextView) dialog.findViewById(R.id.txt_submit);
        EditText et_email_report = (EditText) dialog.findViewById(R.id.et_email_report);
        EditText et_otp = (EditText) dialog.findViewById(R.id.et_otp);
        TextView txtAddMail = (TextView) dialog.findViewById(R.id.txtAddMail_dialog);

        et_email_report.setText(tvEmail.getText().toString().trim());

//        txtVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                emailID = et_email_report.getText().toString();
//                callSendOTP(SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_MRN, ""),
//                        SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
//                        emailID);
//
//                Log.e("DATA_IS", "DATA_IS" + SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_MRN, "") +
//                        SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_ACCESS_TOKEN, "") +
//                        et_email_report.getText().toString());
//            }
//        });

        txtAddMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailID = et_email_report.getText().toString();
                if (isValidEmail(emailID)) {

                    callSendOTP(SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_MRN, ""),
                            SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
                            emailID,
                            SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.SELECTED_HOSPITAL, 0));

                    Log.e("DATA_IS", "DATA_IS" + SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_MRN, "") +
                            SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_ACCESS_TOKEN, "") +
                            et_email_report.getText().toString());
                } else {
                    et_email_report.setError("Please enter valid Email Address ");
                    //  Toast.makeText(ContactUsActivity.this,"Please enter valid Email Address ",Toast.LENGTH_LONG).show();
                }
            }
        });


//        text.setText(msg);
//
        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oTP = Integer.parseInt(et_otp.getText().toString());
                callVerifyOTP(SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_MRN, ""),
                        SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
                        emailID, oTP, SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.SELECTED_HOSPITAL, 0));

                Log.e("DATA_IS", "DATA_IS" + SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_MRN, "") +
                        SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.KEY_ACCESS_TOKEN, "") +
                        emailID + oTP);
            }
        });

        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    // send a callback request
    public void callSendOTP(String mrNumber, String token, String email, int hosp) {
        Common.showDialog(getContext());
        JSONObject object = new JSONObject();
        try {

            object.put("patid", mrNumber);
            object.put("email", email);
            object.put("hosp", hosp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (object).toString());
        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);

        Call<SimpleResponse> xxx = webService.sendOTP("en", "Android", token, "no", body);//"application/json; charset=utf-8",
        xxx.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                Common.hideDialog();
                if (response.isSuccessful()) {
                    SimpleResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            // alertBack(response1.getMessage());
                            Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Common.makeToast(getActivity(), response1.getMessage());
                        }
                    }
                }

                //layBtnSend.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Common.hideDialog();
                Common.makeToast(getActivity(), t.getMessage());

            }
        });

    }

    public void callVerifyOTP(String mrNumber, String token, String email, int otp, int hosp)
    {
        Common.showDialog(getContext());
        JSONObject object = new JSONObject();
        try {
            object.put("patid", mrNumber);
            object.put("email", email);
            object.put("otp", otp);
            object.put("hosp", hosp);
//            object.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (object).toString());
        WebService webService = ServiceGenerator.createService(WebService.class, "Bearer " + token);
        Call<SimpleResponse> xxx = webService.verifyOTP("en", "Android", token, "no", body);//"application/json; charset=utf-8",
        xxx.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                Common.hideDialog();
                if (response.isSuccessful()) {
                    SimpleResponse response1 = response.body();
                    if (response1 != null) {
                        if (response1.getStatus().equalsIgnoreCase("true")) {
                            // alertBack(response1.getMessage());
                            Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            String name = preferences.getString("UPDATE_MAIL", "");
                            if (!name.equalsIgnoreCase("")) {
                                name = name;  /* Edit the value here*/
                                tvEmail.setText(name);
                                tick.setVisibility(View.VISIBLE);
                                txtVerify.setVisibility(View.GONE);
                                SharedPreferencesUtils.getInstance(getContext()).setValue(Constants.EMAIL, tvEmail.getText().toString().trim());
                                Log.e("UPDATE_MAIL", "UPDATE_MAIL" + name);

                                profilePresenter.callGetUserProfileApi(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_MRN, ""),
                                        SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
                                        SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.SELECTED_HOSPITAL, 0));
                            }


                            //tvEmail.setText(update_email);
                            //  tvEmail.setText(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER).getEmail());
//                            String verified_mail = "";
//                            ProfileFragment profileFragment=new ProfileFragment();
//                           String mail_is= profileFragment.updated_mail(verified_mail);
//                          Log.e("UPDATE_MAIL1","UPDATE_MAIL1"+mail_is);
//
//
//
//                            Fragment frg = null;
//                            frg = getActivity().getSupportFragmentManager().findFragmentByTag("PersonalInfoFragment.this");
//                            final FragmentTransaction ft =getActivity(). getSupportFragmentManager().beginTransaction();
//                            ft.detach(frg);
//                            ft.attach(frg);
//                            ft.commit();
                            //  tvEmail.setText(profileresponse.getUserDetails().getEmail());
                            //getActivity().finish();
                        } else {
                            Common.makeToast(getActivity(), response1.getMessage());
                        }
                    }
                }

                //layBtnSend.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Common.hideDialog();
                Common.makeToast(getActivity(), t.getMessage());

            }
        });

    }

    @Override
    public void onGetProfile(ProfileResponse response) {
        SharedPreferencesUtils.getInstance(getActivity()).setValue(Constants.KEY_USER, response.getUserDetails());
    }

    @Override
    public void onGetMedication(MedicationRespone response) {

    }

    @Override
    public void onUpdateProfile(SimpleResponse response) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void onNoInternet() {

    }
}