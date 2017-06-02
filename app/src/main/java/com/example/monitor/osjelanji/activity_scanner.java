package com.example.monitor.osjelanji;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class activity_scanner extends AppCompatActivity {

    private ImageButton button;
    private static final String url = "jdbc:mysql://192.168.1.111:3306/app";
    private static final String user = "root";
    private static final String pass = "monitor2016";
    private String codigobarra;
    private String email ;
    Boolean resultado = false;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_scanner, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent intent1 = new Intent(activity_scanner.this, activity_usuario.class);
                startActivity(intent1);
                return true;
            case R.id.cerrarsesion:
                finish();
                Intent intent4 = new Intent(activity_scanner.this, MainActivity.class);
                startActivity(intent4);
                return true;
            case R.id.avisolegal:
                Intent intent = new Intent(activity_scanner.this, activity_avisolegal.class);
                startActivity(intent);
                return true;
            case R.id.map:
                Intent intent3 = new Intent(activity_scanner.this, MapsActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        button = (ImageButton) this.findViewById(R.id.button);

        email = getIntent().getExtras().getString("email");
        final Activity activity = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                String saludo = "Hola mundo";
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Codigo no encontrado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_scanner.this, Registro.class);
                startActivity(intent);

            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                codigobarra = result.getContents();
                new activity_scanner.MyTask().execute();

                if (resultado == false) {
                    Intent intent = new Intent(activity_scanner.this, siPuedes.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(activity_scanner.this, noPuedes.class);
                    startActivity(intent);
                }


            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class MyTask extends AsyncTask<Object, Object, Boolean> {

        @Override
        protected Boolean doInBackground(Object... params) {

            try {

                Class.forName("com.mysql.jdbc.Driver");
                Log.v("Mysql", "carga correcta del driver");
                Connection con = (Connection) DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                 resultado = st.execute("SELECT usuarios.email,usuario_alergias.codigo_alergia\n" +
                        "            FROM app.usuario_alergias,alimentos_alergias,app.alimentos,usuarios\n" +
                        "            where usuario_alergias.codigo_alergia=alimentos_alergias.codigo_alergia\n" +
                        "            and alimentos.codigo_barras=alimentos_alergias.cb_alimentos and usuarios.email=usuario_alergias.email_usuario\n" +
                        "            and alimentos.codigo_barras='" + codigobarra + "' and usuarios.email='" + email + "'");
           /*     if (resultado == false) {
                    Intent intent = new Intent(activity_scanner.this, siPuedes.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(activity_scanner.this, noPuedes.class);
                    startActivity(intent);
                }*/

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

          /*  SELECT usuarios.email,usuario_alergias.codigo_alergia
            FROM app.usuario_alergias,alimentos_alergias,app.alimentos,usuarios
            where usuario_alergias.codigo_alergia=alimentos_alergias.codigo_alergia
            and alimentos.codigo_barras=alimentos_alergias.cb_alimentos and usuarios.email=usuario_alergias.email_usuario
            and alimentos.codigo_barras=8076802085738 and usuarios.email='angelamsanchez89@gmail.com';*/
            return resultado;
        }

    }

}
