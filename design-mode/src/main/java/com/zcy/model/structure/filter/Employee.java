package com.zcy.model.structure.filter;

public class Employee {
    private String name;
    private String gender;
    private String retireStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRetireStatus() {
        return retireStatus;
    }

    public void setRetireStatus(String retireStatus) {
        this.retireStatus = retireStatus;
    }

    public Employee(String name, String gender, String retireStatus) {
        this.name = name;
        this.gender = gender;
        this.retireStatus = retireStatus;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", retireStatus='" + retireStatus + '\'' +
                '}';
    }
}
