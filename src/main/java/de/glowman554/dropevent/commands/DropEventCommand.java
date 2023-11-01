package de.glowman554.dropevent.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import de.glowman554.dropevent.DropEventMain;

public class DropEventCommand implements CommandExecutor, TabCompleter
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 0)
		{
			DropEventMain.getInstance().getDropEventManager().startEventNow();
		}
		else if (args.length == 1 && args[0].equals("stop"))
		{
			DropEventMain.getInstance().getDropEventManager().cancleCurrentTask();
			DropEventMain.getInstance().getDropEventManager().startDropEventTimer();
		}
		else
		{
			sender.sendMessage("Invalid usage!");
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 1)
		{
			return Arrays.asList(new String[] { "stop" });
		}
		return new ArrayList<String>();
	}

}
