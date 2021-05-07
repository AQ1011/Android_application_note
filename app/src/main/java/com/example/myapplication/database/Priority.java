package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "priority")
public class Priority {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    public String created_date;

    @NonNull
    public String user;
}
