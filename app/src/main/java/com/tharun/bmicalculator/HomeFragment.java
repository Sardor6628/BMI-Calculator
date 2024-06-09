package com.tharun.bmicalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.tharun.bmicalculator.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private float height = 0f;
    private float weight = 0f;
    private int countWeight = 50;
    private int countAge = 19;
    private boolean chosen = true;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents() {
        binding.Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String ht = progress + getResources().getString(R.string.cm);
                binding.heightTxt.setText(ht);
                height = progress / 100f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        binding.weightPlus.setOnClickListener(v -> binding.weightTxt.setText(String.valueOf(countWeight++)));
        binding.weightMinus.setOnClickListener(v -> binding.weightTxt.setText(String.valueOf(countWeight--)));
        binding.agePlus.setOnClickListener(v -> binding.age.setText(String.valueOf(countAge++)));
        binding.ageMinus.setOnClickListener(v -> binding.age.setText(String.valueOf(countAge--)));

        binding.calculate.setOnClickListener(v -> {
            if (!chosen) {
                if (height == 0f) {
                    Toast.makeText(requireContext(), "Height cannot be Zero, Try again", Toast.LENGTH_SHORT).show();
                } else {
                    weight = Float.parseFloat(binding.weightTxt.getText().toString());
                    calculateBMI(binding.age.getText().toString());
                }
            } else {
                Toast.makeText(requireContext(), "Choose your gender", Toast.LENGTH_SHORT).show();
            }
        });

        binding.cardViewMale.setOnClickListener(v -> {
            if (chosen) {
                binding.maleTxt.setTextColor(Color.parseColor("#FFFFFF"));
                binding.maleTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male_white, 0, 0);
                binding.cardViewFemale.setEnabled(false);
                chosen = false;
            } else {
                binding.maleTxt.setTextColor(Color.parseColor("#8D8E99"));
                binding.maleTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male, 0, 0);
                binding.cardViewFemale.setEnabled(true);
                chosen = true;
            }
        });

        binding.cardViewFemale.setOnClickListener(v -> {
            if (chosen) {
                binding.femaleTxt.setTextColor(Color.parseColor("#FFFFFF"));
                binding.femaleTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female_white, 0, 0);
                binding.cardViewMale.setEnabled(false);
                chosen = false;
            } else {
                binding.femaleTxt.setTextColor(Color.parseColor("#8D8E99"));
                binding.femaleTxt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female, 0, 0);
                binding.cardViewMale.setEnabled(true);
                chosen = true;
            }
        });
    }

    private void calculateBMI(String age) {
        if (height == 0f || weight == 0f) {
            Toast.makeText(requireContext(), "Height and Weight cannot be Zero, Try again", Toast.LENGTH_SHORT).show();
            return;
        }
        float bmi = weight / (height * height);
        Bundle bundle = new Bundle();
        bundle.putFloat("bmi", bmi);
        bundle.putString("age", age);
        NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_resultFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
