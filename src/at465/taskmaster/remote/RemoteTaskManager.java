package at465.taskmaster.remote;

import java.io.IOException;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.AsyncTask;

import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.model.TaskLists;

public class RemoteTaskManager {
    private Tasks taskService;
    private Listener listener;
    private Authenticator authenticator;

    public RemoteTaskManager(AccountManager accountManager, String apiKey) {
	authenticator = new Authenticator(accountManager, apiKey);
    }

    public void setTasksListener(Listener listener) {
	this.listener = listener;
    }

    public void loadTaskLists() {
	new AsyncTask<Void, Void, TaskLists>() {

	    @Override
	    protected TaskLists doInBackground(Void... params) {
		try {
		    taskService = taskService == null ? authenticator.authenticate() : taskService;
		    return taskService.tasklists.list().execute();
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (AuthenticatorException e) {
		    e.printStackTrace();
		} catch (OperationCanceledException e) {
		    e.printStackTrace();
		}

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
		try {
		    taskService = taskService == null ? authenticator.authenticate() : taskService;
		    return taskService.tasks.list(taskListId).execute();
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		} catch (AuthenticatorException e) {
		    e.printStackTrace();
		} catch (OperationCanceledException e) {
		    e.printStackTrace();
		}
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
