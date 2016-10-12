package com.example.aaron.socialmedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "	YAnhxV7FoTsH0MPRNemutlSdS";
    private static final String TWITTER_SECRET = "	bEm6KUXQrfY7XpTVGqCG3ASFxYvx9Ln48dXFKB4u2QHZgoKVXX";



    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView resultLogin;
    TwitterLoginButton loginButtonTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();

        //Facebook
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions("email");

        loginButtonTwitter = (TwitterLoginButton) findViewById(R.id.twitter_login_button);


        resultLogin = ((TextView) findViewById(R.id.resultLogin));
        resultLogin.setText("Not Login yet");

        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                resultLogin.setText("Login with Facebook");
            }

            @Override
            public void onCancel() {
                resultLogin.setText("Cancel at login with Facebook");
            }

            @Override
            public void onError(FacebookException exception) {
                resultLogin.setText("Error at login with Facebook");
            }
        });


        //Callback twitter
        loginButtonTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                resultLogin.setText("Login with Twitter");
            }

            @Override
            public void failure(TwitterException exception) {
                resultLogin.setText("Failure with Twitter");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButtonTwitter.onActivityResult(requestCode, resultCode, data);
    }
}
