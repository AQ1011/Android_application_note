package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface StatusDao {

    @Query("SELECT * FROM status")
    List<Status> getAll();

    @Query("SELECT * FROM status WHERE id = :id")
    Status find(int id);

    @Query("SELECT * FROM status WHERE name = :name")
    Status findName(String name);

    @Query("SELECT * FROM categories WHERE user = :username")
    List<Category> getUserStatus(String username);

    @Insert
    void insert(Status status);

    @Update
    void update(Status status);

    @Delete
    void delete(Status status);

}
