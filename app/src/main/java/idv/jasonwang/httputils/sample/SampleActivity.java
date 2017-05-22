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
                login.put("X", "X");
                login.put("X", "X");
                login.put("X", "X");

                httpClient.post("X", login);




//                File file = new File(Environment.getExternalStorageDirectory() + "/download/LARGE_elevation.jpg");
//                FileInputStream inputStream = null;
//                try {
//                    inputStream = new FileInputStream(file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                new HttpUtils().postMultiPart(inputStream);
//                new HttpUtils().postMultiPartWithProgress(file);

//                new HttpUtils().downloadFileWithProgress(Environment.getExternalStorageDirectory() + "/download", "Download_Test.jpg");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                params.put("X", new File(Environment.getExternalStorageDirectory() + "/download/LARGE_elevation.jpg"));
                params.put("X", "X");
                params.put("X", "X");

                httpClient.upload("X", params);
            }
        });
    }
}
