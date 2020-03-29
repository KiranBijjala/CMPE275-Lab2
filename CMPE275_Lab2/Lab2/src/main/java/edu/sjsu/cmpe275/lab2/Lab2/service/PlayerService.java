package edu.sjsu.cmpe275.lab2.Lab2.service;

import edu.sjsu.cmpe275.lab2.Lab2.model.Address;
import edu.sjsu.cmpe275.lab2.Lab2.model.Player;
import edu.sjsu.cmpe275.lab2.Lab2.model.Sponsor;
import edu.sjsu.cmpe275.lab2.Lab2.repository.PlayerRepository;
import edu.sjsu.cmpe275.lab2.Lab2.repository.SponsorRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    public ResponseEntity<?> createPlayer(String firstname, String lastname,
                                          String email, String description, String street, String city,
                                          String state, String zip, String sponsor, String opponent) {
        System.out.println("inside create player service");
        Sponsor sponsor1 = sponsorRepository.findByName(sponsor);
        List<Player> opponentSet = new ArrayList<>();
        Player opponent1 = playerRepository.findByEmail(opponent);
        opponentSet.add(opponent1);
        Address address = new Address();
        address.setStreet(street.trim());
        address.setCity(city.trim());
        address.setState(state.trim());
        address.setZip(zip.trim());

        Player player = new Player();
        player.setFirstName(firstname.trim());
        player.setLastName(lastname.trim());
        player.setEmail(email.trim());
        player.setAddress(address);
        player.setDescription(description.trim());
        player.setSponsor(sponsor1);
        player.setOpponent(opponentSet);

//        player.setSponsor_name(sponsor.trim());

        playerRepository.saveAndFlush(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    public ResponseEntity<?> getPlayer(String id, String responseType){
        System.out.println("getPlayer() service");
        Player player = playerRepository.findByGenId(Long.parseLong(id));
        if(player != null){
            if(responseType.equals("json"))
                return  new ResponseEntity<>(playerToJSONString(player),HttpStatus.OK);
            else
            {
                try {
                    System.out.println("isndie try" + playerToJSONString(player));
                    return  new ResponseEntity<>(XML.toString(new JSONObject(playerToJSONString(player))),HttpStatus.OK);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Player with " + id + "not found");
                }
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player with " + id + "not found");
        }
    }

    public ResponseEntity<?> updatePlayer(String firstname, String lastname,
                                          String email, String description, String street, String city,
                                          String state, String zip, String sponsor, String opponent) {
        //topics.add(topic);
        System.out.println("inside updatePlayer() service");
        Player player = playerRepository.findByEmail(email);
        Sponsor sponsor1 = sponsorRepository.findByName(sponsor);
        JSONObject json = new JSONObject();
        List<Player> opponentSet;
        if(player == null){
//            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
            opponentSet= new ArrayList<>();
        }else{
            opponentSet = (List<Player>) player.getOpponent();
        }
        System.out.println("Updating User");

            Address address = new Address();
            address.setStreet(street.trim());
            address.setCity(city.trim());
            address.setState(state.trim());
            address.setZip(zip.trim());

            player.setFirstName(firstname.trim());
            player.setLastName(lastname.trim());
            player.setEmail(email.trim());
            player.setAddress(address);
            player.setDescription(description.trim());
            player.setSponsor(sponsor1);

            Player opponent1 = playerRepository.findByEmail(opponent);
            opponentSet.add(opponent1);
            player.setOpponent(opponentSet);
            playerRepository.save(player);
            return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    public String playerToJSONString(Player player){
        JSONObject result = new JSONObject();
        JSONObject reservationsJSON = new JSONObject();
        JSONObject arr[] = null;
        System.out.println("inside playerToJSONString()#####");


        System.out.println("inside playerToJSONString() try");
//        result.put("player", fields);

        Map fields = new LinkedHashMap();
        Map add = new LinkedHashMap();

        fields.put("id", ""+player.getGenId());
        fields.put("firstname", player.getFirstName());
        fields.put("lastname", player.getLastName());
        fields.put("email", ""+player.getEmail());
        fields.put("description", player.getDescription());
        fields.put("address", add);
        add.put("street",player.getAddress().getStreet());
        add.put("city",player.getAddress().getCity());
        add.put("state",player.getAddress().getState());
        add.put("zip",player.getAddress().getZip());

//        if(player.getSponsor_name().equals("")){
//            fields.put("sponsor", player.getSponsor_name());
//        } else{
//            Sponsor s = sponsorRepository.findByName(player.getSponsor_name());
//            fields.put("sponsor",sponsorToJSONString(s));
//        }

        JSONObject field = new JSONObject(fields);

        System.out.println(field.toString() );
        System.out.println(result );






//            int i = 0;
//            List<Reservation> reservations = passenger.getReservation();
//            arr = new JSONObject[reservations.size()];
//            System.out.println("reservations size() "+reservations.size());
//
//            for(Reservation reservation : reservations){
//                System.out.println("Reservation");
//                arr[i++] = reservationToJSONString(reservation);
//                System.out.println(reservation.getGenOrderNumber());
//                System.out.println(reservation.getPrice());
//            }
//            reservationsJSON.put("reservation", arr);
//            fields.put("reservations", reservationsJSON);



        return field.toString();
    }

    public JSONObject sponsorToJSONString(Sponsor sponsor){
        JSONObject result = new JSONObject();
        JSONObject reservationsJSON = new JSONObject();
        JSONObject arr[] = null;
        System.out.println("inside sponsorToJSONString()#####");


        System.out.println("inside sponsorToJSONString() try");
//        result.put("player", fields);

        Map fields = new LinkedHashMap();
        Map add = new LinkedHashMap();

        System.out.println(sponsor.getName());
        System.out.println(sponsor.getDescription());
        System.out.println(sponsor.getAddress().getStreet());
        System.out.println(sponsor.getAddress().getCity());
        System.out.println(sponsor.getAddress().getState());
        System.out.println(sponsor.getAddress().getZip());


        fields.put("name", sponsor.getName());
        fields.put("description", sponsor.getDescription());
        fields.put("address", add);
        add.put("street",sponsor.getAddress().getStreet());
        add.put("city",sponsor.getAddress().getCity());
        add.put("state",sponsor.getAddress().getState());
        add.put("zip",sponsor.getAddress().getZip());

        JSONObject field = new JSONObject(fields);

        System.out.println(field.toString());
        System.out.println(result );
        return field;
    }
}
