package homework4.person.phonebook;

import android.content.Intent;
import android.telecom.Call;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

import junit.framework.TestCase;

/**
 * Created by Eric on 2016/5/4.
 */
public class CallFragmentTest extends ActivityUnitTestCase {

    public CallFragmentTest(){
        super(CallFragment.class);
    }

    public void testGetInstance() throws Exception {
        CallFragment callFragment = CallFragment.getInstance();
        assertNotNull(callFragment);
    }

//    public void testBtn(){
//        Button btn = (Button) getActivity().findViewById(R.id.button2);
//        EditText e = (EditText) getActivity().findViewById(R.id.editText);
//        btn.performClick();
//        assertEquals("1", e.getText().toString());
//    }


}