package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity (tableName = "notes")
public class Notes {
    @PrimaryKey (autoGenerate = true)
    private int noteId;
    private String body;
    private int courseId;

    public Notes(int noteId, String body, int courseId){
        this.noteId = noteId;
        this.body = body;
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
