/**
 * 
 */
package hw.app.notelist.ui.activity;

import hw.app.notelist.R;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import hw.app.notelist.utils.NoteListLog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * @author huangwei05
 * 
 */
public class BillEditActivity extends BaseActivity implements OnClickListener, OnItemSelectedListener {
    public static final String TAG = "BillEditActivity";

    public static final String BILL_THUMB = "bill_thumb";
    public static final String BILL_STATES = "bill_states";
    public static final String BILL_TEXTS = "bill_texts";
    public static final String BILL_PRICES = "bill_prices";

    private Bitmap mBillThumBitmap;
    private ArrayList<Integer> mSateArrayList = new ArrayList<Integer>();
    private ArrayList<String> mTextArrayList = new ArrayList<String>();
    private ArrayList<String> mPriceArrayList = new ArrayList<String>();

    private LinearLayout mBillContainer;
    private LinearLayout mAddBillBtn;
    private TextView mTotalCount;
    private LayoutInflater mInflater;
    private AlertDialog mAlertDialog;

    private LinearLayout mLayout;
    private Spinner mBillSpinner;
    private EditText mBillContent;
    private EditText mBillPrice;

    private SpinnerAdapter mSpinnerAdapter;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.xcloud.noteapp.ui.BaseActivity#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return TAG;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.xcloud.noteapp.ui.BaseActivity#getLayoutId()
     */
    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.bill_edit_activity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.xcloud.noteapp.ui.BaseActivity#initView(android.os.Bundle)
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mBillContainer = (LinearLayout) findViewById(R.id.bill_container);
        mAddBillBtn = (LinearLayout) findViewById(R.id.bill_add_container);
        mTotalCount = (TextView) findViewById(R.id.bill_total_count);
        mTotalCount.setFocusable(true);
        mTotalCount.setFocusableInTouchMode(true);

        mAlertDialog =
                new AlertDialog.Builder(this).setTitle(R.string.whether_save)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                fillBillData();

                                Intent intent = new Intent(BillEditActivity.this, BillEditActivity.class);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                mBillThumBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] bitmapByte = baos.toByteArray();

                                intent.putExtra(BILL_THUMB, bitmapByte);
                                intent.putIntegerArrayListExtra(BILL_STATES, mSateArrayList);
                                intent.putStringArrayListExtra(BILL_TEXTS, mTextArrayList);
                                intent.putStringArrayListExtra(BILL_PRICES, mPriceArrayList);

                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                setResult(Activity.RESULT_CANCELED);
                                finish();
                            }
                        }).create();

        Intent intent = getIntent();
        if (intent.hasExtra(BILL_THUMB)) {
            mSateArrayList = intent.getIntegerArrayListExtra(BILL_STATES);
            mTextArrayList = intent.getStringArrayListExtra(BILL_TEXTS);
            mPriceArrayList = intent.getStringArrayListExtra(BILL_PRICES);

            for (int i = 0; i < mSateArrayList.size(); i++) {
                addBillItem(mSateArrayList.get(i), mTextArrayList.get(i), mPriceArrayList.get(i));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.xcloud.noteapp.ui.BaseActivity#initLinstener()
     */
    @Override
    protected void initLinstener() {
        // TODO Auto-generated method stub
        mAddBillBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (showSaveDialog()) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (!showSaveDialog()) {
            super.onBackPressed();
        }
    }

    private boolean showSaveDialog() {
        if (mBillContainer.getChildCount() > 0) {
            mAlertDialog.show();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        addBillItem(0, "", "");
    }

    private void addBillItem(int state, String text, String price) {
        mLayout = (LinearLayout) mInflater.inflate(R.layout.bill_item, null);

        mBillSpinner = (Spinner) mLayout.findViewById(R.id.bill_spinner);
        mBillContent = (EditText) mLayout.findViewById(R.id.bill_content);
        mBillPrice = (EditText) mLayout.findViewById(R.id.bill_price);

        mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.bill_list, R.layout.bill_spinner_dropdown_item);
        mBillSpinner.setAdapter(mSpinnerAdapter);

        mBillSpinner.setSelection(state);
        mBillContent.setText(text);
        mBillPrice.setText(price);

        mBillSpinner.setOnItemSelectedListener(this);
        mBillContainer.addView(mLayout);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        NoteListLog.i(TAG, "onItemSelected : arg0 = " + arg0 + " arg1 = " + arg1 + " arg2 = " + arg2 + " arg3 = "
                + arg3);
        if (arg2 == mSpinnerAdapter.getCount() - 1) {
            mBillContainer.removeView((ViewGroup) arg0.getParent());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        NoteListLog.i(TAG, "onNothingSelected ");
    }

    private void fillBillData() {
        mBillThumBitmap = convertViewToBitmap(mBillContainer);

        mSateArrayList.clear();
        mTextArrayList.clear();
        mPriceArrayList.clear();

        for (int i = 0; i < mBillContainer.getChildCount(); i++) {
            mLayout = (LinearLayout) mBillContainer.getChildAt(i);
            mBillSpinner = (Spinner) mLayout.findViewById(R.id.bill_spinner);
            mBillContent = (EditText) mLayout.findViewById(R.id.bill_content);
            mBillPrice = (EditText) mLayout.findViewById(R.id.bill_price);

            mSateArrayList.add(mBillSpinner.getSelectedItemPosition());
            mTextArrayList.add(mBillContent.getText().toString());
            mPriceArrayList.add(mBillPrice.getText().toString());
        }
    }

    private Bitmap convertViewToBitmap(ViewGroup view) {
        mTotalCount.requestFocus();
        view.setBackgroundResource(R.drawable.btn_bg);

        // view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        // MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    public BasePresenter getPresenter() {
        // TODO Auto-generated method stub
        return null;
    }
}
