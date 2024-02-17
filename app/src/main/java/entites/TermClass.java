package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity(tableName = "terms")
public class TermClass {
    @PrimaryKey (autoGenerate = true)
    private int termId;
    private String termTitle;
    private Long start;
    private Long end;

    public TermClass(int termId, String termTitle,Long start, Long end) {
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
    @Override
    public String toString(){
        return termTitle;
    }
}
