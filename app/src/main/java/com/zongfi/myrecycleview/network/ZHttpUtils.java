package com.zongfi.myrecycleview.network;

import android.app.AlertDialog;
import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by ZHZEPHI on 2015/10/9.
 */
public class ZHttpUtils {

    Context context;
    String url;
    NetworkListener networkListener;

    public ZHttpUtils(Context context,String url, NetworkListener networkListener){
        this.context = context;
        this.url = url;
        this.networkListener = networkListener;
    }

    public void execute(){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                networkListener.onListener(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                new AlertDialog.Builder(context).setTitle("错误").setMessage(msg+"("+error.getExceptionCode()+")").setNegativeButton("OK",null).show();
            }
        });
    }

    public interface NetworkListener{
        void onListener(String data);
    }

}
