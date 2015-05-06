package com.joke.bojunyixiao.listener;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class MyOnRefreshListener2 implements OnRefreshListener2<ListView>{
	private PullToRefreshListView mPtflv;

	public MyOnRefreshListener2(PullToRefreshListView ptflv) {
		this.mPtflv = ptflv;
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

}
