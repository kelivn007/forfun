package hw.app.notelist.ui.activity;

import hw.app.notelist.R;
import hw.app.notelist.action.impl.DisplayNoteAction;
import hw.app.notelist.action.impl.DisplayNoteAction.DisplayType;
import hw.app.notelist.action.impl.OpenNoteAction;
import hw.app.notelist.action.impl.StartLoginAction;
import hw.app.notelist.action.impl.StartLogoutAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import hw.app.notelist.ui.presenter.MainPresenter;
import hw.app.notelist.ui.presenter.MainPresenter.IMainActivityPresenter;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements IMainActivityPresenter, OnQueryTextListener {

    private final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private TextView mDrawerLayoutUserNameText;
    private TextView mDrawerLayoutLoginBtn;
    private TextView mDrawerLayoutLogoutBtn;
    private TextView mDrawerLayoutNoteBtn;
    private TextView mDrawerLayoutImportantBtn;
    private TextView mDrawerLayoutDiscardBtn;
    private ActionBarDrawerToggle mDrawerToggle;

    private TextView mEmptyView;
    private ListView mListView;

    private String mSearchFilter;
    private boolean mIsFiltImport;
    private boolean mIsFiltDelete;

    private SearchView mSearchView;

    private MainPresenter mPresenter;

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.LEFT);
        mDrawerLayoutUserNameText = (TextView) mDrawerLayout.findViewById(R.id.username_text);
        mDrawerLayoutLoginBtn = (TextView) mDrawerLayout.findViewById(R.id.login_btn);
        mDrawerLayoutLogoutBtn = (TextView) mDrawerLayout.findViewById(R.id.logout_btn);
        mDrawerLayoutNoteBtn = (TextView) mDrawerLayout.findViewById(R.id.drawable_layout_note_btn);
        mDrawerLayoutImportantBtn = (TextView) mDrawerLayout.findViewById(R.id.drawable_layout_important_btn);
        mDrawerLayoutDiscardBtn = (TextView) mDrawerLayout.findViewById(R.id.drawable_layout_discard_btn);

        mEmptyView = (TextView) findViewById(R.id.empty_string);
        mListView = (ListView) findViewById(R.id.main_activity_list);

        mPresenter = new MainPresenter(this);
    }

    @Override
    protected void initLinstener() {
        // TODO Auto-generated method stub
        mDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name,
                        R.string.app_name) {
                    public void onDrawerClosed(View view) {
                        invalidateOptionsMenu();
                    }

                    public void onDrawerOpened(View drawerView) {
                        invalidateOptionsMenu();
                    }
                };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        addViewClickAction(mDrawerLayoutLoginBtn, new StartLoginAction(this, mPresenter));
        addViewClickAction(mDrawerLayoutLogoutBtn, new StartLogoutAction(this, mPresenter));
        addViewClickAction(mDrawerLayoutNoteBtn, new DisplayNoteAction(DisplayType.ALL));
        addViewClickAction(mDrawerLayoutImportantBtn, new DisplayNoteAction(DisplayType.IMPORTANT));
        addViewClickAction(mDrawerLayoutDiscardBtn, new DisplayNoteAction(DisplayType.DELETE));

        addItemClickAction(mListView);
        addItemLongClickAction(mListView);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mPresenter.present(this, getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        mSearchView.setOnQueryTextListener(this);

        addMenuClickAction(R.id.action_new, new OpenNoteAction(this, -1));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO Auto-generated method stub
        mSearchFilter = !TextUtils.isEmpty(newText) ? newText : null;
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public BasePresenter getPresenter() {
        // TODO Auto-generated method stub
        return mPresenter;
    }

    @Override
    public void onShowLogin() {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mDrawerLayoutUserNameText.setText("");
                mDrawerLayoutLoginBtn.setVisibility(View.VISIBLE);
                mDrawerLayoutLogoutBtn.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onShowUserName(final String username) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mDrawerLayoutUserNameText.setText(username);
                mDrawerLayoutLoginBtn.setVisibility(View.GONE);
                mDrawerLayoutLogoutBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onSetAdapter(final BaseAdapter adapter) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mListView.setAdapter(adapter);
                mEmptyView.setVisibility(adapter.getCount() > 0 ? View.GONE : View.VISIBLE);
            }
        });
    }
}
