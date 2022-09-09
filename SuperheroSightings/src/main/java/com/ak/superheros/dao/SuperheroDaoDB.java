package com.ak.superheros.dao;

import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Organization;
import com.ak.superheros.entities.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SuperheroDaoDB implements SuperheroDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero getSuperheroById(int id){
        try{
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            return jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Superhero> getAllSuperheroes(){
        final String GET_ALL_SUPERHEROES = "SELECT * FROM superhero";
        return jdbc.query(GET_ALL_SUPERHEROES, new SuperheroMapper());
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero){
        final String ADD_SUPERHERO = "INSERT INTO superhero(name, description, power) values (?, ?, ?)";
        jdbc.update(ADD_SUPERHERO, superhero.getName(), superhero.getDescription(), superhero.getPower());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        return superhero;
    }

    @Override
    public void updateSuperhero(Superhero superhero){
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ?, power = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO, superhero.getName(), superhero.getDescription(), superhero.getPower(), superhero.getId());
    }

    @Override
    @Transactional
    public void deleteSuperhero(int id){
        final String DELETE_SIGHTINGS = "DELETE FROM sighting WHERE superheroId = ?";
        jdbc.update(DELETE_SIGHTINGS, id);

        final String DELETE_ORGS = "DELETE FROM superhero_org WHERE superheroId = ?";
        jdbc.update(DELETE_ORGS, id);

        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    @Override
    public List<Superhero> getSuperheroesFromLocation(Location location){
        final String SELECT_SUPERHEROES_FROM_LOCATION = "SELECT s.* FROM superhero s JOIN " +
                "sighting s ON s.superheroId = s.id WHERE s.locationId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FROM_LOCATION, new SuperheroMapper(), location.getId());
        return superheroes;
    }

    @Override
    public List<Superhero> getSuperheroesFromOrg(Organization organization){
        final String SELECT_SUPERHEROES_FROM_ORG = "SELECT s.* FROM superhero s JOIN " +
                "superhero_org so ON so.superheroId = s.id WHERE so.orgId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FROM_ORG, new SuperheroMapper(), organization.getId());
        return superheroes;
    }

    public static final class SuperheroMapper implements RowMapper<Superhero>{
        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException{
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            superhero.setPower(rs.getString("power"));
            return superhero;
        }
    }
}
