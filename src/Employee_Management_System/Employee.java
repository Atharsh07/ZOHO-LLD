package Employee_Management_System;
import java.util.*;
public class Employee {

    private int id;
    private String name;
    private Employee manager;
    private List<Employee> subordinates;

    public Employee(int id, String name){
        this.id = id;
        this.name = name;
        this.subordinates = new ArrayList<>();
    }

    //setters
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
    }

    //getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Employee getManager() {
        return manager;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + '\'' +
                ", manager=" + (manager != null ? manager.getName() : "None") + '}';
    }

}
