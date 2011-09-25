package at465.taskmaster;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import at465.taskmaster.fragment.TasksListFragment;

public class HomeActivity extends FragmentActivity {

    private TasksListFragment list;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	if (savedInstanceState == null) {
	    list = new TasksListFragment();
	    Bundle args = new Bundle();
	    args.putString("taskListId", "@default");
	    list.setArguments(args);
	    getSupportFragmentManager().beginTransaction().add(R.id.list_container, list, TasksListFragment.class.getName()).commit();
	} else {
	    list = (TasksListFragment) getSupportFragmentManager().findFragmentByTag(TasksListFragment.class.getName());
	}

    }

}