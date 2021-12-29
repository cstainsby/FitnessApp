package stainsby.cole.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * This fragment will be used to display when the user isn't signed in
 *      e.g. if the user is not signed in and trys to access the user page they should be directed to this page
 *  this page will let users access the user authentication and registration activity
 */
public class NotSignedInFragment extends Fragment{

    private static final String TAG = "NotSignedInFragment";

    private Button toAuthenticationButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotSignedInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotSignedInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotSignedInFragment newInstance(String param1, String param2) {
        NotSignedInFragment fragment = new NotSignedInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_not_signed_in, container, false);

        toAuthenticationButton = view.findViewById(R.id.toAuthenticationButton);
        toAuthenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: starting authentication activity");
                Intent intent = new Intent(getActivity(), UserAuthenticationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}