package com.veris.verisimagenes.Activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Util.Routes;

import java.io.File;

public class LectorPDFActivity extends AppCompatActivity {

    private PDFView pdfView;
    private String data;
    private File file;

    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_p_d_f);
        init();
    }

    public void init(){

        getSupportActionBar().setTitle("Atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        data = bundle.getString("data");
        nombre = bundle.getString("nombre");


        String uri = Uri.decode(data);

        armaPDF(uri);

    }

    public void armaPDF(String uri){

     //   pdfView.fromStream()


    }

    public void deleteFiles(Context context) {
        File rootFolder = context.getFilesDir();
        File fileDir = new File(nombre);
        if (fileDir.exists()) {
            File[] listFiles = fileDir.listFiles();
            for (File listFile : listFiles) {
                if (!listFile.delete()) {
                    System.err.println( "Unable to delete file: " + listFile );
                }
            }
        }
        rootFolder.delete();
        Routes.goToAgenda(LectorPDFActivity.this);
    }

    @Override
    public void onBackPressed() {
        deleteFiles(LectorPDFActivity.this);
        super.onBackPressed();
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        deleteFiles(LectorPDFActivity.this);

        return super.getParentActivityIntent();
    }


}