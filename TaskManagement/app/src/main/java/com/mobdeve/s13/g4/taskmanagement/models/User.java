package com.mobdeve.s13.g4.taskmanagement.models;

import java.util.UUID;

/*******************************************************************
 *
 *  The User class...
 *
 *******************************************************************/
public class User {

    // - Class Attributes
    private String id;
    private String username;
    private String email;
    private UserData userData;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public User() {
        generateUserId();
        this.userData = new UserData();
    }

    public User( String username, String email ) {
        generateUserId();
        this.username = username;
        this.email = email;
        this.userData = new UserData();
    }

    private void generateUserId() {
        this.id = UUID.randomUUID().toString();
    }

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/

    /*|*******************************************************
                        Getters & Setters 
        Do not modify these methods as they are designed to 
        only serve the purpose of accessing and updating the 
        state of User objects.
    *********************************************************/
    public String getId()           { return id; }
    public String getUsername()     { return username; }
    public String getEmail()        { return email; }
    public UserData getUserData()   { return userData; }

    public void setUsername( String username )      { this.username = username; }
    public void setEmail( String email )            { this.email = email; }
    public void setUserData( UserData userData )    { this.userData = userData; }
}