package sa.med.imc.myimc.Physicians.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sa.med.imc.myimc.Adapter.PhysicianAdapter;
import sa.med.imc.myimc.Appointmnet.view.AppointmentActivity;
import sa.med.imc.myimc.Interfaces.FragmentListener;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.Physicians.model.NextTimeResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianCompleteDetailCMSResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianDetailResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianResponse;
import sa.med.imc.myimc.Physicians.presenter.PhysicianImpl;
import sa.med.imc.myimc.Physicians.presenter.PhysicianPresenter;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Utils.Common;
import sa.med.imc.myimc.Utils.CustomTypingEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * All Physicians of a Clinic.
 * Patient can view profile of doctor.
 * Patient can get appointment from doctor.
 */

public class PhysiciansFragment extends Fragment implements PhysicianViews, BottomOptionDialog.BottomDailogListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_clinic)
    TextView tv_clinic;
    @BindView(R.id.ed_search)
    CustomTypingEditText edSearch;
    @BindView(R.id.rv_physicians)
    RecyclerView rvPhysicians;
    @BindView(R.id.tv_load_more)
    TextView tv_load_more;
    @BindView(R.id.main_content)
    RelativeLayout mainContent;
    @BindView(R.id.bt_try_again)
    Button btTryAgain;
    @BindView(R.id.main_no_net)
    RelativeLayout mainNoNet;
    @BindView(R.id.main_time_out)
    RelativeLayout mainTimeOut;
    @BindView(R.id.bt_try_again_time_out)
    Button btTryAgainTimeOut;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    @BindView(R.id.main_no_data_trans)
    RelativeLayout mainNoDataTrans;

    public String mParam1 = "", mParam2 = "", noShowStatus = "";
    int pos = -1, page = 0, total_items = 0;
    String depart_id = "", lang_id = "";
    boolean edit = false, isLoading = false, isSearched = false;
    PhysicianAdapter adapter;
    FragmentListener fragmentAdd;
    PhysicianPresenter physicianPresenter;
    List<PhysicianResponse.Physician> list = new ArrayList<>();

    BottomOptionDialog bottomOptionDialog;
    private boolean loadingEnd = false;
    BottomConfirmDialogNextAvailable mBottomConfirmDialogNextAvailable;

    public PhysiciansFragment() {
        // Required empty public constructor
    }

    public static PhysiciansFragment newInstance() {
        PhysiciansFragment fragment = new PhysiciansFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_physicians, container, false);
        ButterKnife.bind(this, view);
        mParam2 = "";
        mParam1 = "";

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        } else if (mParam2.length() > 0)
            ivFilter.setVisibility(View.GONE);
        else
            ivFilter.setVisibility(View.VISIBLE);

        setUpPhysicianRecyclerView();

        physicianPresenter = new PhysicianImpl(this, getActivity());
        isSearched = false;
        edSearch.setText("");

        depart_id = "";
        lang_id = "";

        page = 0;
        callAPI();

        edSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                page = 0;
//                callAPI();
                Common.hideSoftKeyboard(getActivity());

                return true;
            }
            return false;
        });

        edSearch.setOnTypingModified((view1, isTyping) -> {

            if (!isTyping) {
                if (edSearch.getText().toString().trim().length() > 0) {
                    page = 0;
                    callAPI();
                }
            }
        });

        swipeToRefresh.setOnRefreshListener(() -> {
            depart_id = "";
            lang_id = "";
            page = 0;
            tv_load_more.setVisibility(View.GONE);
            callAPI();
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentAdd = (FragmentListener) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.iv_back, R.id.iv_search, R.id.iv_filter, R.id.bt_try_again, R.id.bt_try_again_time_out, R.id.tv_load_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                if (fragmentAdd != null)
                    fragmentAdd.backPressed(Common.CONTAINER_FRAGMENT);
                break;

            case R.id.iv_search:
                if (edit) {
                    ivSearch.setImageResource(R.drawable.ic_search);
                    edSearch.setVisibility(View.GONE);
                    edit = false;
                    edSearch.clearFocus();


                    if (isSearched) {
                        edSearch.setText("");
                        page = 0;
                        callAPI();
                    }

                } else {
                    ivSearch.setImageResource(R.drawable.ic_close);
                    edSearch.setVisibility(View.VISIBLE);
                    edit = true;
                    edSearch.requestFocus();
                    if (getActivity() != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
                break;

            case R.id.iv_filter:
                if (bottomOptionDialog == null)
                    bottomOptionDialog = BottomOptionDialog.newInstance();

                if (!bottomOptionDialog.isAdded())
                    bottomOptionDialog.show(getChildFragmentManager(), "DAILOG");
                break;

            case R.id.bt_try_again:
                page = 0;
                callAPI();
                break;

            case R.id.bt_try_again_time_out:
                page = 0;
                callAPI();
                break;

            case R.id.tv_load_more:
                tv_load_more.setEnabled(false);
                callAPI();
                break;
        }
    }

    // call API to get doctors
    void callAPI() {
        if (edSearch.getText().toString().length() > 0)
            isSearched = true;
        else
            isSearched = false;

        if (SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_USER_TYPE, "").equalsIgnoreCase(Constants.GUEST_TYPE)) {
            physicianPresenter.callGetSearchDoctors(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
                    SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_MRN, ""), mParam2, edSearch.getText().toString(), lang_id, depart_id, Constants.RECORD_SET, String.valueOf(page), Constants.GUEST_TYPE,
                    SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.SELECTED_HOSPITAL, 0));

        } else
