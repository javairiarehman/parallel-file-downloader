# Parallel File Downloader

A Java-based parallel file downloader using HTTP Range requests.

## Features

- Retrieves file size from server (HEAD request)
- Splits file into chunks
- Downloads chunks in parallel using threads
- Merges chunks into a single file
- Tested locally using Docker + Apache

## Requirements

- Java 17+
- Gradle
- Docker (for local test server)

## Run locally

### 1. Start local test server

```bash
docker run --rm -p 8080:80 \
-v $(pwd)/test-files:/usr/local/apache2/htdocs/ \
httpd:latest
