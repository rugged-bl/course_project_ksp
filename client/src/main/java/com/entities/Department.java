package com.entities;

public class Department {
    public Department(String title, String address) {
        this.title = title;
        this.address = address;
    }

    private String title;
    private String address;

    //relates to clients and accounts

    private Long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                '}';
    }
}
