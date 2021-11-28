package service;

import model.DownloadObject;
import model.Pair;
import model.Path;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Helper {

    private static Helper instance = null;

    private Helper() {
    }

    public static Helper getInstance() {
        if (instance == null) {
            synchronized (Helper.class) {
                if (instance == null) {
                    instance = new Helper();
                }
            }
        }
        return instance;
    }

    public void recursiveDownloader(String url, String token, String downloadPath) {
        Path path = UrlParser(url, token);
        DownloadObject downloadObject = fetchDownloadObject(path);
        String downloadFolder = null;
        try {
            downloadFolder = createDownloadFolder(path, downloadPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        download(downloadObject, downloadFolder, path);
    }


    public Path UrlParser(String URL, String token) {
        Path path = new Path();
        String[] urlParts = URL.split("/");
        path.setUsername(urlParts[3]);
        path.setRepository(urlParts[4]);

        StringBuilder directoryPath = new StringBuilder();
        for (int i = 7; i < urlParts.length; i++) {
            directoryPath.append(urlParts[i]);
            directoryPath.append("/");
        }
        path.setDirectory(directoryPath.toString());

        path.setToken(token);

        return path;
    }


    public DownloadObject fetchDownloadObject(Path path) {
        DownloadObject downloadObject = new DownloadObject();

        try {
            URL apiUrl = new URL("https://api.github.com/repos/" + path.getUsername() + "/" + path.getRepository() + "/" + "contents/" + path.getDirectory());
            HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            if (path.getToken().length() > 0)
                connection.setRequestProperty("Authorization", "token " + path.getToken());

            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    response.append(output);
                }
                JSONArray jsonResponse = new JSONArray(response.toString());
                for (int i = jsonResponse.length() - 1; i >= 0; i--) {
                    JSONObject json = jsonResponse.getJSONObject(i);
                    String type = json.getString("type");
                    if (type.equalsIgnoreCase("file")) {
                        downloadObject.setDownload(type, json.getString("download_url"));
                    } else if (type.equalsIgnoreCase("dir")) {
                        // todo: recursive download type:dir html_url:url
                        downloadObject.setDownload(type, json.getString("html_url"));
                    }
                }
            } else {
                throw new RuntimeException("Failed: HTTP Error Code: " + connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL Format!");
            System.exit(1);
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return downloadObject;
    }

    public void download(DownloadObject downloadObject, String downloadPath, Path path) {
        for (int i = 0; i < downloadObject.getSize(); i++) {
            Pair download = downloadObject.getDownload(i);
            if (download.getType().equalsIgnoreCase("file")) {
                FileDownloader downloader = new FileDownloader(download.getUrl(), downloadPath);
                downloader.start();
            } else if (download.getType().equalsIgnoreCase("dir")) {
                recursiveDownloader(download.getUrl(), path.getToken(), downloadPath);
            }
        }
    }

    public String createDownloadFolder(Path path, String downloadPath) throws IOException {
        // crete path string to create new folder
        String folderName = getFolderName(path.getDirectory());
        if (downloadPath.charAt(downloadPath.length() - 1) == '/') {
            downloadPath = downloadPath + folderName;
        } else {
            downloadPath = downloadPath + "/" + folderName;
        }

        File downloadDir = new File(downloadPath);
        boolean isDirCreated = downloadDir.mkdir();
        if (!isDirCreated) {
            throw new IOException("Unable to create folder! ");
        }

        return downloadPath;
    }

    private String getFolderName(String dir) {
        String[] dirParts = dir.split("/");
        return dirParts[dirParts.length - 1];
    }

    public String getFileName(String downloadPath, String downloadUrl) {
        String fileName;
        String[] urlParts = downloadUrl.split("/");
        fileName = urlParts[urlParts.length - 1];
        return downloadPath + "/" + fileName;
    }


}
