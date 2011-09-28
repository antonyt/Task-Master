package at465.taskmaster.view;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import at465.taskmaster.R;
import at465.taskmaster.application.TaskManager;
import at465.taskmaster.application.TaskManager.TasksListener;
import at465.taskmaster.application.TaskMasterApplication;

import com.google.api.services.tasks.model.Task;

public class TaskListFragment extends ListFragment implements TasksListener {
    public static final String TASK_LIST_ID_KEY = "taskListId";
    public static final String TASK_LIST_TITLE_KEY = "taskListTitle";

    private String taskListId;
    private String taskListTitle;
    private TaskManager taskManager;
    private List<Task> tasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	taskListId = getArguments().getString(TASK_LIST_ID_KEY);
	taskListTitle = getArguments().getString(TASK_LIST_TITLE_KEY);

	taskManager = ((TaskMasterApplication) getActivity().getApplication()).getTasksManager();
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
	taskManager.setTaskListener(taskListId, this);
	taskManager.getTasks(taskListId);
    }

    @Override
    public void onDestroyView() {
	super.onDestroyView();
	taskManager.setTaskListener(taskListId, null);
    }

    @Override
    public void tasksUpdated(String taskListId, List<Task> tasks) {
	this.tasks = tasks;
	TaskListAdapter adapter = new TaskListAdapter(getActivity());
	adapter.setData(tasks);
	setListAdapter(adapter);
    }
}
