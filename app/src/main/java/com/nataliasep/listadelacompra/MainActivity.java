package com.nataliasep.listadelacompra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nataliasep.listadelacompra.Models.ShoppingList;
import com.nataliasep.listadelacompra.dao.ShoppingListDAO;
import com.nataliasep.listadelacompra.sqlite.ShoppingListSQLiteHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ShoppingListDAO shoppingListDAO;
    private List<ShoppingList> shoppingLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, ListActivity.class);

        db = ShoppingListSQLiteHelper.getInstance(this).getWritableDatabase();
        if (db == null) {
            Toast.makeText(this, "Error al conectar con la BD", Toast.LENGTH_SHORT).show();
            finish();
        }
        shoppingListDAO = new ShoppingListDAO(db);
        shoppingLists = shoppingListDAO.findAll();

        if (shoppingLists.isEmpty()) {
            setContentView(R.layout.nolist_layout);
            FloatingActionButton bAction = findViewById(R.id.bAction);
            bAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(i);

                }
            });
        } else {
            //setContentView(R.layout.activity_main);
            startActivity(i);
        }
    }

    private void showDialog(Intent i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.editText);
        Button btnCrear = dialogView.findViewById(R.id.btnCrear);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

        final AlertDialog dialog = builder.create();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listName = editText.getText().toString();
                ShoppingList newList = new ShoppingList(listName);
                shoppingListDAO.insert(newList);
                shoppingLists.add(newList);
                startActivity(i);
                dialog.dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}