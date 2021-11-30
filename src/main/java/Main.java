import service.Helper;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[41m";
    public static final String ANSI_RESET = "\u001B[0m ";
    private static final Helper helper = Helper.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.print(ANSI_BLUE_BACKGROUND + "Enter URL for the path:" + ANSI_RESET);
        String url = sc.nextLine();
        System.out.println();
        System.out.print(ANSI_BLUE_BACKGROUND + "Enter Personal Access Token (OPTIONAL):" + ANSI_RESET);
        String token = sc.nextLine();
        System.out.println();
        String userHome = System.getProperty("user.home");
        File downloadPath = new File(userHome, "Downloads");
        helper.recursiveDownloader(url, token, downloadPath);
    }
}
