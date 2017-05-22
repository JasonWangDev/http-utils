package idv.jasonwang.httputils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jason on 2017/5/22.
 */
public class HttpClient {

    private volatile static HttpClient client;

    private OkHttpClient okHttpClient;


    private HttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                                        .build();
    }


    public static HttpClient getInstance() {
        if (client == null)
        {
            synchronized (HttpClient.class)
            {
                if (client == null)
                    client = new HttpClient();
            }
        }

        return client;
    }


    public void get(String url) {
        get(url, null, null);
    }

    public void get(String url, String tag) {
        get(url, null, tag);
    }

    public void get(String url, Map<String, String> params) {
        get(url, params, null);
    }

    public void get(String url, Map<String, String> params, String tag) {
        if (params != null && params.size() > 0)
        {
            HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();

            for (String key : params.keySet())
                builder.addQueryParameter(key, params.get(key));

            url = builder.build().toString();
        }

        Request.Builder requestBuilder = new Request.Builder();
        try
        {
            requestBuilder.url(url);
        }
        catch (IllegalArgumentException e)
        {
            Log.e("TAG", "URL Protocol Fail");

            return;
        }
        if (tag != null)
            requestBuilder.tag(tag);
        Request request = requestBuilder.build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure");
                Log.e("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", response.body().string());
            }
        });
    }

    public void post(String url, Map<String, String> params) {
        post(url, params, null);
    }

    public void post(String url, Map<String, String> params, String tag) {
        FormBody.Builder form = new FormBody.Builder();
        if (params != null)
        {
            for (String key : params.keySet())
                form.add(key, params.get(key));
        }
        RequestBody requestBody = form.build();

        Request.Builder requestBuilder = new Request.Builder();
        try
        {
            requestBuilder.url(url);
        }
        catch (IllegalArgumentException e)
        {
            Log.e("TAG", "URL Protocol Fail");

            return;
        }
        requestBuilder.post(requestBody);
        if (tag != null)
            requestBuilder.tag(tag);
        Request request = requestBuilder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure");
                Log.e("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", response.body().string());
            }
        });
    }

    public void upload(String url, Map<String, String> params, File file) {

    }

    public void upload(String url, Map<String, String> params, File file, String tag) {

    }

    public void download(String url, File output) {

    }

    public void download(String url, File output, String tag) {

    }

    /**
     *
     * 在上傳過程當中取消時，會呼叫 Call onFailure 方法。在下載過程當中取消時，onResponse 會跳出
     * "java.net.SocketException: Socket is closed" 錯誤訊息，程式不會崩潰。
     *
     * @param tag
     */
    public void cancelRequest(String tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }

        for (Call call : okHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
    }

}
