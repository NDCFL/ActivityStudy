package com.fl;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenfeilong on 2017/10/13.
 */
public class LeaveBean implements Serializable {
    private Integer days;
    private String reason;
    private Date leaveTime;

    public LeaveBean(Integer days, String reason, Date leaveTime) {
        this.days = days;
        this.reason = reason;
        this.leaveTime = leaveTime;
    }

    public LeaveBean() {
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }
}
