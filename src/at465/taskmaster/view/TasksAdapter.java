package at465.taskmaster.view;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import at465.taskmaster.R;

import com.google.api.services.tasks.model.Task;

public class TasksAdapter extends BaseAdapter {

    private List<Task> tasks;
    private Context context;
    private LayoutInflater inflater;

    public TasksAdapter(Context context) {
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
	    
	    // set view holder as tag
	    convertView.setTag(holder);
	} else {
	    // do not have to inflate another layout or resolve views
	    // retrieve previously saved view holder
	    holder = (ViewHolder) convertView.getTag();
	}
	
	Task task = tasks.get(position);
	
	holder.title.setText(task.getTitle());
	if (task.getCompleted() != null) {
	    holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	} else {
	    holder.title.setPaintFlags(holder.title.getPaintFlags() & (Paint.STRIKE_THRU_TEXT_FLAG - 1));
	}
	
	return convertView;
    }
    
    private class ViewHolder {
	TextView title, dueDate;
    }

}
