package com.ak.superheros.dao;

import com.ak.superheros.entities.Organization;
import com.ak.superheros.entities.Superhero;

import java.util.List;

public interface OrganizationDao{
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    Organization addOrg(Organization org);
    void updateOrg(Organization org);
    void deleteOrg(int id);
    List<Organization> getOrgsForSuperhero(Superhero superhero);
}
