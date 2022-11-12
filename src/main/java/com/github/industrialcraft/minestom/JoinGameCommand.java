package com.github.industrialcraft.minestom;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

import java.util.UUID;

public class JoinGameCommand extends Command {
    public final MiniGameManager miniGameManager;
    public JoinGameCommand(MiniGameManager miniGameManager) {
        super("joinGame");
        this.miniGameManager = miniGameManager;
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("You executed the command");
        });

        var gameIdArgument = ArgumentType.String("game-id");

        addSyntax((sender, context) -> {
            final String gameId = context.get(gameIdArgument);
            miniGameManager.connect(sender.asPlayer(), UUID.fromString(gameId));
        }, gameIdArgument);
    }
}
