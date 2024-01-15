package com.nataliasep.listadelacompra;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nataliasep.listadelacompra.Adapter.CategoryAdapter;
import com.nataliasep.listadelacompra.Adapter.ItemAdapter;
import com.nataliasep.listadelacompra.Adapter.ItemListAdapter;
import com.nataliasep.listadelacompra.Adapter.ListsAdapter;
import com.nataliasep.listadelacompra.Interfaces.IOnClickListener;
import com.nataliasep.listadelacompra.Models.Category;
import com.nataliasep.listadelacompra.Models.ItemList;
import com.nataliasep.listadelacompra.Models.ShoppingList;
import com.nataliasep.listadelacompra.dao.CategoryDAO;
import com.nataliasep.listadelacompra.dao.ShoppingListDAO;
import com.nataliasep.listadelacompra.sqlite.ShoppingListSQLiteHelper;

import java.util.List;

public class ItemListActivity extends AppCompatActivity implements IOnClickListener {

    private ItemListAdapter ItemListAdapter;
    private ItemAdapter itemAdapter;

    private CategoryAdapter categoryAdapter;
    private SQLiteDatabase db;
    private ShoppingListDAO shoppingListDAO;
    private CategoryDAO categoryDAO;
    private List<ItemList> itemLists;
    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        db = ShoppingListSQLiteHelper.getInstance(this).getWritableDatabase();
        if (db == null) {
            finish();
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            int listId = intent.getIntExtra("id", -1);
            shoppingListDAO = new ShoppingListDAO(db);
            itemLists = shoppingListDAO.getProductsFromShoppingList(listId);
            rvList = findViewById(R.id.rvList);
            ItemListAdapter = new ItemListAdapter(itemLists);
            rvList.setAdapter(ItemListAdapter);
            rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } else {
            finish();
        }

        FloatingActionButton bAction = findViewById(R.id.bAction3);
        bAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Category> categories = categoryDAO.findAll();
                categoryDAO = new CategoryDAO(db);
                rvList = findViewById(R.id.rvList);
                categoryAdapter = new CategoryAdapter(categories, this);

            }
        });


    }

    @Override
    public void onClick(int position) {

    }
}
