package at465.taskmaster.view;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import at465.taskmaster.R;
import at465.taskmaster.application.TaskMasterApplication;
import at465.taskmaster.application.TasksManager;
import at465.taskmaster.application.TasksManager.TasksListener;

import com.google.api.services.tasks.model.Task;

public class TasksListFragment extends ListFragment implements TasksListener {
    private String taskListId;
    private String taskListTitle;
    private TasksManager tasksManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	taskListId = getArguments().getString("taskListId");
	taskListTitle = getArguments().getString("taskListTitle");

	tasksManager = ((TaskMasterApplication) getActivity().getApplication()).getTasksManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View root = inflater.inflate(R.layout.task_list, container, false);
	TextView title = (TextView) root.findViewById(R.id.title);
	title.setText(taskListTitle);
	return root;
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
