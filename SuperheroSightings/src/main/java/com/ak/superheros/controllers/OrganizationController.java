package com.ak.superheros.controllers;

import com.ak.superheros.dao.OrganizationDao;
import com.ak.superheros.dao.SuperheroDao;
import com.ak.superheros.entities.Organization;
import com.ak.superheros.entities.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController{
    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperheroDao superheroDao;

    @GetMapping("organizations")
    public String displayOrgs(Model model){
        List<Organization> organizations = organizationDao.getAllOrgs();
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        model.addAttribute("organizations", organizations);
        model.addAttribute("superheroes", superheroes);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrg(Organization org, HttpServletRequest request){
        String[] superheroIds = request.getParameterValues("superheroId");
        List<Superhero> superheroes = new ArrayList<>();
        for(String id : superheroIds){
            superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(id)));
        }
        org.setMembers(superheroes);
        organizationDao.addOrg(org);

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String orgDetail(Integer id, Model model){
        Organization org = organizationDao.getOrgById(id);
        model.addAttribute("organization", org);
        return "organizationDetail";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrg(Integer id){
        organizationDao.deleteOrg(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrg(Integer id, Model model){
        Organization org = organizationDao.getOrgById(id);
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        model.addAttribute("organization", org);
        model.addAttribute("superheroes", superheroes);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String editOrg(Organization org, HttpServletRequest request){
        String[] superheroIds = request.getParameterValues("superheroId");
        List<Superhero> superheroes = new ArrayList<>();
        for(String id : superheroIds){
            superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(id)));
        }
        org.setMembers(superheroes);
        organizationDao.updateOrg(org);

        return "redirect:/organizations";
    }
}
