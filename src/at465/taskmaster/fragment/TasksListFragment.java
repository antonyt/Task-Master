package at465.taskmaster.fragment;

import java.util.List;

import com.google.api.services.tasks.model.Task;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import at465.taskmaster.TasksAdapter;
import at465.taskmaster.application.TaskMasterApplication;
import at465.taskmaster.application.TasksManager;
import at465.taskmaster.application.TasksManager.TasksListener;

public class TasksListFragment extends ListFragment implements TasksListener {
    private String taskListId;
    private TasksManager tasksManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	taskListId = getArguments().getString("taskListId");
	tasksManager = ((TaskMasterApplication) getActivity().getApplication()).getTasksManager();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	tasksManager.setTaskListener(taskListId, this);
	tasksManager.getTasks(taskListId);
    }

    @Override
    public void onDestroyView() {
	super.onDestroyView();
	tasksManager.setTaskListener(taskListId, null);
    }

    @Override
    public void tasksUpdated(String taskListId, List<Task> tasks) {
	TasksAdapter adapter = new TasksAdapter(getActivity());
	adapter.setData(tasks);
	setListAdapter(adapter);
    }
}
