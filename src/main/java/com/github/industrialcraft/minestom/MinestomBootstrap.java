package com.github.industrialcraft.minestom;

import io.github.bloepiloepi.pvp.PvpExtension;
import io.github.bloepiloepi.pvp.config.PotionConfig;
import io.github.bloepiloepi.pvp.config.PvPConfig;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

public class MinestomBootstrap {
    public static void main(String[] args){
        MinecraftServer server = MinecraftServer.init();
        PvpExtension.init();
        MinecraftServer.getGlobalEventHandler().addChild(PvpExtension.legacyEvents());
        InstanceManager instanceManager = new InstanceManager();
        LobbyInstance lobbyInstance = new LobbyInstance(instanceManager, new Pos(0, 42, 0));
        lobbyInstance.setAsMainLobby();
        lobbyInstance.instance.setGenerator(unit -> unit.modifier().fillHeight(0, 42, Block.STONE));
        MiniGameManager miniGameManager = new MiniGameManager(lobbyInstance);
        miniGameManager.createMiniGame(new SpleefMiniGame(miniGameManager, instanceManager));
        miniGameManager.allMiniGames.values().forEach(miniGame -> System.out.println(miniGame.id));
        MinecraftServer.getCommandManager().register(new JoinGameCommand(miniGameManager));

        server.start("0.0.0.0", 25565);
    }
}
