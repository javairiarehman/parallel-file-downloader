package downloader;

public class Main {
    public static void main(String[] args) {

        FileDownloader downloader = new FileDownloader();

        downloader.download("http://localhost:8080/test.txt");
    }
}
