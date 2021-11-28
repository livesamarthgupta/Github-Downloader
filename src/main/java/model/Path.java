package model;

public class Path {
    String username;
    String repository;
    String directory;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "Path{" +
                "username='" + username + '\'' +
                ", repository='" + repository + '\'' +
                ", directory='" + directory + '\'' +
                '}';
    }
}
