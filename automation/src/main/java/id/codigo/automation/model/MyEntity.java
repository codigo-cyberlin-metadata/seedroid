package id.codigo.automation.model;

/**
 * Created by papahnakal on 27/02/18.
 */

public class MyEntity {
    private int id; // not letting javac to inline 0 since it's primitive
    private String name;
    @Override
    public String toString() {
        return id + "=>" + name;
    }

}
