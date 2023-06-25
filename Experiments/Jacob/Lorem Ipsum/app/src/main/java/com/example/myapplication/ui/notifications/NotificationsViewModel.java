package com.example.myapplication.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Integer ullamcorper, dui ut varius sagittis, velit leo lobortis tellus, tempor mollis ipsum nunc pharetra neque. Etiam lobortis fermentum nisl, in venenatis risus consequat nec. Pellentesque mollis sapien sapien. Integer a quam in ligula pretium ullamcorper vitae vel augue. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus sollicitudin consectetur ultrices. Suspendisse viverra finibus leo, vitae volutpat mi accumsan eget. Morbi malesuada varius elit. Interdum et malesuada fames ac ante ipsum primis in faucibus. In mollis enim nibh, vitae vulputate nisi elementum at. Aliquam vel tortor a nisi iaculis sodales vel in lorem.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}