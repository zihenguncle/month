package jx.com.monthtwo.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    public OkHttpClient mClient;
    private Handler handler = new Handler(Looper.getMainLooper());
    public static OkHttpUtils instance;
    public static OkHttpUtils getInstance(){
        if(instance == null){
            synchronized (OkHttpUtils.class){
                instance = new OkHttpUtils();
            }
        }
        return instance;
    }


    public OkHttpUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void postEnqueue(String url, Map<String,String> params, final Class clazz, final OkCallBack okCallBack){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry : params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        okCallBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    final Object o = new Gson().fromJson(string, clazz);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallBack.success(o);
                        }
                    });
                }catch (Exception e){
                    okCallBack.failed(e);
                }
            }
        });
    }

}
