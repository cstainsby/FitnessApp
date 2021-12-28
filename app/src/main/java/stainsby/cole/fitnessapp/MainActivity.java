package stainsby.cole.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    // the three fragments accessible from the bottom nav menu
    private static final HomeFragment homeFragment = new HomeFragment();
    private static final UserInfoFragment userInfoFragment = new UserInfoFragment();
    private static final ScheduleFragment scheduleFragment = new ScheduleFragment();

    // the bottom navigation view
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initially set the view to home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.home);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        setCurrentFragment(homeFragment);
                        return true;

                    case R.id.schedule:
                        setCurrentFragment(scheduleFragment);
                        return true;

                    case R.id.user:
                        setCurrentFragment(userInfoFragment);
                        return true;
                }
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