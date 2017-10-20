package br.com.ggsoftware.ligadesligadados.receivers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosDAO;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosVO;
import br.com.ggsoftware.ligadesligadados.util.AcaoEnum;
import br.com.ggsoftware.ligadesligadados.util.FrequenciaEnum;
import br.com.ggsoftware.ligadesligadados.util.Util;


public class ReceberAlarme extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		int acao = -1;
		int frequencia = -1;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt-BR"));

		AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(context);
		AlarmeDadosVO alarmeAtual = new AlarmeDadosVO();
		List<AlarmeDadosVO> lAlarmeDadosVO = alarmeDadosDAO.listarPorData();
		Calendar horario = null;
		boolean mostrar  = true;
		for (AlarmeDadosVO alarmeDadosVO : lAlarmeDadosVO) {
			horario = Calendar.getInstance();

			try {
				horario.setTime(sdfCompleto.parse(alarmeDadosVO.getHorario()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (calendar.get(Calendar.HOUR_OF_DAY) == horario.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.MINUTE) == horario.get(Calendar.MINUTE) && calendar.get(Calendar.DAY_OF_MONTH) == horario.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.MONTH) == horario.get(Calendar.MONTH)
					&& calendar.get(Calendar.YEAR) == horario.get(Calendar.YEAR)) {
				acao = alarmeDadosVO.getAcao();
				frequencia = alarmeDadosVO.getFrequencia();
				alarmeAtual = alarmeDadosVO;
				mostrar = (alarmeDadosVO.getMostrar() == 1);
				break;
			}

		}

		if (acao != -1) {

			try {

				if (acao == AcaoEnum.DESLIGAR.getCodigo()) {
					Util.ligaDesligaDados(context, false);
				if(mostrar)
					Util.mostrarNotificacao(context, context.getString(R.string.msg_desligada));
				} else if (acao == AcaoEnum.LIGAR.getCodigo()) {
					Util.ligaDesligaDados(context, true);
					if(mostrar)
					Util.mostrarNotificacao(context, context.getString(R.string.msg_ligada));
				}
				if (frequencia == FrequenciaEnum.UMA_VEZ.getCodigo()) {
					alarmeDadosDAO.delete(alarmeAtual);
				}
			} catch (Exception e) {

			}

		}

	}

}
