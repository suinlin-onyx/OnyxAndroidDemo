package com.onyx.brightnesssample.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BaseRecyclerView extends RecyclerView {

    private PageAdapter pageAdapter;

    public BaseRecyclerView(Context context) {
        super(context);
        init();
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setItemAnimator(null);
        setClipToPadding(true);
        setClipChildren(true);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (!(adapter instanceof PageAdapter)){
            throw new IllegalArgumentException("Please use PageAdapter!");
        }
        initAdapter((PageAdapter) adapter);
    }

    private void initRecycledViewPool() {
        int recycledViewSize = Math.max(5, pageAdapter.getRowCount() * pageAdapter.getColumnCount());
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, recycledViewSize);
        setRecycledViewPool(recycledViewPool);
    }

    private void initAdapter(PageAdapter adapter) {
        pageAdapter = adapter;
        initRecycledViewPool();
        initSpanCount();
    }

    private void initSpanCount() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            ((GridLayoutManager) layoutManager).setSpanCount(pageAdapter.getColumnCount());
        }

    }

    public static abstract class PageAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements OnClickListener{
        protected BaseRecyclerView baseRecyclerView;
        protected OnItemClickListener onItemClickListener;

        public abstract int getRowCount();
        public abstract int getColumnCount();
        public abstract int getDataCount();
        public abstract VH onPageCreateViewHolder(ViewGroup parent, int viewType);
        public abstract void onPageBindViewHolder(VH holder, int position);

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            baseRecyclerView = (BaseRecyclerView) parent;
            return onPageCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(final VH holder, final int position) {
            final int adapterPosition = holder.getAdapterPosition();
            final View view = holder.itemView;
            if (view != null){
                if (position < getDataCount()){
                    view.setVisibility(VISIBLE);
                    view.setFocusable(true);
                    view.setActivated(true);
                    onPageBindViewHolder(holder,adapterPosition);
                }else {
                    view.setFocusable(false);
                    view.setActivated(false);
                    view.setVisibility(INVISIBLE);
                }
            }
        }

    }

}
