package com.onyx.brightnesssample.model;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.device.SDMDevice;
import com.onyx.brightnesssample.R;
import com.onyx.brightnesssample.data.FunctionInfo;
import com.onyx.brightnesssample.databinding.FunctionContainerFragmentBinding;
import com.onyx.brightnesssample.databinding.FunctionItemLayoutBinding;
import com.onyx.brightnesssample.event.DialogTestEvent;
import com.onyx.brightnesssample.event.FunctionChangeEvent;
import com.onyx.brightnesssample.ui.view.BaseRecyclerView;
import com.onyx.brightnesssample.ui.view.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ContainerBundle {

    public Context mContext;
    public FunctionContainerFragmentBinding binding;
    public List<FunctionInfo> FunctionInfoList = new ArrayList<>();

    public ContainerBundle(Context context, FunctionContainerFragmentBinding binding) {
        mContext = context;
        this.binding = binding;
        initData();
        initView();
    }

    public void initData() {
        if(Device.currentDevice().hasFLBrightness(mContext)) {
            FunctionInfoList.add(new FunctionInfo(Constants.FL_BRIGHTNESS,"Brightness"));
        }
        if(Device.currentDevice().hasCTMBrightness(mContext)) {
            FunctionInfoList.add(new FunctionInfo(Constants.CTM_BRIGHTNESS,"Brightness"));
        }

        if (!Device.currentDevice().isTouchable(mContext)) {
            FunctionInfoList.add(new FunctionInfo(Constants.DPAD_TEST,"Dpad"));
            FunctionInfoList.add(new FunctionInfo(Constants.DIALOG_TEST_0,"Dialog1"));
        }

        if (!(Device.currentDevice() instanceof SDMDevice)) {
            FunctionInfoList.add(new FunctionInfo(Constants.VCOM_TEST,"Vcom_setting"));
        }

        for (int i = 0;  i < 7; i++) {
            FunctionInfoList.add(new FunctionInfo(Constants.UNDEFIND_TEST + i,new StringBuffer("  ").append(i).toString()));
        }
    }

    public void initView() {
        BaseRecyclerView.PageAdapter adapte = new BaseRecyclerView.PageAdapter() {
            @Override
            public int getRowCount() {
                return 4;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public int getDataCount() {
                return FunctionInfoList.size();
            }

            @Override
            public RecyclerView.ViewHolder onPageCreateViewHolder(ViewGroup parent, int viewType) {
                BaseViewHolder holder = new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.function_item_layout, parent, false));
                return holder;
            }

            @Override
            public void onPageBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                BaseViewHolder viewHolder = (BaseViewHolder) holder;
                FunctionItemLayoutBinding bind = viewHolder.getBind();
                bind.functionItemTitle.setOnClickListener(this);
                bind.functionItemTitle.setTag(position);
                viewHolder.bindTo(FunctionInfoList.get(position).title);
            }

            @Override
            public int getItemCount() {
                return FunctionInfoList.size();
            }

            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag == null) {
                    return;
                }

                int position = (int) tag;

                FunctionInfo functionInfo = FunctionInfoList.get(position);

                if ((functionInfo.tag & Constants.UNDEFIND_TEST) != 0) {
                    return;
                }

                if ((functionInfo.tag & Constants.DIALOG_TEST_0)  != 0) {
                    EventBus.getDefault().post(new DialogTestEvent(functionInfo.tag));
                } else {
                    EventBus.getDefault().post(new FunctionChangeEvent(0, functionInfo.tag));
                }

            }
        };

        binding.functionView.setNestedScrollingEnabled(true);
        binding.functionView.setLayoutManager(
                new GridLayoutManager(binding.functionView.getContext(), 1, RecyclerView.VERTICAL, false));
        binding.functionView.setAdapter(adapte);
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private final FunctionItemLayoutBinding binding;

        public BaseViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
        public FunctionItemLayoutBinding getBind() {
            return binding;
        }

        void bindTo(final String title) {
            binding.functionItemTitle.setText(title);
            binding.executePendingBindings();
        }
    }

}
