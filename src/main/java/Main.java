import service.Helper;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static final Helper helper = Helper.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter url for the path: ");
        String url = sc.nextLine();
        System.out.println();
        System.out.print("Enter OAUTH Token (OPTIONAL): ");
        String token = sc.nextLine();
        System.out.println();
        String userHome = System.getProperty("user.home");
        File downloadPath = new File(userHome, "Downloads");
        helper.recursiveDownloader(url, token, downloadPath);
    }
}
