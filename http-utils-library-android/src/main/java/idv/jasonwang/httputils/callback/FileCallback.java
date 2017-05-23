package idv.jasonwang.httputils.callback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by jason on 2017/5/23.
 */
public abstract class FileCallback extends Callback<File> {

    public abstract void progress(int progress);


    private final String path;
    private final String name;


    public FileCallback(String path, String name) {
        this.path = path;
        this.name = name;
    }


    @Override
    public File convert(Response response) throws IOException {
        final File file = new File(path, name);
        final InputStream inputStream = response.body().byteStream();
        final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

        long fileSize = response.body().contentLength();
        byte[] buffer = new byte[1024 * 4];
        int readSize;
        long readTotal = 0;

        while ((readSize = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readSize);

            readTotal += readSize;

            progress((int) ((double) readTotal / fileSize * 100));
        }

        outputStream.flush();
        outputStream.close();

        inputStream.close();

        return file;
    }

}
