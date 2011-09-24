package at465.taskmaster;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListAdapter;
import at465.taskmaster.application.PropertyManager;
import at465.taskmaster.application.TaskMasterApplication;

import com.google.api.client.auth.oauth2.draft10.AccessProtectedResource;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.Tasks;

public class HomeActivity extends FragmentActivity {
    private String AUTH_TOKEN_TYPE = "Manage your tasks";

    private Tasks service;
    private AccountManagerFuture<Bundle> f;
    private TasksListFragment list;

    private PropertyManager propertyManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	if (savedInstanceState == null) {
	    list = new TasksListFragment();
	    getSupportFragmentManager().beginTransaction().add(R.id.list_container, list).commit();
	}

	propertyManager = ((TaskMasterApplication) getApplication()).getPropertyManager();

	AccountManager m = AccountManager.get(this);

	Account account = m.getAccountsByType("com.google")[0];
	try {

	    f = m.getAuthToken(account, AUTH_TOKEN_TYPE, true, null, null);
	    m.invalidateAuthToken("com.google", f.getResult().getString(AccountManager.KEY_AUTHTOKEN));
	    f = m.getAuthToken(account, AUTH_TOKEN_TYPE, true, null, null);

	    String accessToken = f.getResult().getString(AccountManager.KEY_AUTHTOKEN);

	    HttpTransport transport = AndroidHttp.newCompatibleTransport();
	    AccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(accessToken);
	    service = new Tasks(transport, accessProtectedResource, new JacksonFactory());
	    service.setKey(propertyManager.getApiKey());
	    service.setApplicationName("TaskMaster");

	    // TaskLists taskLists = service.tasklists.list().execute();
	    com.google.api.services.tasks.model.Tasks tasks = service.tasks.list("@default").execute();
	    ListAdapter adapter = new TasksAdapter(HomeActivity.this, tasks.getItems());
	    list.setListAdapter(adapter);

	} catch (OperationCanceledException e) {
	    e.printStackTrace();
	} catch (AuthenticatorException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}