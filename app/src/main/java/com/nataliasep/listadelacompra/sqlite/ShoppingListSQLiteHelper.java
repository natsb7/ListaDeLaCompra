package com.nataliasep.listadelacompra.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class ShoppingListSQLiteHelper extends SQLiteOpenHelper {
    private static ShoppingListSQLiteHelper sInstance;
    private static final String DB_NAME = "shoppingList.db";
    private static final int DB_VERSION = 1;

    private static final String sqlCreateUser = "CREATE TABLE user (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL); ";

    private static final String slqCreateCategory = "CREATE TABLE category (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "img TEXT); ";

    private static final String sqlCreateItem = "CREATE TABLE item (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "img TEXT, " +
            "category INTEGER NOT NULL, " +
            "FOREIGN KEY (category) REFERENCES category(id)); ";

    private static final String sqlCreateList =  "CREATE TABLE list (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "date DATETIME DEFAULT CURRENT_TIMESTAMP); ";

    private static final String slqCreateItemList = "CREATE TABLE item_list (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "fk_item INTEGER NOT NULL, " +
            "fk_list INTEGER NOT NULL, " +
            "is_purchased INTEGER DEFAULT 0, " +
            "FOREIGN KEY (fk_item) REFERENCES item(id), " +
            "FOREIGN KEY (fk_list) REFERENCES list(id)); ";


    private static final String sqlInsertUsers;

    static {
        sqlInsertUsers = "INSERT INTO user (username) " +
                            "VALUES ('natalia');";
    }

    private static final String sqlInsertCategory;

    static {
        sqlInsertCategory = "INSERT INTO category (name, img) " +
                            "VALUES ('frutas', 'frutas.jpg'), ('verduras', 'verduras.jpg'), " +
                            "('proteínas', 'proteinas.jpg'), ('cereales', 'cereales.jpg');";
    }

    private static final String sqlInsertItem;

    static {
        sqlInsertItem = "INSERT INTO item (name, img, category) " +
                            "VALUES ('manzana', 'manzana.jpg', 1), ('pera', 'pera.jpg', 1), ('plátano', 'platano.jpg', 1), ('naranja', 'naranja.jpg', 1)," +
                            " ('zanahoria', 'zanahoria.jpg', 2), ('lechuga', 'lechuga.jpg', 2), ('col_lombarda', 'col.jpg', 2), ('pepino', 'pepino.jpg', 2), " +
                            " ('pollo', 'pollo.jpg', 3), ('salmon', 'salmon.jpg', 3), ('leche', 'leche.jpg', 3), ('huevos', 'huevos.jpg', 3), " +
                            " ('pan', 'pan.jpg', 4), ('arroz', 'arroz.jpg', 4), ('pasta', 'pasta.jpg', 4), ('avena', 'avena.jpg', 4);";
    }


    public static synchronized ShoppingListSQLiteHelper getInstance(Context context) {
        if (sInstance == null) {
            // Usamos el contexto de la aplicación para asegurarnos que no se pierde
            // el contexto, por ejemplo de una Activity.
            sInstance = new ShoppingListSQLiteHelper(context.getApplicationContext());
        }
        return  sInstance;
    }

    // Definimos el constructor privado para asegurarnos que no lo utilice nadie desde fuera
    // Así forzamos a utilizar getInstance()
    private ShoppingListSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Este método sólo se ejecuta si la base de datos no existe
        db.execSQL(sqlCreateUser);
        db.execSQL(slqCreateCategory);
        db.execSQL(sqlCreateItem);
        db.execSQL(sqlCreateList);
        db.execSQL(slqCreateItemList);

        // Creamos un usuario por defecto
        db.execSQL(sqlInsertUsers);
        db.execSQL(sqlInsertCategory);
        db.execSQL(sqlInsertItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí irán las sentencias de actualización de la base de datos
    }
}
