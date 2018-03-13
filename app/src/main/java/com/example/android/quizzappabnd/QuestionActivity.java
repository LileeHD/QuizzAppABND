package com.example.android.quizzappabnd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // display the question page when the score button is hit (multi-screen apps course)
        Button scoreBtn = findViewById(R.id.score_btn);
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer score = 0;
                // Question 1
                // Good answers list
                // Put every good answers into the list
                ArrayList<Integer> goodAnswers = new ArrayList<>();
                goodAnswers.add(1);
                goodAnswers.add(3);

                // checkboxes list
                // Put every checkbox into the checkbox list
                ArrayList<CheckBox> checkBoxes = new ArrayList<>();
                checkBoxes.add((CheckBox) findViewById(R.id.q1_check_1));
                checkBoxes.add((CheckBox) findViewById(R.id.q1_check_2));
                checkBoxes.add((CheckBox) findViewById(R.id.q1_check_3));
                checkBoxes.add((CheckBox) findViewById(R.id.q1_check_4));

                // Add +1 to the score if the question is correctly checked
                if (isCorrectlyChecked(goodAnswers, checkBoxes)) {
                    score += 1;
                }

                // Question 2
                // the isGoodRadio method will retrieve the radio group and the index of the button that must be selected
                // If it returns true then adds 1 to the score.
                if (isGoodRadio((RadioGroup) findViewById(R.id.q2_radio_group), 3)) {
                    score += 1;
                }

                // Question 3
                // Same as Question 2
                if (isGoodRadio((RadioGroup) findViewById(R.id.q3_radio_group), 2)) {
                    score += 1;
                }

                // Question 4
                // Same as Question 2
                if (isGoodRadio((RadioGroup) findViewById(R.id.q4_radio_group), 1)) {
                    score += 1;
                }

                // Question 5
                // The isGoodField method will retrieve the user input and the good answer.
                // If it returns true then adds 1 to the score.
                if (isGoodField((EditText) findViewById(R.id.q5_user_answer), "Overwatch")) {
                    score += 1;
                }

                // Question 6
                // Same as Question 1
                // Good answers list
                ArrayList<Integer> goodAnswers6 = new ArrayList<>();
                goodAnswers6.add(0);
                goodAnswers6.add(2);

                // checkboxes list
                ArrayList<CheckBox> checkBoxes6 = new ArrayList<>();
                checkBoxes6.add((CheckBox) findViewById(R.id.q6_check_1));
                checkBoxes6.add((CheckBox) findViewById(R.id.q6_check_2));
                checkBoxes6.add((CheckBox) findViewById(R.id.q6_check_3));
                checkBoxes6.add((CheckBox) findViewById(R.id.q6_check_4));

                // Add +1 to the score if the question is correctly checked
                if (isCorrectlyChecked(goodAnswers6, checkBoxes6)) {
                    score += 1;
                }

                // Question 7
                // Same as Question 5
                if (isGoodField((EditText) findViewById(R.id.q7_user_answer), "Switch")) {
                    score += 1;
                }

                // Show the score into a toast
                Context context = getApplicationContext();
                CharSequence text = score.toString();
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // Call the Activity score when the submit_btn is hit
                Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);

                // Send the score to the score_activity
                intent.putExtra("score", score);
                startActivity(intent);
            }
        });
    }

    /**
     * This method checks if the userInput string is equal to the good answer
     * In order to compare the userInput and the good answer properly they are put in lowercase.
     */
    public boolean isGoodField(EditText userInput, String goodAnswer) {
        String text = userInput.getText().toString().toLowerCase();
        return (text.equals(goodAnswer.toLowerCase()));
    }

    /**
     * This method checks if the good radio is checked
     * It gets the checked radio button id
     * It gets the button view
     * it gets the button index from radio group
     * It returns if the index equals the good answer.
     */
    public boolean isGoodRadio(RadioGroup radioGroup, Integer goodAnswer) {
        Integer checked = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(checked);
        Integer index = radioGroup.indexOfChild(radioButton);
        return index.equals(goodAnswer);
    }

    /**
     * The method checks if the good answers are checked
     * For each good answer in the list
     * The checkbox with the good answer as an index must be checked
     * If it is not, returns false (at least one good answer has not been checked )
     * Otherwise it returns true (every good answer has been checked ).
     */
    public boolean hasGoodAnswersChecked(ArrayList<Integer> goodAnswers, ArrayList<CheckBox> checkBoxes) {
        for (Integer goodAnswer : goodAnswers) {
            CheckBox mustBeChecked = checkBoxes.get(goodAnswer);
            if (!mustBeChecked.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method checks if there's bad answers.
     * For each checkbox in the checkbox list:
     * get its index
     * If it's checked and its index is not in the good answers list then it returns true ( at least one bad answer has been checked )
     * Otherwise returns false ( no bad answer has been checked )
     */
    public boolean hasBadAnswersChecked(ArrayList<Integer> goodAnswers, ArrayList<CheckBox> checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            int index = checkBoxes.indexOf(checkBox);
            if (checkBox.isChecked() && !goodAnswers.contains(index)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the question has the good answers checked  - and - has NO bad answers
     */
    public boolean isCorrectlyChecked(ArrayList<Integer> goodAnswers, ArrayList<CheckBox> checkBoxes) {
        return hasGoodAnswersChecked(goodAnswers, checkBoxes) && !hasBadAnswersChecked(goodAnswers, checkBoxes);
    }
}
