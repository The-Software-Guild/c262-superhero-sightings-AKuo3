package com.ak.superheros;

import com.ak.superheros.dao.LocationDao;
import com.ak.superheros.dao.OrganizationDao;
import com.ak.superheros.dao.SightingDao;
import com.ak.superheros.dao.SuperheroDao;
import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Organization;
import com.ak.superheros.entities.Sighting;
import com.ak.superheros.entities.Superhero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SightingDaoDBTest{
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    SightingDao sightingDao;

    public SightingDaoDBTest(){
    }

    @BeforeEach
    public void setUp(){
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations){
            locationDao.deleteLocation(location.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrgs();
        for(Organization organization : organizations){
            organizationDao.deleteOrg(organization.getId());
        }

        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for(Superhero superhero : superheroes){
            superheroDao.deleteSuperhero(superhero.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings){
            sightingDao.deleteSighting(sighting.getId());
        }
    }

    @Test
    public void testAddAndGetSighting(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocationId(location.getId());
        sighting.setDate(Date.valueOf(LocalDate.now()));
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetSightingByDate(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocationId(location.getId());
        sighting.setDate(Date.valueOf(LocalDate.now()));
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingByDate(Date.valueOf(LocalDate.now()));
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocationId(location.getId());
        sighting.setDate(Date.valueOf(LocalDate.now()));
        sighting = sightingDao.addSighting(sighting);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero Name 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Superhero Power 2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(1.0f);
        location2.setLongitude(2.0f);
        location2 = locationDao.addLocation(location2);

        Sighting sighting2 = new Sighting();
        sighting2.setSuperheroId(superhero2.getId());
        sighting2.setLocationId(location2.getId());
        sighting2.setDate(Date.valueOf(LocalDate.now()));
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocationId(location.getId());
        sighting.setDate(Date.valueOf(LocalDate.now()));
        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);


        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero Name 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Superhero Power 2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(1.0f);
        location2.setLongitude(2.0f);
        location2 = locationDao.addLocation(location2);

        sighting.setSuperheroId(superhero2.getId());
        sighting.setLocationId(location2.getId());
        sightingDao.updateSighting(sighting);
        sighting = sightingDao.getSightingById(sighting.getId());

        assertNotEquals(sighting, fromDao);
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSighting(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        sighting.setLocationId(location.getId());
        sighting.setDate(Date.valueOf(LocalDate.now()));
        sighting = sightingDao.addSighting(sighting);
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSighting(sighting.getId());
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }
}
