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


    public void postMultiPart(final File file) {
        RequestBody body1 = new FormBody.Builder()
                .add("params1", "87686")
                .add("params2", "786786")
                .add("params3", "786786")
                .build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
        Request request = new Request.Builder()
                .url("URL")
                .post(body1)
                .build();
        Call call1 = client.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }

            @Override
            public void onResponse(Call calssl, Response response) throws IOException {
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("params1", "7783783")
                        .addFormDataPart("params2", "1786786")
                        .addFormDataPart("params3", "test", RequestBody.create(MediaType.parse("image/jpg"), file))
                        .build();

//                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("URL")
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("TAG", "onFailure");
                        Log.d("TAG", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("TAG", response.headers().toString());
                        Log.d("TAG", response.body().string());
                    }
                });
            }
        });
    }

    public void postMultiPart(final InputStream file) {
        RequestBody body1 = new FormBody.Builder()
                .add("params1", "34346")
                .add("params2", "346346")
                .add("params3", "436436")
                .build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
        Request request = new Request.Builder()
                .url("URL")
                .post(body1)
                .build();
        Call call1 = client.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }

            @Override
            public void onResponse(Call calssl, Response response) throws IOException {
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("4645", "3466")
                        .addFormDataPart("heh", "346346")
                        .addFormDataPart("erherh",
                                "test",
                                new RequestBody() {
                                    @Override
                                    public MediaType contentType() {
                                        return MediaType.parse("image/jpg");
                                    }

                                    @Override
                                    public void writeTo(BufferedSink sink) throws IOException {
                                        int total;
                                        int len;
                                        byte[] buff = new byte[1024];
                                        while ((len = file.read(buff)) != -1)
                                        {
                                            sink.outputStream().write(buff, 0, len);
                                            sink.outputStream().flush();
                                        }

                                        file.close();
                                    }
                                })
                        .build();

//                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url("637837")
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("TAG", "onFailure");
                        Log.d("TAG", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("TAG", response.headers().toString());
                        Log.d("TAG", response.body().string());
                    }
                });

            }
        });
    }

    public void postMultiPartWithProgress(final File file) {
        RequestBody body1 = new FormBody.Builder()
                .add("467", "755745")
                .add("4576", "4576")
                .add("4576", "457")
                .build();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
        Request request = new Request.Builder()
                .url("4576")
                .post(body1)
                .build();
        Call call1 = client.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { }

            @Override
            public void onResponse(Call calssl, Response response) throws IOException {
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("4576", "4576")
                        .addFormDataPart("4576", "4576")
                        .addFormDataPart("5476", "4576", new CountingFileRequestBody(file, "image/jpg"))
                        .build();

//                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("476675")
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("TAG", "onFailure");
                        Log.d("TAG", e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("TAG", response.headers().toString());
                        Log.d("TAG", response.body().string());
                    }
                });
            }
        });
    }


    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override public void log(String message) {
            Log.d("TAG", message);
        }
    });


    public class CountingFileRequestBody extends RequestBody {

        private static final int SEGMENT_SIZE = 2048; // okio.Segment.SIZE

        private final File file;
        private final String contentType;

        public CountingFileRequestBody(File file, String contentType) {
            this.file = file;
            this.contentType = contentType;
        }

        @Override
        public long contentLength() {
            return file.length();
        }

        @Override
        public MediaType contentType() {
            return MediaType.parse(contentType);
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            // Method 1
//            Source source = null;
//            try {
//                source = Okio.source(file);
//                long total = 0;
//                long read;
//
//                while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
//                    total += read;
//                    sink.flush();
//
//                    Log.d("TAG", "Total: " + total);
//                }
//            } finally {
//
//            }

            // Method 2
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
