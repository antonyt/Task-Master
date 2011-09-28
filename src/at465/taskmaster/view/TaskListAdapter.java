package at465.taskmaster.view;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import at465.taskmaster.R;
import at465.taskmaster.application.TaskConstants;

import com.google.api.services.tasks.model.Task;

public class TaskListAdapter extends BaseAdapter {

    private List<Task> tasks;
    private Context context;
    private LayoutInflater inflater;

    public TaskListAdapter(Context context) {
	this.context = context;
	this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<Task> tasks) {
	this.tasks = tasks;
    }

    @Override
    public int getCount() {
	return tasks == null ? 0 : tasks.size();
    }

    @Override
    public Object getItem(int position) {
	return tasks == null ? null : tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	if (convertView == null) {
	    convertView = inflater.inflate(R.layout.task_row, null);
	    
	    // create a view holder for the row and save the references to the views
	    holder = new ViewHolder();
	    holder.title = (TextView) convertView.findViewById(R.id.title);
	    holder.dueDate = (TextView) convertView.findViewById(R.id.due_date);
	    holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
	    
	    // set view holder as tag
	    convertView.setTag(holder);
	} else {
	    // do not have to inflate another layout or resolve views
	    // retrieve previously saved view holder
	    holder = (ViewHolder) convertView.getTag();
	}
	
	final Task task = tasks.get(position);
	final boolean taskCompleted = TaskConstants.COMPLETED.equals(task.getStatus());
	
	// set title
	holder.title.setText(task.getTitle());
	
	// apply strike-through effect if the task is completed; reset strike-through otherwise
	if (taskCompleted) {
	    holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	} else {
	    holder.title.setPaintFlags(holder.title.getPaintFlags() & (Paint.STRIKE_THRU_TEXT_FLAG - 1));
	}
	
	// set checkbox status, and attach checked listener
	// we need to write back to the data once the user clicks the checkbox, and then update our views
	// TODO: do not instantiate a new listener for each call to getView(...)
	holder.checkbox.setOnCheckedChangeListener(null);
	holder.checkbox.setChecked(taskCompleted);
	holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
		    task.setStatus(TaskConstants.COMPLETED);
		} else {
		    task.setStatus(TaskConstants.NEEDS_ACTION);
		    task.setCompleted(null);
		}
		task.set("changed", true);
		notifyDataSetChanged();
	    }
	});
	
	return convertView;
    }

    private class ViewHolder {
	TextView title, dueDate;
	CheckBox checkbox;
    }

}
