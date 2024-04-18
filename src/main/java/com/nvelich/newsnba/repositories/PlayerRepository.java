package com.nvelich.newsnba.repositories;

import com.nvelich.newsnba.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p WHERE p.name = :playerName")
    Player findByName(@Param("playerName") String playerName);
}
