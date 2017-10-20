package br.com.ggsoftware.ligadesligadados.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.util.Util;


public class MeuWidgetProvider extends AppWidgetProvider {

	public static String YOUR_AWESOME_ACTION = "YourAwesomeAction";

	public static boolean click = false;

	public static boolean changed = false;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);
			if (click) {
				if (Util.isEnabled(context)) {
					Util.ligaDesligaDados(context, false);
					views.setImageViewResource(R.id.imgWidth, R.mipmap.img_dados_off_widget);
				} else {
					Util.ligaDesligaDados(context, true);
					views.setImageViewResource(R.id.imgWidth, R.mipmap.img_dados_on_widget);
				}
			} 
			 else {
				if (Util.isEnabled(context))
					views.setImageViewResource(R.id.imgWidth, R.mipmap.img_dados_on_widget);
				else
					views.setImageViewResource(R.id.imgWidth, R.mipmap.img_dados_off_widget);
			}
			Intent intent = new Intent(context, MeuWidgetProvider.class);
			intent.setAction(YOUR_AWESOME_ACTION);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

			views.setOnClickPendingIntent(R.id.imgWidth, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		click = false;
		changed = false;

	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		AppWidgetManager mgr = AppWidgetManager.getInstance(context);
		ComponentName provider = new ComponentName(context, MeuWidgetProvider.class);
		int[] ids = mgr.getAppWidgetIds(provider);
		if (intent.getAction().equals(YOUR_AWESOME_ACTION)) {
			click = true;
		} 
		onUpdate(context, mgr, ids);
	}
}