package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.*;
import co2123.streetfood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Controller
public class AddEditController {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AwardRepository awardRepository;


    private Vendor findVendor(Integer id) {
        if (id == null) return null;
        return vendorRepository.findById(id).orElse(null);
    }


    @RequestMapping("editVendor")
    public String editVendorForm(@RequestParam Integer id, Model model) {
        Vendor vendor = findVendor(id);
        if (vendor == null) return "redirect:/admin";

        model.addAttribute("vendor", vendor);
        return "forms/editVendor";
    }


    @RequestMapping("editedVendor")
    public String editedVendor(@RequestParam Integer id,
                               @ModelAttribute Vendor updatedVendor) {

        Vendor vendor = findVendor(id);
        if (vendor == null) return "redirect:/admin";

        vendor.setName(updatedVendor.getName());
        vendor.setLocation(updatedVendor.getLocation());
        vendor.setCuisineType(updatedVendor.getCuisineType());

        vendorRepository.save(vendor);
        return "redirect:/vendor?id=" + id;
    }


    @RequestMapping("editVendorProfile")
    public String editVendorProfileForm(@RequestParam Integer id, Model model) {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        if (vendor == null) return "redirect:/admin";

        VendorProfile profile = vendor.getProfile();
        if (profile == null) profile = new VendorProfile();

        model.addAttribute("profile", profile);
        model.addAttribute("vendor", id);
        return "forms/editVendorProfile";
    }


    @RequestMapping("editedVendorProfile")
    public String submittedProfileEditForm(@RequestParam Integer id, @ModelAttribute VendorProfile profile, Model model)
    {

        Vendor vendor = vendorRepository.findById(id).orElse(null);
        if (vendor == null) return "redirect:/admin";

        VendorProfile existing = vendor.getProfile();

        if (existing == null) {
            vendor.setProfile(profile);
        } else {
            existing.setBio(profile.getBio());
            existing.setSocialMediaHandle(profile.getSocialMediaHandle());
            existing.setWebsite(profile.getWebsite());
        }

        vendorRepository.save(vendor);
        return "redirect:/vendor?id=" + id;
    }


    @RequestMapping("newDish")
    public String newDish(@RequestParam Integer id, Model model) {
        Vendor vendor = findVendor(id);
        if (vendor == null) return "redirect:/admin";


        model.addAttribute("vendor", vendor);
        model.addAttribute("dish", new Dish());
        model.addAttribute("tags", tagRepository.findAll());
        return "forms/newDish";
    }


    @RequestMapping("addDish")
    public String addDish(@RequestParam Integer vendorid,
                          @RequestParam List<Integer> tagIds,
                          @ModelAttribute Dish dish)
    {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        dish.setVendor(vendor);

        List<Tag> selectedTags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            tagRepository.findById(tagId).ifPresent(selectedTags::add);
        }
        dish.setTags(selectedTags);

        vendor.getDishes().add(dish);

        vendorRepository.save(vendor);

