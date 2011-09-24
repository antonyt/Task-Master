package at465.taskmaster.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class PropertyManager {
    
    private static final String KEY_PROPERTIES = "keys.properties";
    private Properties properties;

    public PropertyManager(Context context) {
	try {
	    AssetManager assetManager = context.getResources().getAssets();
	    InputStream inputStream = assetManager.open(KEY_PROPERTIES);
	    properties = new Properties();
	    properties.load(inputStream);
	} catch (IOException e) {
	    Log.e("PropertyManager", "could not load properties!");
	    e.printStackTrace();
	}
    }
    
    public String getApiKey() {
	return properties.getProperty("apikey");
    }
    
    public String getRedirect() {
	return properties.getProperty("redirect");
    }
    
    public String getSecret() {
	return properties.getProperty("secret");
    }
}
