package com.coder520.attend.service;

import com.coder520.attend.entity.Attend;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/26 13:07
 */
public interface AttendService {
    void signAttend(Attend attend) throws Exception;

    PageQueryBean listAttend(QueryCondition condition);

    void checkAttend();
}
