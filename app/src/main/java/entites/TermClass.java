package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity(tableName = "terms")
public class TermClass {
    @PrimaryKey (autoGenerate = true)
    private int termId;
    private String termTitle;
    private LocalDate start;
    private LocalDate  end;

    public TermClass(int termId, String termTitle,LocalDate start, LocalDate end) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.start = start;
        this.end = end;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
    public ArrayList<CourseClass> associatedCourses;
    @Override
    public String toString(){
        return termTitle;
    }
}
