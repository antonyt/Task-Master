package at465.taskmaster.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at465.taskmaster.application.RemoteTasksManager.Listener;

import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

public class TasksManager {
    private List<TaskList> tasksList = new ArrayList<TaskList>();
    private Map<String, List<Task>> tasks = new HashMap<String, List<Task>>();
    private RemoteTasksManager remoteTasksManager;
    private LocalTasksManager localTasksManager;
    private TasksListener listener;

    public List<TaskList> getTaskLists() {
	return tasksList;
    }

    public void getTasks(String taskListId) {
	if (tasks.containsKey(taskListId)) {
	    listener.tasksUpdated(taskListId, tasks.get(taskListId));
	} else {
	    remoteTasksManager.loadTasks(taskListId);
	}
    }

    public TasksManager(LocalTasksManager localTasksManager, RemoteTasksManager remoteTasksManager) {
	this.localTasksManager = localTasksManager;

	this.remoteTasksManager = remoteTasksManager;
	remoteTasksManager.setTasksListener(new Listener() {

	    @Override
	    public void tasksUpdated(String taskListId, Tasks tasks) {
		tasks.put(taskListId, tasks.getItems());
		listener.tasksUpdated(taskListId, tasks.getItems());
	    }

	    @Override
	    public void tasksListUpdated(TaskLists tasklists) {
		// TODO Auto-generated method stub

	    }
	});

    }

    public void setTasksListener(TasksListener listener) {
	this.listener = listener;
    }

    public static interface TasksListener {
	void tasksUpdated(String taskListId, List<Task> tasks);
    }

}
