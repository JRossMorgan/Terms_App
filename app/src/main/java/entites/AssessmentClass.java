package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity (tableName = "assessments")
public class AssessmentClass {
    @PrimaryKey (autoGenerate = true)
    private int assessmentId;
    private String title;
    private String type;
    private long startDate;
    private Long endDate;
    private boolean notify;
    private boolean endNotify;
    private int courseId;

    public AssessmentClass(int assessmentId, String title, String type,Long startDate, Long endDate, boolean notify, boolean endNotify, int courseId) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notify = notify;
        this.endNotify = endNotify;
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
    public Long getStartDate(){return startDate;}
    public void setStartDate(Long startDate){this.startDate = startDate;}

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
    public boolean getNotify(){return notify;}
    public void setNotify(boolean notify){this.notify = notify;}
    public boolean getEndNotify(){return endNotify;}
    public void setEndNotify(boolean endNotify){this.endNotify = endNotify;}

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

    public String formattedEnd(Long endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(endDate));
    }
    public String formattedStart(Long startDate){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(startDate));
    }
}
