package com.ak.superheros.dao;

import com.ak.superheros.entities.Sighting;

import java.sql.Date;
import java.util.List;

public interface SightingDao{
    Sighting getSightingByDate(Date date);
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    void addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSighting(int id);
}
