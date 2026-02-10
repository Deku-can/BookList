import java.util.UUID;

public class Book {
    private final UUID id;
    private final String title;
    private final String author;
    private boolean isRead;

    public Book(String title, String author) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.isRead = false;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
