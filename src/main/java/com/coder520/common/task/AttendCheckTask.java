package com.coder520.common.task;

import com.coder520.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/28 20:20
 */
public class AttendCheckTask {

    @Autowired
    private AttendService attendService;

    public void checkAttend() {
        attendService.checkAttend();
    }
}
