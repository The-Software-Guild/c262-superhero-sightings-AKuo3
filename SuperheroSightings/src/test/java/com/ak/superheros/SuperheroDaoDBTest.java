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
public class SuperheroDaoDBTest{
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    SightingDao sightingDao;

    public SuperheroDaoDBTest(){
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
    public void testAddAndGetSuperhero(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    public void testGetAllSuperheroes(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero Name 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Superhero Power 2");
        superhero2 = superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = superheroDao.getAllSuperheroes();

        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }

    @Test
    public void testUpdateSuperhero(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superhero.setName("Test Superhero Name 2");
        superhero.setDescription("Test Superhero Description 2");
        superhero.setPower("Test Superhero Power 2");
        superheroDao.updateSuperhero(superhero);
        superhero = superheroDao.getSuperheroById(superhero.getId());
        assertNotEquals(superhero, fromDao);

        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    public void testDeleteSuperhero(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);

        superheroDao.deleteSuperhero(superhero.getId());
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetSuperheroesFromLocation(){
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
        sightingDao.addSighting(sighting);

        List<Superhero> superheroes = superheroDao.getSuperheroesFromLocation(location);
        assertTrue(superheroes.contains(superhero));
    }

    @Test
    public void testGetSuperheroesFromOrg(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero Name");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Superhero Power");
        superhero = superheroDao.addSuperhero(superhero);

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);

        Organization org = new Organization();
        org.setName("Test Org");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setMembers(superheroes);
        org = organizationDao.addOrg(org);

        superheroes = superheroDao.getSuperheroesFromOrg(org);

        assertTrue(superheroes.contains(superhero));
    }
}
