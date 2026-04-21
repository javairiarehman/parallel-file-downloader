package downloader;

public class Main {
    public static void main(String[] args) throws Exception {
        FileDownloader downloader = new FileDownloader();

        downloader.download(
            "http://localhost:8080/test.txt",
            4,
            "output.txt"
        );
    }
}
