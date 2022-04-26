package sa.med.imc.myimc;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import butterknife.BindView;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;


import sa.med.imc.myimc.Utils.LocaleHelper;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!isTaskRoot()) {
            finish();
            return;
        }
        SharedPreferencesUtils.getInstance(SplashActivity.this).setValue(Constants.KEY_VIDEO_PHYSICIAN, false);
        SharedPreferencesUtils.getInstance(SplashActivity.this).setValue(Constants.KEY_NAV_CLASS, false);
        String lang = SharedPreferencesUtils.getInstance(SplashActivity.this).getValue(Constants.KEY_LOGIN_LANGUAGE, Constants.ENGLISH);

        SharedPreferencesUtils.getInstance(SplashActivity.this).setValue(Constants.KEY_LANGUAGE, lang);
        LocaleHelper.setLocale(this, lang);

        findViewById(R.id.imcHospital).setOnClickListener(view -> {
            moveToNextScreen(1);
        });

        findViewById(R.id.firstClinicHospital).setOnClickListener(view -> {
            moveToNextScreen(4);
        });
    }

    private void moveToNextScreen(int selectedHospital) {
        SharedPreferencesUtils.getInstance(SplashActivity.this).setValue(Constants.SELECTED_HOSPITAL, selectedHospital);
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}