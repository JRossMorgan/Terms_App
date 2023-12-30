package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entites.TermClass;

@Dao
public interface TermsDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert (TermClass term);

    @Update
    void update (TermClass term);

    @Delete
    void delete (TermClass term);

    @Query("SELECT * FROM terms ORDER BY start ASC")
    List<TermClass> getAllTerms();
}
