package com.github.industrialcraft.minestom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;

import java.util.HashMap;
import java.util.UUID;

public class MiniGameManager {
    public final HashMap<UUID,MiniGame> allMiniGames;
    public final LobbyInstance lobbyInstance;
    public MiniGameManager(LobbyInstance lobbyInstance) {
        this.lobbyInstance = lobbyInstance;
        this.allMiniGames = new HashMap<>();
        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
    }
    public void remove(MiniGame miniGame){
        this.allMiniGames.remove(miniGame.id);
    }
    public void createMiniGame(MiniGame miniGame){
        this.allMiniGames.put(miniGame.id, miniGame);
    }
    public boolean connect(Player player, UUID miniGame){
        return this.allMiniGames.get(miniGame).joinPlayer(player);
    }
}
