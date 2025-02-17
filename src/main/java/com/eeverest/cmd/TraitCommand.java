package com.eeverest.cmd;

import com.eeverest.cca.TraitComponent;
import com.eeverest.gui.Trait;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class  TraitCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("trait")
                    .requires(source -> source.hasPermissionLevel(2)) // Requires OP level 2+
                    .then(argument("player", EntityArgumentType.player())
                            .then(argument("trait1", StringArgumentType.string())
                                    .suggests((context, builder) -> CommandSource.suggestMatching(getTraitNames(), builder)) // Suggests available traits
                                    .executes(ctx -> setTraits(
                                            EntityArgumentType.getPlayer(ctx, "player"),
                                            StringArgumentType.getString(ctx, "trait1"),
                                            null, null
                                    ))
                                    .then(argument("trait2", StringArgumentType.string())
                                            .suggests((context, builder) -> CommandSource.suggestMatching(getTraitNames(), builder))
                                            .executes(ctx -> setTraits(
                                                    EntityArgumentType.getPlayer(ctx, "player"),
                                                    StringArgumentType.getString(ctx, "trait1"),
                                                    StringArgumentType.getString(ctx, "trait2"),
                                                    null
                                            ))
                                            .then(argument("trait3", StringArgumentType.string())
                                                    .suggests((context, builder) -> CommandSource.suggestMatching(getTraitNames(), builder))
                                                    .executes(ctx -> setTraits(
                                                            EntityArgumentType.getPlayer(ctx, "player"),
                                                            StringArgumentType.getString(ctx, "trait1"),
                                                            StringArgumentType.getString(ctx, "trait2"),
                                                            StringArgumentType.getString(ctx, "trait3")
                                                    ))
                                            )
                                    )
                            )
                    )
            );
        });
    }

    private static int setTraits(ServerPlayerEntity player, String trait1, String trait2, String trait3) {
        TraitComponent component = TraitComponent.get(player);

        Trait t1 = Trait.fromString(trait1);
        Trait t2 = trait2 != null ? Trait.fromString(trait2) : Trait.NONE;
        Trait t3 = trait3 != null ? Trait.fromString(trait3) : Trait.NONE;

        if (t1 == null || t2 == null || t3 == null) {
            player.sendMessage(Text.literal("§cInvalid trait name! Use tab-completion for valid traits."), false);
            return 0;
        }

        component.setTrait1(t1);
        component.setTrait2(t2);
        component.setTrait3(t3);

        player.sendMessage(Text.literal("§aTraits updated: " + t1.name + ", " + t2.name + ", " + t3.name), false);
        return 1;
    }

    private static List<String> getTraitNames() {
        return Arrays.stream(Trait.values())
                .map(trait -> trait.name)
                .collect(Collectors.toList());
    }
}
