package Database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DAO.AssessmentDAO;
import DAO.CoursesDAO;
import DAO.NotesDAO;
import DAO.TermsDAO;
import entites.AssessmentClass;
import entites.CourseClass;
import entites.Notes;
import entites.TermClass;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CoursesDAO mCoursesDAO;
    private NotesDAO mNotesDAO;
    private TermsDAO mTermsDAO;
    private List<AssessmentClass> mAllAssessments;
    private List<CourseClass> mAllCourses;
    private List<Notes> mAllNotes;
    private List<TermClass> mAllTerms;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public Repository(Application application){
        DBB db = DBB.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCoursesDAO = db.coursesDAO();
        mNotesDAO = db.notesDAO();
        mTermsDAO = db.termsDAO();
    }
    public List<AssessmentClass> getAllAssessments(){
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }
    public List<CourseClass> getAllCourses(){
        databaseExecutor.execute(() -> {
            mAllCourses = mCoursesDAO.getAllCourses();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public List<Notes> getAllNotes(){
        databaseExecutor.execute(() -> {
            mAllNotes = mNotesDAO.getAllNotes();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllNotes;
    }
    public List<TermClass> getAllTerms(){
        databaseExecutor.execute(() -> {
            mAllTerms = mTermsDAO.getAllTerms();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }
}
