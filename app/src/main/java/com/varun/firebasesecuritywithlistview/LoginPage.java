package com.varun.firebasesecuritywithlistview;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {

    ImageView LoginImage;
    Button HomeButton,loginButton;
    TextView RegistrationTextView, ForgotPasswordTextView;
    EditText LoginPassword, LoginEmail;

    FirebaseAuth mAuth;

    SignInButton signInButton;
    GoogleSignInClient mgoogleSignInClient;
    public static final int RC_SIGN_IN = 123;
    private static final String EMAIL = "email";

    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_page);

        LoginImage = findViewById(R.id.LoginImage);
        HomeButton = findViewById(R.id.Loginhomebutton);
        RegistrationTextView = findViewById(R.id.RegistrationTextView);
        ForgotPasswordTextView = findViewById(R.id.ForgotPasswordTextView);
        LoginPassword = findViewById(R.id.LoginPassword);
        LoginEmail = findViewById(R.id.LoginEmail);
        signInButton = findViewById(R.id.googleSignIn);
        loginButton = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {

            }

        });

        LoginImage.setOnClickListener(view -> {
            startActivity(new Intent(LoginPage.this, RegistrationPage.class));
        });

        HomeButton.setOnClickListener(view -> {
            loginUser();
        });

        RegistrationTextView.setOnClickListener(view -> {
            startActivity(new Intent(LoginPage.this, RegistrationPage.class));
        });

        ForgotPasswordTextView.setOnClickListener(view -> {
            startActivity(new Intent(LoginPage.this, ForgotPasswordPage.class));
        });

        signInButton.setOnClickListener(view -> {
            signIn();
        });

    }

    private void signIn() {
        Intent signIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void requestGoogleSinnIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onActivityResult(int requestCode1, int resultCode1, @Nullable Intent data1,int requestCode2, int resultCode2, Intent data2) {
//        callbackManager.onActivityResult(requestCode2, resultCode2, data2);
//        super.onActivityResult(requestCode1, resultCode1, data1, requestCode2, resultCode2, data2);
//
//        if (requestCode1 == RC_SIGN_IN){
//            Task<GoogleSignInAccount>task = GoogleSignIn.getSignedInAccountFromIntent(data1);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                firebaseAuthWithGoogle(account.getIdToken());
//
//            }catch (ApiException e){
//                Toast.makeText(LoginPage.this,"Failed"+ e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void firebaseAuthWithGoogle(String account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account,null);
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginPage.this,HomePage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginPage.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser(){
        String email = LoginEmail.getText().toString();
        String pass = LoginPassword.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)){
            Toast.makeText(LoginPage.this,"Enter Details", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if (mAuth.getCurrentUser().isEmailVerified()){
                            startActivity(new Intent(LoginPage.this,HomePage.class));
                        }
                        else{
                            Toast.makeText(LoginPage.this,"Un-Identified User",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginPage.this,"Login Failed "+task.getException()
                                .getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginPage.this, "Authentication Succeeded.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}