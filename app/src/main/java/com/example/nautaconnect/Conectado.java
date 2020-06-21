package com.example.nautaconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Conectado extends AppCompatActivity {

    TextView wifi, datos, msg;
    ImageView iWifi, iDatos;
    Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectado);

        wifi = findViewById(R.id.editEstadoWifi);
        datos = findViewById(R.id.editEstadoDatos);
        salir = findViewById(R.id.buttonExit);
        iWifi = findViewById(R.id.imageWifi);
        iDatos = findViewById(R.id.imageDatos);
        msg = findViewById(R.id.textView345);

        Bundle extras = getIntent().getExtras();
        if (extras.getBoolean("CONECTADO_WIFI")){
            wifi.setText(R.string.conectadowifi);
            iWifi.setImageResource(R.drawable.ic_wifi_ok_24);
            datos.setText(R.string.noconectadodatos);
            iDatos.setImageResource(R.drawable.ic_mobile_off_24);
            msg.setText(R.string.yaconectadomsg);
        }
        if (extras.getBoolean("CONECTADO_DATOS")){
            wifi.setText(R.string.noconectadowifi);
            iWifi.setImageResource(R.drawable.ic_wifi_off_24);
            datos.setText(R.string.conectadodatos);
            iDatos.setImageResource(R.drawable.ic_mobile_ok_24);
            msg.setText(R.string.yaconectadomsg);
        }

        if (!extras.getBoolean("CONECTADO_WIFI") && !extras.getBoolean("CONECTADO_DATOS")){
            wifi.setText(R.string.noconectadowifi);
            iWifi.setImageResource(R.drawable.ic_wifi_off_24);
            datos.setText(R.string.noconectadodatos);
            iDatos.setImageResource(R.drawable.ic_mobile_off_24);
            msg.setText(R.string.sinConexion);
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