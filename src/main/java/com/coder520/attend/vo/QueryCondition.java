package com.coder520.attend.vo;

import com.coder520.common.page.PageQueryBean;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/27 1:54
 */
public class QueryCondition extends PageQueryBean {
    private Long userId;
    private String startDate;
    private String endDate;
    private Byte attendStatus;
    private String rangeDate;

    public Byte getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(Byte attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(String rangeDate) {
        this.rangeDate = rangeDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
