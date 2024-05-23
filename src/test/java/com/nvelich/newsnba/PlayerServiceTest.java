package com.nvelich.newsnba;

import com.nvelich.newsnba.exceptions.YourFriendly404Exception;
import com.nvelich.newsnba.models.Player;
import com.nvelich.newsnba.repositories.PlayerRepository;
import com.nvelich.newsnba.cache.PlayerCache;
import com.nvelich.newsnba.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerCache playerCache;

    @InjectMocks
    private PlayerService playerService;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setId(1L);
        player.setName("LeBron James");
        player.setCount(4);
    }

    @Test
    void testSavePlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player savedPlayer = playerService.savePlayer(player);

        assertNotNull(savedPlayer);
        assertEquals(player.getId(), savedPlayer.getId());
        assertEquals(player.getName(), savedPlayer.getName());
        assertEquals(player.getCount(), savedPlayer.getCount());
    }

   // @Test
   // void testGetAllPlayers() {
   //     List<Player> players = new ArrayList<>();
   //     players.add(player);
//
   //     when(playerRepository.findAll()).thenReturn(players);
//
   //     List<Player> returnedPlayers = playerService.getAllPlayers();

    //    assertNotNull(returnedPlayers);
    //    assertEquals(1, returnedPlayers.size());
    //    assertEquals(player, returnedPlayers.get(0));
  //  }

    @Test
    void testGetPlayerById_PlayerExists() {
        when(playerCache.getPlayer(anyString())).thenReturn(null);
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        Player returnedPlayer = playerService.getPlayerById(1L);

        assertNotNull(returnedPlayer);
        assertEquals(player, returnedPlayer);
    }

   // @Test
   // void testGetPlayerById_PlayerNotExists() {
   //     when(playerCache.getPlayer(anyString())).thenReturn(null);
   //     when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
//
   //     assertThrows(YourFriendly404Exception.class, () -> playerService.getPlayerById(1L));
   // }

    @Test
    void testDeletePlayerById() {
        when(playerCache.getPlayer(anyString())).thenReturn(player);
        doNothing().when(playerRepository).delete(any(Player.class));
        doNothing().when(playerCache).removePlayer(anyString());

        playerService.deletePlayerById(1L);

        verify(playerRepository, times(1)).delete(any(Player.class));
        verify(playerCache, times(1)).removePlayer(anyString());
    }

    @Test
    void testSavePlayers() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        playerService.savePlayers(playerList);

        verify(playerRepository, times(1)).save(any(Player.class));
        verify(playerCache, times(1)).addPlayer(anyString(), any(Player.class));
    }
}
