package androdev.sqlitetut.model;

/**
 * Created by Administrator on 21-Mar-16.
 */
public class Model {
    private int id;
    private String name;
    private String address;
    private String date_birth;
    private String gender;

    //constructor
    public Model(){

    }

    public Model(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Model(String name, String address, String date_birth, String gender){
        this.name = name;
        this.address = address;
        this.date_birth = date_birth;
        this.gender = gender;
    }

    public Model(int id, String name, String address, String date_birth, String gender){
        this.id = id;
        this.name = name;
        this.address = address;
        this.date_birth = date_birth;
        this.gender = gender;
    }

    public Model(String name, String address){
        this.name = name;
        this.address = address;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
