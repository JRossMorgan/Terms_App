package entites;

import java.time.LocalDateTime;

public class Notes {
    private int noteId;
    private String body;
    private LocalDateTime alertTime;

    public Notes(int noteId, String body, LocalDateTime alertTime) {
        this.noteId = noteId;
        this.body = body;
        this.alertTime = alertTime;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(LocalDateTime alertTime) {
        this.alertTime = alertTime;
    }
}
