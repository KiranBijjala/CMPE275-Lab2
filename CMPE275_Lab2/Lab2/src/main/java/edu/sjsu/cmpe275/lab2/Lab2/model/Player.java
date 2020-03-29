package edu.sjsu.cmpe275.lab2.Lab2.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Players")
@EntityListeners(AuditingEntityListener.class)
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long genId;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "description", nullable = false)
    private String description;

//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                    CascadeType.PERSIST,
//                    CascadeType.MERGE
//            },
//            mappedBy = "Players")
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="Opponent",
            joinColumns={@JoinColumn(name="player_id")},
            inverseJoinColumns={@JoinColumn(name="opponent_id")})
    private List<Player> opponent = new ArrayList<>();

    @Embedded
    private Address address;

//    @Column(name = "sponsor_name")
//    private String sponsor_name;


     @ManyToOne(fetch = FetchType.EAGER, optional = true)
//     @JsonIgnoreProperties({"beneficiaries"})
     @JoinColumn(name = "sponsor_name")
     private Sponsor sponsor;



    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getGenId() {
        return genId;
    }

    public void setGenId(long genId) {
        this.genId = genId;
    }

//    public String getSponsor_name() {
//        return sponsor_name;
//    }
//
//    public void setSponsor_name(String sponsor_name) {
//        this.sponsor_name = sponsor_name;
//    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public List<Player> getOpponent() {
        return opponent;
    }

    public void setOpponent(List<Player> opponent) {
        this.opponent = opponent;
    }

//    public String getSponsor() {
//        return sponsor;
//    }
//
//    public void setSponsor(String sponsor) {
//        this.sponsor = sponsor;
//    }

}
