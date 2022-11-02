package com.example.sensors_java;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = findViewById(R.id.btnAdd);
        Button start = findViewById(R.id.btnStart);
        EditText question = findViewById(R.id.etQuestion);
        TextView count = findViewById(R.id.tvCount);

        ArrayList<String> questions = new ArrayList<>();

        add.setOnClickListener(e ->
        {
            if (question.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this, "Question cannot be empty!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            questions.add(question.getText().toString());
            question.setText("");
            count.setText("Number of questions: " + questions.size());
        });

        start.setOnClickListener(e ->
        {
            if (questions.size() < 2)
            {
                Toast.makeText(this, "you should add at lest two questions!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            // start the game
            Intent intent = new Intent(getApplicationContext(), Game.class);
            intent.putExtra("Questions", questions);
            startActivity(intent);
        });
    }
}