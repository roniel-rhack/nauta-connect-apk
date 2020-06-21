package com.example.nautaconnect;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    EditText edit_username, edit_password;
    TextView errorsTextView;
    public User my_user;
    public boolean user_passw_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_connect = findViewById(R.id.buttonConnect);
        edit_username = findViewById(R.id.editUsername);
        edit_password = findViewById(R.id.editPassword);
        errorsTextView = findViewById(R.id.errorTextView);

        user_passw_error = false;

        this.my_user = User.getUser();
        loadUser();
        if (my_user.getUsername()!="prp"){
            edit_username.setText(my_user.getUsername());
            edit_password.setText(my_user.getPassword());
        }else{
            edit_username.setText("");
            edit_password.setText("");
        }

        //TODO: Activar esta opcion (si no existe otra) para comprobar si ya esta conectado por WIFI
        boolean isConectedWifi = false;//isConnectedWifi(this);
        boolean isConnectedDatos = isConnectedMobile(this);

        if (isConectedWifi || isConnectedDatos ){
            Intent intent = new Intent(this, Conectado.class);
            intent.putExtra("CONECTADO_WIFI", isConectedWifi);
            intent.putExtra("CONECTADO_DATOS", isConnectedDatos);
            startActivity(intent);
        }
        if (!isAvailableWifi(this)){
            Intent intent = new Intent(this, Conectado.class);
            intent.putExtra("CONECTADO_WIFI", false);
            intent.putExtra("CONECTADO_DATOS", false);
            startActivity(intent);
        }


        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    sendConnect();
            }
        });
    }

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isAvailable() && isOnlineNet();
    }

    public boolean isAvailableWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isAvailable();
    }

    public boolean isConnectedMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public void loadUser(){
        String data = "";

        try{
            FileInputStream fis = openFileInput("dataNautaConnect.dat");
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            data = new String(buffer);
            if (data!=""){
                Gson gson = new Gson();
                User new_user = gson.fromJson(data, User.class);
                my_user.setUsername(new_user.getUsername());
                my_user.setPassword(new_user.getPassword());
                Toast.makeText(MainActivity.this, "Usuario cargado !", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "No se cargaron usuarios", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendConnect()  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    String str = "td";
                    String str2 = "";
                    String format = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime());
                    Response execute = Jsoup.connect("https://secure.etecsa.net:8443").method(Method.GET).execute();

                    my_user.setCSRFHW(execute.parse().select("input[name=CSRFHW]").first().val());
                    my_user.setUsername(edit_username.getText().toString());
                    my_user.setPassword(edit_password.getText().toString());

                    Document post = Jsoup.connect("https://secure.etecsa.net:8443/EtecsaQueryServlet").cookies(execute.cookies())
                            .data("wlanacname", str2).data("wlanmac", str2).data("firsturl", "notFound.jsp")
                            .data("ssid", str2).data("usertype", str2).data("gotopage", "/nauta_etecsa/LoginURL/mobile_login.jsp")
                            .data("successpage", "/nauta_etecsa/OnlineURL/mobile_index.jsp")
                            .data("loggerId", format).data("lang", "es_ES").data("username", my_user.getUsername())
                            .data("password", my_user.getPassword())
                            .data("CSRFHW", my_user.getCSRFHW()).followRedirects(true).post();
                    if(!post.select("script").last().toString().contains("alert(\"return null\");")){
                        my_user.setSaldoCuenta(post.select("table#sessioninfo > tbody > tr > td").get(3).text());
                        my_user.setEstadoCuenta(post.select("table#sessioninfo > tbody > tr > td").get(1).text());

                        Document loggin = Jsoup.connect("https://secure.etecsa.net:8443/LoginServlet")
                                .data("username", my_user.getUsername()).data("password", my_user.getPassword()).followRedirects(true).post();

                        my_user.setATTRIBUTE_UUID(loggin.select("script").first().toString().split("ATTRIBUTE_UUID=")[1].split("&")[0]);

                        System.out.println(my_user);
                        sendMessage(null);
                    }else{
                        user_passw_error = true;
                    }


                }catch (Exception e){
                    System.out.println(e.getMessage());
                    builder.append("error: ").append(e.getMessage()).append("\n");


                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(builder.toString());
                        if (user_passw_error){
                            errorsTextView.setText(R.string.user_passw_error);
                            edit_password.setText("");
                            edit_username.setText("");
                        }
                    }
                });
            }
        }).start();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DatosActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}