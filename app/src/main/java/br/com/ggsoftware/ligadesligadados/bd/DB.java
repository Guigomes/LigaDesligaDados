package br.com.ggsoftware.ligadesligadados.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{
private static String dbName="alarme_dados.db";
private static String sql = "CREATE TABLE IF NOT EXISTS [alarme_dados] ([id] INTEGER PRIMARY KEY AUTOINCREMENT,[acao] INTEGER,[horario] VARCHAR(23),[frequencia] INTEGER, [origem] VARCHAR[10], [mostrar] INTEGER);";
//private static String sql2 = "DROP TABLE [alarme_dados]";
StringBuilder strb = new StringBuilder();

private static int version = 1;
	public DB(Context context) {
		
		super(context, dbName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
