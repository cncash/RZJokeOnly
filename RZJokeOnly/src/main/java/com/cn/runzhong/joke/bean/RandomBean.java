package com.cn.runzhong.joke.bean;

import java.io.Serializable;
import java.util.List;

public class RandomBean implements Serializable {

    public String reason;
    public int error_code;

    public List<ResultBean> result;


    public class ResultBean  implements Serializable{
        public String content;
        public String hashId;
        public String unixtime;
        public String url;

    }
}
