package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    List<Note> getAllNote();

    @Query("SELECT * FROM notes WHERE name = :name")
    Category findAllNote(String name);

    @Query("SELECT * FROM notes WHERE user = :userId")
    List<Note> getUserNotes(int userId);

    @Query("SElECT * FROM notes WHERE id = :id")
    Note find(int id);

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

}
