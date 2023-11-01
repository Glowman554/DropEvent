package de.glowman554.dropevent;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.glowman554.dropevent.commands.DropEventCommand;

public class DropEventMain extends JavaPlugin
{
	private static DropEventMain instance;

	public DropEventMain()
	{
		instance = this;
	}

	private DropEventManager dropEventManager = new DropEventManager();
	private FileConfiguration config = getConfig();

	private Location dropLocation;
	private DropItem[] dropItems;

	public void loadLocation()
	{
		if (dropLocation != null)
		{
			return;
		}
		int x = config.getInt("cords.x");
		int y = config.getInt("cords.y");
		int z = config.getInt("cords.z");
		World world = getServer().getWorld(config.getString("cords.world"));
		
		dropLocation = new Location(world, x, y, z);
		
		getLogger().log(Level.INFO, "Drop location at " + dropLocation.toString());
	}
	
	private void loadDropItems()
	{
		List<String> dropItemsString = (List<String>) config.getList("items");
		dropItems = new DropItem[dropItemsString.size()];
		
		for (int i = 0; i < dropItemsString.size(); i++)
		{
			String[] dropItemSplit = dropItemsString.get(i).split("\\*");
			
			Material item = Material.getMaterial(dropItemSplit[0].toUpperCase());
			if (item == null)
			{
				throw new IllegalArgumentException("Item " + dropItemSplit[0] + " could not be found!");
			}
			
			dropItems[i] = new DropItem(item, Integer.parseInt(dropItemSplit[1]));
			
			getLogger().log(Level.INFO, "Loaded drop " + dropItems[i].toString());
		}
	}

	@Override
	public void onLoad()
	{
		config.addDefault("cords.x", 0);
		config.addDefault("cords.y", 0);
		config.addDefault("cords.z", 0);
		config.addDefault("cords.world", "world");

		config.addDefault("items", new String[] {"carrot*100", "apple*200"});

		config.options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onEnable()
	{
		// loadLocation();
		loadDropItems();

		dropEventManager.startDropEventTimer();

		DropEventCommand dropEvent = new DropEventCommand();
		getCommand("dropevent").setExecutor(dropEvent);
		getCommand("dropevent").setTabCompleter(dropEvent);
	}

	public static DropEventMain getInstance()
	{
		return instance;
	}

	public DropEventManager getDropEventManager()
	{
		return dropEventManager;
	}
	
	public DropItem[] getDropItems()
	{
		return dropItems;
	}
	
	public Location getDropLocation()
	{
		return dropLocation;
	}
}
