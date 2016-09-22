/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.trendtrade.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import forfun.com.trendtrade.R;
import forfun.com.trendtrade.constant.Constant;
import forfun.com.trendtrade.ui.MyTextWatcher;
import forfun.com.trendtrade.utils.Utils;

public class UpTrendFragment extends EditableFragment {
	public static final String TAG = "UpTrendFragment";

	private EditText up_trend_lowpoint1_edit;
	private EditText up_trend_uppoint1_edit;
	private TextView up_trend_correction_zone1_text;
	private EditText up_trend_lowpoint2_edit;
	private TextView up_trend_resistance_zone_text;
	private TextView up_trend_target_zone1_text;
	private EditText up_trend_takeprice_edit;
	private TextView up_trend_stoplose_text;
	private TextView up_trend_profit_lose_ratio_text;
	private EditText up_trend_uppoint2_edit;
	private TextView up_trend_strutlpoint_text;
	private TextView up_trend_correction_zone2_text;
	private EditText up_trend_lowpoint3_edit;
	private Spinner up_trend_complexwave_spinner;
	private TextView up_trend_target_zone2_text;

	private double up_trend_lowPoint1_num = 0.00;
	private double up_trend_upPoint1_num = 0.00;
	private double up_trend_wave1_length = 0.00;
	private double up_trend_lowPoint2_num = 0.00;
	private double up_trend_min_target_num = 0.00;
	private double up_trend_max_target_num = 0.00;
	private double up_trend_takeprice_num = 0.00;
	private double up_trend_upPoint2_num = 0.00;
	private double up_trend_wave3_length = 0.00;
	private double up_trend_lowPoint3_num = 0.00;

