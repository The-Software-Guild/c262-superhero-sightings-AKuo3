package com.ak.superheros.controllers;

import com.ak.superheros.dao.SuperheroDao;
import com.ak.superheros.entities.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperheroController{

    @Autowired
    SuperheroDao superheroDao;

    @GetMapping("superheroes")
    public String displaySuperheroes(Model model){
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        model.addAttribute("superheroes", superheroes);
        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(HttpServletRequest request){
        Superhero superhero = new Superhero();
        superhero.setName(request.getParameter("name"));
        superhero.setDescription(request.getParameter("description"));
        superhero.setPower(request.getParameter("power"));
        superheroDao.addSuperhero(superhero);
        return "redirect:/superheroes";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superheroDao.deleteSuperhero(id);

        return "redirect:/superheroes";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", superhero);
        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performEditSuperhero(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        superhero.setName(request.getParameter("name"));
        superhero.setDescription(request.getParameter("description"));
        superhero.setPower(request.getParameter("power"));
        superheroDao.updateSuperhero(superhero);

        return "redirect:/superheroes";
    }
}
