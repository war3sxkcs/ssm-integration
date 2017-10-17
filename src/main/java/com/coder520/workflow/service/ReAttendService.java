package com.coder520.workflow.service;

import com.coder520.workflow.entity.ReAttend;

import java.util.List;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/29 1:17
 */
public interface ReAttendService {

    void startReAttendFlow(ReAttend reAttend);

    List<ReAttend> listTasks(String userName);

    void approve(ReAttend reAttend);

    List<ReAttend> listReAttend(String username);
}
