package com.mobdeve.s13.g4.taskmanagement.models;

import java.io.Serializable;
import java.util.UUID;

/*******************************************************************
 *
 *  The Subtask class...
 *
 *******************************************************************/
public class Subtask implements Serializable {

    // - Class Attributes
    private String id;
    private String description;
    private Boolean isCompleted;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public Subtask( String description ) {
        this.description = description;
        this.isCompleted = false;
        generateSubtaskId();
    }

    private void generateSubtaskId() {
        this.id = UUID.randomUUID().toString();
    }

    /*|*******************************************************
                        Getters & Setters
        Do not modify these methods as they are designed to
        only serve the purpose of accessing and updating the
        state of Subtask objects.
    *********************************************************/
    public String getId()           { return id; }
    public String getDescription()  { return description; }
    public boolean isCompleted()    { return isCompleted; }

    public void setDescription( String description )    { this.description = description; }
    public void setCompleted( boolean isCompleted )     { this.isCompleted = isCompleted; }
}