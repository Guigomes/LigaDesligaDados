package br.com.ggsoftware.ligadesligadados.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;


import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosDAO;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosVO;
import br.com.ggsoftware.ligadesligadados.util.AcaoEnum;
import br.com.ggsoftware.ligadesligadados.util.FrequenciaEnum;

public class AgendamentoClaroActivity extends ActionBarActivity {


	EditText mensagem;
	int acao = 0;
	int frequencia = 1;
	AlarmeDadosDAO alarmeDadosDAO;
	CheckBox chkBox;
	//private AdView adView;
	String ANUNCIO_ID = "ca-app-pub-3737758644488199/6714635269";
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agendamento_claro_activity);
		mensagem = (EditText) findViewById(R.id.txtArea_mensagem);
		getSupportActionBar().setTitle(getString(R.string.agendamento_por_sms));
		alarmeDadosDAO = new AlarmeDadosDAO(this);
		chkBox = (CheckBox) findViewById(R.id.chkNoti);
		/*
		adView = new AdView(this);
		adView.setAdUnitId(ANUNCIO_ID);
		adView.setAdSize(AdSize.BANNER);
*/
		LinearLayout layout = (LinearLayout) findViewById(R.id.container);
		// Adicionando o AdView no layout.
/*
		layout.addView(adView,0);
		// Fazendo uma requisi��o para recuperar o an�ncio.
		AdRequest adRequest = new AdRequest.Builder().build();
		// Adicionando a requisi��o no AdView.
		adView.loadAd(adRequest);
*/
	}
	
	public void salvarMensagem(View v){
		
		String mensagem = this.mensagem.getText().toString();
		if(!mensagem.isEmpty()){
		AlarmeDadosVO alarmeDadosVO = new AlarmeDadosVO();
		alarmeDadosVO.setAcao(acao);
		alarmeDadosVO.setFrequencia(frequencia);
		alarmeDadosVO.setHorario(mensagem);
		alarmeDadosVO.setOrigem("SMS");
		if (chkBox.isChecked())
			alarmeDadosVO.setMostrar(1);
		else
			alarmeDadosVO.setMostrar(0);
		alarmeDadosDAO.insert(alarmeDadosVO);
			finish();
			Toast.makeText(getApplicationContext(), getString(R.string.msg_salvo),Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), getString(R.string.erro_msg),Toast.LENGTH_SHORT).show();
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
	//	adView.destroy();
		super.onDestroy();
	}
}
