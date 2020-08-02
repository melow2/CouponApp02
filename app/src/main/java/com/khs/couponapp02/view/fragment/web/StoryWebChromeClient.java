package com.khs.couponapp02.view.fragment.web;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.story.notebook.databinding.FragmentStoryBinding;

public class StoryWebChromeClient extends WebChromeClient {

    private FragmentStoryBinding mBinding;
    private static final String TAG = StoryWebViewClient.class.getSimpleName();

    public StoryWebChromeClient(FragmentStoryBinding binding) {
        this.mBinding = binding;
    }

    // 페이지가 로딩되는 현재 진행 상황을 전해줌. ( 0~100 )
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(newProgress>=100){ }
    }

    // 현재 페이지의 새로운 favicon
    // favicon: 웹 브라우저의 URL이 표시되기 전에 특정 웹사이트와 관련된 16x16 픽셀 아이콘.
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    // 문서 제목의 변경.
    // <title> 제목 <title> 태그 사이의 값을 가져옴.
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    // 자바 스크립트 경고 대화 상자를 디스플레이한다고 클라이언트에게 알려줌.
    // 클라이언트가 true를 반환할 경우, WebView가 클라이언트가 대화 상자를 처리할 수 있다고 여김.
    // 클라이언트가 false를 반환할 경우 WebView는 실행을 계속.
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        final JsResult finalRes = result;
        new AlertDialog.Builder(view.getContext())
                .setMessage(message)
                .setPositiveButton("확인",(dialog,which)->{
                    result.confirm();
                })
                .setCancelable(false)
                .create()
                .show();
        return true;
    }

    // 확인 대화 상자를 디스플레이 한다고 클라이언트에게 알려줌.
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        final JsResult finalRes = result;
        new AlertDialog.Builder(view.getContext())
                .setTitle("대화상자")
                .setMessage(message)
                .setPositiveButton("확인",(dialog,which)-> {
                    result.confirm();
                })
                .setNegativeButton("취소",(dialog, which) ->{
                    result.cancel();
                })
                .create()
                .show();
        return true;
    }
}
