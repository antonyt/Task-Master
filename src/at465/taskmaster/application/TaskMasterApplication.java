package at465.taskmaster.application;

import android.app.Application;

public class TaskMasterApplication extends Application {
    
    private PropertyManager propertyManager;

    @Override
    public void onCreate() {
        super.onCreate();
        propertyManager = new PropertyManager(this);
    }
    
    public PropertyManager getPropertyManager() {
	return propertyManager;
    }
    
}
