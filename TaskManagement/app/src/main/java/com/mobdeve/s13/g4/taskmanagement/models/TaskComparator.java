package com.mobdeve.s13.g4.taskmanagement.models;

import java.util.Comparator;

/*******************************************************************
 *
 *  The Task class...
 *
 *  The Subtask class...
 *
 *******************************************************************/
public class TaskComparator implements Comparator<Task> {

    /*|*******************************************************
                        Behaviour Methods
    *********************************************************/
    @Override
    public int compare( Task t1, Task t2 ) {
        // - Compare tasks based on due date, category, and creation time
        int dueDateComparison = t1.getDueDate().compareTo( t2.getDueDate() );
        if( dueDateComparison != 0 ) {
            return dueDateComparison;
        }

        int categoryComparison = t1.getCategory().getName().compareTo( t2.getCategory().getName() );
        if( categoryComparison != 0 ) {
            return categoryComparison;
        }

        return t1.getDateCreated().compareTo( t2.getDateCreated() );
    }
}