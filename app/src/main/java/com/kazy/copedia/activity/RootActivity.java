package com.kazy.copedia.activity;

import com.kazy.copedia.R;
import com.kazy.copedia.adapter.WordAdapter;
import com.kazy.copedia.model.Word;
import com.kazy.copedia.openHelper.DataBaseHelper;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class RootActivity extends AppCompatActivity {

    @InjectView(R.id.list_view)
    RecyclerView recyclerView;

    @InjectView(R.id.edit_text)
    EditText editText;

    private SQLiteDatabase db;

    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.inject(this);
        setDatabase();
        setUpTextEvent();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wordAdapter = new WordAdapter();
        recyclerView.setAdapter(wordAdapter);
    }

    private void setDatabase() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        db = dataBaseHelper.openDataBase();

        try {
            dataBaseHelper.createEmptyDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    private void setUpTextEvent() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchWord = editable.toString();
                wordAdapter.clear();
                Cursor cursor = db.rawQuery("select * from items where word  like '%" + searchWord + "%' limit 100;", null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String word = cursor.getString(1);
                    String mean = cursor.getString(2);
                    int level = cursor.getInt(3);
                    wordAdapter.add(new Word(id, word, mean, level));
                }
                cursor.close();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
