package homework4.person.phonebook;

import android.test.ActivityUnitTestCase;

import junit.framework.TestCase;

/**
 * Created by Eric on 2016/5/4.
 */
public class ContactPersonListFragmentTest extends ActivityUnitTestCase {

    public ContactPersonListFragmentTest() {
        super(ContactPersonListFragment.class);
    }

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testGetInstance() throws Exception {
        ContactPersonListFragment contactPersonListFragment = ContactPersonListFragment.getInstance(null);
        assertNotNull(contactPersonListFragment);
    }
}