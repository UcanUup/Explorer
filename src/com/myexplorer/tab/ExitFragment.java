package com.myexplorer.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myexplorer.MainActivity.PlaceholderFragment;

public class ExitFragment extends PlaceholderFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().finish();

		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
