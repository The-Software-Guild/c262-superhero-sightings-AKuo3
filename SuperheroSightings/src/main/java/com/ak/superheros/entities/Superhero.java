package com.ak.superheros.entities;

import java.util.Objects;

public class Superhero{
    private int id;
    private String name;
    private String description;
    private String power;

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

    public String getPower(){
        return power;
    }

    public void setPower(String power){
        this.power = power;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Superhero superhero = (Superhero) o;
        return id == superhero.id && name.equals(superhero.name) && Objects.equals(description, superhero.description) && power.equals(superhero.power);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, name, description, power);
    }
}
