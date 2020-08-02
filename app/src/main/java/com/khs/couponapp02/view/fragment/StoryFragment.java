package com.khs.couponapp02.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.story.notebook.R;
import com.story.notebook.databinding.FragmentStoryBinding;
import com.story.notebook.view.fragment.web.Bridge;
import com.story.notebook.view.fragment.web.StoryWebChromeClient;
import com.story.notebook.view.fragment.web.StoryWebViewClient;
import com.story.notebook.util.AES256Util;

import java.lang.ref.WeakReference;

public class StoryFragment extends BaseFragment<FragmentStoryBinding>{

    private Boolean isScoped = false;
    private static final int COUPON_TIME_DELAY_IN_MD = 2000;
    private StoryHandler handler = new StoryHandler(this);
    private WebView mWebview;
    private WebSettings mWebSettings;
    private static final String TAG = StoryFragment.class.getSimpleName();
    private static final String BASE_URL ="AY0P0cL/UJ0ISvdVRnBu1aBEwLNwiqi3QgDIa8wxCyuLw3Zh9HOnnyvOZZxnPYhU";
    private static final String SP_NAME = "pref";
    private static final String SP_KEY = "isScoped";

    private Runnable couponFragmentRunnable = () ->{
        if(true){
            loadWebView();
        }
    };


    private void loadWebView() {
        mBinding.svImage.setVisibility(View.INVISIBLE);
        mWebview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        mBinding.containerMain.addView(mWebview);
        mWebSettings.setJavaScriptEnabled(true);                                        // 자바스크립트 사용여부.
        mWebSettings.setSupportMultipleWindows(false);                                  // 새창 띄우기 허용 여부.
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false);                   // 자바스크립트 새창(멀티뷰) 띄우기 허용 여부.
        mWebSettings.setUseWideViewPort(true);                                          // 화면 사이즈 맞추기 허용 여부.
        mWebSettings.setSupportZoom(false);                                             // 화면 줌 허용 여부.
        mWebSettings.setBuiltInZoomControls(false);                                     // 화면 확대 축소 허용 여부.
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);     // 컨텐츠 사이즈 맞추기.
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);                           // 브라우저 캐시 허용 여부.
        mWebSettings.setDomStorageEnabled(true);                                        // 로컬 저장소 허용 여부.
        mWebSettings.setSaveFormData(true);                                             // 입력된 데이터 저장 여부.
        mWebview.addJavascriptInterface(new Bridge(mWebview),"Bridge");
        mWebview.setWebChromeClient(new StoryWebChromeClient(mBinding));                         // 웹뷰에 크롬 사용 허용, alert 등
        mWebview.setWebViewClient(new StoryWebViewClient(this));                        // 새창 열기 없이 웹뷰 내에서 다시 열기// 페이지 이동 원활.
        mWebview.loadUrl(AES256Util.decryption(BASE_URL)+getString(R.string.test_name));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindView(inflater,container, R.layout.fragment_story);
        mWebview = new WebView(requireContext());
        mWebSettings = mWebview.getSettings();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        SharedPreferences sp = requireContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        isScoped = sp.getBoolean(SP_KEY,false); // 수정.
        if(!isScoped){
            prepare(sp);
            // 확인이되면 바로 loadWebView();
        }
        handler.postDelayed(couponFragmentRunnable,COUPON_TIME_DELAY_IN_MD); // 수정.
        super.onActivityCreated(savedInstanceState);
    }

    private void prepare(SharedPreferences sp) {
        /* 조건 */
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(SP_KEY,true);
        editor.apply();
    }

    public boolean canGoback(){
        return this.mWebview !=null && this.mWebview.canGoBack();
    }

    public void goBack(){
        if(this.mWebview!=null){
            this.mWebview.goBack();
        }
    }

    private static final class StoryHandler extends Handler {
        private final WeakReference<StoryFragment> ref;

        private StoryHandler(StoryFragment fragment) {
            ref = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            StoryFragment fragment = ref.get();
            if(fragment!=null){

            }
        }
    }

}
