package com.example.wilsonpreschool;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.wilsonpreschool.Adapter.SliderAdapter;
import com.example.wilsonpreschool.Domain.SliderItems;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPagerSlider;
    private ProgressBar progressBarBanner;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider imageSlider = view.findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.ss1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.sby, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.ss3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        return view;
    }
}