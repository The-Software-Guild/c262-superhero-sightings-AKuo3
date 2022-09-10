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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrganizationDaoDBTest{
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    SightingDao sightingDao;

    public OrganizationDaoDBTest(){
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
    public void testAddAndGetOrganization(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Power");
        superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Power 2");
        superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        superheroes.add(superhero2);

        Organization org = new Organization();
        org.setName("Test Org");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setMembers(superheroes);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }

    @Test
    public void testGetAllOrganizations(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Power");
        superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Power 2");
        superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        superheroes.add(superhero2);

        Organization org = new Organization();
        org.setName("Test Org");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setMembers(superheroes);
        org = organizationDao.addOrg(org);

        Organization org2 = new Organization();
        org2.setName("Test Org 2");
        org2.setDescription("Test Org Description 2");
        org2.setAddress("Test Org Address 2");
        org2.setMembers(superheroes);
        org2 = organizationDao.addOrg(org2);

        List<Organization> organizations = organizationDao.getAllOrgs();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(org));
        assertTrue(organizations.contains(org2));
    }

    @Test
    public void testUpdateOrganization(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Power");
        superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Power 2");
        superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        superheroes.add(superhero2);

        Organization org = new Organization();
        org.setName("Test Org");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setMembers(superheroes);
        org = organizationDao.addOrg(org);
        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org, fromDao);

        org.setName("Test Org 2");
        org.setDescription("Test Org Description 2");
        org.setAddress("Test Org Address 2");
        organizationDao.updateOrg(org);
        org = organizationDao.getOrgById(org.getId());
        assertNotEquals(org, fromDao);

        fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }

    @Test
    public void testDeleteOrganization(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Power");
        superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Power 2");
        superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        superheroes.add(superhero2);

        Organization org = new Organization();
        org.setName("Test Org");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setMembers(superheroes);
        org = organizationDao.addOrg(org);

        organizationDao.deleteOrg(org.getId());
        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertNull(fromDao);

        List<Superhero> superheroes1 = superheroDao.getAllSuperheroes();
        assertEquals(2, superheroes1.size());
    }

    @Test
    public void testGetOrgsForSuperhero(){
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero");
        superhero.setDescription("Test Superhero Description");
        superhero.setPower("Test Power");
        superheroDao.addSuperhero(superhero);

        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero 2");
        superhero2.setDescription("Test Superhero Description 2");
        superhero2.setPower("Test Power 2");
        superheroDao.addSuperhero(superhero2);

        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(superhero);
        superheroes.add(superhero2);

        Organization org = new Organization();
        org.setName("Test Org");
        org.setDescription("Test Org Description");
        org.setAddress("Test Org Address");
        org.setMembers(superheroes);
        org = organizationDao.addOrg(org);

        superheroes.remove(superhero2);

        Organization org2 = new Organization();
        org2.setName("Test Org 2");
        org2.setDescription("Test Org Description 2");
        org2.setAddress("Test Org Address 2");
        org2.setMembers(superheroes);
        org2 = organizationDao.addOrg(org2);

        List<Organization> organizations = organizationDao.getOrgsForSuperhero(superhero);
        List<Organization> organizations2 = organizationDao.getOrgsForSuperhero(superhero2);

        assertEquals(2, organizations.size());
        assertEquals(1, organizations2.size());
    }
}
