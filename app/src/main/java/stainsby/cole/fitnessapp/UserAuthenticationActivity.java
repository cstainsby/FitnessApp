package stainsby.cole.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

public class UserAuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "UserRegistrationActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        mAuth = FirebaseAuth.getInstance();
    }

    private boolean isRegistered(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        isRegistered = task.getResult().getSignInMethods().isEmpty();
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