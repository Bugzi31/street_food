package co2123.streetfood.controller;

import co2123.streetfood.model.Vendor;
import co2123.streetfood.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Controller
public class VendorValidator implements Validator
{

    @Autowired
    private VendorRepository vendorRepository;

    public VendorValidator(VendorRepository vendorRepository) {this.vendorRepository = vendorRepository;}

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Vendor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Vendor vendor = (Vendor) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "",
                "Your vendor must have a name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "",
                "Your vendor must have a location");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cuisineType", "",
                "Your vendor must have a cuisine type");

        if (vendor.getCuisineType() != null && vendor.getCuisineType().length() > 50)
        {
            errors.rejectValue("cuisineType", "", "Cuisine type must be 50 characters or less");
        }

        for (Vendor a : vendorRepository.findAll())
        {
//            if (vendor.getId() != 0 && a.getId() == vendor.getId())
//            {
//                continue;
//            }
            if (a.getName().equals(vendor.getName()))
            {
                errors.rejectValue("name", "", "Vendor name already exists.");
                break;
            }
        }
    }
}
