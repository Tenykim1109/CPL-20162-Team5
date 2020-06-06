package com.example.cdp_canworks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private long time;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DBHelper helper = new DBHelper(getApplicationContext(), "canworks.db", null, 1);

        //툴바 설정 부분
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //매장 정보 보여주는 listView 구현부분
        ListViewAdapter adapter;
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
        ArrayList<ListViewItem> copyItems = new ArrayList<ListViewItem>();
        copyItems.addAll(items);

        adapter = new ListViewAdapter(this) ;
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Cursor cursor = helper.getSimpleStoreInfo();

        while(cursor.moveToNext()) {
            adapter.addItem(cursor.getString(2), cursor.getString(1));
        }

        //신규매장 생성부분
        ImageButton create = (ImageButton)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Create_New_Store.class);
                startActivity(intent);
            }
        });

    }

    //가게명으로 이름 검색
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_action, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("가게명으로 검색합니다.");
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    //툴바 상단 자물쇠 버튼 클릭시 로그아웃
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.real_logout:
                intent = new Intent(Main.this, Login.class);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
        }
        return false;
    }

    //검색 기능 - 추후 구현예정
    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            return false;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로가기 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            super.onBackPressed();
        }
    }
}
