package com.example.nautaconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Conectado extends AppCompatActivity {

    TextView wifi, datos;
    Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectado);

        wifi = findViewById(R.id.editEstadoWifi);
        datos = findViewById(R.id.editEstadoDatos);
        salir = findViewById(R.id.buttonExit);

        Bundle extras = getIntent().getExtras();
        if (extras.getBoolean("CONECTADO_WIFI")){
            wifi.setText(R.string.conectadowifi);
            datos.setText(R.string.noconectadodatos);
        }
        if (extras.getBoolean("CONECTADO_DATOS")){
            wifi.setText(R.string.noconectadowifi);
            datos.setText(R.string.conectadodatos);
        }

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        });
    }
}