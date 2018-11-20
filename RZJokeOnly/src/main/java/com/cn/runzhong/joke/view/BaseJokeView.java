package com.cn.runzhong.joke.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.cn.runzhong.joke.R;
import com.cn.runzhong.joke.bean.RandomBean;
import com.cn.runzhong.joke.common.HttpRequest;
import com.cn.runzhong.joke.common.JokeConst;
import com.cn.runzhong.joke.util.ADIRecyclerViewUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by CN.
 */

public abstract class BaseJokeView extends RelativeLayout implements OnRefreshListener, OnLoadMoreListener {
    protected IRecyclerView recyclerView;
    protected Activity activity;

    private List<RandomBean.ResultBean> dataList;
    private OnLoadListener onLoadListener;
    private boolean isLoadMore;
    protected String keyJoke;
    public BaseJokeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(attrs);
        initView();
    }

    public BaseJokeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(attrs);
        initView();
    }
    private void initParams(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.JokeView);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.JokeView_key) {
                keyJoke = typedArray.getString(R.styleable.JokeView_key);
            }
        }
        typedArray.recycle();
    }
    public void loadData(Activity activity) {
        this.activity = activity;
        onRefresh();
    }

    private void initView() {
        dataList = new ArrayList<>();
        inflate(getContext(), R.layout.layout_joke, this);
        recyclerView = findViewById(R.id.recyclerView);
        ADIRecyclerViewUtil.setVerticalLinearLayoutManager(getContext(), recyclerView);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        if (dataList == null) {
            dataList = new ArrayList<>();
        } else {
            dataList.clear();
        }
        setAdapterData(dataList);
        loadData();
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        loadData();
    }


    public void loadData() {
        HttpRequest.requestNoNeedAnalyze(JokeConst.BASE_URL_JOKE_RANDOM, getParams(), getRequestTag(), new HttpRequest.HttpResponseSimpleListener() {

            @Override
            public void onNetError(String url, int errorCode) {
                Toast.makeText(getContext(),getContext().getString(R.string.error_joke_load),Toast.LENGTH_SHORT).show();
                if (onLoadListener != null) {
                    onLoadListener.onError(isLoadMore);
                }
                ADIRecyclerViewUtil.judgePullRefreshStatus(recyclerView, Integer.MAX_VALUE);
            }

            @Override
            public void onSuccess(String url, String responseData) {
                Log.v("CN","responseData:"+responseData);
                RandomBean mRandomBean = new Gson().fromJson(responseData, RandomBean.class);
                if (mRandomBean != null && mRandomBean.result != null && mRandomBean.result.size() > 0) {
                    List<RandomBean.ResultBean> resultBeanList = mRandomBean.result;
                    dataList.addAll(resultBeanList);
                }
                if (onLoadListener != null) {
                    onLoadListener.onSuccess();
                }
                setAdapterData(dataList);
                ADIRecyclerViewUtil.judgePullRefreshStatus(recyclerView, Integer.MAX_VALUE);
            }
        });
    }


    public abstract Map<String, String> getParams();

    public abstract String getRequestTag();

    public abstract void setAdapterData(List<RandomBean.ResultBean> multiItemEntityList);

    public OnLoadListener getOnLoadListener() {
        return onLoadListener;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public interface OnLoadListener {
        void onSuccess();

        void onError(boolean isLoadMore);
    }
}
