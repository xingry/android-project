package cn.xingry.android.skin;

import android.app.Activity;
import android.content.SharedPreferences;
import cn.xingry.android.MainActivity;
import cn.xingry.android.R;

/**
 * 皮肤管理器
 * 
 * @author xingruyi
 * 
 */
public class SkinSettingManager {

	public static final String SKIN_REF = "skinSetting";
	public static final String SKIN_KEY = "skinKey"	;

	public SharedPreferences skinSettingPreferences;

	//定义皮肤资源 
	private int[] skinResources = { R.drawable.default_wallpaper,
			R.drawable.wallpaper_c, R.drawable.wallpaper_d,
			R.drawable.wallpaper_f, R.drawable.wallpaper_g };
	
	private Activity mainAcitity;
	
	
	public SkinSettingManager(Activity activity) {
		this.mainAcitity = activity;
		skinSettingPreferences = this.mainAcitity.getSharedPreferences(SKIN_REF, 3);
	}
	
	/**
	 * 获取当前皮肤编号
	 */
	public int getSkinType() {
		return skinSettingPreferences.getInt(SKIN_KEY, 0);
	}
	
	/**
	 * 写皮肤编号到全局设置中
	 */
	public void setSkinType(int n) {
		SharedPreferences.Editor editor = skinSettingPreferences.edit();
		editor.putInt(SKIN_KEY, n);
		editor.commit();
	}
	
	/**
	 * 获取当前皮肤对应资源ID
	 */
	public int getCurrentSkinRes() {
		int getSkinLen = getSkinType();
		if(getSkinLen >= skinResources.length){
			getSkinLen = 0;
		}
		return skinResources[getSkinLen];
	}
	
	/**
	 * 切换皮肤 
	 */
	public void toggleSkins() {
		int skinType = getSkinType();
		skinType = ( skinType == skinResources.length -1 ) ? 0 : ++skinType;
		setSkinType(skinType);
		mainAcitity.getWindow().setBackgroundDrawable(null);
		try {
			this.mainAcitity.getWindow().setBackgroundDrawableResource(getCurrentSkinRes());
		}catch(Throwable e	) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化皮肤 
	 */
	public void initSkin() {
		this.mainAcitity.getWindow().setBackgroundDrawableResource(getCurrentSkinRes());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
