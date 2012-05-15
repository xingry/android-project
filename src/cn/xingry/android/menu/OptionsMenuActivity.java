package cn.xingry.android.menu;

import cn.xingry.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Android应用程序菜单分为三种：选项菜单OptionsMenu，上下文菜单ContextMenu , 子菜单SubMenu
 * @author xingruyi
 *
 */
public class OptionsMenuActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
	}
	
	/**
	 * 初始化界面
	 */
	private void initUI() {
		
		//创建布局管理器
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		TextView txt = new TextView(this);
		txt.setText("点击menu键显示OptionsMenu");
		layout.addView(txt);
	}
	/**
	 * 当点击menu键时调用此方法
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.options_menu,menu);
		return true;
	}
	/**
	 * 当菜单项被点击时调用
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.item01 :
				Toast.makeText(this, "选择了添加菜单", Toast.LENGTH_SHORT).show();
				break;
			case R.id.item02 :
				Toast.makeText(this, "选择了修改菜单", Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(this, "选择了"+item.getItemId()+"菜单", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	/**
	 * 当菜单关闭时调用
	 */
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		Toast.makeText(this,"菜单被关闭", Toast.LENGTH_SHORT).show();
	}
}
