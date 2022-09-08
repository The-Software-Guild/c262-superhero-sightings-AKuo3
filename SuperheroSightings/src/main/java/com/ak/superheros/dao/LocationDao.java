package com.ak.superheros.dao;

import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Superhero;

import java.util.List;

public interface LocationDao{
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location Location);
    void updateLocation(Location Location);
    void deleteLocation(int id);
    List<Location> getLocationsForSuperhero(Superhero superhero);
}
