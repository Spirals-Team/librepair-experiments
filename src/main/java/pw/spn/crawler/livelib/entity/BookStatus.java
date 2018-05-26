package pw.spn.crawler.livelib.entity;

public enum BookStatus {
    UNKNOWN(-1), WISH_TO_READ(0), ALREADY_READ(1), READING(2), UNREAD(3);

    private final int id;

    BookStatus(int id) {
        this.id = id;
    }

    public static BookStatus fromId(int id) {
        for (BookStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return UNKNOWN;
    }

    public int getId() {
        return id;
    }
}
