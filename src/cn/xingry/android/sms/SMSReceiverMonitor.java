package cn.xingry.android.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiverMonitor extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += "收到从 [" + msgs[i].getOriginatingAddress();
				str += " ]发来的短信，内容 :";
				str += msgs[i].getMessageBody().toString();
				str += "\n";
			}
			// ---display the new SMS message---
			Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		}
	}

}
