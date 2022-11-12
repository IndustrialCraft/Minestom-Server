package com.github.industrialcraft.minestom;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;

public class GameWithStartPeriod extends MiniGame{
    private boolean gameStarted;
    private int gameStartTime;
    protected GameWithStartPeriod(MiniGameManager miniGameManager, InstanceContainer instance) {
        super(miniGameManager, instance);
    }
    @Override
    public void onJoin(Player player) {

    }
}
