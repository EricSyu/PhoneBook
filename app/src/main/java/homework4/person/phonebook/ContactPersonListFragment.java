package homework4.person.phonebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactPersonListFragment extends Fragment {

    private static ContactPersonListFragment contactPersonListFragment;
    private ListView listView;
    private ContactDB contactDB;
    private MyContactlstAdapter myContactlstAdapter;
    private ViewPager viewPager;

    public ContactPersonListFragment(){

    }

    @SuppressLint("ValidFragment")
    public ContactPersonListFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactDB = new ContactDB(getContext());
        myContactlstAdapter = new MyContactlstAdapter(getContext(), contactDB.getAll());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactperson_list, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(myContactlstAdapter);
        listView.setOnItemLongClickListener(myContactlstAdapter);
        listView.setOnItemClickListener(myContactlstAdapter);
        return view;
    }

    public static ContactPersonListFragment getInstance(ViewPager viewPager){
        if(contactPersonListFragment==null){
            contactPersonListFragment = new ContactPersonListFragment(viewPager);
        }
        return contactPersonListFragment;
    }

    class MyContactlstAdapter extends BaseAdapter implements AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener,
            AdapterView.OnItemClickListener {
        private LayoutInflater myInflater;
        private ArrayList<ContactPerson> items;
        private Bundle b;

        public MyContactlstAdapter(Context c, ArrayList<ContactPerson> items){
            myInflater = LayoutInflater.from(c);
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            items = contactDB.getAll();
            convertView = myInflater.inflate(R.layout.contact_person_list, null);
            TextView textView_name = (TextView) convertView.findViewById(R.id.textView3);
            TextView textView_price = (TextView) convertView.findViewById(R.id.textView4);

            textView_name.setText(items.get(position).getName());
            textView_price.setText(items.get(position).getPhone());

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setAction("call");
            intent.putExtra("PhoneNumber", items.get(position).getPhone());
            getContext().sendBroadcast(intent);
            viewPager.setCurrentItem(0);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("修改/刪除 聯絡人");

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(80,10,50,10);
            final EditText editText_name = new EditText(getContext());
            final EditText editText_phone = new EditText(getContext());
            editText_name.setText(items.get(position).getName());
            editText_phone.setText(items.get(position).getPhone());
            editText_phone.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            linearLayout.addView(editText_name);
            linearLayout.addView(editText_phone);
            dialog.setView(linearLayout);

            dialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (editText_phone.getText().toString().matches("(09)+[\\d]{8}")) {
                        items.get(position).setName(editText_name.getText().toString());
                        items.get(position).setPhone(editText_phone.getText().toString());
                        contactDB.modify(items.get(position));
                        myContactlstAdapter.notifyDataSetInvalidated();
                    } else {
                        Toast.makeText(getContext(), "輸入電話號碼格式錯誤", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.setNeutralButton("刪除", this);

            dialog.setCancelable(false);
            dialog.show();

            b = new Bundle();
            b.putInt("DelectPos", position);
            return true;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_NEUTRAL){
                int position = b.getInt("DelectPos");
                contactDB.delete(items.get(position).getId());
                items.remove(position);
                myContactlstAdapter.notifyDataSetInvalidated();
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
    }

}
