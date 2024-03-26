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
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private UserData userData;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public User() {
        generateUserId();
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
    public String getEmail()        { return email; }
    public String getUsername()     { return username; }
    public String getFirstName()    { return firstName; }
    public String getLastName()     { return lastName; }
    public UserData getUserData()   { return userData; }

    public void setEmail( String email )            { this.email = email; }
    public void setUsername( String username )      { this.username = username; }
    public void setFirstName( String firstName )    { this.firstName = firstName; }
    public void setLastName( String lastName )      { this.lastName = lastName; }
    public void setUserData( UserData userData )    { this.userData = userData; }
}