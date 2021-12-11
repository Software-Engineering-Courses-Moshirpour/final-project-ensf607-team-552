package com.model;


import com.enums.AnimalStatus;
import com.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "animal"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private int id;

    @Column(
            name = "type",
            nullable = false
    )
    private String type;

    @Column(
            name = "dob",
            nullable = false
    )
    private LocalDate dob;

    @Column(
            name = "region"
    )
    private String region;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AnimalStatus status;



    @Column(
            name = "weight",
            nullable = false
    )
    private double weight;

    @Column(
            name = "age",
            nullable = false
    )
    private int age;


    @Column(name="gender",nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;


    @Column(name="tattoo",nullable = false)
    private String tattoo;


    @Column(name="image_url",nullable = true)
    private String url;

    @Column(name="breed",nullable = false)
    private String breed;

    @Column(name="coat_color")
    private String color;


    @OneToMany(
            mappedBy = "animal",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Prescription> prescribes = new ArrayList<>();


    @OneToMany(
            mappedBy = "animal",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "animal",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<AnimalStatusHistory> hists = new ArrayList<>();


    @OneToMany(
            mappedBy = "animal",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Request> requests = new ArrayList<>();


    public void addRequest(Request request){
        if (!this.requests.contains(request)) {
            this.requests.add(request);
            request.setAnimal(this);
        }
    }


    public void removeRequest(Request request){
        if (this.requests.contains(request)) {
            this.requests.remove(request);
            request.setAnimal(null);
        }
    }


    public void addHist(AnimalStatusHistory hist){
        if (!this.hists.contains(hist)) {
            this.hists.add(hist);
            hist.setAnimal(this);
        }
    }


    public void removeHist(AnimalStatusHistory hist){
        if (this.hists.contains(hist)) {
            this.hists.remove(hist);
            hist.setAnimal(null);
        }
    }




    public void addPrescribe(Prescription prescribe){
        if (!this.prescribes.contains(prescribe)) {
            this.prescribes.add(prescribe);
            prescribe.setAnimal(this);
        }
    }


    public void removePrescribe(Prescription prescribe){
        if (this.prescribes.contains(prescribe)) {
            this.prescribes.remove(prescribe);
            prescribe.setAnimal(null);
        }
    }



    public void addComment(Comment comment){
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setAnimal(this);
        }
    }


    public void removeComment(Comment comment){
        if (this.comments.contains(comment)) {
            this.comments.remove(comment);
            comment.setAnimal(null);
        }
    }



}
