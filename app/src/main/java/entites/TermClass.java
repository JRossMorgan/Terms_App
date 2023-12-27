package entites;

import java.time.LocalDate;
import java.util.ArrayList;

//@Entity (tableName = "terms")
public class TermClass {
    private int termId;
    private LocalDate start;
    private LocalDate  end;

    public TermClass(int termId, LocalDate start, LocalDate end) {
        this.termId = termId;
        this.start = start;
        this.end = end;
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
}
