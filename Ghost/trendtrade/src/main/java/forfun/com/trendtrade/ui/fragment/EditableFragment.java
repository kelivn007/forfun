/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.trendtrade.ui.fragment;

import android.app.Fragment;

public abstract class EditableFragment extends Fragment {
	public abstract void recalculate();
	public abstract void clear();
}
