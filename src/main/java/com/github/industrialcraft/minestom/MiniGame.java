package com.github.industrialcraft.minestom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.instance.InstanceContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class MiniGame {
    public final MiniGameManager miniGameManager;
    public final InstanceContainer instance;
    public final UUID id;
    public final List<Player> players;
    public final EventNode eventNode;
    protected MiniGame(MiniGameManager miniGameManager, InstanceContainer instance) {
        this.miniGameManager = miniGameManager;
        this.id = UUID.randomUUID();
        this.instance = instance;
        this.players = new ArrayList<>();
        this.eventNode = createEventNode();
        MinecraftServer.getGlobalEventHandler().addChild(this.eventNode);
    }
    public <T extends Event> void registerEvent(Class<T> eventClazz, Consumer<T> eventListener){
        this.eventNode.addListener(eventClazz, eventListener);
    }
    public boolean joinPlayer(Player player){
        if(!canJoin(player))
            return false;
        this.players.add(player);
        onJoin(player);
        return true;
    }
    public void destroy(){
        MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
    }
    public boolean canJoin(Player player){
        return true;
    }
    public abstract void onJoin(Player player);
    public EventNode createEventNode(){
        return EventNode.type(id.toString(), EventFilter.INSTANCE, (instanceEvent, instance1) -> instance1 == instance);
    }
}
