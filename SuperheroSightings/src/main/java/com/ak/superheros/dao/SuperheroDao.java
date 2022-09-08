package com.ak.superheros.dao;

import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Organization;
import com.ak.superheros.entities.Superhero;

import java.util.List;

public interface SuperheroDao{
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperheroes();
    Superhero addSuperhero(Superhero superhero);
    void updateSuperhero(Superhero superhero);
    void deleteSuperhero(int id);
    List<Superhero> getSuperheroesFromLocation(Location location);
    List<Superhero> getSuperheroesFromOrg(Organization organization);
}
