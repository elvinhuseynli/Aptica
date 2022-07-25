package com.example.aptica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListener{

    TextView emptyText;
    Button addIcon;
    Database database;
    NotesAdapter notesAdapter;
    RecyclerView recyclerView;
    Cursor result;
    NotesData notesData;
    List<NotesData> notesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        loadData();
    }

    public void initializeViews() {
        emptyText = (TextView) findViewById(R.id.textView);
        addIcon = (Button) findViewById(R.id.addButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        database = new Database(this);
        notesData = new NotesData();
        notesDataList = new ArrayList<NotesData>();
    }

    public void fillNotesData(String id, String title, String note, String time) {
        notesData.title = title;
        notesData.note = note;
        notesData.id = id;
        notesData.date = time;
        System.out.println(notesData.id);
        notesDataList.add(notesData);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loadData() {
        result = database.getData();
        checkEmptiness(result.getCount()==0);
        if(result.getCount()>0) {
            while (result.moveToNext()) {
                fillNotesData(result.getString(0), result.getString(1), result.getString(2), result.getString(3));
            }
            System.out.println(notesDataList.size());
//            System.out.println(notesDataList.get(0).id);
//            System.out.println(notesDataList.get(1).id);
//            System.out.println(notesDataList.get(2).id);
//            System.out.println(notesDataList.get(3).id);

            notesAdapter = new NotesAdapter(notesDataList, this);
            recyclerView.setAdapter(notesAdapter);
            notesAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(0);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void checkEmptiness(Boolean value) {
        if(!value)
            emptyText.setVisibility(View.VISIBLE);
        else
            emptyText.setVisibility(View.INVISIBLE);
    }

    public void tasksPage(View view) {
        Intent taskIntent = new Intent(this, TaskAddition.class);
        startActivity(taskIntent);
    }

    @Override
    public void onNotesClicked(NotesData notesData){

    }
}
