package at465.taskmaster;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListAdapter;
import at465.taskmaster.application.TaskMasterApplication;
import at465.taskmaster.authentication.Authenticator;

import com.google.api.services.tasks.Tasks;

public class HomeActivity extends FragmentActivity {

    private Tasks service;
    private TasksListFragment list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	if (savedInstanceState == null) {
	    list = new TasksListFragment();
	    getSupportFragmentManager().beginTransaction().add(R.id.list_container, list).commit();
	}

	Authenticator authenticator = ((TaskMasterApplication) getApplication()).getAuthenticator();
	service = authenticator.authenticate();

	try {
	    com.google.api.services.tasks.model.Tasks tasks = service.tasks.list("@default").execute();
	    ListAdapter adapter = new TasksAdapter(HomeActivity.this, tasks.getItems());
	    list.setListAdapter(adapter);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}