package at465.taskmaster.application;

import android.app.Application;
import at465.taskmaster.authentication.Authenticator;

public class TaskMasterApplication extends Application {
    
    private PropertyManager propertyManager;
    private Authenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        propertyManager = new PropertyManager(this);
        authenticator = new Authenticator(this);
    }
    
    public PropertyManager getPropertyManager() {
	return propertyManager;
    }
    
    public Authenticator getAuthenticator() {
	return authenticator;
    }
    
}
