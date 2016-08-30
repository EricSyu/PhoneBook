package homework4.person.phonebook;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class CallFragment extends Fragment implements View.OnClickListener {

    private final static String CALL = "android.intent.action.CALL";

    private static CallFragment callFragment;

    private EditText editText;
    private ImageButton imgbtn_delete;
    private Button btn_num[], btn_symbol1, btn_symbol2, btn_call;

    private UIReceiver receiver;

    private int btn_num_id[] = {
            R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.button10, R.id.button12
    };

    public CallFragment(){

    }

    public static CallFragment getInstance(){
        if(callFragment==null){
            callFragment = new CallFragment();
        }
        return callFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerBroadcastReceiver();
    }

    public void registerBroadcastReceiver(){
        receiver = new UIReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("call");
        getContext().registerReceiver(receiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        editText = (EditText) view.findViewById(R.id.editText);
        imgbtn_delete = (ImageButton) view.findViewById(R.id.button);

        btn_num = new Button[10];
        for(int i=0; i<10; i++){
            btn_num[i] = (Button) view.findViewById(btn_num_id[i]);
            btn_num[i].setOnClickListener(this);
        }
        btn_symbol1 = (Button) view.findViewById(R.id.button11);
        btn_symbol2 = (Button) view.findViewById(R.id.button13);
        btn_call = (Button) view.findViewById(R.id.button14);

        imgbtn_delete.setOnClickListener(this);
        btn_symbol1.setOnClickListener(this);
        btn_symbol2.setOnClickListener(this);
        btn_call.setOnClickListener(this);

        editText.setEnabled(false);
        editText.setFocusable(false);
        return view;
    }

    public EditText getEditText() {
        return editText;
    }

    @Override
    public void onClick(View v) {
        for(int i=0; i<9; i++){
            if(v.getId() == btn_num_id[i]){
                editText.append(++i+"");
            }
        }

        if(v.getId() == R.id.button12){
            editText.append(0+"");
        }
        else if(v.getId() == R.id.button11){
            editText.append("*");
        }
        else if(v.getId() == R.id.button13){
            editText.append("#");
        }
        else if(v.getId() == R.id.button){
            String s = editText.getText().toString();
            if(!s.equals(""))
                editText.setText(s.substring(0, s.length()-1));
        }
        else if(v.getId() == R.id.button14){
            try {
                Intent call = new Intent(CALL, Uri.parse("tel:" + editText.getText().toString()));
                startActivity(call);
            }catch (Exception e){
                Toast.makeText(getContext(), "請打開此APP的電話權限", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }

    public class UIReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String s = bundle.getString("PhoneNumber");
            editText.setText(s);
        }
    }
}
