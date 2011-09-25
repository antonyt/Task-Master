package at465.taskmaster.application;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TaskMasterApplication extends Application {

    private PropertyManager propertyManager;
    private TasksManager tasksManager;

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
	RemoteTasksManager remoteTasksManager = new RemoteTasksManager(accountManager, apiKey);

	tasksManager = new TasksManager(localTasksManager, remoteTasksManager);
    }

    public PropertyManager getPropertyManager() {
	return propertyManager;
    }

    public TasksManager getTasksManager() {
	return tasksManager;
    }

}
