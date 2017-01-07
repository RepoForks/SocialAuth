package com.ebr163.socialauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ebr163.socialauth.facebook.FacebookClient;
import com.ebr163.socialauth.facebook.model.FacebookProfile;
import com.ebr163.socialauth.google.GooglePlusClient;
import com.ebr163.socialauth.google.GooglePlusProfile;
import com.ebr163.socialauth.instagram.InstagramClient;
import com.ebr163.socialauth.instagram.model.InstagramProfile;
import com.ebr163.socialauth.vk.VkClient;
import com.ebr163.socialauth.vk.model.VkProfile;
import com.google.android.gms.common.api.Status;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, VkClient.VkProfileLoadedListener, VkClient.VkLogOutListener {

    GooglePlusClient googlePlusClient;
    InstagramClient instagramClient;
    FacebookClient facebookClient;
    VkClient vkClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googlePlusClient = new GooglePlusClient(this, getString(R.string.googleClientId));
        instagramClient = new InstagramClient(this, getString(R.string.instagramRedirectUri), getString(R.string.instagramClientId));
        facebookClient = new FacebookClient(this);
        vkClient = new VkClient(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_plus:
                googlePlusClient.getProfile(new GooglePlusClient.GooglePlusProfileLoadedListener() {
                    @Override
                    public void onProfileLoaded(GooglePlusProfile googlePlusProfile) {
                        Toast.makeText(MainActivity.this, googlePlusProfile.toString(), Toast.LENGTH_SHORT).show();
                        toNextScreen();
                    }
                });
                break;
            case R.id.google_plus_logout:
                googlePlusClient.logOut(new GooglePlusClient.GooglePlusLogOutListener() {
                    @Override
                    public void logOut(Status status) {
                        Toast.makeText(MainActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.instagram:
                instagramClient.getProfile(new InstagramClient.InstagramProfileLoadedListener() {
                    @Override
                    public void onProfileLoaded(InstagramProfile instagramProfile) {
                        Toast.makeText(MainActivity.this, instagramProfile.toString(), Toast.LENGTH_SHORT).show();
                        toNextScreen();
                    }
                });
                break;
            case R.id.instagram_logout:
                instagramClient.logOut(new InstagramClient.InstagramLogOutListener() {
                    @Override
                    public void onLogOut() {
                        Toast.makeText(MainActivity.this, "instagram logout", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.facebook:
                facebookClient.getProfile(new FacebookClient.FacebookProfileLoadedListener() {
                    @Override
                    public void onProfileLoaded(FacebookProfile facebookProfile) {
                        Toast.makeText(MainActivity.this, "facebook", Toast.LENGTH_SHORT).show();
                        toNextScreen();
                    }
                });
                break;
            case R.id.facebook_logout:
                facebookClient.logOut(new FacebookClient.FacebookLogOutListener() {
                    @Override
                    public void onLogOut() {
                        Toast.makeText(MainActivity.this, "facebook logout", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.vk:
                vkClient.getProfile();
                break;
            case R.id.vk_logout:
                vkClient.logOut();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googlePlusClient.onActivityResult(requestCode, resultCode, data);
        instagramClient.onActivityResult(requestCode, resultCode, data);
        facebookClient.onActivityResult(requestCode, resultCode, data);
        vkClient.onActivityResult(requestCode, resultCode, data);
    }

    private void toNextScreen() {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        finish();
    }

    @Override
    public void logOutVk() {
        Toast.makeText(MainActivity.this, "vk logout", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVkProfileLoaded(VkProfile vkProfile) {
        Toast.makeText(MainActivity.this, "vk", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorVkProfileLoaded(VKError error) {
    }
}
