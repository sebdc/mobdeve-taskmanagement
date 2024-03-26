package com.mobdeve.s13.g4.taskmanagement.models;

import com.mobdeve.s13.g4.taskmanagement.models.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*******************************************************************
 *
 *  The Task class...
 *
 *******************************************************************/
public class Task implements Serializable {

    // - Class Attributes
    private String id;
    private String title;
    private String description;
    private Category category;
    private Date dateCreated;
    private Date dueDate;
    private boolean isCompleted;
    private List<Subtask> subtasks;
    private String priorityLevel;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public Task( String title ) {
        this.title = title;
        generateTaskId();
        initTaskDetails();
    }

    public Task( String title, String description ) {
        this.title = title;
        this.description = description;
        generateTaskId();
        initTaskDetails();
    }

    public Task( String title, String description, Category category ) {
        this.title = title;
        this.description = description;
        this.category = category;
        generateTaskId();
        initTaskDetails();
    }

    public Task( String title, String description, Category category, Date dueDate ) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.dueDate = dueDate;
        generateTaskId();
        initTaskDetails();
    }

    private void generateTaskId() {
        this.id = UUID.randomUUID().toString();
    }

    private void initTaskDetails() {
        this.dateCreated = new Date();
        this.isCompleted = false;
        this.subtasks = new ArrayList<>();
    }

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", dateCreated=" + dateCreated +
                ", dueDate=" + dueDate +
                ", isCompleted=" + isCompleted +
                '}';
    }

    public void addSubtask( Subtask subtask ) {
        subtasks.add(subtask);
    }

    public void removeSubtask( Subtask subtask ) {
        subtasks.remove(subtask);
    }

    /*|*******************************************************
                        Getters & Setters 
        Do not modify these methods as they are designed to 
        only serve the purpose of accessing and updating the 
        state of Task objects.
    *********************************************************/
    public String getId()               { return id; }
    public String getTitle()            { return title; }
    public String getDescription()      { return description; }
    public Category getCategory()       { return category; }
    public Date getDueDate()            { return dueDate; }
    public Date getDateCreated()        { return dateCreated; }
    public boolean isCompleted()        { return isCompleted; }
    public String getPriorityLevel()    { return priorityLevel; }
    public List<Subtask> getSubtasks()  { return subtasks; }

    public void setTitle( String title )                { this.title = title; }
    public void setDescription( String description )    { this.description = description; }
    public void setCategory( Category category )        { this.category = category; }
    public void setDueDate( Date dueDate )              { this.dueDate = dueDate; }
    public void setCompleted( boolean isCompleted )     { this.isCompleted = isCompleted; }
    public void setPriorityLevel(String priorityLevel ) { this.priorityLevel = priorityLevel; }

}