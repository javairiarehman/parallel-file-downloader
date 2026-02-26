package downloader;

public class Chunk {
    private final long start;
    private final long end;

    public Chunk(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
