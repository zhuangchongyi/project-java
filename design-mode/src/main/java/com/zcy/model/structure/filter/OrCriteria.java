package com.zcy.model.structure.filter;

import java.util.List;

public class OrCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Employee> meetCriteria(List<Employee> persons) {
        List<Employee> criteria = this.criteria.meetCriteria(persons);
        List<Employee> otherCriteria = this.otherCriteria.meetCriteria(persons);
        for (Employee employee : otherCriteria) {
            if (!criteria.contains(employee)) {
                criteria.add(employee);
            }
        }
        return criteria;
    }
}
