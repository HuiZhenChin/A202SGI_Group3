package com.example.busybuddy;

import java.util.ArrayList;

public class TaskItem implements Comparable<TaskItem> {
    private String title;
    private String dueDate;

    private String prioritySymbol;

    private String folder;

    private String note;

    public static ArrayList<TaskItem> savedtasklist = new ArrayList<>();

    public static ArrayList<TaskItem> a() {
        ArrayList<TaskItem> taskitems = new ArrayList<>();

        for (TaskItem taskItem : savedtasklist) {
            taskitems.add(taskItem);
        }

        return taskitems;

    }

    public TaskItem(String title, String dueDate, String prioritySymbol, String folder, String note) {
        this.title = title;
        this.dueDate = dueDate;
        this.prioritySymbol= prioritySymbol;
        this.folder= folder;
        this.note=note;
    }

    @Override
    public int compareTo(TaskItem otherTask) {
        //sorting
        if ("High Priority".equals(prioritySymbol)) {
            return -1; //high priority tasks come first
        } else if ("Medium Priority".equals(prioritySymbol)) {
            return 0; //medium priority tasks come next
        } else {
            return 1; //low priority tasks come last
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPrioritySymbol(){return prioritySymbol;}

    public String getFolder(){return folder;}

    public String getNote(){return note;}

    public void setFolder(String folder){this.folder= folder;}

    public void setPrioritySymbol(String priority) {
        this.prioritySymbol = priority;
    }


}
