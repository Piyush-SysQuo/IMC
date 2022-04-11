package sa.med.imc.myimc.FitnessAndWellness;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.HealthDataTypes;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sa.med.imc.myimc.BuildConfig;
import sa.med.imc.myimc.Interfaces.FragmentListener;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Utils.Common;
import sa.med.imc.myimc.Wellness.GoogleFitActions;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class FitnessAndWellnessFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.progress_bar_one)
    ProgressBar progress_bar_one;
    @BindView(R.id.progress_bar_two)
    ProgressBar progress_bar_two;
    @BindView(R.id.progress_bar_three)
    ProgressBar progress_bar_three;
    @BindView(R.id.Body_Measurement)
    LinearLayout Body_Measurement;
    @BindView(R.id.lay_heat_and_vital)
    LinearLayout lay_heat_and_vital;
    @BindView(R.id.sleepCycle)
    LinearLayout sleepCycle;
    @BindView(R.id.lay_activity)
    LinearLayout lay_activity;
    @BindView(R.id.step_count)
    TextView tvStepCount;
    @BindView(R.id.one)
    TextView tvCalories;
    @BindView(R.id.tv_connected)
    TextView tvIsWatchConnected;
    @BindView(R.id.two)
    TextView tvHeartRate;
    @BindView(R.id.three)
    TextView tvSleepHours;

