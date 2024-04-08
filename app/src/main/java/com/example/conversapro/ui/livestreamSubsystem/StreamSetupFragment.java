package com.example.conversapro.ui.livestreamSubsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.conversapro.R;

// Fragment responsible for setting up a live stream
public class StreamSetupFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stream_setup, container, false);


        EditText streamTitleEditText = view.findViewById(R.id.streamTitleEditText);

        // Finding the invite button in the layout
        Button inviteButton = view.findViewById(R.id.inviteButton);
        // Setting a click listener for the invite button
        inviteButton.setOnClickListener(v -> {
            // Navigating back to the previous fragment when the invite button is clicked
            NavHostFragment.findNavController(this).navigateUp();
        });

        return view;
    }

}