//            physicianPresenter.callGetSearchDoctors(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
//                    SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_MRN, ""), mParam2, edSearch.getText().toString(), lang_id, depart_id, Constants.RECORD_SET, String.valueOf(page), Constants.GUEST_TYPE,
//                    SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.SELECTED_HOSPITAL, 0));
//            PIYUSH
            physicianPresenter.callGetSearchDoctors(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
                    SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_MRN, ""), mParam2, edSearch.getText().toString(), lang_id, depart_id, Constants.RECORD_SET, String.valueOf(page), "",
                    SharedPreferencesUtils.getInstance(getContext()).getValue(Constants.SELECTED_HOSPITAL, 0));

    }

    // Initialize Physician list view and add adapter to display data,
    void setUpPhysicianRecyclerView() {
        list = new ArrayList<>();
        adapter = new PhysicianAdapter(getActivity(), list, "");
        rvPhysicians.setHasFixedSize(true);
        rvPhysicians.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPhysicians.setAdapter(adapter);
        adapter.setOnItemClickListener(new PhysicianAdapter.OnItemClickListener() {
            @Override
            public void onViewProfile(int position) {
                PhysicianDetailActivity.startActivity(getActivity(), list.get(position).getDocCode(), list.get(position).getClinicCode(), list.get(position));
            }

            @Override
            public void onBookAppointment(int position) {
                pos = position;
                if (list.get(pos).getService() != null) {
                    AppointmentActivity.start(getActivity(), list.get(position));

                } else
                    setNoServicesDialog(getActivity());
            }

            @Override
            public void onBookNextAvailable(int position) {
                pos = position;
                if (list.get(position).getService() != null) {
                    if (mBottomConfirmDialogNextAvailable == null)
                        mBottomConfirmDialogNextAvailable = BottomConfirmDialogNextAvailable.newInstance();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", list.get(pos));
                    bundle.putSerializable("session", list.get(position).getSession());

                    mBottomConfirmDialogNextAvailable.setArguments(bundle);

                    if (!mBottomConfirmDialogNextAvailable.isAdded())
                        mBottomConfirmDialogNextAvailable.show(getChildFragmentManager(), "DAILOG");
                } else
                    setNoServicesDialog(getActivity());
            }
        });

        rvPhysicians.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //get the recycler view position which is completely visible and first
                final int positionView = ((LinearLayoutManager) rvPhysicians.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int numItems = adapter.getItemCount() - 1;

                if (positionView >= numItems) {
                    if (isLoading && loadingEnd) {
                        loadingEnd = false;
                        isLoading = false;
                        callAPI();
                    }
                }
            }
        });
    }

    @Override
    public void onGetPhysicianList(PhysicianResponse response) {
        mainContent.setVisibility(View.VISIBLE);
        mainNoNet.setVisibility(View.GONE);
        mainTimeOut.setVisibility(View.GONE);
        noShowStatus = response.getNo_show_status();
        mainNoDataTrans.setVisibility(View.GONE);

        if (swipeToRefresh.isRefreshing()) {
            list.clear();
            swipeToRefresh.setRefreshing(false);
        }

        if (depart_id.length() > 0 || lang_id.length() > 0)
            ivFilter.setImageResource(R.drawable.ic_filter_green);
        else
            ivFilter.setImageResource(R.drawable.ic_filter);


        if (response.getPhysicians() != null) {
            if (page == 0)
                list.clear();

            list.addAll(response.getPhysicians());
            loadingEnd = response.getPhysicians().size() != 0;

            if (list.size() == 0) {
                rvPhysicians.setVisibility(View.GONE);
                ivSearch.setVisibility(View.VISIBLE);
                mainNoDataTrans.setVisibility(View.VISIBLE);

            } else {

                isLoading = true;
                rvPhysicians.setVisibility(View.VISIBLE);
                ivSearch.setVisibility(View.VISIBLE);

                if (response.getTotal_items() != null)
                    if (response.getTotal_items().length() > 0) {
                        total_items = Integer.parseInt(response.getTotal_items());
                        page = page + 1;
                    }

            }
        } else {
            rvPhysicians.setVisibility(View.GONE);
            ivSearch.setVisibility(View.VISIBLE);
        }
//
//        if (mParam2.length() > 0) {
//            if (SharedPreferencesUtils.getInstance(requireActivity()).getValue(Constants.KEY_LANGUAGE, "").equalsIgnoreCase(Constants.ARABIC))
//                tv_clinic.setText(list.get(0).getClincNameAr());
//            else
//                tv_clinic.setText(list.get(0).getClinicNameEn());
//
//            ivFilter.setVisibility(View.GONE);
//        }
        if (mParam2.length() > 0) {
            if (SharedPreferencesUtils.getInstance(requireActivity()).getValue(Constants.KEY_LANGUAGE, "").equalsIgnoreCase(Constants.ARABIC))

            {
                if(list.size()!=0) {
                    tv_clinic.setText(list.get(0).getClincNameAr());
                }
            }

            else
            {
                if(list.size()!=0)
                {
                    tv_clinic.setText(list.get(0).getClinicNameEn());
                }
                else
                {
                    // Toast.makeText(getContext(),"DATA NOT AVAILABLE",Toast.LENGTH_LONG).show();
                }
            }


            ivFilter.setVisibility(View.GONE);
        }

        else {
            tv_clinic.setText("");
            ivFilter.setVisibility(View.VISIBLE);
        }

        tv_load_more.setEnabled(true);
        adapter.notifyDataSetChanged();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getService() != null) {
                    physicianPresenter.callGetNextAvailableDateTime(SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_ACCESS_TOKEN, ""),
                            list.get(i).getDocCode(), list.get(i).getClinicCode(), list.get(i).getService().getId(), list.get(i).getDeptCode(), i,
                            SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.SELECTED_HOSPITAL, 0));
                }
            }
        }
    }

    @Override
    public void onGetPhysicianProfile(PhysicianDetailResponse response) {

    }


    @Override
    public void showLoading() {
        if (!swipeToRefresh.isRefreshing())
            Common.showDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        Common.hideDialog();
    }

    @Override
    public void onFail(String msg) {
        swipeToRefresh.setRefreshing(false);
        tv_load_more.setEnabled(true);

        if (msg.equalsIgnoreCase("timeout")) {
            if (list.size() == 0) {
                mainContent.setVisibility(View.GONE);
                mainNoNet.setVisibility(View.GONE);
                mainTimeOut.setVisibility(View.VISIBLE);
                mainNoDataTrans.setVisibility(View.GONE);

            } else {
                Common.makeToast(getActivity(), getString(R.string.time_out_messgae));
                isLoading = true;
            }

        } else {
            Common.makeToast(getActivity(), msg);
        }
    }

    @Override
    public void onNoInternet() {
        swipeToRefresh.setRefreshing(false);
        tv_load_more.setEnabled(true);

        if (list.size() == 0) {
            mainContent.setVisibility(View.GONE);
            mainTimeOut.setVisibility(View.GONE);
            mainNoNet.setVisibility(View.VISIBLE);
            mainNoDataTrans.setVisibility(View.GONE);

        } else {
            Common.noInternet(getActivity());
            isLoading = true;
        }
    }

    // If doctor's service is null
    void setNoServicesDialog(Activity activity) {
        String phone = "";
        SharedPreferences preferences = activity.getSharedPreferences(Constants.PREFERENCE_CONFIG, Context.MODE_PRIVATE);
        if (SharedPreferencesUtils.getInstance(activity).getValue(Constants.KEY_LANGUAGE, Constants.ENGLISH).equalsIgnoreCase(Constants.ARABIC)) {
            phone = preferences.getString("ph", "") + "+";
        } else
            phone = "+" + preferences.getString("ph", "");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.alert))
                .setMessage(activity.getString(R.string.no_service_content, phone))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(dlg -> {
            try {
                Typeface typeface = ResourcesCompat.getFont(activity, R.font.font_app);
                ((TextView) alertDialog.findViewById(android.R.id.message)).setTypeface(typeface);

                if (SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.KEY_LANGUAGE, "").equalsIgnoreCase(Constants.ARABIC)) {
                    alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); // set title and message direction to RTL
                } else {
                    alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR); // set title and message direction to RTL
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        alertDialog.show();
    }

    @Override
    public void onDone(String rating, String id) {
        depart_id = rating;
        lang_id = id;

        page = 0;
        callAPI();

    }

    @Override
    public void onClear(String text) {
        if (depart_id.length() > 0 || lang_id.length() > 0) {
            depart_id = "";
            lang_id = "";

            page = 0;
            callAPI();
        }
    }

    @Override
    public void onGetCMSPhysician(PhysicianCompleteDetailCMSResponse response) {

    }

    @Override
    public void onGetNextAvailableTIme(NextTimeResponse response, int pos) {

        if (pos < list.size()) {
            if (response.getData() != null) {
                if (response.getData().size() > 0) {
                    list.get(pos).setDate(response.getData().get(0).getSessionDateShort());
                    list.get(pos).setTime(response.getData().get(0).getSessionTimeShort());
                    list.get(pos).setSessionId(String.valueOf(response.getData().get(0).getSessionId()));
                    list.get(pos).setSession(response.getData().get(0));
                } else {
                    list.get(pos).setDate(null);
                    list.get(pos).setTime(null);
                    list.get(pos).setSessionId("0");
                }

            } else {
                list.get(pos).setDate(null);
                list.get(pos).setTime(null);
                list.get(pos).setSessionId("0");
            }
            adapter.notifyItemChanged(pos);
        }

    }


}
