package idv.jasonwang.httputils;

import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idv.jasonwang.httputils.callback.Callback;
import idv.jasonwang.httputils.callback.FileCallback;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;

/**
 * Created by jason on 2017/5/22.
 */
public class HttpClient {

    private volatile static HttpClient client;

    private OkHttpClient okHttpClient;


    private HttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient = new OkHttpClient.Builder()
                                        .cookieJar(new CookieJar() {
                                            private final Map<String, List<Cookie>> store = new HashMap<>();

                                            @Override
                                            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                                store.put(url.host(), cookies);
                                            }

                                            @Override
                                            public List<Cookie> loadForRequest(HttpUrl url) {
                                                List<Cookie> cookies = store.get(url.host());

                                                return cookies != null ? cookies : new ArrayList<Cookie>();
                                            }
                                        })
                                        .addNetworkInterceptor(logging)
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

        execute(okHttpClient, request, null);
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

        execute(okHttpClient, request, null);
    }

    public void upload(String url, Map<String, Object> params) {
        upload(url, params ,null);
    }

    public void upload(String url, Map<String, Object> params, String tag) {
        MultipartBody.Builder form = new MultipartBody.Builder();
        form.setType(MultipartBody.FORM);
        if (params != null)
        {
            for (String key : params.keySet())
            {
                if (!(params.get(key) instanceof File))
                    form.addFormDataPart(key, (String) params.get(key));
                else
                {
                    File file = (File) params.get(key);
                    form.addFormDataPart(key, file.getName(), new CountingFileRequestBody(file));
                }
            }
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

       execute(okHttpClient, request, null);
    }

    public void download(String url, Callback callback) {
        download(url, null, callback);
    }

    public void download(String url, String tag, Callback callback) {
        Request.Builder builder = new Request.Builder();
        try
        {
            builder.url(url);
        }
        catch (IllegalArgumentException e)
        {
            Log.e("TAG", "URL Protocol Fail");

            return;
        }
        if (tag != null)
            builder.tag(tag);
        Request request = builder.build();

       execute(okHttpClient, request, callback);
    }




    private class CountingFileRequestBody extends RequestBody {

        private final File file;

        public CountingFileRequestBody(File file) {
            this.file = file;
        }

        @Override
        public long contentLength() {
            return file.length();
        }

        @Override
        public MediaType contentType() {
            String type = null;
            String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
            if (extension != null)
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            return MediaType.parse(type);
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            FileInputStream inputStream = new FileInputStream(file);
            long total = 0;
            int len;
            byte[] buff = new byte[4096];
            while ((len = inputStream.read(buff)) != -1)
            {
                sink.outputStream().write(buff, 0, len);
                sink.outputStream().flush();

                total += len;

                Long progress = (long) ((double) total / contentLength() * 100);
                Log.d("TAG", String.format("Progress: %d", progress));
            }

            inputStream.close();
        }

    }





    /**
     * 在上傳過程當中取消時，會呼叫 Call onFailure 方法。在下載過程當中取消時，onResponse 會跳出
     * "java.net.SocketException: Socket is closed" 錯誤訊息，程式不會崩潰。
     *
     * @param client
     */
    public void cancelAll(OkHttpClient client) {
        cancel(client, null);
    }

    /**
     *
     * @param client
     * @param tag
     */
    public void cancel(OkHttpClient client, String tag) {
        for (Call call : client.dispatcher().queuedCalls())
        {
            if (null == tag)
                call.cancel();
            else if (tag.equals(call.request().tag()))
                call.cancel();
        }

        for (Call call : client.dispatcher().runningCalls())
        {
            if (null == tag)
                call.cancel();
            else if (tag.equals(call.request().tag()))
                call.cancel();
        }
    }


    private void execute(OkHttpClient client, Request request, final Callback callback) {
        if (client == null || request == null)
            throw new NullPointerException("OkHttpClient or Request can't null.");

        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null)
                    callback.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null)
                    callback.onResponse(callback.convert(response));
            }
        });
    }

}
