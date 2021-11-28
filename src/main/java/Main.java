import service.Helper;

import java.util.Scanner;

public class Main {
    private static final Helper helper = Helper.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter url for the path: ");
        String url = sc.nextLine();
        System.out.println("Enter OAUTH Token: ");
        String token = sc.nextLine();
        System.out.println("Enter path for download: ");
        String downloadPath = sc.nextLine();
        helper.recursiveDownloader(url, token, downloadPath);
    }
}
