package br.com.ggsoftware.ligadesligadados.activitys;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.ggsoftware.ligadesligadados.R;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosDAO;
import br.com.ggsoftware.ligadesligadados.bd.AlarmeDadosVO;
import br.com.ggsoftware.ligadesligadados.util.AcaoEnum;
import br.com.ggsoftware.ligadesligadados.util.FrequenciaEnum;


public class ListarActivity extends ActionBarActivity {

	private ViewGroup mContainerView;
	TextView empty;
	AlarmeDadosDAO alarmeDadosDAO;
	AlertDialog alertDialog;
	//private AdView adView;
	String ANUNCIO_ID = "ca-app-pub-3737758644488199/7831441665";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_activity);
		
		getSupportActionBar().setTitle(getString(R.string.meus_agendamentos));
		mContainerView = (ViewGroup) findViewById(R.id.container);

		alarmeDadosDAO = new AlarmeDadosDAO(this);
		empty = (TextView) findViewById(R.id.empty);
		empty.setText(getString(R.string.msg_vazio));
		List<AlarmeDadosVO> lAlarmeDadosVO = alarmeDadosDAO.listarPorData();

		if (lAlarmeDadosVO.isEmpty()) {
			empty.setVisibility(View.VISIBLE);
		}

		for (AlarmeDadosVO alarmeDadosVO : lAlarmeDadosVO) {
			addItem(alarmeDadosVO);
		}
		/*
		adView = new AdView(this);
		adView.setAdUnitId(AN�NCIO_ID);
		adView.setAdSize(AdSize.BANNER);
*/
		LinearLayout layout = (LinearLayout) findViewById(R.id.bannerLayout);
		// Adicionando o AdView no layout.

		/*
		layout.addView(adView,0);
		// Fazendo uma requisi��o para recuperar o an�ncio.
		AdRequest adRequest = new AdRequest.Builder().build();
		// Adicionando a requisi��o no AdView.
		adView.loadAd(adRequest);

*/
	}

	
	private void addItem(AlarmeDadosVO alarmeDadosVO) {

		empty.setVisibility(View.GONE);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_listar_activity, mContainerView, false);

		if (alarmeDadosVO.getAcao() == AcaoEnum.LIGAR.getCodigo())
			((ImageView) newView.findViewById(R.id.imgStatus)).setImageResource(R.mipmap.img_ligar);
		else
			((ImageView) newView.findViewById(R.id.imgStatus)).setImageResource(R.mipmap.img_desligar);

		((TextView) newView.findViewById(R.id.txtIdBD)).setText(alarmeDadosVO.getId().toString());
		((TextView) newView.findViewById(android.R.id.text1)).setText(alarmeDadosVO.getHorario());
		((TextView) newView.findViewById(R.id.frequencia)).setText(getString(FrequenciaEnum.getTextoByCodigo(alarmeDadosVO.getFrequencia())));

		newView.findViewById(R.id.delete_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				TextView t = (TextView) newView.findViewById(R.id.txtIdBD);

				AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(getApplicationContext());
				AlarmeDadosVO alarmeDadosVO = alarmeDadosDAO.buscaPorId(Integer.parseInt(t.getText().toString()));
				alarmeDadosDAO.delete(alarmeDadosVO);
				mContainerView.removeView(newView);

				if (mContainerView.getChildCount() == 0) {
					empty.setVisibility(View.VISIBLE);
				}
			}
		});


		mContainerView.addView(newView, 0);
	}

	private void addItemData(final AlarmeDadosVO alarmeDadosVO) {
		// Instantiate a new "row" view.

		empty.setVisibility(View.GONE);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_listar_por_data_activity, mContainerView, false);

		// Set the text in the new row to a random country.
		if (alarmeDadosVO.getAcao() == AcaoEnum.LIGAR.getCodigo())
			((ImageView) newView.findViewById(R.id.imgStatus)).setImageResource(R.mipmap.img_ligar);
		else
			((ImageView) newView.findViewById(R.id.imgStatus)).setImageResource(R.mipmap.img_desligar);

		((TextView) newView.findViewById(R.id.txtIdBD)).setText(alarmeDadosVO.getId().toString());
		Button btnMensagem = (Button) newView.findViewById(R.id.buttonMensagem);
		btnMensagem.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View customView = inflater.inflate(R.layout.novo_sorteio_dialog, null);
				((TextView) customView.findViewById(R.id.txtMensagem)).setText(alarmeDadosVO.getHorario());

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListarActivity.this);
				alertDialogBuilder.setView(customView);
				View customTitleView = inflater.inflate(R.layout.custom_title, null);
				alertDialogBuilder.setCustomTitle(customTitleView);
				alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
		if(alarmeDadosVO.getFrequencia() == FrequenciaEnum.DIARIAMENTE.getCodigo())
			((TextView) newView.findViewById(R.id.frequencia)).setText(getString(R.string.sempre));
		else
			((TextView) newView.findViewById(R.id.frequencia)).setText(getString(FrequenciaEnum.getTextoByCodigo(alarmeDadosVO.getFrequencia())));

		newView.findViewById(R.id.delete_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				
				TextView t = (TextView) newView.findViewById(R.id.txtIdBD);

				AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(getApplicationContext());
				AlarmeDadosVO alarmeDadosVO = alarmeDadosDAO.buscaPorId(Integer.parseInt(t.getText().toString()));
				alarmeDadosDAO.delete(alarmeDadosVO);
				mContainerView.removeView(newView);

				if (mContainerView.getChildCount() == 0) {
					empty.setVisibility(View.VISIBLE);
				}
			}
		});

		mContainerView.addView(newView, 0);
	}



	public void porData(View v) {
		v.setBackgroundColor(getResources().getColor(R.color.azul));
		((Button) findViewById(R.id.porMensagem)).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
		mContainerView.removeAllViews();
		List<AlarmeDadosVO> lAlarmeDadosVO = alarmeDadosDAO.listarPorData();

		for (AlarmeDadosVO alarmeDadosVO : lAlarmeDadosVO) {
			addItem(alarmeDadosVO);
		}
		if (lAlarmeDadosVO.isEmpty()) {
			empty.setVisibility(View.VISIBLE);
		}

	}

	public void porMensagem(View v) {
		v.setBackgroundColor(getResources().getColor(R.color.azul));
		((Button) findViewById(R.id.porData)).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
		mContainerView.removeAllViews();
		List<AlarmeDadosVO> lAlarmeDadosVO = alarmeDadosDAO.listarPorMensagem();
		for (AlarmeDadosVO alarmeDadosVO : lAlarmeDadosVO) {
			addItemData(alarmeDadosVO);
		}

		if (lAlarmeDadosVO.isEmpty()) {
			empty.setVisibility(View.VISIBLE);
		}

	}

	public void fecharDialog(View v) {
		alertDialog.dismiss();
	}
	@Override
	protected void onResume() {
		super.onResume();
	//	adView.resume();
		
	}

	@Override
	protected void onPause() {
	//	adView.pause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
	//	adView.destroy();
		super.onDestroy();
	}
}
