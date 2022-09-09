package com.ak.superheros.controllers;

import com.ak.superheros.dao.LocationDao;
import com.ak.superheros.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LocationController{
    @Autowired
    LocationDao locationDao;

    @GetMapping("locations")
    public String displayLocations(Model model){
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(Float.parseFloat(request.getParameter("latitude")));
        location.setLongitude(Float.parseFloat(request.getParameter("longitude")));
        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request){
        locationDao.deleteLocation(Integer.parseInt(request.getParameter("id")));
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model){
        Location location = locationDao.getLocationById(Integer.parseInt(request.getParameter("id")));
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(Float.parseFloat(request.getParameter("latitude")));
        location.setLongitude(Float.parseFloat(request.getParameter("longitude")));
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }
}
