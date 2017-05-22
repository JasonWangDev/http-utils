package idv.jasonwang.httputils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
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
 * Created by jason on 2017/5/19.
 */
public class HttpUtils {



    public void downloadFile(final String path, final String filename) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("74576").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File file = new File(path, filename);
                if (file.exists())
                {
                    Log.d("TAG", "File Create Fail");

                    return;
                }

                final InputStream inputStream = response.body().byteStream();
                final FileOutputStream fileOutputStream = new FileOutputStream(file);

                byte[] buffer = new byte[2048];
                int len;
                while ((len = inputStream.read(buffer)) != -1)
                {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();

                Log.d("TAG", "Download File Done.");
            }
        });
    }

    public void downloadFileWithProgress(final String path, final String filename) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(logging).build();
        final Request request = new Request.Builder().url("547567").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Long startTime = System.currentTimeMillis();

                File file = new File(path, filename);
                if (file.exists())
                {
                    Log.d("TAG", "File Create Fail");

                    return;
                }

                final InputStream inputStream = response.body().byteStream();
                final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));

                byte[] buffer = new byte[1024 * 8];
                int len;
                long size = response.body().contentLength();
                long total = 0;
                while ((len = inputStream.read(buffer)) != -1)
                {
                    output.write(buffer, 0, len);

                    total += len;
                    Long progress = (long) ((double) total / size * 100);
                    Log.d("TAG", String.format("Progress: %d", progress));
                }
                output.flush();
                output.close();
                inputStream.close();

                file.delete();

                Log.d("TAG", "Download File Done. Time: " + (System.currentTimeMillis() - startTime));
            }
        });
    }

}
