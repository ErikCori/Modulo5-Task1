package com.codeoftheweb.salvo.Controller;


import com.codeoftheweb.salvo.Model.GamePlayer;
import com.codeoftheweb.salvo.Model.Ship;
import com.codeoftheweb.salvo.Repository.GamePlayerRepository;
import com.codeoftheweb.salvo.Repository.GameRepository;
import com.codeoftheweb.salvo.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return gameRepository.findAll().stream().map(game -> game.makeGameDto()).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object>gameView(@PathVariable long gamePlayerId){
        return gameViewDto(gamePlayerRepository.getOne(gamePlayerId));
    }
    private Map<String, Object> gameViewDto(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(g ->g.makeGamePlayerDto()));
        dto.put("ships", getShipList(gamePlayer.getShips()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream()
                                                                .flatMap(gp->gp.getSalvoes()
                                                                                .stream()
                                                                                .map(salvo -> salvo.makeSalvoDto())
                                                                        )
                                                                .collect(Collectors.toList())
        );
        return dto;
    }
    private List<Map<String, Object>> getShipList(Set<Ship> ships){
        return ships.stream().map(ship-> ship.makeShipDto()).collect(Collectors.toList());
    }
    @RequestMapping("/leaderboard")
    public List<Object> leaderboardView(){
        return playerRepository.findAll().stream().map(player -> player.makeTableLeaderboard()).collect(Collectors.toList());
    }



}
