package edu.sjsu.cmpe275.lab2.Lab2.repository;

import edu.sjsu.cmpe275.lab2.Lab2.model.Player;
import edu.sjsu.cmpe275.lab2.Lab2.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    public Player findByEmail(String email);
    public Player findByGenId(Long genId);

}
