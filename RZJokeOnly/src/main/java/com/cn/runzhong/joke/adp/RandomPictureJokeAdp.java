package com.cn.runzhong.joke.adp;

import android.app.Activity;
import android.net.Uri;

import com.chad.library.adapter.base.BaseViewHolder;
import com.cn.runzhong.joke.R;
import com.cn.runzhong.joke.bean.RandomBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class RandomPictureJokeAdp extends BaseJokeAdp {
    public RandomPictureJokeAdp(Activity activity, List<RandomBean.ResultBean> resultBeanList) {
        super(activity,R.layout.adp_picture_joke, resultBeanList, true);
    }

    private void loadDataView(BaseViewHolder helper, RandomBean.ResultBean item) {
        SimpleDraweeView mSdvImageView = helper.getView(R.id.sdv_image_view);
        helper.setText(R.id.content, item.content);
        Uri uri = Uri.parse(item.url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mSdvImageView.setAspectRatio(1.8f);
//        mSdvImageView.setAspectRatio(1.33f);
        mSdvImageView.setController(controller);
    }


    @Override
    protected void convert(BaseViewHolder helper, RandomBean.ResultBean item) {
        loadDataView(helper, item);
    }
}
