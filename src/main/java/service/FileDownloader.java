package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloader extends Thread {

    private final String downloadUrl;
    private final File downloadPath;

    public FileDownloader(String downloadUrl, File downloadPath) {
        this.downloadUrl = downloadUrl;
        this.downloadPath = downloadPath;
    }

    @Override
    public void run() {
        Helper helper = Helper.getInstance();

        try {
            URL fetchUrl = new URL(downloadUrl);
            ReadableByteChannel readableByteChannel = Channels.newChannel(fetchUrl.openStream());
            File fileName = helper.getFileName(downloadPath, downloadUrl);

            FileOutputStream fos = new FileOutputStream(fileName);
            fos.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            System.out.print(".");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
