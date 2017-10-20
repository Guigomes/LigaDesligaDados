package br.com.ggsoftware.ligadesligadados.activitys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosDAO;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosVO;
import br.com.ggsoftware.ligadesligadados.util.AcaoEnum;
import br.com.ggsoftware.ligadesligadados.util.FrequenciaEnum;


public class MenuPrincipalActivity extends ActionBarActivity {

	//private AdView adView;
	String ANUNCIO_ID = "ca-app-pub-3737758644488199/5522704069";
	EditText edtData;
	Object iConnectivityManager;
	Button btnOnOff;
	TimePicker timePicker;
	Integer acao = 0;
	Integer frequencia = 1;
	int hora, minuto;
	SimpleDateFormat sdfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt-BR"));
	SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt-BR"));
	SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss", new Locale("pt-BR"));
	int mYear, mMonth, mDay;
	Calendar calendar = Calendar.getInstance();
	Calendar horarioEdtData = Calendar.getInstance();
	CheckBox chkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_principal_activity);
		getSupportActionBar().setTitle(getString(R.string.app_name));
/*
		adView = new AdView(this);
		adView.setAdUnitId(AN�NCIO_ID);
		adView.setAdSize(AdSize.BANNER);

*/

		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		edtData = (EditText) findViewById(R.id.edtData);

		edtData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dateDialog();

			}
		});

		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				hora = hourOfDay;
				minuto = minute;
			}
		});

		edtData.setText(sdfData.format(Calendar.getInstance().getTime()).toString());

		chkBox = (CheckBox) findViewById(R.id.chkNoti);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.bannerLayout);
		/*
		// Adicionando o AdView no layout.
		layout.addView(adView,0);
		// Fazendo uma requisi��o para recuperar o an�ncio.
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("1133DB1CB840D26F99B0A6B3535B8202").build();
		// Adicionando a requisi��o no AdView.
		adView.loadAd(adRequest);
*/
	}

	public void agendar(View v) throws ParseException {

		AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(getApplicationContext());
		List<AlarmeDadosVO> lAlarmeDadosVO = alarmeDadosDAO.listarPorData();

		boolean repetido = false;
		horarioEdtData.setTime(sdfData.parse(edtData.getText().toString()));
		horarioEdtData.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
		horarioEdtData.set(Calendar.MINUTE, timePicker.getCurrentMinute());
		horarioEdtData.set(Calendar.SECOND, 0);
		if (horarioEdtData.compareTo(Calendar.getInstance()) >= 0) {

			for (AlarmeDadosVO alarmeDadosVO : lAlarmeDadosVO) {

				int hora = calendar.get(Calendar.HOUR);
				int minuto = calendar.get(Calendar.MINUTE);
				Log.i("STRS", String.valueOf(calendar.get(Calendar.HOUR) == Calendar.SUNDAY));
				calendar.setTime(sdfCompleto.parse(alarmeDadosVO.getHorario()));
				if (hora == timePicker.getCurrentHour() && minuto == timePicker.getCurrentMinute() && calendar.get(Calendar.DAY_OF_MONTH) == horarioEdtData.get(Calendar.DAY_OF_MONTH)
						&& calendar.get(Calendar.MONTH) == horarioEdtData.get(Calendar.MONTH) && calendar.get(Calendar.YEAR) == horarioEdtData.get(Calendar.YEAR)) {
					Toast.makeText(getApplicationContext(), getString(R.string.erro_alarme_repetido), Toast.LENGTH_SHORT).show();
					repetido = true;
					break;
				}

			}
			if (!repetido) {

				AlarmeDadosVO alarmeDadosVO = new AlarmeDadosVO();
				final int id = alarmeDadosDAO.proximoId();
				Intent it = new Intent("EXECUTAR_ALARME");
				PendingIntent p = PendingIntent.getBroadcast(MenuPrincipalActivity.this, id, it, 0);
				AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

				calendar.setTime(horarioEdtData.getTime());
				calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
				calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
				calendar.set(Calendar.SECOND, 0);

				alarmeDadosVO.setFrequencia(frequencia);
				alarmeDadosVO.setAcao(acao);
				alarmeDadosVO.setHorario(sdfCompleto.format(calendar.getTime()));

				alarmeDadosVO.setOrigem("DATA");
				if (chkBox.isChecked())
					alarmeDadosVO.setMostrar(1);
				else
					alarmeDadosVO.setMostrar(0);
				alarmeDadosDAO.insert(alarmeDadosVO);

				long time = calendar.getTimeInMillis();

				if (frequencia == FrequenciaEnum.DIARIAMENTE.getCodigo()) {
					alarme.setRepeating(AlarmManager.RTC_WAKEUP, time, 86400000, p); //
					
					Toast.makeText(this, getString(R.string.foi_agendado_para) + " " + getString(AcaoEnum.getcodigoTextoByCodigo(acao)) + " " + getString(R.string.no_dia) + " " + sdfData.format(calendar.getTime()) + " " + getString(R.string.as) + " " +  sdfHora.format(calendar.getTime()) +  "h.", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, getString(R.string.foi_agendado_para) + " " + getString(AcaoEnum.getcodigoTextoByCodigo(acao)) + " " + getString(R.string.no_dia) + " " + sdfData.format(calendar.getTime()) + " " + getString(R.string.as) + " " +  sdfHora.format(calendar.getTime()) + "h.", Toast.LENGTH_LONG).show();
					alarme.set(AlarmManager.RTC_WAKEUP, time, p);
				}
			}
		} else {
			Toast.makeText(this, getString(R.string.erro_alarme_passado), Toast.LENGTH_SHORT).show();
		}
	}

	public void onRadioButtonClickedAcao(View view) {
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radio_ligar:
			if (checked)
				acao = AcaoEnum.LIGAR.getCodigo();
			break;
		case R.id.radio_desligar:
			if (checked)
				acao = AcaoEnum.DESLIGAR.getCodigo();
			break;
		}

	}

	public void onRadioButtonClickedFrequencia(View view) {
		boolean checked = ((RadioButton) view).isChecked();

		switch (view.getId()) {
		case R.id.radio_umaVez:
			if (checked)
				frequencia = FrequenciaEnum.UMA_VEZ.getCodigo();
			break;
		case R.id.radio_diariamente:
			if (checked)
				frequencia = FrequenciaEnum.DIARIAMENTE.getCodigo();
			break;

		}

	}

	public void dateDialog() {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				edtData.setText(sdfData.format(calendar.getTime()));

			}
		}, mYear, mMonth, mDay);

		dpd.show();

	}





	public void agendarSms(View v) {
		Intent it = new Intent(MenuPrincipalActivity.this, AgendamentoClaroActivity.class);
	
		startActivity(it);
	}

	public void listar(View v) {

		startActivity(new Intent(this, ListarActivity.class));

	}
	@Override
	protected void onResume() {
		super.onResume();
		//adView.resume();
		
	}

	@Override
	protected void onPause() {
		//adView.pause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		//adView.destroy();
		super.onDestroy();
	}
}
