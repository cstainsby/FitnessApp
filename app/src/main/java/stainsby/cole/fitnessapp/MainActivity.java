package stainsby.cole.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // the three fragments accessible from the bottom nav menu
    private final HomeFragment homeFragment = new HomeFragment();
    private final UserInfoFragment userInfoFragment = new UserInfoFragment();
    private final ScheduleFragment scheduleFragment = new ScheduleFragment();
    private final NotSignedInFragment notSignedInFragment = new NotSignedInFragment();

    private FirebaseAuth mAuth;

    // the bottom navigation view
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize firebase components
        mAuth = FirebaseAuth.getInstance();

        // initially set the view to home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.home);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Log.d(TAG, "onNavigationItemSelected: navigating to home fragment");
                        setCurrentFragment(homeFragment);
                        return true;

                    case R.id.schedule:
                        Log.d(TAG, "onNavigationItemSelected: navigating to schedule fragment ");
                        setCurrentFragment(scheduleFragment);
                        return true;

                    case R.id.user:
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        // if the user isn't signed in they cant use the user tab, redirect them to not signed in fragment
                        if(currentUser == null) {
                            Log.d(TAG, "onNavigationItemSelected: starting authentication activity");
                            setCurrentFragment(notSignedInFragment);
                        } else {
                            Log.d(TAG, "onNavigationItemSelected: navigating to user info fragment");
                            setCurrentFragment(userInfoFragment);
                        }
                        return true;
                }
                Log.d(TAG, "onNavigationItemSelected: error, displaying error fragment");
                throwErrorFragment();
                return false;
            }
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .setReorderingAllowed(true)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    /**
     * this function will be used to show an error screen,
     */
    private void throwErrorFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // TODO: 12/27/2021 error fragment should take arguments and decide how to display the error
        //  pass in args here
        ErrorFragment errorFragment = new ErrorFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, errorFragment, null)
                .setReorderingAllowed(true)
                .addToBackStack(errorFragment.getTag())
                .commit();
    }
}