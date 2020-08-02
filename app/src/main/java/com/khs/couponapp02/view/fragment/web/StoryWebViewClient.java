package com.khs.couponapp02.view.fragment.web;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.story.notebook.view.fragment.StoryFragment;

public class StoryWebViewClient extends WebViewClient {

    private static final String TAG = StoryWebViewClient.class.getSimpleName();
    private ProgressDialog mProgress;
    private StoryFragment mFragment;

    public StoryWebViewClient(StoryFragment fragment) {
        this.mFragment = fragment;
        setProgressDialog();
    }

    private void setProgressDialog() {
        mProgress = new ProgressDialog(mFragment.getActivity());
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setMessage("연결중입니다..");
        mProgress.setCancelable(false);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        mProgress.show();
        view.loadUrl(url);
        if(mProgress.isShowing()){
            mProgress.dismiss();
        }
        return true;
    }

    // 페이지 로딩이 완료될 떄까지 여러번 호출.
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(removeElement(view,url), url);
    }

    private WebView removeElement(WebView view, String url) {

        view.loadUrl("javascript:function removeElementById(elementId){\n" +
                "    if(document.getElementById(elementId)){\n" +
                "      var element = document.getElementById(elementId);\n" +
                "      var parentNode = element.parentNode;\n" +
                "      parentNode.removeChild(header);\n" +
                "    }\n" +
                "}\n");

        view.loadUrl("javascript:function removeElementByClass(elementClass){\n" +
                "    if(document.getElementsByClassName(elementClass)){\n" +
                "      var element = document.getElementsByClassName(elementClass);\n" +
                "      for(var i=0; i<element.length; i++) {\n" +
                "        element[i].remove();\n" +
                "      }\n" +
                "    }\n" +
                "}\n");

        view.loadUrl("javascript:removeElementById('header');");                // 상단메인 제거.
        view.loadUrl("javascript:removeElementByClass('path');");               // 경로 제거.
        view.loadUrl("javascript:removeElementByClass('subtitle');");           // 서브타이틀 제거.
        view.loadUrl("javascript:removeElementByClass('lately_coupon');");      // 최근 본 쿠폰 제거.

        return view;
    }


    // 페이지 로딩이 시작됨. 한번만 호출.
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgress.show();;
        super.onPageStarted(view, url, favicon);
    }

    // 페이지 로딩 완료.
    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:window.Bridge.getHtml(document.getElementsByTagName('body')[0].innerHTML);");
        if(mProgress.isShowing())
            mProgress.dismiss();
        super.onPageFinished(view, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);

        switch(error.getErrorCode()) {
            case ERROR_AUTHENTICATION: break;                        // 서버에서 사용자 인증 실패
            case ERROR_BAD_URL: break;                               // 잘못된 URL
            case ERROR_CONNECT: break;                               // 서버로 연결 실패
            case ERROR_FAILED_SSL_HANDSHAKE: break;                  // SSL handshake 수행 실패
            case ERROR_FILE: break;                                  // 일반 파일 오류
            case ERROR_FILE_NOT_FOUND: break;                        // 파일을 찾을 수 없습니다
            case ERROR_HOST_LOOKUP: break;                           // 서버 또는 프록시 호스트 이름 조회 실패
            case ERROR_IO: break;                                    // 서버에서 읽거나 서버로 쓰기 실패
            case ERROR_PROXY_AUTHENTICATION: break;                  // 프록시에서 사용자 인증 실패
            case ERROR_REDIRECT_LOOP: break;                         // 너무 많은 리디렉션
            case ERROR_TIMEOUT: break;                               // 연결 시간 초과
            case ERROR_TOO_MANY_REQUESTS: break;                     // 페이지 로드중 너무 많은 요청 발생
            case ERROR_UNKNOWN: break;                               // 일반 오류
            case ERROR_UNSUPPORTED_AUTH_SCHEME: break;               // 지원되지 않는 인증 체계
            case ERROR_UNSUPPORTED_SCHEME: break;                    // URI가 지원되지 않는 방식
        }

    }
}
