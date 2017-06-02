package com.example.monitor.osjelanji;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Registro extends AppCompatActivity implements View.OnClickListener {

    ImageButton camara;
    ImageView imagen;
    Intent i;
    Bitmap bmp;
    final static int cons = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent intent1 = new Intent(Registro.this, activity_usuario.class);
                startActivity(intent1);
                return true;
            case R.id.scannermenu:
                Intent intent = new Intent(Registro.this, activity_scanner.class);
                startActivity(intent);
                return true;
            case R.id.cerrarsesion:
                finish();
                Intent intent4 = new Intent(Registro.this, MainActivity.class);
                startActivity(intent4);
                return true;
            case R.id.avisolegal:
                Intent intent2 = new Intent(Registro.this, activity_avisolegal.class);
                startActivity(intent2);
                return true;
            case R.id.map:
                Intent intent3 = new Intent(Registro.this, MapsActivity.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        init();

    }
    public void init() {
        camara = (ImageButton) findViewById(R.id.camara);
        camara.setOnClickListener(this);

        imagen = (ImageView) findViewById(R.id.imagen);
        imagen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id;
        id = v.getId();
        switch (id) {
            case R.id.camara:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cons);
                break;
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) ;
        {
            Bundle ext = data.getExtras();
            bmp = (Bitmap) ext.get("data");
            imagen.setImageBitmap(bmp);
        }

    }

}
