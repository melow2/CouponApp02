package com.khs.couponapp02.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.awesome.admanager.AdManager;
import com.awesome.admanager.OnInterstitialAdLoadListener;
import com.awesome.admanager.data.Ad;
import com.awesome.admanager.data.AdName;
import com.awesome.admanager.data.AdType;
import com.story.notebook.R;
import com.story.notebook.databinding.ActivityMainBinding;
import com.story.notebook.view.dialog.StoryCloseDialog;
import com.story.notebook.view.fragment.StoryFragment;
import com.story.notebook.util.AES256Util;
import com.story.notebook.viewmodel.MainVM;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private StoryFragment storyFragment;
    private AdManager interstitial, banner;
    private StoryCloseDialog closeDialog;
    private MainVM mainVM;

    private Observer<Boolean> splashTimeDelayObserver = aBoolean ->{
        if(aBoolean!=null && aBoolean) {
            mBinding.imgSplash.setVisibility(View.GONE);
            setStoryFragment();
            interstitialAd();
            bottomBannerAd();
            popUpAd();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView(R.layout.activity_main);
        init();
    }


    private void init() {
        mainVM = new ViewModelProvider(this).get(MainVM.class);
        this.getLifecycle().addObserver(mainVM);
        mainVM.getSplashTimeDelay().observe(this,splashTimeDelayObserver);
    }

    private void setStoryFragment() {
        storyFragment = new StoryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_coupon,storyFragment)
                .commitNow();
    }

    private void interstitialAd() {
        interstitial = new AdManager.Builder(this)
                .setAdmangerTest(true)
                .setBaseUrl(AES256Util.decryption(getString(R.string.interstitial_order_url)))
                .setAd(new Ad(AdName.ADMOB, AdType.INTERSTITIAL, getString(R.string.admob_interstitial_id)))
                .setAd(new Ad(AdName.CAULY, AdType.INTERSTITIAL, getString(R.string.cauly_media_code)))
                .setAd(new Ad(AdName.MANPLUS, AdType.INTERSTITIAL, getString(R.string.mezzo_media_publisher_code) + ',' + getString(R.string.mezzo_media_media_code) + ',' + getString(R.string.mezzo_media_interstitial_section_code)))
                .setAd(new Ad(AdName.FACEBOOK, AdType.INTERSTITIAL, getString(R.string.facebook_interstitial_id)))
                .setOnInterstitialAdLoadListener(new OnInterstitialAdLoadListener() {
                    @Override
                    public void onAdLoaded() {
                        interstitial.showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad() {
                    }
                })
                .build();
        interstitial.load();
    }

    private void bottomBannerAd() {
        banner = new AdManager.Builder(this)
                .setAdmangerTest(true)
                .setBaseUrl(AES256Util.decryption(getString(R.string.bottom_banner_order_url)))
                .setContainer(mBinding.lytBanner)
                .setAd(new Ad(AdName.ADMOB, AdType.BANNER, getString(R.string.admob_bottom_banner_id)))
                .setAd(new Ad(AdName.CAULY, AdType.BANNER, getString(R.string.cauly_media_code)))
                .setAd(new Ad(AdName.MANPLUS, AdType.BANNER, getString(R.string.mezzo_media_publisher_code) + ',' + getString(R.string.mezzo_media_media_code) + ',' + getString(R.string.mezzo_media_interstitial_section_code)))
                .setAd(new Ad(AdName.FACEBOOK, AdType.BANNER, getString(R.string.facebook_bottom_banner_id)))
                .build();
        banner.load();
    }

    private void popUpAd() {
        closeDialog = new StoryCloseDialog(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            closeDialog.create();
        }
        closeDialog.addButtonListener(new StoryCloseDialog.ButtonEvent() {
            @Override
            public void onPositiveBtn() {
                closeDialog.dismiss();
                finish();
            }

            @Override
            public void onNegativeBtn() {
                closeDialog.dismiss();
            }
        });
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && this.storyFragment!=null && this.storyFragment.canGoback()){
            this.storyFragment.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        if(this.storyFragment!=null && this.storyFragment.canGoback()){
            this.storyFragment.goBack();
        }else {
            if(closeDialog!=null){
                closeDialog.show();
            }else{
                super.onBackPressed();
            }
        }
    }
}