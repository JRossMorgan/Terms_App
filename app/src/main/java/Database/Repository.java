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
    public void insert (AssessmentClass assessmentClass){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.insert(assessmentClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(AssessmentClass assessmentClass){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.update(assessmentClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(AssessmentClass assessmentClass){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.delete(assessmentClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
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
    public void insert (CourseClass courseClass){
        databaseExecutor.execute(()-> {
            mCoursesDAO.insert(courseClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(CourseClass courseClass){
        databaseExecutor.execute(()-> {
            mCoursesDAO.update(courseClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(CourseClass courseClass){
        databaseExecutor.execute(()-> {
            mCoursesDAO.delete(courseClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
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
    public void insert(Notes notes){
        databaseExecutor.execute(()-> {
            mNotesDAO.insert(notes);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
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
    public void insert(TermClass term){
        databaseExecutor.execute(()-> {
            mTermsDAO.insert(term);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(TermClass termClass){
        databaseExecutor.execute(()-> {
            mTermsDAO.update(termClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(TermClass termClass){
        databaseExecutor.execute(()-> {
            mTermsDAO.delete(termClass);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
