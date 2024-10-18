package com.example.booklibrary.entities;

import java.time.LocalDateTime;

public class Time {
    public Time(){
    }

    public String getLocalTime(){
        LocalDateTime myObj = LocalDateTime.now();
        return myObj.toString();
    }
}
