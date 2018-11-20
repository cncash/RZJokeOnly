package com.cn.runzhong.joke.adp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.runzhong.joke.ImageDetailActivity;
import com.cn.runzhong.joke.bean.RandomBean;
import com.cn.runzhong.joke.common.JokeConst;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CN.
 */

public abstract class BaseJokeAdp extends BaseQuickAdapter<RandomBean.ResultBean, BaseViewHolder> {
    protected Activity activity;

    public BaseJokeAdp(final Activity activity,int resLayoutId, final List<RandomBean.ResultBean> resultBeanList, final boolean isPicture) {
        super(resLayoutId,resultBeanList);
        this.activity = activity;
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isPicture) {
                    ViewCompat.setTransitionName(view, JokeConst.ELEMENT_NAME);
                    ActivityOptionsCompat optionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    activity, view, JokeConst.ELEMENT_NAME);
                    ActivityCompat.setExitSharedElementCallback(activity,
                            new android.support.v4.app.SharedElementCallback() {
                                @Override
                                public void onSharedElementEnd(List<String> sharedElementNames,
                                                               List<View> sharedElements, List<View>
                                                                       sharedElementSnapshots) {
                                    super.onSharedElementEnd(sharedElementNames, sharedElements,
                                            sharedElementSnapshots);
                                    for (final View view : sharedElements) {
                                        if (view instanceof SimpleDraweeView) {
                                            view.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            });
                    Intent intent = new Intent(activity, ImageDetailActivity.class);
                    intent.putExtra(JokeConst.URI, getData().get(position - 2));
                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
                }
            }
        });
    }

}
