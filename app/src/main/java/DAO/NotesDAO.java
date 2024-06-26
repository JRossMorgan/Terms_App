package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entites.Notes;

@Dao
public interface NotesDAO {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert (Notes note);

    @Update
    void update(Notes note);

    @Delete
    void delete (Notes note);

    @Query("SELECT * FROM notes ORDER BY noteId ASC")
    List<Notes> getAllNotes();
}
