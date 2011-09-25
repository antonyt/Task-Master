package at465.taskmaster;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import at465.taskmaster.application.TaskMasterApplication;
import at465.taskmaster.application.TasksManager;
import at465.taskmaster.application.TasksManager.TaskListListener;

import com.google.api.services.tasks.model.TaskList;

public class HomeActivity extends FragmentActivity implements TaskListListener {
    
    private TasksManager tasksManager;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	viewPager = (ViewPager) findViewById(R.id.view_pager);
	tasksManager = ((TaskMasterApplication) getApplication()).getTasksManager();
	tasksManager.setTaskListListener(this);
	tasksManager.getTaskLists();

    }
    
    @Override
    protected void onDestroy() {
	tasksManager.setTaskListListener(null);
        super.onDestroy();
    }

    @Override
    public void taskListsUpdated(final List<TaskList> taskLists) {
	viewPager.setAdapter(new TaskFragmentPagerAdapter(getSupportFragmentManager(), taskLists));
    }

}