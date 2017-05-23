package idv.jasonwang.httpclient;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jason on 2017/5/23.
 */
public interface HttpClientable {

    void get(String url);

    void get(String url, ParamStore params);

    void get(String url, ParamStore params, String tag);

    void post(String url, SparseArray<String> params);

    void post(String url, SparseArray<String> params, String tag);

    void upload(String url, HashMap<String, Object> params);

    void upload(String url, HashMap<String, Object> params, String tag);

    void download(String url);

    void download(String url, String tag);

    void cancelAll();

    void cancel(String tag);

}
