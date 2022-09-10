package com.ak.superheros.dao;

import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Sighting;
import com.ak.superheros.entities.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDao{
    @Autowired
    JdbcTemplate jdbc;

    //TODO: fix, returns null
    @Override
    public Sighting getSightingByDate(Date date){
        try{
            final String GET_SIGHTING_BY_DATE = "SELECT * FROM sighting WHERE date = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_DATE, new SightingMapper(), date);
            sighting.setSuperhero(associateIdWithSuperhero(sighting.getSuperheroId()));
            sighting.setLocation(associateIdWithLocation(sighting.getLocationId()));
            return sighting;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Sighting getSightingById(int id){
        try{
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperhero(associateIdWithSuperhero(sighting.getSuperheroId()));
            sighting.setLocation(associateIdWithLocation(sighting.getLocationId()));
            return sighting;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings(){
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        for(Sighting sighting : sightings){
            sighting.setSuperhero(associateIdWithSuperhero(sighting.getSuperheroId()));
            sighting.setLocation(associateIdWithLocation(sighting.getLocationId()));
        }
        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting){
        final String ADD_SIGHTING = "INSERT INTO sighting (superheroId, locationId, sightingDate) VALUES (?, ?, ?)";
        jdbc.update(ADD_SIGHTING, sighting.getSuperheroId(), sighting.getLocationId(), sighting.getDate());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting){
        final String UPDATE_SIGHTING = "UPDATE sighting SET superheroId = ?, locationId = ?, sightingDate = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING, sighting.getSuperheroId(), sighting.getLocationId(), sighting.getDate(), sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSighting(int id){
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    private Superhero associateIdWithSuperhero(int id){
        try{
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            return jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroDaoDB.SuperheroMapper(), id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    private Location associateIdWithLocation(int id){
        try{
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationDaoDB.LocationMapper(), id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    private static final class SightingMapper implements RowMapper<Sighting>{
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException{
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setSuperheroId(rs.getInt("superheroId"));
            sighting.setLocationId(rs.getInt("locationId"));
            sighting.setDate(rs.getDate("sightingDate"));
            return sighting;
        }
    }
}
