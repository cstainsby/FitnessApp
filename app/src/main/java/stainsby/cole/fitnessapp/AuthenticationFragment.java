package stainsby.cole.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AuthenticationFragment extends Fragment {

    private static final String TAG = "AuthenticationFragment";

    private TextView titleTextView;
    private Button continueButton;
    private Button backButton;

    private List<Map<String, String>> data;

    private UserViewModel userModel;

    public AuthenticationFragment() {
        // create data structure to be passed back to UserAuthenticationActivity
        data = new ArrayList<>();

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userModel = new UserViewModel().get(UserViewModel.class);
    }

    /**
     * when the fragment is stopped, it should save the data that is available to firebase
     */
    @Override
    public void onStop() {
        super.onStop();

        saveDataToFirebase();
    }

    protected abstract void saveDataToFirebase();

    public
}
