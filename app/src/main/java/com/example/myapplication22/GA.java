package com.example.myapplication22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication22.Models.Notes;
import com.kyanogen.signatureview.SignatureView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import yuku.ambilwarna.AmbilWarnaDialog;

public class GA extends AppCompatActivity {

    SignatureView signatureView;
    ImageButton imgEraser,imgColor;
    SeekBar seekBar;
    TextView txtpensize;
    ImageView imageView_save;
    Notes notes;
    boolean isOldNote = false;

    EditText editText_title, editText_notes;

    int defaultcolor;

    private static String filename;
    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZAMETKII");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        signatureView = findViewById(R.id.signature_view);
        imgColor= findViewById(R.id.btnColor);
        imgEraser=findViewById(R.id.btnEraser);
        seekBar=findViewById(R.id.penSize);
        txtpensize=findViewById(R.id.TxtPenSize);
        imageView_save=findViewById(R.id.imageView_save);
        editText_title=findViewById(R.id.editText_title);
        editText_notes=findViewById(R.id.editText_notes);
        notes = new Notes();
        try {


            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote = true;


        } catch (Exception e) {
            e.printStackTrace();


        }




        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_title.getText().toString();
                String description = notes.toString();
                if (description.isEmpty()) {
                    Toast.makeText(GA.this, "Please, enter description", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date date = new Date();

                if (!isOldNote) {
                    notes = new Notes();
                }
                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",notes);
                setResult(Activity.RESULT_OK,intent);
                finish();




            }
        });






        defaultcolor= ContextCompat.getColor(GA.this,R.color.black);

        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = 1;
                txtpensize.setText(i+"dp");
                signatureView.setPenSize(i);
                txtpensize.setText(String.valueOf(seekBar.getProgress()));

                seekBar.setMax(100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        imgEraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { signatureView.clearCanvas();

            }
        });
    }
    private void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultcolor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultcolor=color;
                signatureView.setPenColor(color);

            }
        });
        ambilWarnaDialog.show();
    }

}

