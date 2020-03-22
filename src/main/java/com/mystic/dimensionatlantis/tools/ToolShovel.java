package com.mystic.dimensionatlantis.tools;

import com.mystic.dimensionatlantis.Main;
import com.mystic.dimensionatlantis.init.ModItems;

import net.minecraft.item.ItemSpade;

public class ToolShovel extends ItemSpade
{

	public ToolShovel(String name, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.ATLANTISTAB);
		
		ModItems.ITEMS.add(this);
	}

}