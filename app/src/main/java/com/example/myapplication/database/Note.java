package com.example.myapplication.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes", foreignKeys = {
        @ForeignKey(onDelete = ForeignKey.CASCADE, entity = Category.class,
                parentColumns = "id", childColumns = "category"),
        @ForeignKey(onDelete = ForeignKey.CASCADE, entity = Priority.class,
                parentColumns = "id", childColumns = "priority"),
        @ForeignKey(onDelete = ForeignKey.CASCADE, entity = Status.class,
                parentColumns = "id", childColumns = "status"),
        @ForeignKey(onDelete = ForeignKey.CASCADE, entity = User.class,
                parentColumns = "id", childColumns = "user")
})
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String name;

    public String plan_date;

    public String created_date;

    public int category;

    public int priority;

    public int status;

    @NonNull
    public int user;

    @Override
    public String toString() {
        return this.name;
    }
}
