package com.ak.superheros.dao;

import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LocationDaoDB implements LocationDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id){
        try{
            final String GET_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations(){
        final String GET_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location){
        final String ADD_LOCATION = "INSERT INTO location(name, description, address, latitude, longitude " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(ADD_LOCATION, location.getName(), location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location){
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, address = ?, latitude = ?, longitude = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION, location.getName(), location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude(), location.getId());
    }

    @Override
    @Transactional
    public void deleteLocation(int id){
        final String DELETE_SIGHTINGS = "DELETE from sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTINGS, id);

        final String DELETE_LOCATION = "DELETE from location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsForSuperhero(Superhero superhero){
        final String SELECT_LOCATIONS_FOR_SUPERHERO = "SELECT l.* FROM location l JOIN " +
                "sighting s ON s.locationId = l.id WHERE s.superheroId = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_SUPERHERO, new LocationMapper(),
                superhero.getId());
        return locations;
    }

    public static final class LocationMapper implements RowMapper<Location>{
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException{
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getFloat("latitude"));
            location.setLongitude(rs.getFloat("longitude"));
            return location;
        }
    }
}
