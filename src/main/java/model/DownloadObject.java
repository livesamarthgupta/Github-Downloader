package model;


import java.util.ArrayList;

public class DownloadObject {
    private final ArrayList<Pair> download;

    public DownloadObject() {
        download = new ArrayList<>();
    }

    public void setDownload(String type, String url) {
        Pair pair = new Pair(type, url);
        download.add(pair);
    }

    public Pair getDownload(int index) {
        return download.get(index);
    }

    public int getSize() {
        return download.size();
    }

}
