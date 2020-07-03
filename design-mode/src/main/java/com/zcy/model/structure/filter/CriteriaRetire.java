package com.zcy.model.structure.filter;

import java.util.ArrayList;
import java.util.List;

public class CriteriaRetire implements Criteria {
    @Override
    public List<Employee> meetCriteria(List<Employee> persons) {
        List<Employee> singlePersons = new ArrayList<Employee>();
        for (Employee person : persons) {
            if ("YES".equalsIgnoreCase(person.getRetireStatus())) {
                singlePersons.add(person);
            }
        }
        return singlePersons;
    }
}
