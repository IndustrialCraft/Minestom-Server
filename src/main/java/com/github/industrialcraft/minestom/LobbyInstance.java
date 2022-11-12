package com.github.industrialcraft.minestom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
//import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.utils.PacketUtils;
import net.minestom.server.utils.Utils;
import net.minestom.server.utils.position.PositionUtils;

import java.nio.ByteBuffer;

public class LobbyInstance {
    public final InstanceContainer instance;
    public final Pos spawn;
    public LobbyInstance(InstanceManager instanceManager, Pos spawn) {
        this.instance = instanceManager.createInstanceContainer();
        this.spawn = spawn;
    }
    public void setAsMainLobby(){
        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(spawn);
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().addItemStack(ItemStack.of(Material.BOW));
            player.getInventory().addItemStack(ItemStack.of(Material.ARROW, 64));
        });
    }
    /*public static void sendMarker(Player player){
        byte[] data = new byte[100];
        ByteBuffer buf = ByteBuffer.wrap(data);
        NetworkBuffer netBuf = new NetworkBuffer(buf);
        netBuf.write(NetworkBuffer.LONG, getPosition(0, 50, 0));
        netBuf.write(NetworkBuffer.INT, getColor(255, 0, 0, 255));
        netBuf.write(NetworkBuffer.STRING, "ahoj");
        netBuf.write(NetworkBuffer.INT, 10000);
        player.sendPluginMessage("minecraft:debug/game_test_add_marker", data);
    }*/
    public static int getColor(int r, int g, int b, int a){
        int encoded = 0;
        encoded = encoded | ((int) b);
        encoded = encoded | ((int) g << 8);
        encoded = encoded | ((int) r << 16);
        encoded = encoded | ((int) a << 24);
        return encoded;
    }
    public static long getPosition(int x, int y, int z){
        return ((long) (x & 0x3FFFFFF) << 38) | ((long) (z & 0x3FFFFFF) << 12) | (y & 0xFFF);
    }
}
