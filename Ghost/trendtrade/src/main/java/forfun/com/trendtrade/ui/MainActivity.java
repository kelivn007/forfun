/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.trendtrade.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import forfun.com.trendtrade.R;
import forfun.com.trendtrade.ui.fragment.DownTrendFragment;
import forfun.com.trendtrade.ui.fragment.EditableFragment;
import forfun.com.trendtrade.ui.fragment.UpTrendFragment;
public class MainActivity extends Activity {

	private static final int UP_TYPE = 0;
	private static final int DOWN_TYPE = 1;
	private String[] DATA = { "上涨趋势", "下跌趋势" };

	private Spinner trendSpinner;
	private ArrayAdapter<String> trendSpinnerAdapter;
	private FragmentManager fragmentManager;
	private EditableFragment upTrendFragment;
	private EditableFragment downTrendFragment;
	private EditableFragment currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = getFragmentManager();

		trendSpinner = (Spinner) findViewById(R.id.trend_spinner);
		trendSpinnerAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, DATA);
		trendSpinner.setAdapter(trendSpinnerAdapter);
		trendSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switchFragment(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		switchFragment(UP_TYPE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_recalculate:
			currentFragment.recalculate();
			return true;
		case R.id.action_clear:
			currentFragment.clear();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void switchFragment(int type) {
		switch (type) {
		case UP_TYPE:
			if (upTrendFragment == null) {
				upTrendFragment = new UpTrendFragment();
			}

			if (currentFragment != upTrendFragment) {
				if (currentFragment != null) {
					fragmentManager.beginTransaction().hide(currentFragment)
							.commit();
				}

				if (!upTrendFragment.isAdded()) {
					fragmentManager
							.beginTransaction()
							.add(R.id.trend_fragment_container, upTrendFragment)
							.commit();
				} else {
					fragmentManager.beginTransaction().show(upTrendFragment)
							.commit();
				}

				currentFragment = upTrendFragment;
			}

			// if (currentFragment != null) {
			// fragmentManager.beginTransaction().remove(currentFragment);
			// }
			// currentFragment = fragmentManager
			// .findFragmentByTag(UpTrendFragment.TAG);
			// if (currentFragment == null) {
			// currentFragment = new UpTrendFragment();
			// fragmentManager.beginTransaction()
			// .add(R.id.trend_fragment_container, currentFragment)
			// .commit();
			// } else {
			// fragmentManager
			// .beginTransaction()
			// .replace(R.id.trend_fragment_container, currentFragment)
			// .commit();
			// }

			// if (upTrendFragment == null) {
			// upTrendFragment = new UpTrendFragment();
			// currentFragment = upTrendFragment;
			// fragmentManager.beginTransaction()
			// .add(R.id.trend_fragment_container, upTrendFragment)
			// .commit();
			// } else if (currentFragment != upTrendFragment) {
			// currentFragment = upTrendFragment;
			// fragmentManager
			// .beginTransaction()
			// .replace(R.id.trend_fragment_container,
			// currentFragment,UpTrendFragment.TAG)
			// .commit();
			// }

			break;
		case DOWN_TYPE:
			if (downTrendFragment == null) {
				downTrendFragment = new DownTrendFragment();
			}

			if (currentFragment != downTrendFragment) {
				if (currentFragment != null) {
					fragmentManager.beginTransaction().hide(currentFragment)
							.commit();
				}

				if (!downTrendFragment.isAdded()) {
					fragmentManager
							.beginTransaction()
							.add(R.id.trend_fragment_container,
									downTrendFragment).commit();
				} else {
					fragmentManager.beginTransaction().show(downTrendFragment)
							.commit();
				}

				currentFragment = downTrendFragment;
			}

			// currentFragment = fragmentManager
			// .findFragmentByTag(DownTrendFragment.TAG);
			// if (currentFragment == null) {
			// currentFragment = new DownTrendFragment();
			// }
			// fragmentManager.beginTransaction()
			// .replace(R.id.trend_fragment_container, currentFragment)
			// .commit();

			// if (currentFragment != null) {
			// fragmentManager.beginTransaction().remove(currentFragment);
			// }
			// currentFragment = fragmentManager
			// .findFragmentByTag(DownTrendFragment.TAG);
			//
			// if (currentFragment == null) {
			// currentFragment = new DownTrendFragment();
			// }
			// fragmentManager.beginTransaction()
			// .replace(R.id.trend_fragment_container,
			// currentFragment,DownTrendFragment.TAG)
			// .commit();
			break;
		default:
			break;
		}
	}
}
