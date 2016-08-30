package homework4.person.phonebook;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private ContactDB contactDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        contactDB = new ContactDB(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("新增聯絡人");

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(80,10,50,10);
            final EditText editText_name = new EditText(this);
            final EditText editText_phone = new EditText(this);
            editText_name.setHint("請輸入聯絡人名稱");
            editText_phone.setHint("請輸入電話號碼");
            editText_phone.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            linearLayout.addView(editText_name);
            linearLayout.addView(editText_phone);
            dialog.setView(linearLayout);

            dialog.setPositiveButton("新增", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (editText_phone.getText().toString().matches("(09)+[\\d]{8}")) {
                        ContactPerson contactPerson = new ContactPerson(editText_name.getText().toString(), editText_phone.getText().toString());
                        contactDB.insert(contactPerson);
                    } else {
                        Toast.makeText(MainActivity.this, "輸入電話號碼格式錯誤", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog.setCancelable(false);
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if(position == 0){
                fragment = CallFragment.getInstance();
            }
            else if (position == 1){
                fragment = ContactPersonListFragment.getInstance(mViewPager);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "電話";
                case 1:
                    return "聯絡人";
            }
            return null;
        }
    }
}
