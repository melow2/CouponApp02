package com.khs.couponapp02.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.story.notebook.view.activity.MainActivity;
import com.story.notebook.view.fragment.StoryFragment;

import java.lang.ref.WeakReference;

public class MainVM extends AndroidViewModel implements LifecycleObserver {

    private MutableLiveData<Boolean> splashTimeDelay = new MutableLiveData<>();
    public Handler handler = new SplashHandler(this);
    private static final int SPLASH_TIME_DELAY_IN_MD = 3000;
    private static final String TAG = MainVM.class.getSimpleName();
    private Runnable splashTimeDelayRunnable = () -> splashTimeDelay.postValue(true);
    public MainVM(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public MutableLiveData<Boolean> getSplashTimeDelay(){
        return splashTimeDelay;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(){
        handler.postDelayed(splashTimeDelayRunnable,SPLASH_TIME_DELAY_IN_MD);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        handler.removeCallbacks(splashTimeDelayRunnable);
    }

    public static final class SplashHandler extends Handler {
        private final WeakReference<MainVM> ref;

        private SplashHandler(MainVM mainVM) {
            ref = new WeakReference<>(mainVM);
        }
        @Override
        public void handleMessage(@NonNull Message msg) { }
    }

}
