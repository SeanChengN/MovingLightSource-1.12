/**
    Copyright (C) 2014 by jabelar

    This file is part of jabelar's Minecraft Forge modding examples; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    For a copy of the GNU General Public License see <http://www.gnu.org/licenses/>.

	If you're interested in licensing the code under different terms you can
	contact the author at julian_abelar@hotmail.com 
*/

package com.blogspot.jabelarminecraft.movinglightsource;

import java.io.File;

import com.blogspot.jabelarminecraft.movinglightsource.proxy.CommonProxy;
import com.blogspot.jabelarminecraft.movinglightsource.utilities.Utilities;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod( modid = MainMod.MODID, 
      name = MainMod.MODNAME, 
      version = MainMod.MODVERSION,
      guiFactory = "com.blogspot.jabelarminecraft."+MainMod.MODID+".gui.GuiFactory")
public class MainMod
{
    public static final String MODID = "movinglightsource";
    public static final String MODNAME = "Torches and Moving Light Sources";
    public static final String MODVERSION = "1.0.2";
    public static final String MODDESCRIPTION = 
    		"Certain items such as torches and glowstone will give\n"+
    		"off light when wielded. You can also use torches to\n"+
    		"burn entities.";
    public static final String MODAUTHOR = "jabelar";
    public static final String MODCREDITS = "JnaeJnae";
    public static final String MODURL = "www.jabelarminecraft.blogspot.com";
    public static final String MODLOGO = "modconfiggraphic.png";
 
	// use a named channel to identify packets related to this mod
    public static final String NETWORK_CHANNEL_NAME = MODID;
	public static FMLEventChannel channel;

	// networking
	public static SimpleNetworkWrapper network;

    // set up configuration properties (will be read from config file in preInit)
    public static File configFile;
    public static Configuration config;
    public static boolean allowHeldItemsToGiveOffLight = true;
    public static boolean allowBurningEntitiesToGiveOffLight = true;
    public static boolean allowTorchesToBurnEntities = true;
    
    // instantiate the mod
    @Instance(MODID)
    public static MainMod instance;
        
    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="com.blogspot.jabelarminecraft.movinglightsource.proxy.ClientProxy", serverSide="com.blogspot.jabelarminecraft.movinglightsource.proxy.CommonProxy")
    public static CommonProxy proxy;
            
    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry."
    public void fmlLifeCycleEvent(FMLPreInitializationEvent event) 
    {   	
        // DEBUG
        System.out.println("preInit()"+event.getModMetadata().name);
                
        // hard-code mod information so don't need mcmod.info file
        event.getModMetadata().autogenerated = false ; // stops it from complaining about missing mcmod.info
        event.getModMetadata().credits = TextFormatting.BLUE+MODCREDITS;
        event.getModMetadata().authorList.add(TextFormatting.RED+MODAUTHOR);
        event.getModMetadata().description = Utilities.multiLineTextFormatting(TextFormatting.YELLOW, MODDESCRIPTION);
        event.getModMetadata().url = MODURL;
        event.getModMetadata().logoFile = MODLOGO;
        
        proxy.fmlLifeCycleEvent(event);
    }

	@EventHandler
    // Do your mod setup. Build whatever data structures you care about. Register recipes."
    // Register network handlers
    public void fmlLifeCycleEvent(FMLInitializationEvent event) 
    {
    	
        // DEBUG
        System.out.println("init()");
        
        proxy.fmlLifeCycleEvent(event);
    }

    public static void saveProperties()
    {
        try
        {
            config.save();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
