package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity (tableName = "notes")
public class Notes {
    @PrimaryKey (autoGenerate = true)
    private int noteId;
    private String body;
    private String courseTitle;

    public Notes(int noteId, String body, String courseTitle){
        this.noteId = noteId;
        this.body = body;
        this.courseTitle = courseTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseId(String courseTitle) {
        this.courseTitle = courseTitle;
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

    @Override
    public String toString(){
        return body;
    }
}
