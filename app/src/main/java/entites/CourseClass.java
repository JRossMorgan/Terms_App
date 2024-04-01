package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity (tableName = "courses")
public class CourseClass {
    @PrimaryKey (autoGenerate = true)
    private int courseId;
    private String title;
    private Long start;
    private Long end;
    private String status;
    private String instructorName;
    private String instructorNumber;
    private String instructorEmail;
    private boolean notifications;
    private boolean endNotification;
    private int termId;

    public CourseClass(int courseId, String title, Long start, Long end, String status, String instructorName, String instructorNumber, String instructorEmail, boolean notifications, boolean endNotification, int termId) {
        this.courseId = courseId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorNumber = instructorNumber;
        this.instructorEmail = instructorEmail;
        this.notifications = notifications;
        this.endNotification = endNotification;
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
    public boolean getNotifications(){ return notifications;}
    public void setNotifications(boolean notifications){
        this.notifications = notifications;
    }
    public boolean getEndNotification(){return endNotification;}
    public void setEndNotification(boolean endNotification){this.endNotification = endNotification;}

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    public String toString(){
        return title;
    }

    public String formattedStart(Long start){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(start));
    }
    public String formattedEnd(Long end){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(end));
    }
}
