package at465.taskmaster.application;

import java.util.List;

import com.google.api.services.tasks.model.Task;

public class Util {
    public static int getIndentLevel(List<Task> tasks, int position) {
	int parentCount = 0;
	
	Task task = tasks.get(position);
	String parent = task.getParent();
	
	// repeatedly find your parent
	// increment count each time until no more parents
	while (parent != null) {
	    task = tasks.get(position--);
	    if (task.getId().equals(parent)) {
		parent = task.getParent();
		parentCount++;
	    }
	}
	
	return parentCount;
    }
}
