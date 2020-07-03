package com.zcy.model.structure.filter;

import java.util.List;

public interface Criteria {
    List<Employee> meetCriteria(List<Employee> persons);
}
