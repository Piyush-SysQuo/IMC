package sa.med.imc.myimc.Managebookings.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import sa.med.imc.myimc.Appointmnet.model.PriceResponse;
import sa.med.imc.myimc.Base.BaseActivity;
import sa.med.imc.myimc.Interfaces.CheckLocation;
import sa.med.imc.myimc.Interfaces.FragmentListener;
import sa.med.imc.myimc.Managebookings.model.BookingResponse;
import sa.med.imc.myimc.Managebookings.presenter.BookingImpl;
import sa.med.imc.myimc.Managebookings.presenter.BookingPresenter;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.Network.SimpleResponse;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Telr.TelrActivity;
import sa.med.imc.myimc.Telr.TelrFragment;
import sa.med.imc.myimc.Utils.Common;
import sa.med.imc.myimc.Utils.NonSwipeViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageBookingsActivity extends BaseActivity implements BookingViews, CheckLocation, LocationListener, FragmentListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    NonSwipeViewPager viewPager;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.main_toolbar)
    RelativeLayout mainToolbar;
    @BindView(R.id.main_content)
    LinearLayout mainContent;
    @BindView(R.id.bt_try_again)
    Button btTryAgain;
    @BindView(R.id.main_no_net)
    RelativeLayout mainNoNet;
    @BindView(R.id.bt_try_again_time_out)
    Button btTryAgainTimeOut;
    @BindView(R.id.main_time_out)
    RelativeLayout mainTimeOut;
    @BindView(R.id.main_no_data)
    RelativeLayout mainNoData;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    private ViewPagerAdapter mViewPagerAdapter;
    onFilterClick filterClick;

    Fragment telrfragment;
  public static String tag = "tag";

    int page = 0;
    AlertDialog.Builder builder;
    LocationManager locationManager;
    Double latitude = 0.0, longitude = 0.0;
    Location location;
    boolean isGPSEnable = false, isNetworkEnable = false;
    BookingPresenter presenter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ManageBookingsActivity.class);
        activity.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity) {
        Log.v(activity.getClass().getSimpleName(), "startActivityForResult: ");
        Intent intent = new Intent(activity, ManageBookingsActivity.class);
        activity.startActivityForResult(intent, TelrActivity.PAYMENT_CODE);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedPreferencesUtils.getInstance(ManageBookingsActivity.this).getValue(Constants.KEY_LANGUAGE, Constants.ENGLISH).equalsIgnoreCase(Constants.ARABIC)) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        ManageBookingsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);
        ButterKnife.bind(this);

        presenter = new BookingImpl(this, this);
        mainContent.setVisibility(View.VISIBLE);
        mainNoNet.setVisibility(View.GONE);
        mainTimeOut.setVisibility(View.GONE);

        setupViewPager();

        if (SharedPreferencesUtils.getInstance(ManageBookingsActivity.this).getValue(Constants.KEY_LANGUAGE, Constants.ENGLISH).equalsIgnoreCase(Constants.ARABIC)) {
            viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewPager.setRotationY(180);

        } else {
            viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

    }


    @OnClick({R.id.bt_try_again, R.id.bt_try_again_time_out, R.id.iv_back, R.id.iv_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_try_again:
                break;

            case R.id.bt_try_again_time_out:
                break;

            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.iv_filter:
                filterClick = (onFilterClick) mViewPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());

                if (filterClick != null)
                    filterClick.onClick(viewPager.getCurrentItem(), ivFilter);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        ManageBookingsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void setupViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment fragment = UpcomingBookingFragment.newInstance();
        Fragment fragment1 = CompletedBookingFragment.newInstance();
        Fragment fragment2 = CancelledBookingFragment.newInstance();
        Fragment fragment3 = NoShowFragment.newInstance();

        mViewPagerAdapter.addFragment(fragment, getResources().getString(R.string.upcoming));
        mViewPagerAdapter.addFragment(fragment1, getResources().getString(R.string.completed));
        mViewPagerAdapter.addFragment(fragment2, getResources().getString(R.string.cancelled));
        mViewPagerAdapter.addFragment(fragment3, getResources().getString(R.string.no_show));

        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);         /* limit is a fixed integer*/

        tabs.setupWithViewPager(viewPager);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabs.setTabTextColors(getResources().getColor(R.color.text_grey_color), getResources().getColor(R.color.colorPrimaryDark));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void startFragment(String title, Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment oldFragment = fragmentManager.findFragmentByTag(backStateName);
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            Fragment currentFragment = fragmentManager.findFragmentByTag(backStateName);
            if (currentFragment != null) {
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.addToBackStack(backStateName).commit();
            } else {
                fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
                fragmentTransaction.addToBackStack(backStateName).commit();
            }
        } else {
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.add(R.id.main_container_wrapper, fragment);
            fragmentTransaction.addToBackStack(backStateName).commit();

        }
    }

    @Override
    public void onGetBookings(BookingResponse response) {
        mainContent.setVisibility(View.VISIBLE);
        mainNoNet.setVisibility(View.GONE);
        mainTimeOut.setVisibility(View.GONE);


    }

    @Override
    public void onReschedule(SimpleResponse response) {

    }

    @Override
    public void onCancel(SimpleResponse response) {

    }

    @Override
    public void onDownloadConfirmation(ResponseBody response, Headers headers) {

    }

    @Override
    public void onGetPrice(PriceResponse response) {

    }

    @Override
    public void showLoading() {
        Common.showDialog(this);

    }

    @Override
    public void hideLoading() {
        Common.hideDialog();
    }

    @Override
    public void onFail(String msg) {
        if (msg.equalsIgnoreCase("timeout")) {
            mainContent.setVisibility(View.GONE);
            mainNoNet.setVisibility(View.GONE);
            mainTimeOut.setVisibility(View.VISIBLE);

        } else {
            makeToast(msg);

        }
    }

    @Override
    public void onNoInternet() {
        mainContent.setVisibility(View.GONE);
        mainNoNet.setVisibility(View.VISIBLE);
        mainTimeOut.setVisibility(View.GONE);
    }

    @Override
    public void openProfileMedicineFragment(String value) {

    }

    @Override
    public void openPhysicianFragment(String value, String clinic_id) {

    }

    @Override
    public void openClinicFragment(String value) {

    }

    @Override
    public void openPaymentInfoFragment(Serializable value, String key) {

    }

    @Override
    public void openTelrFragment(Serializable book, Serializable res, String value) {
        if (telrfragment == null)
            telrfragment = TelrFragment.newInstance();

        Bundle args = new Bundle();
        args.putSerializable("booking", book);
        args.putSerializable("price", res);
        telrfragment.setArguments(args);

        startFragment("", telrfragment);
        Common.CONTAINER_FRAGMENT = value;
        mainToolbar.setVisibility(View.GONE);
    }

    @Override
    public void openMedicineFragment(String type, String value) {

    }

    @Override
    public void backPressed(String type) {

    }

    @Override
    public void openLMSRecordFragment(String value, String type, String episodeId) {

    }

    @Override
    public void startTask(int time) {

    }

    @Override
    public void openHealthSummary(String value) {

    }

    @Override
    public void openFitness(String value) {

    }

    @Override
    public void openWellness(String value) {

    }

    @Override
    public void openBodyMeasurement(String value) {

    }

    @Override
    public void openActivity(String value) {

    }

    @Override
    public void openSleepCycle(String value) {

    }

    @Override
    public void openHeatAndVitals(String value) {

    }

    @Override
    public void openAllergies(String value) {

    }

    @Override
    public void openVitalSigns(String value) {

    }

    @Override
    public void checkLocation() {
        checkPermissionFirst();
    }

    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private SparseArray<Fragment> registeredFragments = new SparseArray<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }


    public interface onFilterClick {
        void onClick(int position, ImageView view);
    }


    // check permission to save reports required storage permission
    void checkPermissionFirst() {

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport reportP) {

                if (reportP.areAllPermissionsGranted()) {
                    getLocation();
                } else if (reportP.isAnyPermissionPermanentlyDenied()) {
                    Common.permissionDialog(ManageBookingsActivity.this);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {
                token.continuePermissionRequest();/* ... */
            }
        }).check();
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                getLocation();
//
//            } else {
//                requestPermissions(new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                }, 33);
//            }
//        } else
//            getLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 33: {
                if (grantResults.length > 0) {
                    getLocation();
                }
            }
        }
    }

    void getLocation() {
        if (isGPSEnabled(this)) {
            fn_getlocation();
        } else {
            if (builder == null) {
                builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
                builder.setTitle("Enable GPS");
                builder.setMessage("Enable GPS to provide location")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                dialog.dismiss();

                                builder = null;
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                builder = null;
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    public static boolean isGPSEnabled(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    private void fn_getlocation() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        SharedPreferencesUtils.getInstance(getApplicationContext()).setValue(Constants.KEY_LAT, String.valueOf(latitude));
                        SharedPreferencesUtils.getInstance(getApplicationContext()).setValue(Constants.KEY_LONG, String.valueOf(longitude));
                    }
                }

//                mGpsTracker.connect();

            } else if (isGPSEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        SharedPreferencesUtils.getInstance(getApplicationContext()).setValue(Constants.KEY_LAT, String.valueOf(latitude));
                        SharedPreferencesUtils.getInstance(getApplicationContext()).setValue(Constants.KEY_LONG, String.valueOf(longitude));
                    }
                }
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            SharedPreferencesUtils.getInstance(getApplicationContext()).setValue(Constants.KEY_LAT, String.valueOf(latitude));
            SharedPreferencesUtils.getInstance(getApplicationContext()).setValue(Constants.KEY_LONG, String.valueOf(longitude));

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Common.onActivityResult(this,requestCode,resultCode,data);
    }
}
