package com.cn.runzhong.joke.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cn.runzhong.joke.adp.RandomTextJokeAdp;
import com.cn.runzhong.joke.bean.RandomBean;
import com.cn.runzhong.joke.common.JokeConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CN.
 */

public class TextJokeView extends BaseJokeView {
    private RandomTextJokeAdp textJokeAdp;
    public TextJokeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextJokeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("key", keyJoke);
        return map;
    }

    @Override
    public String getRequestTag() {
        return "TextJokeView";
    }

    @Override
    public void setAdapterData(List<RandomBean.ResultBean> resultBeanList) {
        if(textJokeAdp == null){
            textJokeAdp = new RandomTextJokeAdp(activity,resultBeanList);
            recyclerView.setIAdapter(textJokeAdp);
        }else{
            textJokeAdp.notifyDataSetChanged();
        }
    }

}
