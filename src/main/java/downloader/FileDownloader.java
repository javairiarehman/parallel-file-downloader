package downloader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.concurrent.*;

public class FileDownloader {

    public void download(String urlStr, int chunks, String outputFile) throws Exception {

        URL url = new URL(urlStr);

        // 1. Try HEAD request to get file size
        HttpURLConnection headConn = (HttpURLConnection) url.openConnection();
        headConn.setRequestMethod("HEAD");

        int fileSize = headConn.getContentLength();

        // 2. Fallback if HEAD fails
        if (fileSize <= 0) {
            HttpURLConnection getConn = (HttpURLConnection) url.openConnection();
            try (InputStream in = getConn.getInputStream()) {
                byte[] data = in.readAllBytes();
                fileSize = data.length;

                // If we already downloaded full file, just save it
                Files.write(Paths.get(outputFile), data);
                return;
            }
        }

        int chunkSize = fileSize / chunks;
        byte[][] parts = new byte[chunks][];

        ExecutorService executor = Executors.newFixedThreadPool(chunks);

        // 3. Download chunks in parallel
        for (int i = 0; i < chunks; i++) {
            final int index = i;
            final int start = i * chunkSize;
            final int end = (i == chunks - 1) ? fileSize - 1 : (start + chunkSize - 1);

            executor.submit(() -> {
                try {
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

                    try (InputStream in = conn.getInputStream()) {
                        parts[index] = in.readAllBytes();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        // 4. Combine chunks
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            for (byte[] part : parts) {
                if (part != null) {
                    output.write(part);
                }
            }
            Files.write(Paths.get(outputFile), output.toByteArray());
        }
    }
}
