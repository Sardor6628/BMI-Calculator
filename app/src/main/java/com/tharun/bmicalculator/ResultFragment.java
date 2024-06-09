package com.tharun.bmicalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.tharun.bmicalculator.databinding.FragmentResultBinding;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private float bmi;
    private String age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            bmi = getArguments().getFloat("bmi");
            age = getArguments().getString("age");
        }

        bmi = Math.round(bmi * 100) / 100.0f;

        binding.yourBmi.setText(String.valueOf(bmi));
        binding.condition.setText(Utils.checkAdult(Integer.parseInt(age), bmi));

        binding.recalculate.setOnClickListener(v -> requireActivity().onBackPressed());

        SpannableString spannable =
                new SpannableString("Suggestion: " + Utils.getSuggestions(bmi));
        ForegroundColorSpan fColor =
                new ForegroundColorSpan(Color.parseColor("#FE0049"));
        spannable.setSpan(fColor, 0, 11, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        binding.suggestion.setText(spannable);

        SpannableString spannable1 =
                new SpannableString(age + " (" + category(Integer.parseInt(age)) + ")");
        binding.ageTxt.setText(spannable1);

        binding.share.setOnClickListener(v -> shareContent(bmi));

        return binding.getRoot();
    }

    private String category(int age) {
        if (age >= 2 &&
                age <= 19) {
            return "Teenage";
        } else {
            return "Adult";
        }
    }

    private void shareContent(float bmi) {
        try {
            String strShareMessage = "Hello!! my BMI is " + bmi + "\n\n" +
                    "my current condition: " + binding.condition.getText() + "\n" +
                    "my age is: " + binding.ageTxt.getText() + "\n\n" +
                    "App suggested me that: " + binding.suggestion.getText();

            Intent i =
                    new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, strShareMessage);
            i.setType("text/plain");
            startActivity(Intent.createChooser(i, "Select App to Share with your friends"));

        } catch (Exception e) {
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
