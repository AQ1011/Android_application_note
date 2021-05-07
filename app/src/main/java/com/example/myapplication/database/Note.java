package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String name;

    public String plan_date;

    public String created_date;

    public String category;

    public  String priority;

    public String status;

    @NonNull
    public String user;
}