	private String[] DATA = { "第三浪", "第五浪", "忽略" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.up_trend_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		/**
		 * 上升趋势
		 */

		up_trend_lowpoint1_edit = (EditText) view
				.findViewById(R.id.up_trend_lowpoint1);
		up_trend_uppoint1_edit = (EditText) view
				.findViewById(R.id.up_trend_uppoint1);
		up_trend_correction_zone1_text = (TextView) view
				.findViewById(R.id.up_trend_correction_zone1);
		up_trend_lowpoint2_edit = (EditText) view
				.findViewById(R.id.up_trend_lowpoint2);
		up_trend_resistance_zone_text = (TextView) view
				.findViewById(R.id.up_trend_resistance_zone);
		up_trend_target_zone1_text = (TextView) view
				.findViewById(R.id.up_trend_target_zone1);
		up_trend_takeprice_edit = (EditText) view
				.findViewById(R.id.up_trend_takeprice);
		up_trend_stoplose_text = (TextView) view
				.findViewById(R.id.up_trend_stoplose);
		up_trend_profit_lose_ratio_text = (TextView) view
				.findViewById(R.id.up_trend_profit_lose_ratio);
		up_trend_uppoint2_edit = (EditText) view
				.findViewById(R.id.up_trend_uppoint2);
		up_trend_strutlpoint_text = (TextView) view
				.findViewById(R.id.up_trend_strutlpoint);
		up_trend_correction_zone2_text = (TextView) view
				.findViewById(R.id.up_trend_correction_zone2);
		up_trend_lowpoint3_edit = (EditText) view
				.findViewById(R.id.up_trend_lowpoint3);
		up_trend_complexwave_spinner = (Spinner) view
				.findViewById(R.id.up_trend_complexwave);
		up_trend_target_zone2_text = (TextView) view
				.findViewById(R.id.up_trend_target_zone2);

		up_trend_lowpoint1_edit.addTextChangedListener(new MyTextWatcher() {

			@Override
			public void afterTextChanged(Editable edit) {
				if (!TextUtils.isEmpty(edit.toString())) {
					up_trend_lowPoint1_num = Double.valueOf(edit.toString());
				} else {
					up_trend_lowPoint1_num = 0.00;
				}
				refreshLowPoint1();
			}
		});

		up_trend_uppoint1_edit.addTextChangedListener(new MyTextWatcher() {

			@Override
			public void afterTextChanged(Editable edit) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(edit.toString())) {
					up_trend_upPoint1_num = Double.valueOf(edit.toString());
					refreshUpPoint1();
				}
			}
		});

		up_trend_lowpoint2_edit.addTextChangedListener(new MyTextWatcher() {

			@Override
			public void afterTextChanged(Editable edit) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(edit.toString())) {
					up_trend_lowPoint2_num = Double.valueOf(edit.toString());
					refreshLowPoint2();
				}
			}
		});

		up_trend_takeprice_edit.addTextChangedListener(new MyTextWatcher() {

			@Override
			public void afterTextChanged(Editable edit) {
				if (!TextUtils.isEmpty(edit.toString())) {
					up_trend_takeprice_num = Double.valueOf(edit.toString());
					refreshTakePrice();
				}
			}
		});

		up_trend_uppoint2_edit.addTextChangedListener(new MyTextWatcher() {

			@Override
			public void afterTextChanged(Editable edit) {
				if (!TextUtils.isEmpty(edit.toString())) {
					up_trend_upPoint2_num = Double.valueOf(edit.toString());
					refreshUpPoint2();
				}
			}

		});

		up_trend_lowpoint3_edit.addTextChangedListener(new MyTextWatcher() {

			@Override
			public void afterTextChanged(Editable edit) {
				if (!TextUtils.isEmpty(edit.toString())) {
					up_trend_lowPoint3_num = Double.valueOf(edit.toString());
					if (Utils.isBiggerZero(up_trend_lowPoint3_num)) {
						refreshTarget2();
					}
				}
			}

		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, DATA);
		up_trend_complexwave_spinner.setAdapter(adapter);
		up_trend_complexwave_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						refreshTarget2();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void refreshLowPoint1() {
		up_trend_stoplose_text.setText(Utils
				.formatDouble(up_trend_lowPoint1_num));
	}

	private void refreshUpPoint1() {
		if (Utils.isBiggerZero(up_trend_upPoint1_num)
				&& Utils.isBiggerZero(up_trend_lowPoint1_num)) {
			up_trend_wave1_length = up_trend_upPoint1_num
					- up_trend_lowPoint1_num;
			if (Utils.isBiggerZero(up_trend_wave1_length)) {
				up_trend_correction_zone1_text.setText(Utils
						.formatDouble(up_trend_upPoint1_num
								- up_trend_wave1_length
								* Constant.MIN_CORRECTION1_COEFFICIENT)
						+ "~"
						+ Utils.formatDouble(up_trend_upPoint1_num
								- up_trend_wave1_length
								* Constant.MAX_CORRECTION1_COEFFICIENT));
			}

			up_trend_strutlpoint_text.setText("" + up_trend_upPoint1_num);
		}
	}

	private void refreshLowPoint2() {
		if (Utils.isBiggerZero(up_trend_lowPoint2_num)
				&& Utils.isBiggerZero(up_trend_wave1_length)) {

			up_trend_resistance_zone_text.setText(Utils
					.formatDouble(up_trend_lowPoint2_num
							+ up_trend_wave1_length
							* Constant.MIN_RESISTENCE_COEFFICIENT)
					+ "~"
					+ Utils.formatDouble(up_trend_lowPoint2_num
							+ up_trend_wave1_length
							* Constant.MAX_RESISTENCE_COEFFICIENT));

			up_trend_min_target_num = up_trend_lowPoint2_num
					+ up_trend_wave1_length * Constant.TARGET1_COEFFICIENT;

			up_trend_max_target_num = up_trend_upPoint1_num
					+ up_trend_wave1_length * Constant.TARGET1_COEFFICIENT;

			up_trend_target_zone1_text.setText(Utils
					.formatDouble(up_trend_min_target_num)
					+ "~"
					+ Utils.formatDouble(up_trend_max_target_num));

		}
	}

	private void refreshTakePrice() {
		if (Utils.isBiggerZero(up_trend_takeprice_num)
				&& Utils.isBiggerZero(up_trend_lowPoint1_num)
				&& Utils.isBiggerZero(up_trend_min_target_num)
				&& Utils.isBiggerZero(up_trend_max_target_num)) {

			double lose = up_trend_takeprice_num - up_trend_lowPoint1_num;
			double min_profit = up_trend_min_target_num
					- up_trend_takeprice_num;
			double max_profit = up_trend_max_target_num
					- up_trend_takeprice_num;
			up_trend_profit_lose_ratio_text.setText(Utils
					.formatDouble(min_profit / lose)
					+ "~"
					+ Utils.formatDouble(max_profit / lose));
		}
	}

	private void refreshUpPoint2() {
		if (Utils.isBiggerZero(up_trend_upPoint2_num)) {
			up_trend_wave3_length = up_trend_upPoint2_num
					- up_trend_lowPoint2_num;
			if (Utils.isBiggerZero(up_trend_wave3_length)) {
				up_trend_correction_zone2_text.setText(Utils
						.formatDouble(up_trend_upPoint2_num
								- up_trend_wave3_length
								* Constant.MIN_CORRECTION2_COEFFICIENT)
						+ "~"
						+ Utils.formatDouble(up_trend_upPoint2_num
								- up_trend_wave3_length
								* Constant.MAX_CORRECTION2_COEFFICIENT));
			}
		}
	}

	private void refreshTarget2() {
		int index = up_trend_complexwave_spinner.getSelectedItemPosition();
		double target1 = up_trend_lowPoint3_num + up_trend_wave1_length
				* Constant.TARGET2_TYPE1_COEFFICIENT;
		double target2 = up_trend_lowPoint3_num + up_trend_wave1_length
				* Constant.TARGET2_TYPE2_COEFFICIENT;

		SpannableString msp = new SpannableString(Utils.formatDouble(target1)
				+ "~" + Utils.formatDouble(target2));

		switch (index) {
		case 0:
			msp.setSpan(new ForegroundColorSpan(Color.RED), 0, msp.toString()
					.indexOf("~"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			msp.setSpan(new RelativeSizeSpan(1.3f), 0,
					msp.toString().indexOf("~"),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			break;
		case 1:
			msp.setSpan(new ForegroundColorSpan(Color.RED), msp.toString()
					.indexOf("~") + 1, msp.toString().length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			msp.setSpan(new RelativeSizeSpan(1.3f),
					msp.toString().indexOf("~") + 1, msp.toString().length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			break;
		case 2:
			target1 = up_trend_lowPoint1_num + up_trend_wave1_length
					* Constant.TARGET2_COEFFICIENT;
			target2 = up_trend_upPoint1_num + up_trend_wave1_length
					* Constant.TARGET2_COEFFICIENT;
			msp = new SpannableString(Utils.formatDouble(target1) + "~"
					+ Utils.formatDouble(target2));
			break;
		default:
			break;
		}

		if (Utils.isBiggerZero(target1) && Utils.isBiggerZero(target2)) {
			up_trend_target_zone2_text.setText(msp);
		}
	}

	@Override
	public void recalculate() {
		// TODO Auto-generated method stub
		refreshLowPoint1();
		refreshUpPoint1();
		refreshLowPoint2();
		refreshTakePrice();
		refreshUpPoint2();
		refreshTarget2();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		up_trend_lowpoint1_edit.setText("");
		up_trend_uppoint1_edit.setText("");
		up_trend_lowpoint2_edit.setText("");
		up_trend_takeprice_edit.setText("");
		up_trend_uppoint2_edit.setText("");
		up_trend_lowpoint3_edit.setText("");
		
		up_trend_lowPoint1_num = 0.00;
		up_trend_upPoint1_num = 0.00;
		up_trend_wave1_length = 0.00;
		up_trend_lowPoint2_num = 0.00;
		up_trend_min_target_num = 0.00;
		up_trend_max_target_num = 0.00;
		up_trend_takeprice_num = 0.00;
		up_trend_upPoint2_num = 0.00;
		up_trend_wave3_length = 0.00;
		up_trend_lowPoint3_num = 0.00;
	}
}
