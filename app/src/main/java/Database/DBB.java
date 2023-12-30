package Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import DAO.AssessmentDAO;
import DAO.CoursesDAO;
import DAO.NotesDAO;
import DAO.TermsDAO;
import entites.AssessmentClass;
import entites.CourseClass;
import entites.Notes;
import entites.TermClass;

@Database(entities = {AssessmentClass.class, CourseClass.class, Notes.class, TermClass.class}, version = 1, exportSchema = false)
public abstract class DBB extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CoursesDAO coursesDAO();
    public abstract NotesDAO notesDAO();
    public abstract TermsDAO termsDAO();
    private static volatile DBB INSTANCE;
    static DBB getDatabase (final Context context){
        if (INSTANCE == null){
            synchronized (DBB.class){
                if(INSTANCE == null){
                    Room.databaseBuilder(context.getApplicationContext(), DBB.class, "OurDatabase.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
