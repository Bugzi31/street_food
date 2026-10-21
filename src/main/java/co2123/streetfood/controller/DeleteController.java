package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.*;
import co2123.streetfood.repository.AwardRepository;
import co2123.streetfood.repository.DishRepository;
import co2123.streetfood.repository.PhotoRepository;
import co2123.streetfood.repository.ReviewRepository;
import co2123.streetfood.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteController
{
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AwardRepository awardRepository;


    /*public DeleteController(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }*/

    @RequestMapping("/deleteVendor")
    public String deleteVendor(@RequestParam("id") Integer id)
    {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        if (vendor == null)
        {
            return "redirect:/admin";
        }

        for (Dish d : vendor.getDishes())
        {
            if (d.getReviews() != null)
            {
                d.getReviews().clear();
            }
        }
        vendor.getDishes().clear();
        vendor.getAwards().clear();
        vendor.getPhotos().clear();
        vendorRepository.delete(vendor);

        return "redirect:/admin";
    }


    @RequestMapping("/deleteDish")
    public String deleteDish(@RequestParam Integer vendorid, @RequestParam Integer dishid)
    {

        Vendor vendor = vendorRepository.findById(vendorid).orElse(null);
        Dish dish = dishRepository.findById(dishid).orElse(null);

        if (vendor == null || dish == null)
        {
            return "redirect:/admin";
        }

        vendor.getDishes().remove(dish);
        dishRepository.delete(dish);

        return "redirect:/vendor?id=" + vendorid;
    }


    @RequestMapping("/deleteReview")
    public String deleteReview(@RequestParam Integer vendorId, @RequestParam Integer reviewId)
    {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null)
        {
            reviewRepository.delete(review);
        }

        return "redirect:/vendor?id=" + vendorId;
    }



    @RequestMapping("/deletePhoto")
    public String deletePhoto(@RequestParam Integer photoId)
    {
        Photo foundPhoto = photoRepository.findById(photoId).orElse(null);
        if (foundPhoto == null)
        {
            return "redirect:/admin";
        }
        Integer vendorId = foundPhoto.getVendor().getId();
        photoRepository.delete(foundPhoto);

        return "redirect:/vendor?id=" + vendorId;
    }

   // private final AwardRepository awardRepository;
    @RequestMapping("/deleteAward")
    public String deleteAward(@RequestParam Integer awardId)
    {
        Award foundAward = awardRepository.findById(awardId).orElse(null);

        if(foundAward == null)
        {
            return "redirect:/admin";
        }

        Integer vendorId = foundAward.getVendor().getId();
        awardRepository.delete(foundAward);
        return "redirect:/vendor?id=" + vendorId;
    }

}
