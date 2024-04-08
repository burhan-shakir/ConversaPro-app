package com.example.conversapro.ui.dashboard;

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

public class StreamSetupFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stream_setup, container, false);


        EditText streamTitleEditText = view.findViewById(R.id.streamTitleEditText);


        Button inviteButton = view.findViewById(R.id.inviteButton);
        inviteButton.setOnClickListener(v -> {


            NavHostFragment.findNavController(this).navigateUp();
        });

        return view;
    }

}
