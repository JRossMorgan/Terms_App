package entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "terms")
public class TermClass {
    @PrimaryKey (autoGenerate = true)
    private int termId;
    private String termTitle;
    private Long start;
    private Long end;
    private boolean notify;
    private boolean alsoNotify;

    public TermClass(int termId, String termTitle,Long start, Long end, boolean notify, boolean alsoNotify) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.start = start;
        this.end = end;
        this.notify = notify;
        this.alsoNotify = alsoNotify;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public int getTermId() {return termId;}

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public Long getStart() {return start;}

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
    public boolean getNotify(){return notify;}
    public void setNotify(boolean notify){this.notify = notify;}
    public boolean getAlsoNotify(){return alsoNotify;}
    public void setAlsoNotify(boolean alsoNotify){this.alsoNotify = alsoNotify;}
    @Override
    public String toString() {return termTitle;}

    public String formattedStart(Long start){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(start));
    }
    public String formattedEnd(Long end){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(end));
    }
}
