package stainsby.cole.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "UserRegistrationActivity";

    private FirebaseAuth mAuth;

    private TextView titleTextView;
    private Button continueButton;
    private Button backButton;

    // TODO: 12/29/2021 Is there a better solution than this?
    public static final int SIGN_IN = 0;
    public static final int REGISTER = 1;

    private final RequiredUserInfoFrag requiredInfoFragment = new RequiredUserInfoFrag();
    private final OptionalUserInfoFrag optionalUserInfoFragment = new OptionalUserInfoFrag();

    // keep track of authType
    private int authType;

    // the user object we will be populating and sending to firebase
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        // xml view elements
        titleTextView = findViewById(R.id.userAuthenticationTitle);
        
        continueButton = findViewById(R.id.continueAuthenticationButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if the back button is pressed
                if(requiredInfoFragment != null && requiredInfoFragment.isVisible()) {
                    Log.d(TAG, "onClick: going to optional user info form");
                    setCurrentFragment(optionalUserInfoFragment);
                    continueButton.setText("Finish");

                    // add all required data to the new user object
                    user.setFirstName();
                    user.setLastName();
                    user.setEmail();
                    user.setPassword();
                }
                else if (optionalUserInfoFragment != null && optionalUserInfoFragment.isVisible()) {
                    Log.d(TAG, "onClick: going back to home fragment in main activity");
                    // send user back to home screen
                    // all user data should be saved to firebase



                    Intent intent = new Intent(UserAuthenticationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d(TAG, "onClick: Error on back button");
                    // TODO: 12/29/2021 figure out error frag/activity
                }
            }
        });
        
        backButton = findViewById(R.id.backAuthenticationButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if the back button is press
                if(requiredInfoFragment != null && requiredInfoFragment.isVisible()) {
                    Log.d(TAG, "onClick: going back to home fragment in main activity");
                    // send user back to home screen
                    // all data should be wiped, no update to firebase needed
                    Intent intent = new Intent(UserAuthenticationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (optionalUserInfoFragment != null && optionalUserInfoFragment.isVisible()) {
                    Log.d(TAG, "onClick: going back to required info frag");
                    setCurrentFragment(requiredInfoFragment);
                    continueButton.setText("Continue");
                }
                else {
                    Log.d(TAG, "onClick: Error on back button");
                    // TODO: 12/29/2021 figure out error frag/activity
                }
            }
        });

        // firebase elements
        mAuth = FirebaseAuth.getInstance();

        // read in intent data
        Intent data = getIntent();
        this.authType = data.getIntExtra("authType", -1);

        switch(authType) {
            case SIGN_IN:
                titleTextView.setText("Sign In");
                // if we are just signing in, no need to collect the optional data
                continueButton.setText("Finish");
            case REGISTER:
                titleTextView.setText("Register");
        }

        setCurrentFragment(requiredInfoFragment);

        user = new User();
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .setReorderingAllowed(true)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    /**
     * this function will check all data within user object to make sure if it is valid
     * blocks advancement through forms if some of the data is invalid
     * @return
     */
    private boolean validateUserInput() {
        // TODO: 12/29/2021  
        return true;
    }

    private Fragment getCurrentFragment(){
        List<Fragment> backStackFragments = getSupportFragmentManager().getFragments();

        if(!backStackFragments.isEmpty()) {
            int size = backStackFragments.size();

            Log.d(TAG, "getCurrentFragment: Current fragment is " + backStackFragments.get(size - 1));
            return backStackFragments.get(size - 1);
        }
        else {
            // if nothing has been moved to, nothing on back stack so required info is the current frag.
            Log.d(TAG, "getCurrentFragment: Current fragment is " + requiredInfoFragment);
            return requiredInfoFragment;
        }
    }

    private void isRegistered(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        //TODO isRegistered = task.getResult().getSignInMethods().isEmpty();
                    }
                });
    }

    /**
     * sign in user with given email and password
     * should only be called when email is already registered in database
     */
    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(UserAuthenticationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * register the user with the given information
     * following the required information, the user will then be sent to a page to input their "optional" information
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     */
    private void registerUser(String email, String password, String firstName, String lastName) {

        // TODO: 12/28/2021 add progress bar  
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(firstName, lastName, email, password);

                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(UserAuthenticationActivity.this, "User has been registered", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(UserAuthenticationActivity.this, "Falied to register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    /**
     * Build ActionCodeSettings which will provide firebase instructions when making an email link
     */
    private void buildActionCodeSettings() {
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "stainsby.cole.fitnessapp",
                                true, /* installIfNotAvailable */
                                "30"    /* minimumVersion */)
                        .build();
    }

    private String getUserEmail() {
        String email = "";
        return email;
    }

    /**
     * send authentication link to email based on action code settings
     * @param email
     * @param actionCodeSettings
     */
    private void sendLinkToEmail(String email, ActionCodeSettings actionCodeSettings) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}