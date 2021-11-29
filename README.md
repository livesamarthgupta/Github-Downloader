# Github-Downloader

Have you ever wanted to download just a single folder or a file from github but couldn't as Github doesn't allow Directory download. Here comes Github-Downloader.
---
With Github-Downloader you can download any directory in Github that you can access using the browser. You just need to have the URL of the resource and that's it.
It will download the directory in your system downloads folder.

### TO GENERATE RUNNABLE JAR

`mvn clean compile assembly:single`

### TO RUN THE JAR

1. Make sure you have Java installed and PATH is set.
2. Run the jar.

`java -jar Github-Downloader-1.0-SNAPSHOT-jar-with-dependencies.jar`

### PREVIEW

![Preview](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/b2rlcta0wimwggybu7gq.png)


##### NOTE: Directory structure with more than 60 files may require personal access Token.
###### TO OBTAIN PERSONAL ACCESS TOKEN:

1. Go to your Github account settings.
2. Go to Developer Settings > Personal Access Tokens.
3. Click on "Generate New Token"
4. Select "repo" as scope and set expiration.
5. Click on "Generate Token"
