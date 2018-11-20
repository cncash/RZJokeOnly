package com.cn.runzhong.joke.adp;

import android.app.Activity;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cn.runzhong.joke.R;
import com.cn.runzhong.joke.bean.RandomBean;

import java.util.List;


public class RandomTextJokeAdp extends BaseJokeAdp {

    public RandomTextJokeAdp(final Activity activity, final List<RandomBean.ResultBean> resultBeanList) {
        super(activity, R.layout.adp_txt_joke,resultBeanList, false);
    }


    private void loadDataView(BaseViewHolder helper, RandomBean.ResultBean item) {
        helper.setText(R.id.tv_content, item.content);
    }

    @Override
    protected void convert(BaseViewHolder helper, RandomBean.ResultBean item) {
        loadDataView(helper, item);
    }
}
