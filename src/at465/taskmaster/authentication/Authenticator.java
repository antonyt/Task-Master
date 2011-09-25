package at465.taskmaster.authentication;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;

import com.google.api.client.auth.oauth2.draft10.AccessProtectedResource;
import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.Tasks;

public class Authenticator {

    private String AUTH_TOKEN_TYPE = "Manage your tasks";

    private AccountManager accountManager;
    private String apiKey;

    private AccountManagerFuture<Bundle> accountManagerFuture;

    public Authenticator(AccountManager accountManager, String apiKey) {
	this.apiKey = apiKey;
	this.accountManager = accountManager;
    }

    public Tasks authenticate() {
	Account account = accountManager.getAccountsByType("com.google")[0];

	try {
	    accountManagerFuture = accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, true, null, null);
	    accountManager.invalidateAuthToken("com.google",
		    accountManagerFuture.getResult().getString(AccountManager.KEY_AUTHTOKEN));
	    accountManagerFuture = accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, true, null, null);

	    String accessToken = accountManagerFuture.getResult().getString(AccountManager.KEY_AUTHTOKEN);

	    HttpTransport transport = AndroidHttp.newCompatibleTransport();
	    AccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(accessToken);
	    Tasks service = new Tasks(transport, accessProtectedResource, new JacksonFactory());
	    service.setKey(apiKey);
	    service.setApplicationName("TaskMaster");

	    return service;

	} catch (OperationCanceledException e) {
	    e.printStackTrace();
	} catch (AuthenticatorException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return null;
    }
}
