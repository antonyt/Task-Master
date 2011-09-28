package at465.taskmaster;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import at465.taskmaster.application.TaskMasterApplication;
import at465.taskmaster.application.TaskManager;
import at465.taskmaster.application.TaskManager.TaskListListener;
import at465.taskmaster.view.TaskFragmentPagerAdapter;

import com.google.api.services.tasks.model.TaskList;

public class HomeActivity extends FragmentActivity implements TaskListListener {
    
    private TaskManager taskManager;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// set layout and resolve view pager
	setContentView(R.layout.main);
	viewPager = (ViewPager) findViewById(R.id.view_pager);
	
	// ask for task lists from the TasksManager
	taskManager = ((TaskMasterApplication) getApplication()).getTasksManager();
	taskManager.setTaskListListener(this);
	taskManager.getTaskLists();

    }
    
    @Override
    protected void onDestroy() {
	taskManager.saveAll();
	taskManager.setTaskListListener(null);
        super.onDestroy();
    }

    @Override
    public void taskListsUpdated(final List<TaskList> taskLists) {
	viewPager.setAdapter(new TaskFragmentPagerAdapter(getSupportFragmentManager(), taskLists));
    }

    
}