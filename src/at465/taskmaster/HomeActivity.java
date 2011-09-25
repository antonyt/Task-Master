package at465.taskmaster;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import at465.taskmaster.application.TaskMasterApplication;
import at465.taskmaster.application.TasksManager;
import at465.taskmaster.application.TasksManager.TasksListener;

import com.google.api.services.tasks.model.Task;

public class HomeActivity extends FragmentActivity implements TasksListener {

    private TasksListFragment list;
    private TasksAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	if (savedInstanceState == null) {
	    list = new TasksListFragment();
	    getSupportFragmentManager().beginTransaction().add(R.id.list_container, list, TasksListFragment.class.getName()).commit();
	} else {
	    list = (TasksListFragment) getSupportFragmentManager().findFragmentByTag(TasksListFragment.class.getName());
	}

	adapter = new TasksAdapter(this);
	list.setListAdapter(adapter);

	TasksManager tasksManager = ((TaskMasterApplication) getApplication()).getTasksManager();
	tasksManager.setTasksListener(this);
	tasksManager.getTasks("@default");
    }

    @Override
    public void tasksUpdated(String taskListId, List<Task> tasks) {
	adapter.setData(tasks);
	adapter.notifyDataSetChanged();
    }

}