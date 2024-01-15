package com.nataliasep.listadelacompra;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nataliasep.listadelacompra.Adapter.ListsAdapter;
import com.nataliasep.listadelacompra.Interfaces.IOnClickListener;
import com.nataliasep.listadelacompra.Models.ShoppingList;
import com.nataliasep.listadelacompra.dao.ShoppingListDAO;
import com.nataliasep.listadelacompra.sqlite.ShoppingListSQLiteHelper;

import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity implements IOnClickListener {

    private ListsAdapter listsAdapter;
    private SQLiteDatabase db;
    private ShoppingListDAO shoppingListDAO;
    private List<ShoppingList> shoppingLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        db = ShoppingListSQLiteHelper.getInstance(this).getWritableDatabase();
        if (db == null) {
            Log.e(getClass().getSimpleName(), "Cannot connect to database");
            finish();
        }
        shoppingListDAO = new ShoppingListDAO(db);
        shoppingLists = shoppingListDAO.findAll();
        RecyclerView rvList = findViewById(R.id.rvList);
        listsAdapter = new ListsAdapter(this, shoppingLists);
        rvList.setAdapter(listsAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        FloatingActionButton bAction = findViewById(R.id.bAction3);
        bAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });


    }

    private void showDialog() {
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
                long id = shoppingListDAO.insert(newList);
                newList = shoppingListDAO.findById(id);
                shoppingLists.add(newList);
                listsAdapter.refresh();
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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, ItemListActivity.class);
        intent.putExtra("id", shoppingLists.get(position).getId());
        startActivity(intent);

    }
}
