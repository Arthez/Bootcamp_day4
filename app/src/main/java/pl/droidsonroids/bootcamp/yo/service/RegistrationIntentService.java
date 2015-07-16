package pl.droidsonroids.bootcamp.yo.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import pl.droidsonroids.bootcamp.yo.BuildConfig;
import pl.droidsonroids.bootcamp.yo.Constants;
import pl.droidsonroids.bootcamp.yo.api.ApiService;
import pl.droidsonroids.bootcamp.yo.model.User;


public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegistrationIS";
    private String mUserName;
    private SharedPreferences sharedPreferences;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mUserName = intent.getAction();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {

            synchronized (TAG) {

                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(BuildConfig.GCM_SENDER_ID,
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                Log.i(TAG, "GCM Registration Token: " + token);
                Log.i(TAG, "GCM Registration ID: " + mUserName);
                sendRegistrationToServer(token);
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
        }

    }

    private void sendRegistrationToServer(String token) {
        User user = ApiService.API_SERVICE.register(mUserName, token);
        sharedPreferences.edit().putInt(Constants.KEY_USER_ID, user.getId()).apply();
    }


}
