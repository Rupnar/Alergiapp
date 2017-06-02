package com.example.monitor.osjelanji;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button entrar, alta;
    EditText usuario,contraseña;
    private static final String url = "jdbc:mysql://192.168.1.111:3306/app";
    private static final String user = "root";
    private static final String pass = "monitor2016";
    public String NombreUsuario;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent intent1 = new Intent(MainActivity.this, activity_usuario.class);
                startActivity(intent1);
                return true;
            case R.id.scannermenu:
                Intent intent = new Intent(MainActivity.this, activity_scanner.class);
                startActivity(intent);
                return true;
            case R.id.cerrarsesion:
                finish();
                Intent intent4 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent4);
                return true;
            case R.id.avisolegal:
                Intent intent2 = new Intent(MainActivity.this, activity_avisolegal.class);
                startActivity(intent2);
                return true;
            case R.id.map:
                Intent intent3 = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById (R.id.contraseña);
        entrar = (Button) findViewById(R.id.entrar);
        alta = (Button) findViewById(R.id.alta);


        entrar.setOnClickListener(new View.OnClickListener(){

                public void onClick (View v) {
                    new MyTask().execute();


                }

        });

        alta.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {

                Intent intent = new Intent(MainActivity.this, activity_usuario.class);
                startActivity(intent);

            }
        });

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private String mensaje;
        private String nombredb="", passworddb="";
        String nombreLogin = usuario.getText().toString();
        String passwordLogin = contraseña.getText().toString();


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Log.v("Mysql", "carga correcta del driver");
                Connection con = (Connection) DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                String sql = ("SELECT * FROM usuarios where email = '" + nombreLogin + "' AND contraseña = '" + passwordLogin + "' ; ");
                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                String nombredb = rs.getString(1);
                String passworddb = rs.getString(4);

                if (passworddb.equals(passwordLogin) && nombredb.equals(nombreLogin)) {
                    mensaje = "Login Correcto";
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
                mensaje = "Login Incorrecto";
            }

            return null;
        }
        @Override
        protected void onPostExecute (Void result){
            contraseña.setText(nombredb);
            usuario.setText(passworddb);
            Alerta(mensaje);

            if (mensaje.equals("Login Correcto")) {
                Intent intent = new Intent(MainActivity.this, activity_scanner.class);
                intent.putExtra("email", nombreLogin);
                startActivity(intent);
            }

            super.onPostExecute(result);
        }
    }


    public void Alerta(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.show();
    }


}
