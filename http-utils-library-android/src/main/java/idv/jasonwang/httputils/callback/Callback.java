package idv.jasonwang.httputils.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 *網路請求結果回調的抽象類
 *
 * Created by jason on 2017/5/23.
 */
public abstract class Callback<T> {

    public abstract void onFail();
    public abstract void onResponse(T response);

    public abstract T convert(Response response) throws IOException;

}
