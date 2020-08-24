package com.onyx.brightnesssample.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.onyx.android.sdk.utils.Debug;
import com.onyx.brightnesssample.event.BackToRootFragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


@SuppressLint("ValidFragment")
public abstract class BaseSupportFragment<T extends ViewDataBinding> extends SupportFragment {
    private static final String TAG = BaseSupportFragment.class.getSimpleName();
    private Context mContext;

    protected T binding;
    abstract protected int getLayoutId();

    public BaseSupportFragment(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    /**
     * Permission Related:
     * 1.Using EasyPermissions#hasPermissions(...) to check if the app already has the required permissions.
     * This method can take any number of permissions as its final argument.
     * 2.Requesting permissions with EasyPermissions#requestPermissions.
     * This method will request the system permissions and show the rationale string provided if necessary.
     * The request code provided should be unique to this request,
     * and the method can take any number of permissions as its final argument.
     * 3.Use rather AfterPermissionGranted annotation or override onPermissionsGranted/onPermissionsDenied to handle
     * user permission control.
     * <p>
     * example:com.onyx.android.libsetting.view.activity.WifiSettingActivity
     * ref link/more info:https://github.com/googlesamples/easypermissions/blob/master/README.md
     */


    public boolean isTopFragment() {
        ISupportFragment topFragment = getTopFragment();
        boolean isTopFragment = topFragment == this;
        Debug.d("current fragment is top = " + isTopFragment + ", topFragment = " + topFragment + ", " +
                "currentFragment = " + this);
        return isTopFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.d(getClass(), "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Debug.d(getClass(), "onActivityCreated");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Debug.d(getClass(), "onSaveInstanceState");
    }

    @Override
    public void onResume() {
        super.onResume();
        Debug.d(getClass(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Debug.d(getClass(), "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Debug.d(getClass(), "onDestroyView");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Debug.d(getClass(), "onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Debug.d(getClass(), "onHiddenChanged hidden = " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Debug.d(getClass(), "setUserVisibleHint isVisibleToUser = " + isVisibleToUser);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Debug.d(getClass(), "onLazyInitView");
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Debug.d(getClass(), "onSupportVisible");
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        Debug.d(getClass(), "onSupportInvisible");
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void pop() {
        super.pop();
        Debug.d(getClass(), "pop");
    }

    @Override
    public void popChild() {
        super.popChild();
        Debug.d(getClass(), "popChild");
    }

    public void startChild(ISupportFragment toFragment) {
        getSupportDelegate().startChild(toFragment);
        Debug.d(getClass(), "startChild");
    }

    public void startChildForResult(ISupportFragment toFragment, int requestCode) {
        getSupportDelegate().startChildForResult(toFragment, requestCode);
        Debug.d(getClass(), "startChildForResult");
    }

    public void startChildWithPop(ISupportFragment toFragment) {
        getSupportDelegate().startChildWithPop(toFragment);
        Debug.d(getClass(), "startChildWithPop");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }

    @Override
    public boolean onBackPressedSupport() {
        if (this.isVisible()) {
            EventBus.getDefault().post(new BackToRootFragmentEvent());
            return true;
        }
        return false;
    }

}