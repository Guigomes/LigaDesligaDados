package br.com.ggsoftware.ligadesligadados.receivers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosDAO;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosVO;
import br.com.ggsoftware.ligadesligadados.util.FrequenciaEnum;


public class LigarTelefone extends BroadcastReceiver{
	SimpleDateFormat sdfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",new Locale("pt-BR"));

	@Override
	public void onReceive(Context context, Intent intent) {
		
		AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(context);
		List<AlarmeDadosVO> lAlarmeDadosVO = alarmeDadosDAO.listarPorData();
		for (AlarmeDadosVO alarmeDadosVO : lAlarmeDadosVO) {
			
			
			Intent it = new Intent("EXECUTAR_ALARME");
			
			PendingIntent p = PendingIntent.getBroadcast(context, alarmeDadosVO.getId(), it,0);
			AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			Calendar c = Calendar.getInstance();

		
			try {
				c.setTime(sdfCompleto.parse(alarmeDadosVO.getHorario()));
			} catch (ParseException e) {
				e.printStackTrace();
			}					
			long time = c.getTimeInMillis();
			
			if(alarmeDadosVO.getFrequencia() == FrequenciaEnum.DIARIAMENTE.getCodigo())
				alarme.setRepeating(AlarmManager.RTC_WAKEUP, time, 86400000,p); //
			else
				alarme.set(AlarmManager.RTC_WAKEUP, time, p);
			
		}
	}

}
