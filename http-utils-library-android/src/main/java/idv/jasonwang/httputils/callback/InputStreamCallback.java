package idv.jasonwang.httputils.callback;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by jason on 2017/5/23.
 */
public abstract class InputStreamCallback extends Callback<InputStream> {

    @Override
    public InputStream convert(Response response) throws IOException {
        return response.body().byteStream();
    }

}
