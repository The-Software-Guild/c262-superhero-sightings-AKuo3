package com.ak.superheros.dao;

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
public class OrganizationDaoDB implements OrganizationDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrgById(int id){
        try{
            final String GET_ORG_BY_ID = "SELECT * FROM organization WHERE id = ?";
            return jdbc.queryForObject(GET_ORG_BY_ID, new OrganizationMapper(), id);
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrgs(){
        final String GET_ALL_ORGS = "SELECT * FROM organization";
        return jdbc.query(GET_ALL_ORGS, new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organization addOrg(Organization org){
        final String INSERT_ORG = "INSERT INTO organization(name, description, address) VALUES (?, ?, ?)";
        jdbc.update(INSERT_ORG, org.getName(), org.getDescription(), org.getAddress());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        return org;
    }

    @Override
    public void updateOrg(Organization org){
        final String UPDATE_ORG = "UPDATE organization SET name = ?, description = ?, address = ? WHERE id = ?";
        jdbc.update(UPDATE_ORG, org.getName(), org.getDescription(), org.getAddress(), org.getId());
    }

    @Override
    @Transactional
    public void deleteOrg(int id){
        final String DELETE_SUPERHERO_ORGS = "DELETE FROM superhero_org WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO_ORGS, id);

        final String DELETE_ORG = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    public List<Organization> getOrgsForSuperhero(Superhero superhero){
        final String SELECT_ORGS_FOR_SUPERHERO = "SELECT o.* FROM organization o JOIN " +
                "superhero_org so ON so.orgId = o.id WHERE so.superheroId = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGS_FOR_SUPERHERO, new OrganizationMapper(),
                superhero.getId());
        return orgs;
    }

    public static final class OrganizationMapper implements RowMapper<Organization>{
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException{
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            return org;
        }
    }
}
