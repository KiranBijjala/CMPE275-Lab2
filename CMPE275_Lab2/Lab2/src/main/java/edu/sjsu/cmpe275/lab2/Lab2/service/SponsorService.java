package edu.sjsu.cmpe275.lab2.Lab2.service;

import edu.sjsu.cmpe275.lab2.Lab2.model.Address;
import edu.sjsu.cmpe275.lab2.Lab2.model.Sponsor;
import edu.sjsu.cmpe275.lab2.Lab2.repository.SponsorRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {
//    @Autowired
//    private PlayerRepository playerRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    public ResponseEntity<?> createSponsor(String name, String description, String street, String city,
                                           String state, String zip){
        System.out.println("Inside create Sponsor service");
        Address address = new Address();
        address.setCity(city);
        address.setState(state);
        address.setStreet(street);
        address.setZip(zip);

        Sponsor sponsor = new Sponsor();
        sponsor.setAddress(address);
        sponsor.setDescription(description);
        sponsor.setName(name);

        sponsorRepository.saveAndFlush(sponsor);

        return ResponseEntity.status(HttpStatus.CREATED).body(sponsor);
    }

//    public ResponseEntity<?> createPlayer(String name, String description, String street, String city,
//                                          String state, String zip) {
//        System.out.println("inside create player service");
//
//        Address address = new Address();
//        address.setStreet(street.trim());
//        address.setCity(city.trim());
//        address.setState(state.trim());
//        address.setZip(zip.trim());
//
//        Sponsor sponsor = new Sponsor();
//        sponsor.setName(name.trim());
//        sponsor.setDescription(description.trim());
//        sponsor.setAddress(address);
//
//        sponsorRepository.saveAndFlush(sponsor);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(sponsor);
//    }


    public ResponseEntity<?> updateSponsor(String name, String description, String street, String city, String state, String zip) {
        System.out.println("inside create player service");
        Sponsor sponsor = sponsorRepository.findByName(name);
        if(sponsor == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sponsor not found");
        }
        JSONObject json = new JSONObject();
        sponsor.setName(name.trim());
        sponsor.setDescription(description.trim());

        Address address = new Address();
        address.setStreet(street.trim());
        address.setCity(city.trim());
        address.setState(state.trim());
        address.setZip(zip.trim());
        sponsor.setAddress(address);

        sponsorRepository.saveAndFlush(sponsor);

        return ResponseEntity.status(HttpStatus.CREATED).body(sponsor);
    }
}
