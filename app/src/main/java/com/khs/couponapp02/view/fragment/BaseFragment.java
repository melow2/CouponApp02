package com.khs.couponapp02.view.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

/**
 * @auther hyeoksin
 * @since
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {
    B mBinding;

    protected final void bindView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull int layout){
        mBinding = DataBindingUtil.inflate(inflater,layout,container,false);
    }
}
