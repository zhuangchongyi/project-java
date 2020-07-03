package com.zcy.model.structure.filter;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author zhuangchongyi
 * @Description 过滤器/标准模式
 * 过滤器模式使用不同的条件过滤对象。
 * 这些标准可以通过逻辑操作链接在一起。
 * 过滤器模式是一种结构型模式
 *
 * 感觉用Stream做过滤会不会改好
 * @Date 2020/7/2 15:03
 */
public class Main {
    public static void main(String[] args) {
        List<Employee> persons = new ArrayList<Employee>();
        persons.add(new Employee("Tom", "MALE", "YES"));
        persons.add(new Employee("Jack", "MALE", "NO"));
        persons.add(new Employee("Jane", "FEMALE", "NO"));
        persons.add(new Employee("Diana", "FEMALE", "YES"));
        persons.add(new Employee("Mike", "MALE", "NO"));
        persons.add(new Employee("Bob", "MALE", "YES"));

        Criteria male = new CriteriaMale();
        Criteria female = new CriteriaFemale();
        Criteria retire = new CriteriaRetire();

        AndCriteria andCriteria = new AndCriteria(retire, male);
        OrCriteria orCriteria = new OrCriteria(retire, female);

        andCriteria.meetCriteria(persons);
        orCriteria.meetCriteria(persons);

        System.out.println("Males: ");
        printPersons(male.meetCriteria(persons));
        System.out.println("Females: ");
        printPersons(female.meetCriteria(persons));
        System.out.println("Retire: ");
        printPersons(retire.meetCriteria(persons));
    }

    private static void printPersons(List<Employee> meetCriteria) {
        for (Employee employee : meetCriteria) {
            System.out.println(employee.toString());
        }
    }
}
