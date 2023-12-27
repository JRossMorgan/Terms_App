package entites;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AssessmentClass {
    private int assessmentId;
    private String title;
    private String type;
    private LocalDateTime endDate;

    public AssessmentClass(int assessmentId, String title, String type, LocalDateTime endDate) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.type = type;
        this.endDate = endDate;
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
}
