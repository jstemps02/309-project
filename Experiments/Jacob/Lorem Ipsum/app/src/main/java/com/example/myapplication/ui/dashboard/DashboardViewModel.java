package com.example.myapplication.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pellentesque placerat ultricies velit. Ut vitae feugiat sapien, vel semper tellus. Pellentesque gravida lacus leo, vitae cursus enim imperdiet sit amet. Nullam aliquet, arcu nec cursus aliquet, lorem urna luctus est, a accumsan mi turpis nec ex. Vestibulum semper tincidunt erat, non dapibus ante finibus quis. Praesent posuere justo aliquam neque vestibulum, vel semper nunc vehicula. Cras at est convallis, mattis massa aliquet, ultrices dui. Donec ultricies rhoncus ipsum sed finibus. Nam tortor dolor, mattis vitae ultrices commodo, pharetra vitae eros. Nunc fermentum orci id efficitur dapibus. Aliquam erat volutpat. Vivamus eget dui et velit condimentum dictum porttitor nec leo. Phasellus nec nisl viverra, imperdiet turpis eget, lacinia leo. Vivamus vulputate elementum magna, ornare semper neque lobortis eu. Morbi eget ligula ac nulla egestas euismod nec et diam.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}