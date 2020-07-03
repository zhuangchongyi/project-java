package com.zcy.model.structure.combination;

/**
 * @Author zhuangchongyi
 * @Description 组合模式
 * 组合模式是结构型模式，因为它创建了一组对象的树结构。
 * 组合模式将一组对象视为单个对象。
 * 组合模式使用一个类来表示树结构。
 * 在组合模式中，我们创建一个包含自己对象的类的组
 * @Date 2020/7/2 15:13
 */
public class Main {
    public static void main(String[] args) {
        Employee CEO = new Employee("John","CEO");
        Employee headSales = new Employee("Rob","Sales");
        Employee headMarketing = new Employee("Mike","Marketing");
        Employee programmer1 = new Employee("Lili","Programmer");
        Employee programmer2 = new Employee("Bob","Programmer");
        Employee tester1 = new Employee("Jack","Tester");
        Employee tester2 = new Employee("Tom","Tester");

        CEO.add(headSales);
        CEO.add(headMarketing);
        headSales.add(tester1);
        headSales.add(tester2);
        headMarketing.add(programmer1);
        headMarketing.add(programmer2);

        System.out.println(CEO);
        for (Employee employee : CEO.getSubordinates()) {
            System.out.println(employee);
            for (Employee subordinate : employee.getSubordinates()) {
                System.out.println(subordinate);
            }
        }

    }
}
