package com.example.wilsonpreschool.Domain;

public class Programs {
    private int Id;
    private String Prog;

    public Programs() {
    }

    @Override
    public String toString() {
        return Prog;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProg() {
        return Prog;
    }

    public void setProg(String prog) {
        Prog = prog;
    }
}
