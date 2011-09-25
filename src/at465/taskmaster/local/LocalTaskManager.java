package at465.taskmaster.local;

import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskLists;

public class LocalTaskManager {
    private Listener listener;
    private SharedPreferences sharedPreferences;

    public LocalTaskManager(SharedPreferences sharedPreferences) {
	this.sharedPreferences = sharedPreferences;
    }
    
    public void setTasksListener(Listener listener) {
	this.listener = listener;
    }

    public void loadTaskLists() {
	new AsyncTask<Void, Void, TaskLists>() {

	    @Override
	    protected TaskLists doInBackground(Void... params) {
		return null;
	    }

	    @Override
	    protected void onPostExecute(TaskLists result) {
		listener.tasksListUpdated(result);
	    }

	}.execute();
    }

    public void loadTasks(final String taskListId) {
	new AsyncTask<Void, Void, com.google.api.services.tasks.model.Tasks>() {

	    @Override
	    protected com.google.api.services.tasks.model.Tasks doInBackground(Void... params) {
		return null;
	    }

	    @Override
	    protected void onPostExecute(com.google.api.services.tasks.model.Tasks result) {
		listener.tasksUpdated(taskListId, result);
	    };

	}.execute();
    }

    public static interface Listener {
	void tasksUpdated(String taskListId, com.google.api.services.tasks.model.Tasks tasks);

	void tasksListUpdated(TaskLists tasklists);
    }
}
