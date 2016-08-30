package homework4.person.phonebook;

/**
 * Created by WEI-ZHE on 2016/4/7.
 */
public class ContactPerson {

    private long id;
    private String name;
    private String phone;

    public ContactPerson(){

    }

    public ContactPerson(String n, String p){
        name = n;
        phone = p;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
