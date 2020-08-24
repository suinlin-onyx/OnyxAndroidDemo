package com.onyx.brightnesssample.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.base.BaseSupportFragment;
import com.onyx.brightnesssample.model.ContainerBundle;
import com.onyx.brightnesssample.data.FunctionInfo;
import com.onyx.brightnesssample.databinding.FunctionContainerFragmentBinding;
import com.onyx.brightnesssample.event.DialogTestEvent;
import com.onyx.brightnesssample.ui.dialog.OnyxAlertDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FunctionContainerFragment extends BaseSupportFragment<FunctionContainerFragmentBinding> {

    public View root;
    public List<FunctionInfo> FunctionInfoList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.function_container_fragment;
    }

    public FunctionContainerFragment(Context context) {
        super(context);
    }

    public static FunctionContainerFragment newInstance(@Nullable Bundle args, Context context) {
        FunctionContainerFragment fragment = new FunctionContainerFragment(context);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.function_container_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        new ContainerBundle(getContext(), binding);
    }

    @Override
    public boolean onBackPressedSupport() {
        android.util.Log.d("arvin", "onBackPressedSupport");
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogTestEvent(DialogTestEvent event) {
        String test[] = new String[]{"test1", "test2", "test3", "test4"};
        AlertDialog.Builder builder = OnyxAlertDialog.instance(getContext()).builder;
        builder.setTitle("Dialog1 test");
        builder.setItems(test, null);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setNeutralButton(android.R.string.copy, null);
        builder.setPositiveButton(android.R.string.ok, null);
        OnyxAlertDialog.instance(getContext()).show();

    }


}
