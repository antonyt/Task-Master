package at465.taskmaster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import at465.taskmaster.fragment.TasksListFragment;

public class HomeActivity extends FragmentActivity {

    private TasksListFragment list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
	viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

	    @Override
	    public int getCount() {
		return 1;
	    }

	    @Override
	    public Fragment getItem(int position) {
		list = new TasksListFragment();
		Bundle args = new Bundle();
		switch (position) {
		case 0:
		    args.putString("taskListId", "@default");
		    break;
		}
		list.setArguments(args);
		return list;
	    }
	});

    }

}