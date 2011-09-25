package at465.taskmaster.view;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.api.services.tasks.model.TaskList;

public class TaskFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<TaskList> taskLists;

    public TaskFragmentPagerAdapter(FragmentManager fragmentManager, List<TaskList> taskLists) {
	super(fragmentManager);
	this.taskLists = taskLists;
    }

    @Override
    public Fragment getItem(int position) {
	Fragment list = new TaskListFragment();
	Bundle args = new Bundle();
	args.putString(TaskListFragment.TASK_LIST_ID_KEY, taskLists.get(position).getId());
	args.putString(TaskListFragment.TASK_LIST_TITLE_KEY, taskLists.get(position).getTitle());
	list.setArguments(args);
	return list;
    }

    @Override
    public int getCount() {
	return taskLists.size();
    }

}
