package cn.xingry.android.tab;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cn.xingry.android.R;

public class TabHostActivity extends TabActivity {

	private TabHost mTabHost;

	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// construct the tabhost
		setContentView(R.layout.tab);

		setupTabHost();
		// mTabHost.getTabWidget().setDividerDrawable(R.drawable.minitab_default);
		Intent intent = new Intent().setClass(this, HomeActivity.class);

		setupTab(new TextView(this), "首页", R.drawable.ic_tab_artists, intent);
		intent = new Intent().setClass(this, CatelogActivity.class);
		setupTab(new TextView(this), "分类", R.drawable.ic_tab_artists, intent);
		intent = new Intent().setClass(this, SongsActivity.class);
		setupTab(new TextView(this), "搜索", R.drawable.ic_tab_songs, intent);
		intent = new Intent().setClass(this, CatelogActivity.class);
		setupTab(new TextView(this), "用户", R.drawable.ic_tab_songs, intent);
	}

	private void setupTab(final View view, final String tag, int drawable,
			Intent intent) {
		View tabview = createTabView(mTabHost.getContext(), tag, drawable);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(intent);
		mTabHost.addTab(setContent);

	}

	private static View createTabView(final Context context, final String text,
			int drawable) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		ImageView iv = (ImageView) view.findViewById(R.id.icon);
		iv.setImageResource(drawable);
		return view;
	}

}
