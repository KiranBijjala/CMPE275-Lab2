package edu.sjsu.cmpe275.lab2.Lab2.controller;

import edu.sjsu.cmpe275.lab2.Lab2.model.Player;
import edu.sjsu.cmpe275.lab2.Lab2.model.Sponsor;
import edu.sjsu.cmpe275.lab2.Lab2.repository.PlayerRepository;
import edu.sjsu.cmpe275.lab2.Lab2.repository.SponsorRepository;
import edu.sjsu.cmpe275.lab2.Lab2.service.PlayerService;
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
@RequestMapping("/api/v1")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    PlayerService playerService;

    @GetMapping("/")
    public String get() {
        return "Working!";
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayersById(@PathVariable(value = "id") Long userId)
            throws InvalidConfigurationPropertyValueException {
        Player player =
                playerRepository
                        .findById(userId)
                        .orElseThrow(() -> new InvalidConfigurationPropertyValueException("", null, "User not found on :: " + userId));
        return ResponseEntity.ok().body(player);
    }


//    @PostMapping("/createPlayers")
//    public ResponseEntity<?> createPlayer(@Valid @RequestBody Player player){
//        String firstname = player.getFirstName().trim();
//        String lastname = player.getLastName().trim();
//        String email = player.getEmail().trim();
//        String description = player.getDescription().trim();
////        String address = player.getAddress().trim();
////        String [] add = address1.split(",");
//        String street = add[0];
//        String city = add[1];
//        String state = add[2];
//        String zip = add[3];
//        String sponsor = player.getSponsor().trim();
////        String sponsor = "";
//
////        Address address = new Address();
////        address.setStreet(street);
////        address.setCity(city);
////        address.setState(state);
////        address.setZip(zip);
////
////        Address address2 = addressRepository.save(address);
////        long add_id = address2.getId();
//
////        player.setAddress(String.valueOf(add_id));
//
//        if(firstname == null || lastname == null || email == null || firstname.equals("") || lastname.equals("") || email.equals("") ){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter");
//        }
//        System.out.println("Inside create player method " + player);
//        playerRepository.save(player);
//        return ResponseEntity.status(HttpStatus.CREATED).body(player);
//    }

    // to create a player
    @RequestMapping(value="/player", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPlayer(
            @RequestParam(value="firstname", required = true) String firstname,
            @RequestParam(value = "lastname", required = true) String lastname,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value ="city", required = false) String city,
            @RequestParam(value ="state", required = false) String state,
            @RequestParam(value ="zip", required = false) String zip,
            @RequestParam(value ="sponsor", required = false) String sponsor,
            @RequestParam(value ="opponent", required = false) String opponent
    ) {

        System.out.println("inside create player controller");
        Sponsor sp;
        Player p;

        if(firstname.equals("") || lastname.equals("") || email.equals("") ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter");
        }

        if (!sponsor.equals("")|| (sponsor != null)){
            sp =  sponsorRepository.findByName(sponsor);
            if (sp == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | Sponsor does not exist");
            }
        }

        p = playerRepository.findByEmail(email);
        if (p != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | Email already exists");
        }

        return playerService.createPlayer(firstname,lastname,email,description,street,city,state,zip,sponsor,opponent);
    }

    // to get player
    @RequestMapping(value="/player/{id}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPlayer(@PathVariable String id,
                                          @RequestParam(value = "format", required=false) String xml){
        System.out.println("Inside get player controller");
        String responseType="json";

        if(xml != null && xml.equalsIgnoreCase("xml")){ // ?xml=true
            responseType="xml";
        }

        return playerService.getPlayer(id, responseType);
    }

    // to update player
    @RequestMapping(value="/player/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePlayer(
            @RequestParam(value="firstname", required = true) String firstname,
            @RequestParam(value = "lastname", required = true) String lastname,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value ="city", required = false) String city,
            @RequestParam(value ="state", required = false) String state,
            @RequestParam(value ="zip", required = false) String zip,
            @RequestParam(value ="sponsor", required = false) String sponsor,
            @RequestParam(value ="opponent", required = false) String opponent
    ) {
//        if(opponent == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | cannot provide opponent parameter");
//        }

        System.out.println("inside update player controller");
        Sponsor sp;
        Player p;

        if(email.equals("") ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | email must be present");
        }

        if (!sponsor.equals("")){
            sp =  sponsorRepository.findByName(sponsor);
            if (sp == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter | Sponsor does not exist");
            }
        }

        return playerService.updatePlayer(firstname,lastname,email,description,street,city,state,zip,sponsor,opponent);
    }


}
