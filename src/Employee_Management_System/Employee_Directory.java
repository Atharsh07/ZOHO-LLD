package Employee_Management_System;

import java.util.*;

public class Employee_Directory {
    private Map<Integer, Employee> idMap = new HashMap<>();
    private Map<String, List<Employee>> nameMap = new HashMap<>();

    public void addEmployee(int id, String name, Integer managerId){
        Employee employee = new Employee(id, name);
        idMap.put(id, employee);
        // Store by name (to support prefix search)
        nameMap.computeIfAbsent(name.toLowerCase(), k -> new ArrayList<>()).add(employee);
        // Link with manager if exists
        if(managerId != null && idMap.containsKey(managerId)){
            Employee manager = idMap.get(managerId);
            employee.setManager(manager);
            manager.addSubordinate(employee);
        }
    }

    public Employee getEmployeeById(int id) {
        return idMap.get(id);
    }

    public List<Employee> getSubordinates(int id) {
        Employee e = idMap.get(id);
        List<Employee> result = new ArrayList<>();
        if (e != null) {
            collectSubordinates(e, result);
        }
        return result;
    }

    private void collectSubordinates(Employee emp, List<Employee> result) {
        for (Employee sub : emp.getSubordinates()) {
            result.add(sub);
            collectSubordinates(sub, result); // recursive
        }
    }

    public List<Employee> searchByPrefix(String prefix) {
        List<Employee> result = new ArrayList<>();
        prefix = prefix.toLowerCase();
        for (String key : nameMap.keySet()) {
            if (key.startsWith(prefix)) {
                result.addAll(nameMap.get(key));
            }
        }
        return result;
    }

}
