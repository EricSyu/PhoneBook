package homework4.person.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactDB {

    public static final String TABLE_NAME = "Contact";

    public static final String KEY_ID = "_ID";
    public static final String Column_Name = "Name";
    public static final String Column_Phone = "Phone";

    private SQLiteDatabase db;

    public ContactDB(Context context){
        db = DBHelper.getDatabase(context);
    }

    public void close(){
        db.close();
    }

    public ContactPerson insert(ContactPerson item){
        ContentValues cv = new ContentValues();

        cv.put(Column_Name, item.getName());
        cv.put(Column_Phone, item.getPhone());

        long id = db.insert(TABLE_NAME, null, cv);

        item.setId(id);

        return item;
    }

    public void modify(ContactPerson item){

        ContentValues values = new ContentValues();
        values.put(Column_Name, item.getName());
        values.put(Column_Phone, item.getPhone());

        db.update(TABLE_NAME, values, "_ID=" + item.getId(), null);
    }

    public ArrayList<ContactPerson> getAll() {
        ArrayList<ContactPerson> result = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public ContactPerson getRecord(Cursor cursor) {
        ContactPerson result = new ContactPerson();
        result.setId(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setPhone(cursor.getString(2));
        return result;
    }
}
