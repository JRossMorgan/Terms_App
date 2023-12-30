package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entites.CourseClass;
import entites.Notes;

@Dao
public interface CoursesDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert (CourseClass course);

    @Update
    void update (CourseClass course);

    @Delete
    void delete (CourseClass course);

    @Query("SELECT * FROM courses ORDER BY title ASC")
    List<CourseClass> getAllCourses();

   // @Query("SELECT * FROM courses WHERE courses.courseId = notes.courseId ORDER BY title ASC")
    
}
