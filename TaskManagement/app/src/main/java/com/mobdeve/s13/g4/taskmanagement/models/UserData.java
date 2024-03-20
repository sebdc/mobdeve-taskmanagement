package com.mobdeve.s13.g4.taskmanagement.models;

import java.util.Date;
import java.util.TreeSet;

/*******************************************************************
 *
 *  The UserData class...
 *
 *******************************************************************/
public class UserData {

    // - Class Attributes
    TreeSet<Task> taskSet;
    TreeSet<Task> completedTasks;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public UserData() {
        taskSet = new TreeSet<>(new TaskComparator());
        completedTasks = new TreeSet<>(new TaskComparator());
        initTempData();
    }

    public void initTempData() {
        taskSet.add( new Task("Task 1", "Description 1", new Category("Category A") , new Date()) );
        taskSet.add( new Task("Task 2", "Description 2", new Category("Category B") , new Date()) );
        taskSet.add( new Task("Task 3", "Description 3", new Category("Category C") , new Date()) );
    }

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/
    public void displayAllTasks() {
        System.out.println( "Tasks in sorted order:" );
        for( Task task : taskSet ) {
            System.out.println( task );
        }
    }

    /*|*******************************************************
                        Getters & Setters 
        Do not modify these methods as they are designed to 
        only serve the purpose of accessing and updating the 
        state of UserData objects.
    *********************************************************/
    public TreeSet<Task> getTaskSet()           { return taskSet; }
    public TreeSet<Task> getCompletedTasks()    { return completedTasks; }
} 