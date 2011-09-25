package at465.taskmaster;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import at465.taskmaster.fragment.TasksListFragment;

import com.google.api.services.tasks.model.TaskList;

public class TaskFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<TaskList> taskLists;

    public TaskFragmentPagerAdapter(FragmentManager fragmentManager, List<TaskList> taskLists) {
	super(fragmentManager);
	this.taskLists = taskLists;
    }

    @Override
    public Fragment getItem(int position) {
	Fragment list = new TasksListFragment();
	Bundle args = new Bundle();
	args.putString("taskListId", taskLists.get(position).getId());
	list.setArguments(args);
	return list;
    }

    @Override
    public int getCount() {
	return taskLists.size();
    }

}
