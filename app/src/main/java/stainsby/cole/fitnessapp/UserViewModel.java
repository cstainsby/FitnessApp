package stainsby.cole.fitnessapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    // this user live data object will be used to hold data about the user
    private final MutableLiveData<User> user = new MutableLiveData<User>();

    /**
     * setter for user live data
     * @param user
     */
    public void setUser(User user) {
        this.user.setValue(user);
    }

    /**
     * getter for the live user data
     * @return
     */
    public LiveData<User> getUser() {
        return user;
    }

}
