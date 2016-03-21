package androdev.sqlitetut;

/**
 * Created by Administrator on 21-Mar-16.
 */
public class Model {
    private int id;
    private String name;
    private String address;

    //constructor
    public Model(){

    }

    public Model(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
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

}
