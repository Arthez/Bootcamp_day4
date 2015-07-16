package pl.droidsonroids.bootcamp.yo.service;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;


public class CustomInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
