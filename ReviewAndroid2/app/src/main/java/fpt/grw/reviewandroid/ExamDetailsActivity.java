package fpt.grw.reviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import entities.ExamDetail;

public class ExamDetailsActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("exam_date");

        EditText inputName = findViewById(R.id.inputNameDetail);
        inputName.setText(name);

        Button btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(view -> {
            TextView inputPicURL = findViewById(R.id.inputPictureURL);
            ImageView imageView = findViewById(R.id.imageView);
            DownloadImageTask task = new DownloadImageTask(mProgressDialog,this,imageView);
            task.execute(inputPicURL.getText().toString());
        });
        Button btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(view -> {
            EditText inputQuestion = findViewById(R.id.inputQuestion);
            TextView inputPicURL = findViewById(R.id.inputPictureURL);
            int exam_id = intent.getIntExtra("id",0);
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            long detail_id = dbHelper.insertDetail(exam_id,inputQuestion.getText().toString(),
                    inputPicURL.getText().toString());
            Toast.makeText(this, String.valueOf(detail_id), Toast.LENGTH_SHORT).show();
        });

        Button btnViewAll = findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(view -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            int examId = intent.getIntExtra("id",0);
            List<ExamDetail> examDetails = dbHelper.getExamDetails(examId);
            //ArrayAdapter<ExamDetail> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1 ,examDetails);
            MyCustomAdapter adapter = new MyCustomAdapter(this,examDetails);

            ListView listView = findViewById(R.id.detail_listView);
            listView.setAdapter(adapter);
        });
    }
}