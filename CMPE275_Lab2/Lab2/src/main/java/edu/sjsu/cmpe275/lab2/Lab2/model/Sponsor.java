package edu.sjsu.cmpe275.lab2.Lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Sponsor")
@EntityListeners(AuditingEntityListener.class)
public class Sponsor {

    @Id
    @Column(name = "sponsor_name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

//    @OneToMany(mappedBy = "bookCategory", cascade = CascadeType.ALL)
//    private Set<Book> books;

    @Embedded
    private Address address;

//    @Column(name = "beneficiaries", nullable = false)
//    private String beneficiaries;

    @JsonIgnoreProperties({"sponsor"})
     @OneToMany(fetch = FetchType.EAGER, mappedBy = "sponsor")
     private List<Player> beneficiaries;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public List<Player> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Player> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

}
