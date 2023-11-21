package com.shivasai.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.shivasai.todolist.Model.Task;


public class SecondActivity extends AppCompatActivity {

    EditText titleEditText, descriptionEditText;
    CheckBox completeCheckBox;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        completeCheckBox = findViewById(R.id.completeCheckBox);

        db = new DatabaseHandler(this);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            addTaskToDatabase();
            finish();
        });
    }

    private void addTaskToDatabase() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        boolean isCompleted = completeCheckBox.isChecked();

        if (!title.isEmpty()) {
            Task newTask = new Task();
            newTask.setTitle(title);
            newTask.setDescription(description);
            newTask.setCompleted(isCompleted);

            db.addTask(newTask);

            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {

        }
    }
}