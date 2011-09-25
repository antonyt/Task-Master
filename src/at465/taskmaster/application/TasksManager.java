package at465.taskmaster.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at465.taskmaster.local.LocalTasksManager;
import at465.taskmaster.remote.RemoteTasksManager;
import at465.taskmaster.remote.RemoteTasksManager.Listener;

import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

public class TasksManager {
    private List<TaskList> taskLists = new ArrayList<TaskList>();
    private Map<String, List<Task>> tasks = new HashMap<String, List<Task>>();
    private Map<String, TasksListener> taskListeners = new HashMap<String, TasksListener>();
    private TaskListListener taskListListener;
    private RemoteTasksManager remoteTasksManager;
    private LocalTasksManager localTasksManager;

    public void getTaskLists() {
	if (taskLists.size() > 0) {
	    taskListListener.taskListsUpdated(taskLists);
	} else {
	    remoteTasksManager.loadTaskLists();
	}
    }

    public void getTasks(String taskListId) {
	if (tasks.containsKey(taskListId)) {
	    taskListeners.get(taskListId).tasksUpdated(taskListId, tasks.get(taskListId));
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
		TasksManager.this.tasks.put(taskListId, tasks.getItems());
		taskListeners.get(taskListId).tasksUpdated(taskListId, tasks.getItems());
	    }

	    @Override
	    public void tasksListUpdated(TaskLists tasklists) {
		taskLists = tasklists.getItems();
		taskListListener.taskListsUpdated(tasklists.getItems());
	    }
	});

    }

    public void setTaskListener(String taskListId, TasksListener listener) {
	taskListeners.put(taskListId, listener);
    }
    
    public void setTaskListListener(TaskListListener listener) {
	taskListListener = listener;
    }

    public static interface TasksListener {
	void tasksUpdated(String taskListId, List<Task> tasks);
    }
    
    public static interface TaskListListener {
	void taskListsUpdated(List<TaskList> taskLists);
    }

}
