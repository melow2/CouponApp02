package com.khs.couponapp02.view.activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.story.notebook.R;


public abstract class BaseActivity<Z extends ViewDataBinding> extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public Z mBinding;
    public Toolbar toolbar;

    protected final void bindView(int layout){
        mBinding = DataBindingUtil.setContentView(this,layout);
    }

    protected static void startActivityAnimation(@NonNull Context context){
        if(context instanceof AppCompatActivity){
            ((AppCompatActivity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 슬라이드 애니메이션.
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
