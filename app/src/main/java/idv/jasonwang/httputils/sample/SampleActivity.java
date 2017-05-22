package idv.jasonwang.httputils.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import idv.jasonwang.httputils.HttpClient;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient httpClient = HttpClient.getInstance();

                Map<String, String> params = new HashMap<>();
                params.put("user_account", "test001@salesky.net");
                params.put("user_passwd", "doit123456");
                params.put("product_code", "salesky");

                httpClient.post("https://dev.sky-family.net/api/M_SaleSky_Login.php", params);


//                File file = new File(Environment.getExternalStorageDirectory() + "/download/LARGE_elevation.jpg");
//                FileInputStream inputStream = null;
//                try {
//                    inputStream = new FileInputStream(file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }

//                new HttpUtils().postMultiPart(inputStream);
//                new HttpUtils().postMultiPartWithProgress(file);

//                new HttpUtils().downloadFileWithProgress(Environment.getExternalStorageDirectory() + "/download", "Download_Test.jpg");
            }
        });
    }
}
