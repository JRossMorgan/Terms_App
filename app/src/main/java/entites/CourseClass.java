package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity (tableName = "courses")
public class CourseClass {
    @PrimaryKey (autoGenerate = true)
    private int courseId;
    private String title;
    private LocalDate start;
    private LocalDate end;
    private String status;
    private String instructorName;
    private String instructorNumber;
    private String instructorEmail;
    private int termId;

    public CourseClass(int courseId, String title, LocalDate start, LocalDate end, String status, String instructorName, String instructorNumber, String instructorEmail, int termId) {
        this.courseId = courseId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorNumber = instructorNumber;
        this.instructorEmail = instructorEmail;
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public ArrayList<AssessmentClass> associatedAssessment;
    public ArrayList<Notes> associatesNotes;
}
