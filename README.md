# Parallel File Downloader

## Overview

This project implements a parallel file downloader in Java using HTTP Range requests.
It downloads a file in multiple chunks concurrently and reconstructs the final file.

---

## Features

* Downloads files using HTTP `Range` headers
* Splits file into chunks and downloads them in parallel
* Combines chunks into a complete file
* Handles fallback when `HEAD` request does not return file size
* Includes unit tests to verify correctness

---

## Tech Stack

* Java 17
* Gradle
* JUnit 5

---

## Project Structure

```
parallel-file-downloader/
├── src/
│   ├── main/java/downloader/
│   │   ├── FileDownloader.java
│   │   └── Main.java
│   └── test/java/downloader/
│       └── FileDownloaderTest.java
├── test-files/
│   └── test.txt
├── build.gradle
└── README.md
```

---

## How It Works

1. Sends a `HEAD` request to determine file size
2. Splits the file into chunks
3. Uses multiple threads to download chunks in parallel
4. Each request uses the `Range` header (e.g., `bytes=0-1023`)
5. Combines all chunks into the final output file

---

## Setup & Run

### 1. Start Local Server

Run the following Docker command:

```
docker run --rm -p 8080:80 -v $(pwd)/test-files:/usr/local/apache2/htdocs/ httpd:latest
```

Access test file at:

```
http://localhost:8080/test.txt
```

---

### 2. Run Tests

```
./gradlew test
```

---

### 3. Run Downloader (optional)

```
./gradlew run
```

---

## Example

The downloader splits the file into 4 chunks and downloads them in parallel:

```
downloader.download("http://localhost:8080/test.txt", 4, "output.txt");
```

---

## Testing

The unit test:

* Downloads the file using the parallel downloader
* Compares it with the original file
* Verifies both files are identical

---

## Notes

* The implementation uses `ExecutorService` for parallelism
* Includes fallback logic if `Content-Length` is unavailable
* Works with any server supporting HTTP Range requests

---

## Author

Javairia Rehman
