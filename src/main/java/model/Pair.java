package model;

public class Pair {
    private String type;
    private String url;

    public Pair(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public Pair() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
