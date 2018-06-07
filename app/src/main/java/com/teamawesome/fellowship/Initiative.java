package com.teamawesome.fellowship;

import java.util.Date;

/**
 * Created by Aronson1 on 6/6/18.
 */

public class Initiative {

    String title;
    String description;
    Date startDate;
    Date endDate;
    String taskInterval;
    int groupSize;
    boolean isOpen;

    public Initiative(String title, String description, Date startDate, Date endDate,
            String taskInterval, int groupSize, boolean isOpen) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskInterval = taskInterval;
        this.groupSize = groupSize;
        this.isOpen = isOpen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTaskInterval() {
        return taskInterval;
    }

    public void setTaskInterval(String taskInterval) {
        this.taskInterval = taskInterval;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
