package com.example.android.studybreak;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String userName;
    String searchString = "restaurant";
    int questionNumber = 0;
    TextView question;
    EditText nameEntry, locationEntry;
    Button nextButton;
    LinearLayout question2, question4, question7;
    RadioGroup question1, question3, question5, question6;
    CheckBox question2a, question2b, question2c, question4a, question4b, question4c, question4d;
    //FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEntry = findViewById(R.id.name_entry);
        nameEntry.clearFocus();
        locationEntry = findViewById(R.id.locationEntry);
        locationEntry.clearFocus();
        nextButton = findViewById(R.id.next_button);
        question = findViewById(R.id.question);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        question6 = findViewById(R.id.question6);
        question2a = findViewById(R.id.question2a);
        question2b = findViewById(R.id.question2b);
        question2c = findViewById(R.id.question2c);
        question4a = findViewById(R.id.question4a);
        question4b = findViewById(R.id.question4b);
        question4c = findViewById(R.id.question4c);
        question7 = findViewById(R.id.question7);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNumber == 0) {
                    beginQuiz();
                } else if (questionNumber == 1) {
                    nextQuestion(question1, question2);
                    question.setText(R.string.question2);
                } else if (questionNumber == 2) {
                    nextQuestion(question2, question3);
                    question.setText(R.string.question3);
                } else if (questionNumber == 3) {
                    nextQuestion(question3, question4);
                    question.setText(R.string.question4);
                } else if (questionNumber == 4) {
                    nextQuestion(question4, question5);
                    question.setText(R.string.question5);
                } else if (questionNumber == 5) {
                    nextQuestion(question5, question6);
                    question.setText(R.string.question6);
                } else if (questionNumber == 6) {
                    nextQuestion(question6, question7);
                    question.setText(R.string.question7);
                } else if (questionNumber == 7) {
                    searchString = searchString + " in " + locationEntry.getText().toString();
                    questionNumber++;
                } else if (questionNumber == 8) {
                    executeSearch();
                }
            }


        });
    }


    /*
        method to set greeting after name entry
     */
    private String setGreeting() {
        userName = nameEntry.getText().toString();
        nameEntry.clearFocus();
        return getResources().getString(R.string.greeting, userName);
    }

    /*
    method to move from initial screen to the first quiz question
     */
    public void beginQuiz() {
        question.setText(setGreeting());
        nameEntry.setVisibility(View.GONE);
        question1.setVisibility(View.VISIBLE);
        questionNumber++;
    }

    /*
        Method to move from one question to the next
     */
    private void nextQuestion(View v, View w) {
        setView(v, w);
        getAnswer();
        questionNumber++;
    }

    /*
        method to get to set the view
     */
    private void setView(View v, View w) {
        v.setVisibility(View.GONE);
        w.setVisibility(View.VISIBLE);
    }

    /*
        method to get answer from radio button
     */
    private void getRadioAnswer() {
        if (questionNumber == 1) {
            if (question1.getCheckedRadioButtonId() != -1) {
                RadioButton answer = findViewById(question1.getCheckedRadioButtonId());
                String answerText = answer.getText().toString();
                if (answerText == getResources().getString(R.string.question1a)) {
                    searchString = getResources().getString(R.string.answer1a) + " " + searchString;
                }
                if (answerText == getResources().getString(R.string.question1b)) {
                    searchString = getResources().getString(R.string.answer1b) + " " + searchString;
                }
            }
        } else if (questionNumber == 3) {
            if (question3.getCheckedRadioButtonId() != -1) {
                RadioButton answer = findViewById(question3.getCheckedRadioButtonId());
                searchString = answer.getText().toString() + " " + searchString;
            }
        } else if (questionNumber == 5) {
            if (question5.getCheckedRadioButtonId() != -1) {
                RadioButton answer = findViewById(question5.getCheckedRadioButtonId());
                searchString = answer.getText().toString() + " " + searchString;
            }
        } else if (questionNumber == 6) {
            if (question6.getCheckedRadioButtonId() != -1) {
                RadioButton answer = findViewById(question6.getCheckedRadioButtonId());
                String answerText = answer.getText().toString();
                if (answerText == getResources().getString(R.string.question6a)) {
                    searchString = getResources().getString(R.string.answer6a) + " " + searchString;
                }
            }
        }
    }

    /*
    method to get answers from checkboxes
     */
    private void getChecked() {
        if (questionNumber == 2) {
            if (question2a.isChecked()) {
                searchString = question2a.getText().toString() + " " + searchString;
            }

            if (question2b.isChecked()) {
                searchString = getResources().getString(R.string.question2b) + " " + searchString;
            }
            if (question2c.isChecked()) {
                searchString = question2c.getText().toString() + " " + searchString;
            }
        } else if (questionNumber == 4) {
            if (question4a.isChecked()) {
                searchString = question4a.getText().toString() + " " + searchString;
            }
            if (question4b.isChecked()) {
                searchString = question4b.getText().toString() + " " + searchString;
            }
            if (question4c.isChecked()) {
                searchString = question4c.getText().toString() + " " + searchString;
            }
        }

    }

    /*
   method to decide which answer to get
    */
    private void getAnswer() {
        if (questionNumber % 2 != 0 || questionNumber == 6) {
            getRadioAnswer();
        } else {
            getChecked();
        }
    }

    private void executeSearch() {
        Intent quizResult = new Intent(Intent.ACTION_WEB_SEARCH);
        quizResult.putExtra(SearchManager.QUERY, searchString);
        if (quizResult.resolveActivity(getPackageManager()) != null) {
            startActivity(quizResult);
        }
        questionNumber=0;
    }
}



