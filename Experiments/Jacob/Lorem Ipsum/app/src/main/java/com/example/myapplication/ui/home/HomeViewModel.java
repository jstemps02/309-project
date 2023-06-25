package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eros magna, tristique eu turpis malesuada, euismod rutrum libero. Sed quis enim sem. Donec dapibus ligula sit amet dolor tincidunt, non eleifend nibh ultrices. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Suspendisse vel interdum est. Aliquam efficitur turpis orci, nec fermentum enim tincidunt a. Etiam commodo accumsan diam vitae sagittis. Quisque ultrices risus id metus volutpat, eu euismod enim imperdiet.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}