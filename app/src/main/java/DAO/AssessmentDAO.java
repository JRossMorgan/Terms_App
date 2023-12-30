package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entites.AssessmentClass;

@Dao
public interface AssessmentDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert (AssessmentClass assessment);

    @Update
    void update (AssessmentClass assessment);

    @Delete
    void delete (AssessmentClass assessment);

    @Query("SELECT * FROM assessments ORDER BY title ASC")
    List<AssessmentClass> getAllAssessments();
}
