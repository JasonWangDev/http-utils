package idv.jasonwang.httputils.sample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import idv.jasonwang.httputils.HttpUtils;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory() + "/download/LARGE_elevation.jpg");
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                new HttpUtils().postMultiPart(inputStream);
                new HttpUtils().postMultiPartWithProgress(file);
            }
        });
    }
}
