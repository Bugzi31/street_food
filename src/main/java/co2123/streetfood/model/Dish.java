package co2123.streetfood.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.util.List;

@Entity
public class Dish
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Integer id;
    private String name;
    private String description;
    private int spiceLevel;
    private double price;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable
//    (
//        name = "dish_tags",
//        joinColumns = @JoinColumn(name = "dish_id"),
//        inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )

    private List<Tag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(int spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}

