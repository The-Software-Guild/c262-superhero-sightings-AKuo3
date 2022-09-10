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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class LocationDaoDBTest{
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoDBTest(){
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
    public void testAddAndGetLocation(){
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);

        location = locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocations(){
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(3.0f);
        location2.setLongitude(4.0f);
        locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation(){
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(location.getId());

        location.setName("Test Location Name 2");
        location.setDescription("Test Location Description 2");
        location.setAddress("Test Location Address 2");
        location.setLatitude(3.0f);
        location.setLongitude(4.0f);
        locationDao.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocation(){
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(1.0f);
        location.setLongitude(2.0f);
        locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        locationDao.deleteLocation(location.getId());
        fromDao = locationDao.getLocationById(location.getId());

        assertNull(fromDao);
    }
}
