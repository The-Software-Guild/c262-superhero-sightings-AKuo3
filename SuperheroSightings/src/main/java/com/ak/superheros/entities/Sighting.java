package com.ak.superheros.entities;

import java.sql.Date;
import java.util.Objects;

public class Sighting{
    private int id;
    private int superheroId;
    private int locationId;
    private Date date;
    private Superhero superhero;
    private Location location;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getSuperheroId(){
        return superheroId;
    }

    public void setSuperheroId(int superheroId){
        this.superheroId = superheroId;
    }

    public int getLocationId(){
        return locationId;
    }

    public void setLocationId(int locationId){
        this.locationId = locationId;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Superhero getSuperhero(){
        return superhero;
    }

    public void setSuperhero(Superhero superhero){
        this.superhero = superhero;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        Sighting sighting = (Sighting) o;
        return id == sighting.id && superheroId == sighting.superheroId && locationId == sighting.locationId && date.equals(sighting.date);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, superheroId, locationId, date);
    }

}
