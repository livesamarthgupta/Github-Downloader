# Github-Downloader [![CI](https://github.com/livesamarthgupta/Github-Downloader/actions/workflows/main.yml/badge.svg?branch=master)](https://github.com/livesamarthgupta/Github-Downloader/actions/workflows/main.yml)

Have you ever wanted to download just a single folder or a file from github but couldn't as Github doesn't allow Directory download. Here comes Github-Downloader.

With Github-Downloader you can download any directory in Github that you can access using the browser. You just need to have the URL of the resource and that's it.
It will download the directory in your system downloads folder.

<p align="left">
  <a href="https://github.com/livesamarthgupta/Github-Downloader/releases/"><img alt="GitHub All Releases" src="https://img.shields.io/github/downloads/livesamarthgupta/Github-Downloader/total.svg?label=Download&logo=github&style=for-the-badge"></a> 
</p>

---

### TO RUN ON MAC/LINUX

```
$ sh gitdir
```

For running first time might need executable permission,

```
$ chmod +x gitdir
```

Note: You can make it available as a command by placing the binary `gitdir` under `/usr/local/bin/`


### TO RUN ON WINDOWS

```
$ sh gitdir
```

Note: You will need bash shell to run on Windows. (See [Cygwin](https://www.cygwin.com/) or [GitBash](https://git-scm.com/downloads)). Place it in your terminal home/bin, this will add a new command `gitdir`

---

### TO BUILD THE PROJECT

```
$ mvn clean compile assembly:single
```

---

### PREVIEW

![Image Preview](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/bas2rh8pqphbw7608bq2.png)

---

##### NOTE: Directory structure with more than 60 files may require personal access Token.
###### TO OBTAIN PERSONAL ACCESS TOKEN:

1. Go to your Github account settings.
2. Go to Developer Settings > Personal Access Tokens.
3. Click on "Generate New Token"
4. Select "repo" as scope and set expiration.
5. Click on "Generate Token"
