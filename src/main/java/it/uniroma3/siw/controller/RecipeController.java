package it.uniroma3.siw.controller;


import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;
    
    @Autowired
    UserService userService;
    
    @Autowired
    CredentialsService credentialsService;

    

    @GetMapping("/recipes")
    public String allRecipes(Model model){
        model.addAttribute("recipes", this.recipeRepository.findAll());
        UserDetails userDetails = this.userService.getUserDetails();
        
        if(userDetails != null && this.credentialsService.getCredentials(userDetails.getUsername()).getRole().equals(Credentials.ADMIN_ROLE)){
			model.addAttribute("admin", true);
		}
        
        return "recipes.html";
    }

   
}
