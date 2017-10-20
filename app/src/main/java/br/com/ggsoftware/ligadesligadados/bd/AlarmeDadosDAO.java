package br.com.ggsoftware.ligadesligadados.bd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AlarmeDadosDAO {

	private static String table_name = "alarme_dados";
	private static Context ctx;
	private String[] columns = { "id", "acao", "horario", "frequencia", "origem", "mostrar" };

	@SuppressWarnings("static-access")
	public AlarmeDadosDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(AlarmeDadosVO alarmeDadosVO) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		ContentValues ctv = new ContentValues();

		ctv.put("acao", alarmeDadosVO.getAcao());
		ctv.put("horario", alarmeDadosVO.getHorario());
		ctv.put("frequencia", alarmeDadosVO.getFrequencia());
		ctv.put("origem", alarmeDadosVO.getOrigem());
		ctv.put("mostrar", alarmeDadosVO.getMostrar());

		return (db.insert(table_name, null, ctv) > 0);
	}

	public boolean delete(AlarmeDadosVO alarmeDadosVO) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[] { alarmeDadosVO.getId().toString() }) > 0);
	}

	public boolean update(AlarmeDadosVO alarmeDadosVO) {

		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		ContentValues ctv = new ContentValues();

		ctv.put("acao", alarmeDadosVO.getAcao());
		ctv.put("horario", alarmeDadosVO.getHorario());
		ctv.put("frequencia", alarmeDadosVO.getFrequencia());
		ctv.put("origem", alarmeDadosVO.getOrigem());
		ctv.put("mostrar", alarmeDadosVO.getMostrar());
		return (db.update(table_name, ctv, "id=?", new String[] { alarmeDadosVO.getId().toString() }) > 0);
	}

	public AlarmeDadosVO buscaPorId(Integer id) {

		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		Cursor rs = db.query(table_name, columns, "id=?", new String[] { id.toString() }, null, null, null);

		AlarmeDadosVO alarmeDadosVO = null;
		if (rs.moveToFirst()) {
			alarmeDadosVO = new AlarmeDadosVO();
			alarmeDadosVO.setId(rs.getInt(rs.getColumnIndex("id")));
			alarmeDadosVO.setAcao(rs.getInt(rs.getColumnIndex("acao")));
			alarmeDadosVO.setHorario(rs.getString(rs.getColumnIndex("horario")));
			alarmeDadosVO.setFrequencia(rs.getInt(rs.getColumnIndex("frequencia")));
			alarmeDadosVO.setOrigem(rs.getString(rs.getColumnIndex("origem")));
			alarmeDadosVO.setMostrar(rs.getInt(rs.getColumnIndex("mostrar")));
		}
		rs.close();
		db.close();
		return alarmeDadosVO;
	}

	public List<AlarmeDadosVO> listarPorData() {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		List<AlarmeDadosVO> listaAlarmeDadosVO = new ArrayList<AlarmeDadosVO>();
		Cursor rs = db.rawQuery("SELECT * FROM " + table_name + " WHERE origem = 'DATA'", null);
		while (rs.moveToNext()) {
			AlarmeDadosVO alarmeDadosVO = new AlarmeDadosVO();
			alarmeDadosVO.setId(rs.getInt(rs.getColumnIndex("id")));
			alarmeDadosVO.setAcao(rs.getInt(rs.getColumnIndex("acao")));
			alarmeDadosVO.setHorario(rs.getString(rs.getColumnIndex("horario")));
			alarmeDadosVO.setFrequencia(rs.getInt(rs.getColumnIndex("frequencia")));
			alarmeDadosVO.setOrigem(rs.getString(rs.getColumnIndex("origem")));
			alarmeDadosVO.setMostrar(rs.getInt(rs.getColumnIndex("mostrar")));

			listaAlarmeDadosVO.add(alarmeDadosVO);
		}
		rs.close();
		db.close();
		return listaAlarmeDadosVO;
	}

	public List<AlarmeDadosVO> listarPorMensagem() {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		List<AlarmeDadosVO> listaAlarmeDadosVO = new ArrayList<AlarmeDadosVO>();
		Cursor rs = db.rawQuery("SELECT * FROM " + table_name + " WHERE origem = 'SMS'", null);
		while (rs.moveToNext()) {
			AlarmeDadosVO alarmeDadosVO = new AlarmeDadosVO();
			alarmeDadosVO.setId(rs.getInt(rs.getColumnIndex("id")));
			alarmeDadosVO.setAcao(rs.getInt(rs.getColumnIndex("acao")));
			alarmeDadosVO.setHorario(rs.getString(rs.getColumnIndex("horario")));
			alarmeDadosVO.setFrequencia(rs.getInt(rs.getColumnIndex("frequencia")));
			alarmeDadosVO.setOrigem(rs.getString(rs.getColumnIndex("origem")));
			alarmeDadosVO.setMostrar(rs.getInt(rs.getColumnIndex("mostrar")));

			listaAlarmeDadosVO.add(alarmeDadosVO);
		}
		rs.close();
		db.close();
		return listaAlarmeDadosVO;
	}

	public int proximoId() {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		Cursor rs = db.rawQuery("SELECT max(id) FROM " + table_name, null);
		rs.moveToFirst();
		int id = rs.getInt(0);
		id += 1;
		rs.close();
		db.close();
		return id;
	}
}
