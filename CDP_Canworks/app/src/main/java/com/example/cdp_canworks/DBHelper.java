package com.example.cdp_canworks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //로그인
    public String Login(String id, String pwd) {
        SQLiteDatabase db = getReadableDatabase();
        String nickname = "\0";
        Cursor cursor = db.rawQuery("SELECT * FROM member_info WHERE id='" + id + "' AND pwd='" + pwd + "';", null); //입력받은 id와 pwd값으로 조회
        while (cursor.moveToNext()) {
            nickname = cursor.getString(2);
        }
        return nickname;
    }

    //신규매장생성
    public void createStore(String storeNum, String storeName, String storeId, String storePassword, String address1, String address2, String phone, String emblem) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO store_info (storeNum, storeName, storeId, storePassword, address1, address2, phone, emblem) " +
                "VALUES ('"+storeNum+"','"+storeName+"','"+storeId+"','"+storePassword+"','"+address1+"','"+address2+"','"+phone+"','"+emblem+"');");
        db.close();
    }

    //store_info 테이블 안의 모든 값 불러옴
    public Cursor getSimpleStoreInfo() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM store_info", null);
        return cursor;
    }

    //store_info 테이블 안의 특정 column 조회
    public Cursor getStoreInfo(String Number) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM store_info WHERE storeNum='"+ Number +"';", null); //매장 번호로 검색
        return cursor;
    }

    public String getStoreImage(String id) { //매장 이미지 가져오기
        SQLiteDatabase db = getReadableDatabase();
        String emblemPath = "";
        Cursor cursor = db.rawQuery("SELECT * FROM store_info WHERE storeId='" + id + "';", null);
        while(cursor.moveToNext()) {
            emblemPath = cursor.getString(cursor.getColumnIndex("emblem"));
        }
        return emblemPath;
    }

    //매장 정보 삭제
    public void deleteInfo(String storeId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM store_info WHERE storeId='"+storeId+"';");
        db.close();
    }

    //메장 정보 수정
    public void updateInfo(String storeNum, String storeName, String storePassword, String address1, String address2, String phone, String emblemPath) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE store_info SET storeName ='"+storeName+"',"+"storePassword='"+storePassword+"',"+
                "address1='"+address1+"',"+"address2='"+address2+"',"+"phone='"+phone+"',"+"emblem='"+emblemPath+"'WHERE storeNum='"+storeNum+"';");
        db.close();
    }

    //PersonalData 추가
    public void updateLabelInfo(String customer, String phone, String mail, int gender, String age){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO label_info (customer, phone, email, gender, age) " +
                "VALUES ('"+customer+"','"+phone+"','"+mail+"','"+gender+"','"+age+"');");
        db.close();
    }

    //QR코드 등록
    public void updateQRcode(byte[] QRcode, String storeId) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql = "UPDATE store_info SET QRcode=? WHERE storeId = '"+storeId+"';";
            SQLiteStatement insertStmt = db.compileStatement(sql);
            insertStmt.clearBindings();
            insertStmt.bindBlob(1, QRcode);
            insertStmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    //운영자가 labelNum 등록
    public void setLabelNum(String labelNum, String storeId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO label_info (labelNum, storeId) VALUES ('"+labelNum+"','"+storeId+"');");
        db.close();
    }

    public Cursor getLabelInfo(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM label_info WHERE storeId = '"+id+"';", null);
        return cursor;
    }
}
