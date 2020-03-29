package edu.sjsu.cmpe275.lab2.Lab2.controller;

import edu.sjsu.cmpe275.lab2.Lab2.model.Player;
import edu.sjsu.cmpe275.lab2.Lab2.model.Sponsor;
import edu.sjsu.cmpe275.lab2.Lab2.repository.PlayerRepository;
import edu.sjsu.cmpe275.lab2.Lab2.repository.SponsorRepository;
import edu.sjsu.cmpe275.lab2.Lab2.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/v1/sponsor")
public class SponsorController  {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private SponsorService sponsorService;

    @GetMapping("/test")
    public String get() {
        return "Working!";
    }

//    @GetMapping()
//        public List<Sponsor> getAllSponsor() {
//            return sponsorRepository.findAll();
//    }
//    @GetMapping("/{id}")
//    public Sponsor getSponsorByName(@PathVariable(value = "name") String name) {
//        System.out.println("Inside sponsor by name"+ name);
//        return sponsorRepository.findByName(name);
//    }

    @GetMapping("/sponsors")
    public List<Sponsor> getAllSponsor() {
        return sponsorRepository.findAll();
    }

//    @GetMapping("{id}")
//    public Sponsor getSponsorByName(@PathVariable(value = "name") String name){
//        System.out.println("Inside sponsor");
//        return sponsorRepository.findByName(name);
//    }

    @GetMapping("/{name}")
    public ResponseEntity<Sponsor> getSponsorByName(@PathVariable(value = "name") String name)
            throws InvalidConfigurationPropertyValueException {
        System.out.println("Indide get sponsor by name"+name);
        Sponsor sponsor =
                sponsorRepository
                        .findByName(name);
//                        .orElseThrow(() -> new InvalidConfigurationPropertyValueException("", null, "User not found on :: " + name));
        return ResponseEntity.ok().body(sponsor);
    }

    @RequestMapping(method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSponsor(
            @RequestParam(value ="name",required = true) String name,
            @RequestParam(value ="description",required =false) String description,
            @RequestParam(value ="street",required =false) String street,
            @RequestParam(value ="city",required =false) String city,
            @RequestParam(value ="state",required =false) String state,
            @RequestParam(value ="zip",required =false) String zip
    ) {

        System.out.println("inside create sponsor controller");
        Sponsor sp;
        Player p;

        if( name==null || name.isEmpty() ||  name.length()<2 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter");
        }


        sp =  sponsorRepository.findByName(name);
        if (sp != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | Sponsor already not exist");
        }

        return sponsorService.createSponsor(name,description,street,city,state,zip);
    }

    @RequestMapping(method= RequestMethod.PUT, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSponsor(
            @RequestParam(value ="name",required = true) String name,
            @RequestParam(value ="description",required =false) String description,
            @RequestParam(value ="street",required =false) String street,
            @RequestParam(value ="city",required =false) String city,
            @RequestParam(value ="state",required =false) String state,
            @RequestParam(value ="zip",required =false) String zip
    ) {

        System.out.println("inside create sponsor controller");
        Sponsor sp;
//        Player p;

        if( name==null || name.isEmpty() ||  name.length()<2 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter");
        }


        sp =  sponsorRepository.findByName(name);
        if (sp != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | Sponsor already not exist");
        }

        return sponsorService.updateSponsor(name,description,street,city,state,zip);
    }

//    @DeleteMapping("/sponsor/{name}")
//    public ResponseEntity<Sponsor> deleteSponsorByName(@PathVariable(value = "name") String name)
//                throws InvalidConfigurationPropertyValueException {
//        Long num =
//                sponsorRepository.deleteByName(name);
//                if(num>=1){
//                    return ResponseEntity.ok().body("Successfully deleted");
//                }
////        sponsorRepository.deleteByName(name);
//        return new ResponseEntity<So>(HttpStatus.NO_CONTENT);
//    }

    @RequestMapping(value = "/sponsor/{name}")
    public ResponseEntity<Sponsor> deleteSponsor(@PathVariable("name") String name) {
        System.out.println("Fetching & Deleting Sponsor with name " + name);

//        Sponsor user = sponsorRepository.findByName(name);
//        if (user == null) {
//            System.out.println("Unable to delete Sponsor with name " + name + " not found");
//            return new ResponseEntity<Sponsor>(HttpStatus.NOT_FOUND);
//        }
        sponsorRepository.deleteById(name);
        return new ResponseEntity<Sponsor>(HttpStatus.NO_CONTENT);
    }

//   @RequestMapping(method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateSponsor(@RequestParam() String ){
//
//   }

//    @RequestMapping(method = RequestMethod.PUT)
//    public updateSponsor(@RequestParam)


}


