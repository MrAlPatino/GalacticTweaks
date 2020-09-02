package net.rom.gctweaks.core.mixins;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandLocate;

@Mixin(CommandLocate.class)
public abstract class CommandLocateMixin {

	@Redirect(
			method = "getTabCompletions(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/command/ICommandSender;[Ljava/lang/String;Lnet/minecraft/util/math/BlockPos;)Ljava/util/List;",
			at = @At(
					value = "INVOKE",
					target = "net/minecraft/command/CommandLocate.getListOfStringsMatchingLastWord([Ljava/lang/String;[Ljava/lang/String;)Ljava/util/List;"))
	private List<String> addDungeonsToList (String[] args, String... possibilities) {
		List<String> list = CommandBase.getListOfStringsMatchingLastWord(args, "Dungeon");
		list.addAll(Arrays.stream(possibilities).collect(Collectors.toList()));
		return CommandBase.getListOfStringsMatchingLastWord(args, list);
	}
}
