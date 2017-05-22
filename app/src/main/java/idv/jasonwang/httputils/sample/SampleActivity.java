package idv.jasonwang.httputils.sample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import idv.jasonwang.httputils.HttpClient;

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

                httpClient.post("sefs", login);

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpClient.download("sef", new File(Environment.getExternalStorageDirectory() + "/download", "Download_Test_1.jpg"));
            }
        });
    }
}
