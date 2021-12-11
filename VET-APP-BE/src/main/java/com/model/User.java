package com.model;


import com.enums.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "user_name",
            nullable = false
    )
    private String username;



    @Column(
            name = "phone",
            nullable = true

    )
    private String phone;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @Column(
            name = "dob"
    )
    private LocalDate dob;

    @Column(
            name = "creation_date"
    )
    private LocalDate createdAt;


    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;



    @Column(
            name = "password",
            nullable = false
    )
    private String password;


    @Column(name = "reset_password_token")
    private String resetPasswordToken;




    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Request> requests  = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Prescription> prescribes = new ArrayList<>();


    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Comment> comments = new ArrayList<>();


    public void addRequest(Request request){
        if (!this.requests.contains(request)) {
            this.requests.add(request);
            request.setUser(this);
        }
    }


    public void removePrescribe(Request request){
        if (this.requests.contains(request)) {
            this.requests.remove(request);
            request.setUser(null);
        }
    }


    public void addComment(Comment comment){
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setUser(this);
        }
    }


    public void removeComment(Comment comment){
        if (this.comments.contains(comment)) {
            this.comments.remove(comment);
            comment.setUser(null);
        }
    }


    public void addPrescribe(Prescription prescribe){
        if (!this.prescribes.contains(prescribe)) {
            this.prescribes.add(prescribe);
            prescribe.setUser(this);
        }
    }


    public void removePrescribe(Prescription prescribe){
        if (this.prescribes.contains(prescribe)) {
            this.prescribes.remove(prescribe);
            prescribe.setUser(null);
        }
    }



    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String phone, Status status, LocalDate dob, String email) {
        this.username = username;
        this.phone = phone;
        this.status = status;
        this.dob = dob;
        this.email = email;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
