package com.abdilahstudio.firebasecrud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.snackbar.Snackbar;

public class DBCreateActivity extends AppCompatActivity {

    private static final String TAG = "DBCreateActivity";
    private DatabaseReference database;
    private Button btSubmit;
    private EditText etNik;
    private EditText etNama;
    private Spinner etJa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreate);

        etNik = findViewById(R.id.nik);
        etNama = findViewById(R.id.nama_dosen);
        etJa = findViewById(R.id.spinnerJA);
        btSubmit = findViewById(R.id.bt_submit);

        database = FirebaseDatabase.getInstance().getReference();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(etNik.getText().toString()) && !isEmpty(etNama.getText().toString())) {
                    submitDosen(new Dosen(etNik.getText().toString(), etNama.getText().toString(), etJa.getSelectedItem().toString(), database.push().getKey()));
                } else {
                    Toast.makeText( DBCreateActivity.this, "Data Dosen tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void submitDosen(Dosen dosen) {
        Log.d(TAG, "Submit dosen...");
        Log.d(TAG, "Data dosen : " + dosen.toString());
        database.child("dosens").child(dosen.getKey()).setValue(dosen)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText( DBCreateActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        etNik.setText("");
                        etNama.setText("");
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error ", e);
                        Toast.makeText( DBCreateActivity.this, "Data gagal ditambahkan: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, DBCreateActivity.class);
    }
}
