package com.cn.runzhong.joke.view;

import android.content.Context;
import android.util.AttributeSet;

import com.cn.runzhong.joke.adp.RandomPictureJokeAdp;
import com.cn.runzhong.joke.bean.RandomBean;
import com.cn.runzhong.joke.common.JokeConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CN.
 */

public class PictureJokeView extends BaseJokeView {
    private RandomPictureJokeAdp pictureJokeAdp;

    public PictureJokeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureJokeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("key", keyJoke);
        map.put("type", "pic");
        return map;
    }

    @Override
    public String getRequestTag() {
        return "PictureJokeView";
    }

    @Override
    public void setAdapterData(List<RandomBean.ResultBean> resultBeanList) {
        if(pictureJokeAdp == null){
            pictureJokeAdp = new RandomPictureJokeAdp(activity,resultBeanList);
            recyclerView.setIAdapter(pictureJokeAdp);
        }else{
            pictureJokeAdp.notifyDataSetChanged();
        }
    }

}
