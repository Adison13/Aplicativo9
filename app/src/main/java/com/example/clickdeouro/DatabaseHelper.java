package com.example.clickdeouro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jogo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_JOGADAS = "jogadas";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_JOGADAS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data TEXT, " +
                "pontuacao INTEGER, " +
                "duracao INTEGER, " +
                "foiRecorde INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOGADAS);
        onCreate(db);
    }

    public void inserirJogada(String data, int pontuacao, int duracao, int foiRecorde) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("data", data);
        valores.put("pontuacao", pontuacao);
        valores.put("duracao", duracao);
        valores.put("foiRecorde", foiRecorde);
        db.insert(TABLE_JOGADAS, null, valores);
        db.close();
    }

    public List<Jogada> getUltimasJogadas(int limite) {
        List<Jogada> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOGADAS + " ORDER BY id DESC LIMIT " + limite, null);

        while (cursor.moveToNext()) {
            Jogada j = new Jogada();
            j.setId(cursor.getInt(0));
            j.setData(cursor.getString(1));
            j.setPontuacao(cursor.getInt(2));
            j.setDuracao(cursor.getInt(3));
            j.setFoiRecorde(cursor.getInt(4));
            lista.add(j);
        }

        cursor.close();
        db.close();
        return lista;
    }
}


