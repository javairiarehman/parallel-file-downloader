package downloader;

import org.junit.jupiter.api.Test;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileDownloaderTest {

    @Test
    void testDownload() throws Exception {
        FileDownloader downloader = new FileDownloader();

        downloader.download("http://localhost:8080/test.txt", 4, "output.txt");

        byte[] original = Files.readAllBytes(Paths.get("test-files/test.txt"));
        byte[] downloaded = Files.readAllBytes(Paths.get("output.txt"));

        assertArrayEquals(original, downloaded);
    }
}

