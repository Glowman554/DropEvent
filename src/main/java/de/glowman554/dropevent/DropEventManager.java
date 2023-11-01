package de.glowman554.dropevent;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Math;

public class DropEventManager
{
	private BukkitTask currentTask;
	private Random random = new Random();

	public void startDropEventTimer()
	{
		currentTask = Bukkit.getScheduler().runTaskLater(DropEventMain.getInstance(), this::stage1, (58 * 60 * 20));
	}

	private void stage1()
	{
		Bukkit.broadcastMessage("§e§lsʟᴏᴡᴍᴄ.ᴅᴇ >> §cᴅʀᴏᴘᴇᴠᴇɴᴛ ᴀᴍ ᴄʙ-sᴘᴀᴡɴ sᴛᴀʀᴛᴇᴛ ɪɴ 2. ᴍɪɴᴜᴛᴇɴ.");
		currentTask = Bukkit.getScheduler().runTaskLater(DropEventMain.getInstance(), this::stage2, (2 * 60 * 20));
	}

	private void stage2()
	{
		DropEventMain.getInstance().loadLocation();
		Bukkit.broadcastMessage("§e§lsʟᴏᴡᴍᴄ.ᴅᴇ >> §cᴅʀᴏᴘᴇᴠᴇɴᴛ ᴀᴍ ᴄʙ-sᴘᴀᴡɴ sᴛᴀʀᴛᴇᴛ!");

		int playerCount = Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld() == DropEventMain.getInstance().getDropLocation().getWorld()).toList().size();

		int maxItem;
		if (playerCount < 1)
		{
			startDropEventTimer();
			return;
		}
		else if (playerCount < 6)
		{
			maxItem = 30;
		}
		else if (playerCount < 11)
		{
			maxItem = 50;
		}
		else if (playerCount < 21)
		{
			maxItem = 75;
		}
		else if (playerCount < 41)
		{
			maxItem = 100;
		}
		else
		{
			maxItem = 125;
		}

		doEvent(maxItem);
	}

	public DropItem getRandomDropItem()
	{
		int randomIndex = random.nextInt(DropEventMain.getInstance().getDropItems().length);
		return DropEventMain.getInstance().getDropItems()[randomIndex];
	}

	private int getRandomCoordinateVariation()
	{
		return (int) ((Math.random() * 40) - 20);
	}

	private void doEvent(int maxItem)
	{
		int tickDelay = (5 * 60 * 20) / maxItem;
		int[] ammount = {maxItem};

		currentTask = Bukkit.getScheduler().runTaskTimer(DropEventMain.getInstance(), () -> {
			DropItem item = getRandomDropItem();

			Location loc = DropEventMain.getInstance().getDropLocation().clone();
			loc.add(getRandomCoordinateVariation(), 0, getRandomCoordinateVariation());

			loc.getWorld().dropItem(loc, new ItemStack(item.getItem(), item.getAmmount()));

			Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
			FireworkMeta meta = firework.getFireworkMeta();
			FireworkEffect effect = FireworkEffect.builder().flicker(true).trail(true).with(Type.BALL).withColor(Color.RED).withFade(Color.YELLOW).build();
			meta.addEffect(effect);
			meta.setPower(1);
			firework.setFireworkMeta(meta);

			ammount[0]--;

			if (ammount[0] < 1)
			{
				Bukkit.broadcastMessage("§e§lsʟᴏᴡᴍᴄ.ᴅᴇ >> §cᴅʀᴏᴘᴇᴠᴇɴᴛ ɪsᴛ ᴊᴇᴛᴢᴛ ᴢᴜᴇɴᴅᴇ.");
				currentTask.cancel();
				startDropEventTimer();
			}
		}, 0, tickDelay);
	}

	public void cancleCurrentTask()
	{
		currentTask.cancel();
	}

	public void startEventNow()
	{
		currentTask.cancel();
		stage2();
	}
}
