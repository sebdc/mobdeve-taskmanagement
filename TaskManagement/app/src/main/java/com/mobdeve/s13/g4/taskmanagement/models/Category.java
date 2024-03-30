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
    private String name;                // - Primary key
    private Date dateCreated;
    private String mainColor;           // - Hex code
    private String subColor;            // - Hex code
    private int ongoingTaskCount;
    private int completedTaskCount;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public Category( String name ) {
        this.name = name;
        this.dateCreated = new Date();
        this.ongoingTaskCount = 0;
        this.completedTaskCount = 0;
        this.mainColor = "";
        this.subColor = "";
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
    public String getName()             { return name; }
    public Date getDateCreated()        { return dateCreated; }
    public int getOngoingTaskCount()    { return ongoingTaskCount; }
    public int getCompletedTaskCount()  { return completedTaskCount; }
    public String getMainColor()        { return mainColor; }
    public String getSubColor()         { return subColor; }

    public void setName( String name )              { this.name = name; }
    public void setDateCreated( Date dateCreated )  { this.dateCreated = dateCreated; }
    public void setOngoingTaskCount( int count )    { this.ongoingTaskCount = count; }
    public void setCompletedTaskCount( int count )  { this.completedTaskCount = count; }
    public void setMainColor( String mainColor )    { this.mainColor = mainColor; }
    public void setSubColor( String subColor )      { this.subColor = subColor; }
}