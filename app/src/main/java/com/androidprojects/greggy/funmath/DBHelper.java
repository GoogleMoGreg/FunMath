package com.androidprojects.greggy.funmath;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by greggy on 4/4/17.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "DB_Highscore";
    public static final String HIGHSCORE_TABLE = "Highscores";
    public static final String HIGHSCORE_COL_PK = "pk";
    public static final String HIGHSCORE_COL_CATEGORY = "Category";
    public static final String HIGHSCORE_COL_HIGHSCORE = "Highscore";


    public static final String TAG_LOG = "MESSAGE";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Highscores "+
        "(pk INTEGER PRIMARY KEY, Category TEXT, Highscore TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+HIGHSCORE_TABLE);
        onCreate(db);
    }

    public void InsertData(String Category,String Highscore){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HIGHSCORE_COL_CATEGORY,Category);
        cv.put(HIGHSCORE_COL_HIGHSCORE,Highscore);
        db.insert(HIGHSCORE_TABLE,null,cv);
        Log.d(TAG, "Successfully Created a DB...");
    }

    public int CheckRowNum(){
        SQLiteDatabase db = getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HIGHSCORE_TABLE);
        return numRows;

    }
    String valscore;
    public void UpdateDBScore(int pk,int score){

        SQLiteDatabase db = getReadableDatabase();
        Cursor getdbscore = db.rawQuery( "SELECT Highscore FROM "+HIGHSCORE_TABLE+" WHERE pk="+pk+" ",null);
        if (getdbscore.moveToFirst()){
                  valscore = getdbscore.getString(getdbscore.getColumnIndex(HIGHSCORE_COL_HIGHSCORE));
        }
        int DBscore = ZeroParser(valscore);
        if (score>DBscore){
            Log.d(TAG_LOG,"score value is "+score+" Score in DB is "+
            DBscore);
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(HIGHSCORE_COL_HIGHSCORE,String.valueOf(score));
            db.update(HIGHSCORE_TABLE,cv,HIGHSCORE_COL_PK +" = ?",
                    new String[]{String.valueOf(pk)});
            Log.d(TAG_LOG,"Successfully updated DB!");
        }
        getdbscore.close();
    }

    public int ZeroParser(String scoreval){
        int dbScore;
        Log.d(TAG_LOG,"Score is "+valscore);
        if (scoreval == null){
            dbScore = 0;
        }

        else {
            dbScore = Integer.valueOf(scoreval);
        }
        return dbScore;
    }

    public String getTableValues(){
        SQLiteDatabase db = getReadableDatabase();
        String tableString = String.format("TABLE %s: \n",HIGHSCORE_TABLE);
        Cursor allRows = db.rawQuery("SELECT * FROM "+HIGHSCORE_TABLE,null);
        if (allRows.moveToFirst()){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames){
                    tableString +=String.format("%s: %s\n",name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString +="\n";
            }while (allRows.moveToNext());
        }
        allRows.close();
        return tableString;
    }

    public int ViewHighScore(int pk){

        SQLiteDatabase db = getReadableDatabase();
        Cursor getdbscore = db.rawQuery( "SELECT Highscore FROM "+HIGHSCORE_TABLE+" WHERE pk="+pk+" ",null);
        if (getdbscore.moveToFirst()){
            valscore = getdbscore.getString(getdbscore.getColumnIndex(HIGHSCORE_COL_HIGHSCORE));
        }
        int DBscore = ZeroParser(valscore);

        return DBscore;
    }

}
