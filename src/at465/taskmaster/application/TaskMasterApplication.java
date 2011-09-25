package at465.taskmaster.application;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import at465.taskmaster.local.LocalTasksManager;
import at465.taskmaster.remote.RemoteTaskManager;

public class TaskMasterApplication extends Application {

    private PropertyManager propertyManager;
    private TaskManager taskManager;

    @Override
    public void onCreate() {
	super.onCreate();
	propertyManager = new PropertyManager(this);

	// instantiate local tasks manager and dependencies
	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	LocalTasksManager localTasksManager = new LocalTasksManager(sharedPreferences);

	// instantiate remote tasks manager and dependencies
	AccountManager accountManager = AccountManager.get(this);
	String apiKey = propertyManager.getApiKey();
	RemoteTaskManager remoteTaskManager = new RemoteTaskManager(accountManager, apiKey);

	taskManager = new TaskManager(localTasksManager, remoteTaskManager);
    }

    public PropertyManager getPropertyManager() {
	return propertyManager;
    }

    public TaskManager getTasksManager() {
	return taskManager;
    }

}
