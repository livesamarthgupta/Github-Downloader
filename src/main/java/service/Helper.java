package service;

import model.DownloadObject;
import model.Details;
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

    private static volatile Helper instance = null;

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

    public File getFileName(File downloadPath, String downloadUrl) {
        String fileName;
        String[] urlParts = downloadUrl.split("/");
        fileName = urlParts[urlParts.length - 1];
        return new File(downloadPath, fileName);
    }

    public void recursiveDownloader(String url, String token, File downloadPath) {
        Details details = UrlParser(url, token);
        DownloadObject downloadObject = fetchDownloadObject(details);
        File downloadFolder = null;
        try {
            downloadFolder = createDownloadFolder(details, downloadPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        downloadObject.download(downloadFolder, details);
    }


    private Details UrlParser(String URL, String token) {
        Details details = new Details();
        String[] urlParts = URL.split("/");
        details.setUsername(urlParts[3]);
        details.setRepository(urlParts[4]);

        StringBuilder directoryPath = new StringBuilder();
        for (int i = 7; i < urlParts.length; i++) {
            directoryPath.append(urlParts[i]);
            directoryPath.append("/");
        }
        details.setDirectory(directoryPath.toString());

        details.setToken(token);

        return details;
    }


    private DownloadObject fetchDownloadObject(Details details) {
        DownloadObject downloadObject = new DownloadObject();
        try {
            URL apiUrl = new URL("https://api.github.com/repos/" + details.getUsername() + "/" + details.getRepository() + "/" + "contents/" + details.getDirectory());
            HttpsURLConnection connection = (HttpsURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            if (details.getToken().length() > 0)
                connection.setRequestProperty("Authorization", "token " + details.getToken());
            System.out.print("...");
            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JSONArray jsonResponse = getJsonResponse(br);
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject json = jsonResponse.getJSONObject(i);
                    String type = json.getString("type");
                    if (type.equalsIgnoreCase("file")) {
                        downloadObject.setDownload(type, json.getString("download_url"));
                    } else if (type.equalsIgnoreCase("dir")) {
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

    private JSONArray getJsonResponse(BufferedReader br) throws IOException {
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        return new JSONArray(response.toString());
    }

    private File createDownloadFolder(Details details, File downloadPath) throws IOException {
        String folderName = getFolderName(details.getDirectory());
        File downloadDir = new File(downloadPath, folderName);
        boolean isDirCreated = downloadDir.mkdir();
        if (!isDirCreated) {
            throw new IOException("Unable to create folder! ");
        }

        return downloadDir;
    }

    private String getFolderName(String dir) {
        String[] dirParts = dir.split("/");
        return dirParts[dirParts.length - 1];
    }


}
