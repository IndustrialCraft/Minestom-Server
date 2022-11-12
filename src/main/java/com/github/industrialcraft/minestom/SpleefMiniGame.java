package com.github.industrialcraft.minestom;

import io.github.bloepiloepi.pvp.events.ProjectileHitEvent;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.projectile.ProjectileCollideWithBlockEvent;
import net.minestom.server.event.entity.projectile.ProjectileUncollideEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class SpleefMiniGame extends MiniGame{
    private Set<Player> spectators;
    protected SpleefMiniGame(MiniGameManager miniGameManager, InstanceManager instanceManager) {
        super(miniGameManager, instanceManager.createInstanceContainer());
        this.spectators = new HashSet<>();
        instance.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 1, Block.SNOW_BLOCK);
        });
        registerEvent(PlayerBlockBreakEvent.class, playerBlockBreakEvent -> {
            playerBlockBreakEvent.setResultBlock(Block.AIR);
            playerBlockBreakEvent.getPlayer().getInventory().addItemStack(ItemStack.of(Material.SNOWBALL));
        });
        registerEvent(PlayerMoveEvent.class, playerMoveEvent -> {
            if(playerMoveEvent.getNewPosition().y() < 0 && !spectators.contains(playerMoveEvent.getPlayer())){
                setAsSpectator(playerMoveEvent.getPlayer());
            }
        });

    }
    public void setAsSpectator(Player player){
        if(spectators.contains(player))
            return;
        player.getInventory().clear();
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(new Pos(0, 5, 0)).join();
        spectators.add(player);
    }
    @Override
    public void onJoin(Player player) {
        player.setInstance(instance).join();
        player.teleport(new Pos(0, 5, 0)).join();
        player.setGameMode(GameMode.SURVIVAL);
        player.setInstantBreak(true);
        player.getInventory().clear();
    }
}
