package com.mobdeve.s13.g4.taskmanagement.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mobdeve.s13.g4.taskmanagement.R;
import com.mobdeve.s13.g4.taskmanagement.adapters.*;
import com.mobdeve.s13.g4.taskmanagement.fragments.CalendarFragment;
import com.mobdeve.s13.g4.taskmanagement.fragments.HomeFragment;
import com.mobdeve.s13.g4.taskmanagement.fragments.ProfileFragment;
import com.mobdeve.s13.g4.taskmanagement.models.*;
import com.mobdeve.s13.g4.taskmanagement.database.*;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private static final String USERS_NODE = "users";
    private static final String CATEGORIES_NODE = "categories";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /*|*******************************************************
                        User Functionalities 
    *********************************************************/
    public void addUserToFirebase( User user ) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(user.getId());
        userRef.child("email").setValue(user.getEmail());
        userRef.child("username").setValue(user.getUsername());
    }

    public void editUserInFirebase( User updatedUser ) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(updatedUser.getId());

        Map<String, Object> updatedUserData = new HashMap<>();
        updatedUserData.put("email", updatedUser.getEmail());
        updatedUserData.put("username", updatedUser.getUsername());

        userRef.updateChildren(updatedUserData);
    }

    public void deleteUserFromFirebase( String userId ) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(userId);
        userRef.removeValue();
    }


    /*|*******************************************************
                    Category Functionalities 
    *********************************************************/
    public void addCategoryToFirebase( Category category ) {
        DatabaseReference categoryRef = databaseReference.child(CATEGORIES_NODE).child(category.getId());
        categoryRef.child("name").setValue(category.getName());
        categoryRef.child("dateCreated").setValue(category.getDateCreated().getTime()); // Store date as long
        categoryRef.child("ongoingTaskCount").setValue(category.getOngoingTaskCount());
        categoryRef.child("completedTaskCount").setValue(category.getCompletedTaskCount());
    }

    public void editCategoryInFirebase( Category updatedCategory ) {
        DatabaseReference categoryRef = databaseReference.child(CATEGORIES_NODE).child(updatedCategory.getId());

        Map<String, Object> updatedCategoryData = new HashMap<>();
        updatedCategoryData.put("name", updatedCategory.getName());
        updatedCategoryData.put("ongoingTaskCount", updatedCategory.getOngoingTaskCount());
        updatedCategoryData.put("completedTaskCount", updatedCategory.getCompletedTaskCount());

        categoryRef.updateChildren(updatedCategoryData);
    }

    public void deleteCategoryFromFirebase( String categoryId ) {
        DatabaseReference categoryRef = databaseReference.child(CATEGORIES_NODE).child(categoryId);
        categoryRef.removeValue();
    }


    /*|*******************************************************
                    Task Functionalities 
    *********************************************************/
    public void addTaskToFirebase( String userId, Task task ) {
        DatabaseReference tasksRef = databaseReference.child(USERS_NODE).child(userId).child("tasks").push();
        String taskId = tasksRef.getKey();

        Map<String, Object> taskData = new HashMap<>();
        taskData.put("id", task.getId());
        taskData.put("title", task.getTitle());
        taskData.put("description", task.getDescription());
        taskData.put("categoryId", task.getCategory().getId());
        taskData.put("dateCreated", task.getDateCreated().getTime()); // Store date as long
        taskData.put("dueDate", task.getDueDate() != null ? task.getDueDate().getTime() : null); // Store date as long or null
        taskData.put("isCompleted", task.isCompleted());
        taskData.put("priorityLevel", task.getPriorityLevel());

        tasksRef.setValue(taskData);

        // - Add subtasks
        for( Subtask subtask : task.getSubtasks() ) {
            DatabaseReference subtasksRef = tasksRef.child("subtasks").push();
            Map<String, Object> subtaskData = new HashMap<>();
            subtaskData.put("description", subtask.getDescription());
            subtaskData.put("isCompleted", subtask.isCompleted());
            subtasksRef.setValue(subtaskData);
        }
    }

    public void editTaskInFirebase( String userId, Task updatedTask ) {
        DatabaseReference taskRef = databaseReference.child(USERS_NODE).child(userId).child("tasks").child(updatedTask.getId());

        Map<String, Object> updatedTaskData = new HashMap<>();
        updatedTaskData.put("title", updatedTask.getTitle());
        updatedTaskData.put("description", updatedTask.getDescription());
        updatedTaskData.put("categoryId", updatedTask.getCategory().getId());
        updatedTaskData.put("dueDate", updatedTask.getDueDate() != null ? updatedTask.getDueDate().getTime() : null);
        updatedTaskData.put("isCompleted", updatedTask.isCompleted());
        updatedTaskData.put("priorityLevel", updatedTask.getPriorityLevel());

        taskRef.updateChildren(updatedTaskData);
    }

    public void deleteTaskFromFirebase( String userId, String taskId ) {
        DatabaseReference taskRef = databaseReference.child(USERS_NODE).child(userId).child("tasks").child(taskId);
        taskRef.removeValue();
    }


    /*|*******************************************************
                    Subtask Functionalities 
    *********************************************************/
    public void addSubtaskToFirebase( String userId, String taskId, Subtask subtask ) {
        DatabaseReference subtasksRef = databaseReference.child(USERS_NODE).child(userId).child("tasks").child(taskId).child("subtasks").push();
        Map<String, Object> subtaskData = new HashMap<>();
        subtaskData.put("description", subtask.getDescription());
        subtaskData.put("isCompleted", subtask.isCompleted());
        subtasksRef.setValue(subtaskData);
    }

    public void editSubtaskInFirebase( String userId, String taskId, Subtask updatedSubtask ) {
        DatabaseReference subtaskRef = databaseReference.child(USERS_NODE).child(userId).child("tasks").child(taskId).child("subtasks").child(updatedSubtask.getId());

        Map<String, Object> updatedSubtaskData = new HashMap<>();
        updatedSubtaskData.put("description", updatedSubtask.getDescription());
        updatedSubtaskData.put("isCompleted", updatedSubtask.isCompleted());

        subtaskRef.updateChildren(updatedSubtaskData);
    }

    public void deleteSubtaskFromFirebase( String userId, String taskId, String subtaskId ) {
        DatabaseReference subtaskRef = databaseReference.child(USERS_NODE).child(userId).child("tasks").child(taskId).child("subtasks").child(subtaskId);
        subtaskRef.removeValue();
    }

}