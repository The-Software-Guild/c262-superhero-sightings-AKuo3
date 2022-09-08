package com.ak.superheros.entities;

import java.util.List;
import java.util.Objects;

public class Organization{
    private int id;
    private String name;
    private String description;
    private String address;
    private List<Superhero> members;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public List<Superhero> getMembers(){
        return members;
    }

    public void setMembers(List<Superhero> members){
        this.members = members;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Organization that = (Organization) o;
        return id == that.id && name.equals(that.name) && Objects.equals(description, that.description) && address.equals(that.address) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, name, description, address, members);
    }
}
