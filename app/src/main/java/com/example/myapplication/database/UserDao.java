package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :Email")
    User loadByEmail(@NonNull String Email);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}
