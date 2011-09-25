package at465.taskmaster.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at465.taskmaster.local.LocalTaskManager;
import at465.taskmaster.remote.RemoteTaskManager;
import at465.taskmaster.remote.RemoteTaskManager.Listener;

import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

public class TaskManager {
    private List<TaskList> taskLists = new ArrayList<TaskList>();
    private Map<String, List<Task>> tasks = new HashMap<String, List<Task>>();
    private Map<String, TasksListener> taskListeners = new HashMap<String, TasksListener>();
    private TaskListListener taskListListener;
    private RemoteTaskManager remoteTaskManager;
    private LocalTaskManager localTaskManager;

    public void getTaskLists() {
	if (taskLists.size() > 0) {
	    taskListListener.taskListsUpdated(taskLists);
	} else {
	    remoteTaskManager.loadTaskLists();
	}
    }

    public void getTasks(String taskListId) {
	if (tasks.containsKey(taskListId)) {
	    taskListeners.get(taskListId).tasksUpdated(taskListId, tasks.get(taskListId));
	} else {
	    remoteTaskManager.loadTasks(taskListId);
	}
    }

    public TaskManager(LocalTaskManager localTaskManager, RemoteTaskManager remoteTaskManager) {
	this.localTaskManager = localTaskManager;

	this.remoteTaskManager = remoteTaskManager;
	remoteTaskManager.setTasksListener(new Listener() {

	    @Override
	    public void tasksUpdated(String taskListId, Tasks tasks) {
		TaskManager.this.tasks.put(taskListId, tasks.getItems());
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