        return "redirect:/vendor?id=" + vendorid;
    }


    @RequestMapping("newReview")
    public String newReview(@RequestParam Integer vendorid,
                            @RequestParam Integer dishid,
                            Model model) {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        Dish dish = vendor.getDishes().stream().filter(d -> d.getId() == dishid).findFirst().orElse(null);

        if (dish == null) return "redirect:/admin";

        model.addAttribute("vendor", vendor);
        model.addAttribute("dishid",dishid);
        model.addAttribute("review", new Review());

        return "forms/newReview";
    }


    @RequestMapping("addReview")
    public String addReview(@RequestParam Integer vendorid,
                            @RequestParam Integer dishid,
                            @ModelAttribute Review review) {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        Dish dish = vendor.getDishes().stream().filter(d -> d.getId() == dishid).findFirst().orElse(null);

        if (dish == null) return "redirect:/admin";

        review.setDish(dish);
        review.setReviewDate(LocalDateTime.now());

        dish.getReviews().add(review);

        vendorRepository.save(vendor);

        return "redirect:/vendor?id=" + vendorid;
    }


    @RequestMapping("newPhoto")
    public String newPhoto(@RequestParam Integer vendorid, Model model) {
        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        model.addAttribute("vendor", vendor);
        model.addAttribute("photo", new Photo());
        return "forms/newPhoto";
    }


    @RequestMapping("addPhoto")
    public String addPhoto(@RequestParam Integer vendorid,
                           @ModelAttribute Photo photo) {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        photo.setVendor(vendor);
        vendor.getPhotos().add(photo);

        vendorRepository.save(vendor);

        return "redirect:/vendor?id=" + vendorid;
    }


    @RequestMapping("newAward")
    public String newAward(@RequestParam Integer vendorid, Model model) {
        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        model.addAttribute("vendor", vendor);
        model.addAttribute("award", new Award());
        return "forms/newAward";
    }


    @RequestMapping("addAward")
    public String addAward(@RequestParam Integer vendorid, @ModelAttribute Award award)
    {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        award.setVendor(vendor);
        vendor.getAwards().add(award);

        vendorRepository.save(vendor);
        return "redirect:/vendor?id=" + vendorid;
    }


    @RequestMapping("editDish")
    public String editDish(@RequestParam Integer vendorid,
                           @RequestParam Integer dishid,
                           Model model) {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        Dish dish = vendor.getDishes().stream()
                .filter(d -> d.getId() == dishid)
                .findFirst().orElse(null);

        if (dish == null) return "redirect:/admin";

        model.addAttribute("vendor", vendor);
        model.addAttribute("dish", dish);
        model.addAttribute("tags", tagRepository.findAll());

        return "forms/editDish";
    }


    @RequestMapping("editedDish")
    public String editedDish(@RequestParam Integer vendorid,
                             @RequestParam Integer dishid,
                             @RequestParam(required = false) List<Integer> tagIds,
                             @ModelAttribute Dish updatedDish) {

        Vendor vendor = findVendor(vendorid);
        if (vendor == null) return "redirect:/admin";

        Dish dish = vendor.getDishes().stream()
                .filter(d -> d.getId() == dishid)
                .findFirst()
                .orElse(null);

        if (dish == null) return "redirect:/admin";

        dish.setName(updatedDish.getName());
        dish.setDescription(updatedDish.getDescription());
        dish.setSpiceLevel(updatedDish.getSpiceLevel());
        dish.setPrice(updatedDish.getPrice());

        List<Tag> newTags = new ArrayList<>();
        if (tagIds != null) {
            for (Integer tagId : tagIds) {
                tagRepository.findById(tagId).ifPresent(newTags::add);
            }
        }
        dish.setTags(newTags);

        vendorRepository.save(vendor); // saves the updated dish too

        return "redirect:/vendor?id=" + vendorid;
    }


    @RequestMapping("editReview")
    public String editReview(@RequestParam Integer vendorId,
                             @RequestParam Integer reviewId,
                             Model model) {

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) return "redirect:/admin";

        Vendor vendor = findVendor(vendorId);
        if (vendor == null) return "redirect:/admin";

        model.addAttribute("vendor", vendor);
        model.addAttribute("review", review);
        return "forms/editReview";
    }


    @RequestMapping("editedReview")
    public String editedReview(@RequestParam Integer vendorId,
                               @RequestParam Integer reviewId,
                               @ModelAttribute Review updated) {

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) return "redirect:/admin";

        review.setReviewerName(updated.getReviewerName());
        review.setComment(updated.getComment());
        review.setRating(updated.getRating());

        reviewRepository.save(review);

        return "redirect:/vendor?id=" + vendorId;
    }


    @RequestMapping("editPhoto")
    public String editPhoto(@RequestParam Integer photoId, Model model) {
        Photo photo = photoRepository.findById(photoId).orElse(null);
        if (photo == null) return "redirect:/admin";

        model.addAttribute("photo", photo);
        return "forms/editPhoto";
    }


    @RequestMapping("editedPhoto")
    public String editedPhoto(@RequestParam Integer photoId,
                              @ModelAttribute Photo updatedPhoto) {

        Photo photo = photoRepository.findById(photoId).orElse(null);
        if (photo == null) return "redirect:/admin";

        photo.setDescription(updatedPhoto.getDescription());
        photo.setUrl(updatedPhoto.getUrl());

        photoRepository.save(photo);

        return "redirect:/vendor?id=" + photo.getVendor().getId();
    }


    @RequestMapping("editAward")
    public String editAward(@RequestParam Integer awardId, Model model) {
        Award award = awardRepository.findById(awardId).orElse(null);
        if (award == null) return "redirect:/admin";

        model.addAttribute("award", award);
        return "forms/editAward";
    }


    @RequestMapping("editedAward")
    public String editedAward(@RequestParam Integer awardId,
                              @ModelAttribute Award updatedAward) {

        Award award = awardRepository.findById(awardId).orElse(null);
        if (award == null) return "redirect:/admin";

        award.setTitle(updatedAward.getTitle());
        award.setYear(updatedAward.getYear());

        awardRepository.save(award);

        return "redirect:/vendor?id=" + award.getVendor().getId();
    }
}
