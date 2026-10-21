package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.Vendor;
import co2123.streetfood.repository.VendorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller


public class AdminController
{
    @Autowired
    private VendorRepository vendorRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {

        binder.addValidators(new VendorValidator(vendorRepository));
    }

    @RequestMapping("/admin")
    public String showAdminPage(Model model)
    {
        model.addAttribute("vendors", vendorRepository.findAll());

        return "admin";
    }


    @RequestMapping("/newVendor")
    public String newVendor(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "forms/newVendor";
    }


    @RequestMapping("/addVendor")
    public String addVendor(@Valid @ModelAttribute Vendor vendor, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "forms/newVendor";
        }

        vendorRepository.save(vendor);
        return "redirect:/admin";
    }

    @RequestMapping("/vendor")
    public String listVendor(@RequestParam Integer id, Model model)
    {

        Vendor foundVendor = vendorRepository.findById(id).orElse(null);

        if (foundVendor == null)
        {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        return "vendorSummary";
    }


}