//    @BindView(R.id.cardAssessment)
//    CardView cardAssessment;

    FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .addDataType(DataType.TYPE_DISTANCE_DELTA)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED)
            .addDataType(DataType.TYPE_MOVE_MINUTES)
            .addDataType(DataType.TYPE_HEIGHT)
            .addDataType(DataType.TYPE_WEIGHT)
            .addDataType(DataType.TYPE_HEART_RATE_BPM)
            .addDataType(HealthDataTypes.TYPE_BLOOD_PRESSURE)
            .addDataType(HealthDataTypes.TYPE_BLOOD_GLUCOSE)
            .addDataType(HealthDataTypes.TYPE_OXYGEN_SATURATION)
            .addDataType(HealthDataTypes.TYPE_BODY_TEMPERATURE)
            .addDataType(DataType.TYPE_SLEEP_SEGMENT).build();

    private Boolean runningQOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    FragmentListener fragmentAdd;
    private OnDataPointListener onDataPointListener;


    public FitnessAndWellnessFragment() {
        // Required empty public constructor
    }

    public static FitnessAndWellnessFragment newInstance() {
        FitnessAndWellnessFragment fragment = new FitnessAndWellnessFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentAdd = (FragmentListener) getActivity();


    }

    private void circularProgressBar() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitnessandwellness, container, false);
        ButterKnife.bind(this, view);
        receiveArguments();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkConnected();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkPermissionsAndRun(GoogleFitActions.RECORD_RAW_STEPS_COUNT);
    }

    private void receiveArguments() {
//        if (SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.SELECTED_HOSPITAL, 1) == 4) {
//            cardAssessment.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            GoogleFitActions postSignInAction = GoogleFitActions.values()[requestCode];
            if (postSignInAction != null) {
                performActionForRequestCode(postSignInAction);
            }
        } else {
//            Common.showAlert(requireActivity(), "There is an error signing into Google Fit. Please try again later." );
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        removeStepsCountListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.iv_back, R.id.Body_Measurement,R.id.lay_heat_and_vital,R.id.sleepCycle,R.id.lay_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                if (fragmentAdd != null)
                    fragmentAdd.backPressed(Common.CONTAINER_FRAGMENT);
                break;

            case R.id.Body_Measurement:
                if (fragmentAdd != null) {
                    fragmentAdd.openBodyMeasurement("BodyMeasurementFragment");
                }
                break;
            case  R.id.lay_heat_and_vital:
                if (fragmentAdd != null) {
                    fragmentAdd.openHeatAndVitals("HeatAndVitals");
                }
                break;

            case  R.id.sleepCycle:
                if (fragmentAdd != null) {
                    fragmentAdd.openSleepCycle("SleepCycle");
                }
                break;

                case  R.id.lay_activity:
                if (fragmentAdd != null) {
                    fragmentAdd.openActivity("Activity");
                }
                break;


//            case R.id.lay_item_allergies:
//                if (fragmentAdd != null)
//                    fragmentAdd.openAllergies("AllergiesFragment");
//                break;
//
//            case R.id.lay_item_vital_signs:
//                if (fragmentAdd != null)
//                    fragmentAdd.openVitalSigns("VitalSignsFragment");
//                break;
//
//            case R.id.lay_item_assessment:
//                CompletedFormsListActivity.startActivity(getActivity());
//                break;
//        }
        }
    }

    private void checkPermissionsAndRun(GoogleFitActions googleFitActions) {
        if (permissionApproved()) {
            fitSignIn(googleFitActions);
        } else {
            requestRuntimePermissions(googleFitActions);
        }
    }

    private void fitSignIn(GoogleFitActions googleFitActions) {
        if (oAuthPermissionsApproved()) {
            performActionForRequestCode(googleFitActions);
        } else {
            if (googleFitActions != null) {
                GoogleSignIn.requestPermissions(
                        this,
                        googleFitActions.ordinal(),
                        getGoogleAccount(), fitnessOptions);
            }
        }
    }

    private Boolean oAuthPermissionsApproved() {
        return GoogleSignIn.hasPermissions(getGoogleAccount(), fitnessOptions);
    }

    private GoogleSignInAccount getGoogleAccount() {
        return GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions);
    }

    private void performActionForRequestCode(GoogleFitActions googleFitActions) {
        switch (googleFitActions) {
            case RECORD_RAW_STEPS_COUNT:
                recordStepsCount();
                break;
            case RAW_STEPS_COUNT:
                getRawStepsCount();
                break;
        }
    }

    private void recordStepsCount() {
        Fitness.getRecordingClient(requireContext(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(unused -> {
                    Log.i(TAG, "Successfully subscribed!");
                    readData();
                    checkPermissionsAndRun(GoogleFitActions.RAW_STEPS_COUNT);
                })
                .addOnFailureListener( e ->
                        Log.w(TAG, "There was a problem subscribing.", e));
    }

    private void getRawStepsCount() {
        Fitness.getSensorsClient(getApplicationContext(), GoogleSignIn.getAccountForExtension(getApplicationContext(), fitnessOptions))
                .findDataSources(
                        new DataSourcesRequest.Builder()
                                .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                                .setDataSourceTypes(DataSource.TYPE_DERIVED)
                                .build())
                .addOnSuccessListener(dataSources -> {
                    for (DataSource dataSource : dataSources) {
//                        Log.i(TAG, "Data source found: ${it.streamIdentifier}");
//                        Log.i(TAG, "Data Source type: ${it.dataType.name}");

                        if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA)) {
//                            Log.i(TAG, "Data source for STEP_COUNT_DELTA found!");
                            registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_DELTA);
                        }
                    }})
                .addOnFailureListener(e ->
                        Log.e(TAG, "Find data sources request failed", e));
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        onDataPointListener = dataPoint -> {
            for (Field field : dataPoint.getDataType().getFields()) {
                readData();
            }
        };
        Fitness.getSensorsClient(requireContext(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
                .add(
                        new SensorRequest.Builder()
                                .setDataSource(dataSource) // Optional but recommended
                                // for custom data sets.
                                .setDataType(dataType) // Can't be omitted.
                                .setSamplingRate(1, TimeUnit.SECONDS)
                                .build(),
                        onDataPointListener
                )
                .addOnSuccessListener(unused ->
                        Log.i(TAG, "Listener registered!"))
                .addOnFailureListener(task ->
                        Log.e(TAG, "Listener not registered.", task.getCause()));
    }

    private Boolean permissionApproved() {
        Boolean activityRecognition;
        Boolean location;

        if (runningQOrLater) {
            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACTIVITY_RECOGNITION)) {
                activityRecognition = true;
            } else {
                activityRecognition = false;
            }

            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                location = true;
            } else {
                location = false;
            }
        } else {
            activityRecognition = true;
            location = true;
        }

        return activityRecognition && location;
    }

    private void requestRuntimePermissions(GoogleFitActions googleFitActions) {
        String[] requiredPermissions = {Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.ACCESS_FINE_LOCATION};

        Boolean shouldProvideRationaleOfActivityRecognition =
                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION);

        Boolean shouldProvideRationaleOfLocation =
                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (googleFitActions != null) {
            if (shouldProvideRationaleOfActivityRecognition) {
                Common.showAlert(requireActivity(), "We Need Activity Recognition Permission For Recording Your Health Data.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(requiredPermissions, googleFitActions.ordinal());
                    }
                });

            } else if (shouldProvideRationaleOfLocation) {
                Common.showAlert(requireActivity(), "We Need Fine Location Permission For Recording Your Health Data.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(requiredPermissions, googleFitActions.ordinal());
                    }
                });
            } else {
                requestPermissions(requiredPermissions, googleFitActions.ordinal());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (grantResults.length <= 0) {
            Log.i(TAG, "User interaction was cancelled.");
        } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            GoogleFitActions googleFitActions = GoogleFitActions.values()[requestCode];
            if (googleFitActions != null) {
                fitSignIn(googleFitActions);
            }
        } else {
            Common.showAlert(requireActivity(), requireActivity().getString(R.string.permission_denied_explanation), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    private void removeStepsCountListener() {
        if (onDataPointListener != null) {
            Fitness.getSensorsClient(requireContext(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
                    .remove(onDataPointListener)
                    .addOnSuccessListener(unused ->
                            Log.i(TAG, "Listener was removed!"))
                    .addOnFailureListener(e ->
                            Log.i(TAG, "Listener was not removed."));
        }
    }

    private void readData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, +1);
        long endTime = calendar.getTimeInMillis();

        getStepsCountHistory(startTime, endTime);
        getCaloriesHistory(startTime, endTime);
        getHeartRateHistory(startTime, endTime);
        getSleepData(startTime, endTime);
    }

    private void getStepsCountHistory(long startTime, long endTime) {
        DataSource ESTIMATED_STEP_COUNT_DELTA = new DataSource.Builder()
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setType(DataSource.TYPE_DERIVED)
                .setStreamName("estimated_steps")
                .setAppPackageName("com.google.android.gms")
                .build();

        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(ESTIMATED_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(requireContext(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
                .readData(readRequest)
                .addOnSuccessListener (response -> {
                    for (Bucket bucket : response.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            dumpStepsCountDataSet(dataSet);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading data from Google Fit", e));
    }

    private void getCaloriesHistory(long startTime, long endTime) {
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(requireContext(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
                .readData(readRequest)
                .addOnSuccessListener (response -> {
                    for (Bucket bucket : response.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            dumpCaloriesDataSet(dataSet);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading data from Google Fit", e));
    }

    private void dumpStepsCountDataSet(DataSet dataSet) {
        if (dataSet.getDataPoints().size() > 0) {
            for (DataPoint dp : dataSet.getDataPoints()) {
                for (Field field : dp.getDataType().getFields()) {
                    try {
                        int totalSteps = dp.getValue(field).asInt();
                        tvStepCount.setText(totalSteps + "");
                        Double progress = (totalSteps / 8000.0) * 100;
                        progress_bar.setProgress(progress.intValue());
                    } catch (Exception e) {
                        tvStepCount.setText("0");
                        progress_bar.setProgress(0);
                    }
                }
            }
        } else {
            tvStepCount.setText("0");
            progress_bar.setProgress(0);
        }
    }

    private void getHeartRateHistory(long startTime, long endTime) {
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_HEART_RATE_BPM)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(requireContext(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
                .readData(readRequest)
                .addOnSuccessListener (response -> {
                    for (Bucket bucket : response.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            dumpHeartRateDataSet(dataSet);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "There was an error reading data from Google Fit", e));
    }

    private void getSleepData(long startTime, long endTime) {
        SessionReadRequest request = new SessionReadRequest.Builder()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .read(DataType.TYPE_SLEEP_SEGMENT)
                .readSessionsFromAllApps()
                .includeSleepSessions()
                .enableServerQueries()
                .build();

        Fitness.getSessionsClient(requireContext(), getGoogleAccount()).readSession(request)
                .addOnSuccessListener(sessionReadResponse -> {
                    long totalSleepDuration = 0;
                        for (Session session : sessionReadResponse.getSessions()) {
                            List<DataSet> dataSets = sessionReadResponse.getDataSet(session);
                            for (DataSet dataSet : dataSets) {
                                    for (DataPoint dataPoint : dataSet.getDataPoints()) {
                                        try {
                                            long durationMillis = dataPoint.getEndTime(TimeUnit.MILLISECONDS) - dataPoint.getStartTime(TimeUnit.MILLISECONDS);
                                            totalSleepDuration = totalSleepDuration + durationMillis;
                                            tvSleepHours.setText(TimeUnit.MILLISECONDS.toHours(totalSleepDuration) + "");
                                            Double progress = (TimeUnit.MILLISECONDS.toHours(totalSleepDuration) / 8.0) * 100;
                                            progress_bar_three.setProgress(progress.intValue());
                                        } catch (Exception e) {
                                            tvSleepHours.setText("0");
                                            progress_bar_three.setProgress(0);
                                        }
                                    }
                                }
                        }
                });
    }

    private void dumpHeartRateDataSet(DataSet dataSet) {
        if (dataSet.getDataPoints().size() > 0) {
            for (DataPoint dp : dataSet.getDataPoints()) {
                for (Field field : dp.getDataType().getFields()) {
                    try {
                        double bpm = dp.getValue(field).asFloat();
                        tvHeartRate.setText(String.format("%.0f", bpm));
                        Double progress = (bpm / 175.0) * 100;
                        progress_bar_two.setProgress(progress.intValue());
                    } catch (Exception e) {
                        tvHeartRate.setText("0");
                        progress_bar_two.setProgress(0);
                    }
                }
            }
        } else {
            tvHeartRate.setText("0");
            progress_bar_two.setProgress(0);
        }
    }

    private void dumpCaloriesDataSet(DataSet dataSet) {
        if (dataSet.getDataPoints().size() > 0) {
            for (DataPoint dp : dataSet.getDataPoints()) {
                for (Field field : dp.getDataType().getFields()) {
                    try {
                        double totalCalories = dp.getValue(field).asFloat();
                        tvCalories.setText(String.format("%.1f", totalCalories / 1000));
                        progress_bar_one.setProgress(progress_bar.getProgress());
                    } catch (Exception e) {
                        tvCalories.setText("0.0");
                        progress_bar_one.setProgress(0);
                    }
                }
            }
        } else {
            tvCalories.setText("0.0");
            progress_bar_one.setProgress(0);
        }
    }

    public void checkConnected() {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(requireContext(), serviceListener, BluetoothProfile.HEADSET);
    }

    private BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener()
    {
        @Override
        public void onServiceDisconnected(int profile) {
            tvIsWatchConnected.setText("Not Connected");
        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy)
        {
            for (BluetoothDevice device : proxy.getConnectedDevices()) {
                String name = device.getName();
                String address = device.getAddress();
                String threadName = Thread.currentThread().getName();
                Log.i("onServiceConnected", "|" + device.getName() + " | " + device.getAddress() + " | " + proxy.getConnectionState(device) + "(connected = "
                        + BluetoothProfile.STATE_CONNECTED + ")");

                tvIsWatchConnected.setText("Connected");
            }

            BluetoothAdapter.getDefaultAdapter().closeProfileProxy(profile, proxy);
        }
    };
}
