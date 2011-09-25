package at465.taskmaster.local;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

public class LocalTaskManager {
    private SharedPreferences sharedPreferences;

    public LocalTaskManager(SharedPreferences sharedPreferences) {
	this.sharedPreferences = sharedPreferences;
    }

    public Tasks restoreTasks(String taskListId) {
	String s = sharedPreferences.getString("tasks_for_" + taskListId, null);
	if (s == null) {
	    return null;
	}
	try {
	    return new JacksonFactory().createJsonParser(s).parse(Tasks.class, null);
	} catch (JsonParseException e) {
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public TaskLists restoreTaskLists() {
	String s = sharedPreferences.getString("tasklists", null);
	if (s == null) {
	    return null;
	}
	try {
	    return new JacksonFactory().createJsonParser(s).parse(TaskLists.class, null);
	} catch (JsonParseException e) {
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return null;
    }

    public void saveTasks(String taskListId, Tasks tasks) {
	Editor editor = sharedPreferences.edit();
	editor.putString("tasks_for_" + taskListId, tasks.toString());
	editor.commit();
    }

    public void saveTaskLists(TaskLists tasklists) {
	Editor editor = sharedPreferences.edit();
	editor.putString("tasklists", tasklists.toString());
	editor.commit();
    }
}
