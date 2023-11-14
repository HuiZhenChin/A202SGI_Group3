package com.example.busybuddy;

import java.util.ArrayList;
import java.util.List;

// TaskItem (task in recycler view Priority List)
public class TaskItem implements Comparable<TaskItem> {
    private String title;
    private String dueDate;
    private String prioritySymbol;
    private int priorityNo, taskID;
    private String folder;
    private String note;
    private String status;
    private List<String> tasks;

    // savedtasklist for storing task
    public static ArrayList<TaskItem> savedtasklist = new ArrayList<>();

    public static ArrayList<TaskItem> a() {
        ArrayList<TaskItem> taskitems = new ArrayList<>();

        for (TaskItem taskItem : savedtasklist) {
            taskitems.add(taskItem);
        }

        return taskitems;

    }

    // constructor
    public TaskItem(String title, List<String> tasks, String dueDate, String prioritySymbol, int priorityNo, String folder, String note, String status) {
        this.title = title;
        this.dueDate = dueDate;
        this.prioritySymbol= prioritySymbol;
        this.priorityNo= priorityNo;
        this.folder= folder;
        this.note=note;
        this.status= status;
        this.tasks= tasks;  // list of tasks for DisplayFolder activity

    }


    @Override
    public int compareTo(TaskItem otherTask) {
        //sorting
        if ("High Priority".equals(prioritySymbol)) {
            return -1; // high priority tasks come first
        } else if ("Medium Priority".equals(prioritySymbol)) {
            return 0; // medium priority tasks come next
        } else {
            return 1; // low priority tasks come last
        }
    }

    public String getTitle() {
        return title;
    }
    public String getDueDate() {
        return dueDate;
    }
    public String getPrioritySymbol(){return prioritySymbol;}
    public int getPriorityNo(){return priorityNo;}

    public String getFolder(){return folder;}

    public String getNote(){return note;}

    public List<String> getTask() {
        return tasks;
    }
    public String getStatus(){return status;}

    public void setFolder(String folder){this.folder= folder;}
    public void setPrioritySymbol(String priority) {
        this.prioritySymbol = priority;
    }
    public void setPriorityNo(int priorityNo){this.priorityNo= priorityNo;}
    public void setStatus(String status){this.status= status;}

}

