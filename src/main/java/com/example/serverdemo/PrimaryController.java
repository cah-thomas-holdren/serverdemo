package com.example.serverdemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrimaryController {

    @GetMapping("/listing")
    public String listing(@RequestParam(name="name", required=false, defaultValue="User") String userName,
                          @RequestParam(name="productName", required=false, defaultValue="All") String productName,
                          Model model) {


        model.addAttribute("name", userName);

        model.addAttribute("productName", productName);

        return "listing";
    }

    @GetMapping("/products")
    public String products(Model model) {
        ProductBunch products = getProducts();

        model.addAttribute("products", products.getProducts());

        return "/products";
    }


    /**
     * Get a random number of products between 1 and 10.
     * URL is set to the Chuck Norris jokes API database.
     */
    private ProductBunch getProducts() {
        URL url;
        try {
            url = new URL("http://api.icndb.com/jokes/random?exclude=explicit");
        } catch (MalformedURLException e) {
            System.out.println("Issue accessing the URL. Error message: " + e.getMessage());
            throw new RuntimeException("Cannot access products at this time.\n");
        }
        Random random = new Random();
        int numProducts = random.nextInt(10) + 1;
        return new ProductBunch(numProducts, url);
    }

}
