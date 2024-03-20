package com.mobdeve.s13.g4.taskmanagement.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/*******************************************************************
 *
 *  The Category class...
 *  * 
 *******************************************************************/
public class Category implements Serializable {

    // - Class Attributes
    private String id;
    private String name;
    private Date dateCreated;
    private int ongoingTaskCount;
    private int completedTaskCount;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public Category( String name ) {
        generateCategoryId();
        this.name = name;
        this.dateCreated = new Date();
        this.ongoingTaskCount = 0;
        this.completedTaskCount = 0;
    }

    private void generateCategoryId() {
        this.id = UUID.randomUUID().toString();
    }

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/
    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }

    /*|*******************************************************
                        Getters & Setters 
        Do not modify these methods as they are designed to 
        only serve the purpose of accessing and updating the 
        state of UserData objects.
    *********************************************************/
    public String getId()               { return id; }
    public String getName()             { return name; }
    public Date getDateCreated()        { return dateCreated; }
    public int getOngoingTaskCount()    { return ongoingTaskCount; }
    public int getCompletedTaskCount()  { return completedTaskCount; }

    public void setName( String name )              { this.name = name; }
    public void setOngoingTaskCount( int count )    { this.ongoingTaskCount = count; }
    public void setCompletedTaskCount( int count )  { this.completedTaskCount = count; }
}