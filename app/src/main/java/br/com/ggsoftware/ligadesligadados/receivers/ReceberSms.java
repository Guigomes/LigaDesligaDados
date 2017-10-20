package br.com.ggsoftware.ligadesligadados.receivers;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosDAO;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosVO;
import br.com.ggsoftware.ligadesligadados.util.AcaoEnum;
import br.com.ggsoftware.ligadesligadados.util.FrequenciaEnum;
import br.com.ggsoftware.ligadesligadados.util.Sms;
import br.com.ggsoftware.ligadesligadados.util.Util;


public class ReceberSms extends BroadcastReceiver {

	SmsMessage msg;
	String celular;

	SimpleDateFormat sdfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt-BR"));
	SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt-BR"));

	@Override
	public void onReceive(Context context, Intent intent) {
		Sms sms = new Sms();
		SmsMessage msg = sms.receberMensagem(intent);
		celular = msg.getDisplayOriginatingAddress();
		String mensagem = msg.getDisplayMessageBody();
		boolean mostrar = true;
		AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(context);
		for (AlarmeDadosVO alarmeDadosVO : alarmeDadosDAO.listarPorMensagem()) {
			if (alarmeDadosVO.getHorario().equalsIgnoreCase(mensagem)) {
				mostrar = (alarmeDadosVO.getMostrar() == 1);
				Util.ligaDesligaDados(context, (alarmeDadosVO.getAcao() == AcaoEnum.LIGAR.getCodigo()));
				if (mostrar) {
					if (alarmeDadosVO.getAcao() == AcaoEnum.LIGAR.getCodigo())
						Util.mostrarNotificacao(context, context.getString(R.string.msg_ligada));
					else
						Util.mostrarNotificacao(context, context.getString(R.string.msg_desligada));
				}
				if (alarmeDadosVO.getFrequencia() == FrequenciaEnum.UMA_VEZ.getCodigo())
					alarmeDadosDAO.delete(alarmeDadosVO);
			}
		}

	}
}