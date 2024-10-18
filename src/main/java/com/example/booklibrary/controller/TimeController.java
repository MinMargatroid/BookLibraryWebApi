package com.example.booklibrary.controller;

import com.example.booklibrary.entities.Time;
import org.springframework.web.bind.annotation.*;
@RestController

public class TimeController {

    @RequestMapping("/time")
    public String getCurrentTime(){
        Time time = new Time();
        return time.getLocalTime();
    }
}
