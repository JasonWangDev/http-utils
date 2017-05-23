package idv.jasonwang.httputils.sample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import idv.jasonwang.httputils.HttpClient;
import idv.jasonwang.httputils.callback.FileCallback;
import idv.jasonwang.httputils.callback.InputStreamCallback;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final HttpClient httpClient = HttpClient.getInstance();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> login = new HashMap<>();
                login.put("sef", "sefsef");
                login.put("sef", "sef");
                login.put("sef", "sef");

                httpClient.post("https://appdev/api/M_ServiceSky_Login.php", login);

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpClient.download("https://upload.wikimedia.org/wikipedia/commons/3/3d/LARGE_elevation.jpg",
                        new FileCallback(Environment.getExternalStorageDirectory() + "/download", "Download_Test_1.jpg") {
                            @Override
                            public void onFail() {
                                Log.d("TAG", "onFail");
                            }

                            @Override
                            public void onResponse(File response) {
                                Log.d("TAG", "onResponse");
                            }

                            @Override
                            public void progress(int progress) {
                                Log.d("TAG", "Progress: " + progress + "%");
                            }
                        });
            }
        });
    }

}
