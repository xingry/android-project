package cn.xingry.android.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CatelogActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textview = new TextView(this);
		textview.setText("This is the Albums tab");
		setContentView(textview);
	}
}
