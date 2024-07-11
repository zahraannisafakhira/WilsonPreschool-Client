package com.example.wilsonpreschool;

public class DataClass {

    private String name;
    private String gender;
    private String birth;
    private String address;
    private String parent;
    private String home;
    private String parents;
    private String key;

    public DataClass() {
        // Default constructor required for calls to DataSnapshot.getValue(DataClass.class)
    }

    public DataClass(String name, String gender, String birth, String address, String parent, String home, String parents) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.parent = parent;
        this.home = home;
        this.parents = parents;
    }

    // Getters and setters for all fields
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
