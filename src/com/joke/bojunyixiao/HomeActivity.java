package com.joke.bojunyixiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.joke.bojunyixiao.adapter.MyListAdapter;
import com.joke.bojunyixiao.listener.MyOnRefreshListener2;

public class HomeActivity extends Activity implements OnClickListener {
	private MyListAdapter listAdapter;
	private ViewPager vp = null;
	private List<View> views = null;
	private TextView tvTag1, tvTag2, tvTag3;
	private ImageView cursor = null;
	private int offset; // 间隔
	private int cursorWidth; // 游标的长度
	private int originalIndex = 0;
	private Animation animation = null;
	private Timer timer = null;
	private TimerTask timeTask = null;
	private boolean isExit = false;
	private PullToRefreshListView ptrlvHotjokes;
	private PullToRefreshListView ptrlvPicjokes;
	private PullToRefreshListView ptrlvAlljokes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		views = initView();

		initTextView();
		vp = (ViewPager) findViewById(R.id.vpViewPager1);
		vp.setAdapter(new MyViewPager(views));

		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					tvTag1.setTextColor(Color.GREEN);
					tvTag2.setTextColor(Color.BLACK);
					tvTag3.setTextColor(Color.BLACK);
					break;
				case 1:
					tvTag1.setTextColor(Color.BLACK);
					tvTag2.setTextColor(Color.GREEN);
					tvTag3.setTextColor(Color.BLACK);
					break;
				case 2:
					tvTag1.setTextColor(Color.BLACK);
					tvTag2.setTextColor(Color.BLACK);
					tvTag3.setTextColor(Color.GREEN);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		MyViewPager adapter = (MyViewPager) vp.getAdapter();
		View v1 = adapter.getItemAtPosition(0);
		View v2 = adapter.getItemAtPosition(1);
		View v3 = adapter.getItemAtPosition(2);
		ptrlvAlljokes = (PullToRefreshListView) v1
				.findViewById(R.id.ptrlvAllJokes);
		ptrlvPicjokes = (PullToRefreshListView) v2
				.findViewById(R.id.ptrlvPicJokes);
		ptrlvHotjokes = (PullToRefreshListView) v3
				.findViewById(R.id.ptrlvHotJokes);
		listAdapter = new MyListAdapter(this, getSimulationNews(10));
		initPullToRefreshListView(ptrlvAlljokes, listAdapter);
		initPullToRefreshListView(ptrlvPicjokes, listAdapter);
		initPullToRefreshListView(ptrlvHotjokes, listAdapter);
	}

	public ArrayList<HashMap<String, String>> getSimulationNews(int n) {
		ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hm;
		for (int i = 0; i < n; i++) {
			hm = new HashMap<String, String>();
			if (i % 2 == 0) {
				hm.put("uri",
						"http://images.china.cn/attachement/jpg/site1000/20131029/001fd04cfc4813d9af0118.jpg");
			} else {
				hm.put("uri",
						"http://photocdn.sohu.com/20131101/Img389373139.jpg");
			}
			hm.put("title", "国内成品油价两连跌几成定局");
			hm.put("content", "国内成品油今日迎调价窗口，机构预计每升降价0.1元。");
			hm.put("review", i + "跟帖");
			ret.add(hm);
		}
		return ret;
	}

	public void initTextView() {
		tvTag1 = (TextView) findViewById(R.id.tvTag1);
		tvTag2 = (TextView) findViewById(R.id.tvTag2);
		tvTag3 = (TextView) findViewById(R.id.tvTag3);
		tvTag1.setOnClickListener(this);
		tvTag2.setOnClickListener(this);
		tvTag3.setOnClickListener(this);
	};

	public List<View> initView() {

		views = new ArrayList<View>();
		views.add(LayoutInflater.from(this).inflate(R.layout.all_jokes, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.pic_jokes, null));
		views.add(LayoutInflater.from(this).inflate(R.layout.hot_jokes, null));
		return views;
	}

	class MyViewPager extends PagerAdapter {
		private List<View> views;

		public MyViewPager(List<View> views) {
			this.views = views;
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(views.get(position));

			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public View getItemAtPosition(int position) {
			return views.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}
	}

	public void initPullToRefreshListView(PullToRefreshListView ptrlv,
			MyListAdapter listAdapter) {

		ptrlv.setMode(Mode.BOTH);
		ptrlv.setOnRefreshListener(new MyOnRefreshListener2(ptrlv));
		ptrlv.setAdapter(listAdapter);
	}

	public void initCursor(int tagNum) {
		cursorWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.cursor_pink).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		offset = ((dm.widthPixels / tagNum) - cursorWidth) / 2;

		cursor = (ImageView) findViewById(R.id.ivCursor);
		Matrix matrix = new Matrix();
		matrix.setTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvTag1:
			vp.setCurrentItem(0);
			break;
		case R.id.tvTag2:
			vp.setCurrentItem(1);
			break;
		case R.id.tvTag3:
			vp.setCurrentItem(2);
			break;
		}
	}
}
