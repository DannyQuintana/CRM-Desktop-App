package model;

/**
 * The Contact model contains the fields and methods to be used for Contact objects.
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    public Contact(){}

    /**
     * Constructor
     * @param contactId contact ID
     * @param contactName contact name
     * @param email contact email
     */
    public Contact(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    //Getters and setters
    public int getContactId(){
        return contactId;
    }

    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    public String getContactName(){
        return contactName;
    }

    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString(){
        return "ID "+ contactId + ": " + contactName;
    }
}
