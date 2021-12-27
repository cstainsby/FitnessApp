package stainsby.cole.fitnessapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CustomFragmentStateAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 3;

    public CustomFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new UserInfoFragment();
            case 2:
                return new ScheduleFragment();
            default:
                return new ErrorFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
