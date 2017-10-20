package br.com.ggsoftware.ligadesligadados.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.activitys.MenuPrincipalActivity;


public class Util {

	public static void ligaDesligaDados(Context context, boolean ligar) {

		try {

			Object iConnectivityManager;
			final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			@SuppressWarnings("rawtypes")
			final Class conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			iConnectivityManager = iConnectivityManagerField.get(conman);
			@SuppressWarnings("rawtypes")
			final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			@SuppressWarnings("unchecked")
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(iConnectivityManager, ligar);
			

		} catch (Exception e) {

		}
	}

	@SuppressWarnings("deprecation")
	public static void mostrarNotificacao(Context context, String msg) {
	
		Intent i = new Intent(context, MenuPrincipalActivity.class);
		PendingIntent detailsIntent = PendingIntent.getActivity(context, 0, i, 0);
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notif = new Notification(R.mipmap.img_dialog, context.getString(R.string.alarme_dados), System.currentTimeMillis());
		CharSequence from = context.getString(R.string.app_name);
		//notif.setLatestEventInfo(context, from, msg, detailsIntent);

		notif.defaults |= Notification.DEFAULT_VIBRATE;
		nm.notify(0, notif);
		
	}

	public static boolean isEnabled(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED) {
			return true;
		} else {
			return false;
		}

	}
}
