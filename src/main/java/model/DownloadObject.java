package model;


import service.FileDownloader;
import service.Helper;

import java.io.File;
import java.util.ArrayList;

public class DownloadObject {
    private final ArrayList<UrlTypePair> download;

    public DownloadObject() {
        download = new ArrayList<>();
    }

    public void setDownload(String type, String url) {
        UrlTypePair urlTypePair = new UrlTypePair(type, url);
        download.add(urlTypePair);
    }

    public UrlTypePair getDownload(int index) {
        return download.get(index);
    }

    public int getSize() {
        return download.size();
    }

    public void download(File downloadPath, Details details) {
        Helper helper = Helper.getInstance();
        for (int i = 0; i < this.getSize(); i++) {
            UrlTypePair download = this.getDownload(i);
            if (download.getType().equalsIgnoreCase("file")) {
                FileDownloader downloader = new FileDownloader(download.getUrl(), downloadPath);
                downloader.start();
            } else if (download.getType().equalsIgnoreCase("dir")) {
                helper.recursiveDownloader(download.getUrl(), details.getToken(), downloadPath);
            }
        }
    }

}
