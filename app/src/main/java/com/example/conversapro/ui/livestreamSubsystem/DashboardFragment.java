package com.example.conversapro.ui.livestreamSubsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.conversapro.R;
import com.example.conversapro.databinding.FragmentDashboardBinding;
public class DashboardFragment extends Fragment {
    // Binding object for the fragment's layout
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Creating a ViewModel instance associated with this fragment
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        // Observing changes in the text LiveData and updating the TextView accordingly
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Setting click listener for a button in the layout
        binding.clickButton.setOnClickListener(v -> {
            // Use the Navigation Component to navigate to the StreamSetupFragment
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_startStream);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
