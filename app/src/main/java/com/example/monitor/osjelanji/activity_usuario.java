package com.example.monitor.osjelanji;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class activity_usuario extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.1.111:3306/app";
    private static final String user = "root";
    private static final String pass = "monitor2016";
    private EditText nombre, password, email, apellido;
    private CheckBox[] alergias_seleccionades=new CheckBox[15];
    Button enviar;
    Toast toast;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.scannermenu:
                Intent intent = new Intent(activity_usuario.this, activity_scanner.class);
                startActivity(intent);
                return true;
            case R.id.cerrarsesion:
                finish();
                Intent intent4 = new Intent(activity_usuario.this, MainActivity.class);
                startActivity(intent4);
                return true;
            case R.id.avisolegal:
                Intent intent2 = new Intent(activity_usuario.this, activity_avisolegal.class);
                startActivity(intent2);
                return true;
            case R.id.map:
                Intent intent3 = new Intent(activity_usuario.this, MapsActivity.class);
                startActivity(intent3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        nombre = (EditText) findViewById(R.id.editNombre);
        apellido = (EditText) findViewById(R.id.editApellidos);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editContraseña);
        alergias_seleccionades[0]=(CheckBox) findViewById(R.id.gluten);
        alergias_seleccionades[1]=(CheckBox) findViewById(R.id.crustaceos);
        alergias_seleccionades[2]=(CheckBox) findViewById(R.id.huevos);
        alergias_seleccionades[3]=(CheckBox) findViewById(R.id.pescado);
        alergias_seleccionades[4]=(CheckBox) findViewById(R.id.cacahuetes);
        alergias_seleccionades[5]=(CheckBox) findViewById(R.id.soja);
        alergias_seleccionades[6]=(CheckBox) findViewById(R.id.leche);
        alergias_seleccionades[7]=(CheckBox) findViewById(R.id.frutos);
        alergias_seleccionades[8]=(CheckBox) findViewById(R.id.apio);
        alergias_seleccionades[9]=(CheckBox) findViewById(R.id.mostaza);
        alergias_seleccionades[10]=(CheckBox) findViewById(R.id.sesamo);
        alergias_seleccionades[11]=(CheckBox) findViewById(R.id.azufre);
        alergias_seleccionades[12]=(CheckBox) findViewById(R.id.altramuces);
        alergias_seleccionades[13]=(CheckBox) findViewById(R.id.molusco);
        alergias_seleccionades[14]=(CheckBox) findViewById(R.id.noAlergias);

       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        enviar = (Button) findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                new MyTask().execute();
                Intent intent = new Intent(activity_usuario.this, activity_scanner.class);
                intent.putExtra("email", email.getText());
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private class MyTask extends AsyncTask {

        String fNombre = nombre.getText().toString();
        String fPassword = password.getText().toString();
        String fEMail = email.getText().toString();
        String fapellido = apellido.getText().toString();
        Connection con = null;
        Statement st = null;

        @Override
        protected Void doInBackground(Object[] params) {

            try{

                Class.forName("com.mysql.jdbc.Driver");
                Log.v("Mysql","carga correcta del driver");
                con = DriverManager.getConnection(url, user, pass);

                //CAMPOS VACIOS
                if (fNombre.length() == 0 || fPassword.length() == 0 || fEMail.length() == 0 || fapellido.length() == 0){
                    System.out.println("Ha dejado campos vacios");
                    //Toast.makeText(activity_usuario.this, "Ha dejado campos vacios",Toast.LENGTH_LONG).show();
                    return  null;
                }

                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                int resultado = st.executeUpdate("INSERT INTO usuarios VALUES ('"+fEMail+"','"+fNombre+"','"+fapellido+"','"+fPassword+"')");

                //INGRESO INCORRECTO
                if(resultado>0){
                    prueba();
                    System.out.println("INGRESO CORRECTO");
                    //Toast.makeText(activity_usuario.this, "INGRESO CORRECTO",Toast.LENGTH_LONG).show();
                }

            } catch (ClassNotFoundException e) {

                e.printStackTrace();

            } catch (SQLException e) {
                //EXCEPTION EMAIL IGUAL
                if (e.getErrorCode() == 1062){
                    e.printStackTrace();
                    System.out.println("EMAIL REPETIDO");
                    //Toast.makeText(activity_usuario.this, "EMAIL REPETIDO",Toast.LENGTH_LONG).show();
                    return null;
                }

            } finally {
                //CERRAR CONEXION
                if(con != null){
                    try {
                        st.close();
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;

            }


        }

        private void prueba() {
            try {
                Statement auxQuery;
                for (int i = 0; i < alergias_seleccionades.length; i++) {
                    if (alergias_seleccionades[i].isChecked()) {
                        st = con.createStatement();
                        auxQuery = con.createStatement();
                        auxQuery.executeQuery("SELECT\n" +
                                "    idalergenos\n" +
                                "FROM\n" +
                                "    app.alergenos where nombre_alergias='" + alergias_seleccionades[i].getText() + "'");
                        ResultSet rs = auxQuery.getResultSet();
                        while (rs.next()) {
                            int codigo = rs.getInt("idalergenos");
                            st.executeUpdate("insert into usuario_alergias values('" + fEMail + "'," + codigo + ")");

                        }
                        // rs.close();

                    }

                }

            } catch (SQLException e) {
                String s = e.getMessage();
            }
        }
    }



}
