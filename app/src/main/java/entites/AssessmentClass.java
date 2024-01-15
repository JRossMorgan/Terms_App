package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity (tableName = "assessments")
public class AssessmentClass {
    @PrimaryKey (autoGenerate = true)
    private int assessmentId;
    private String title;
    private String type;
    private LocalDateTime endDate;
    private int courseId;

    public AssessmentClass(int assessmentId, String title, String type, LocalDateTime endDate, int courseId) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.type = type;
        this.endDate = endDate;
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    @Override
    public String toString(){
        return title;
    }
}
