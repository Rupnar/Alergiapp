package com.example.monitor.osjelanji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class activity_avisolegal extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_avisolegal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent intent1 = new Intent(activity_avisolegal.this, activity_usuario.class);
                startActivity(intent1);
                return true;
            case R.id.cerrarsesion:
                finish();
                Intent intent4 = new Intent(activity_avisolegal.this, MainActivity.class);
                startActivity(intent4);
                return true;
            case R.id.scanner:
                Intent intent = new Intent(activity_avisolegal.this, activity_scanner.class);
                startActivity(intent);
                return true;
            case R.id.map:
                Intent intent3 = new Intent(activity_avisolegal.this, MapsActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisolegal);
      ;


    }


}
