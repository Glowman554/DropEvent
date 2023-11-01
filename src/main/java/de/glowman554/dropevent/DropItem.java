package de.glowman554.dropevent;

import org.bukkit.Material;

public class DropItem
{
	private final Material item;
	private final int ammount;

	public DropItem(Material item, int ammount)
	{
		this.item = item;
		this.ammount = ammount;
	}

	public int getAmmount()
	{
		return ammount;
	}

	public Material getItem()
	{
		return item;
	}
	
	@Override
	public String toString()
	{
		return String.format("DropItem{item=%s, ammount=%d}", item, ammount);
	}
}
