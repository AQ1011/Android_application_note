package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories")
    List<Category> getAllCategory();

    @Query("SELECT * FROM categories WHERE id = :id")
    Category findAllCategory(int id);

    @Query("SELECT * FROM categories WHERE user = :username")
    List<Category> getUserCategory(String username);

    @Query("SElECT * FROM categories WHERE user = :username AND name = :name")
    Category find(String name, String username);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

}
