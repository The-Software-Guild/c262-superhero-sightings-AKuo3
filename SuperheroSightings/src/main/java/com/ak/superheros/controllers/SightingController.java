package com.ak.superheros.controllers;

import com.ak.superheros.dao.LocationDao;
import com.ak.superheros.dao.SightingDao;
import com.ak.superheros.dao.SuperheroDao;
import com.ak.superheros.entities.Location;
import com.ak.superheros.entities.Sighting;
import com.ak.superheros.entities.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SightingController{
    @Autowired
    SightingDao sightingDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperheroDao superheroDao;

    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locationDao.getAllLocations();
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request){
        String locationId = request.getParameter("locationId");
        String superheroId = request.getParameter("superheroId");
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model){
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id){
        sightingDao.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editCourse(Integer id, Model model){
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locationDao.getAllLocations();
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("superheroes", superheroes);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditCourse(Sighting sighting, HttpServletRequest request){
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");

        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }
}
