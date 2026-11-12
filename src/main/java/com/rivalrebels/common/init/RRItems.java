package com.rivalrebels.common.init;

import java.util.ArrayList;
import java.util.List;

import com.rivalrebels.ModInfo;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.creativetabs.HydroRR;
import com.rivalrebels.common.creativetabs.NuclearRR;
import com.rivalrebels.common.items.*;
import com.rivalrebels.common.items.weapons.FlameThrower;
import com.rivalrebels.common.items.weapons.M202;
import com.rivalrebels.common.items.weapons.PlasmaCannonItem;
import com.rivalrebels.common.items.weapons.Tesla;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RRItems {
    public static CreativeTabs nuclear_rr = new NuclearRR("rival_rebels");
    public static CreativeTabs hydro_rr = new HydroRR("rival_rebels2");

    public static List<Item> itemListx = new ArrayList<>();

    public static Item empty_rod;
    public static Item nuclear_rod;
    public static Item battery;
    public static Item hydro_rod;
    public static Item redstone_rod;
    public static Item lord_vertice_lemonade;
    public static Item plasma_cannon;
    public static Item pliers;
    public static Item fuse;
    public static Item antennae;
    public static Item gas_grenade;
    public static Item nuclear_lemonade;
    public static Item detonator;
    public static Item fuel;
    public static Item rocket;
    public static Item knife;
    public static Item flamethrower;
    public static Item tesla;
    public static Item m202;
    public static Item copper_core;
    public static Item tungsten_core;
    public static Item titanium_core;

    //Item blocks go below here
    public static Item i_loader;
    public static Item i_mine;


    // Armour
    public static Item troll_helmet;


    public static void initItems() {
        empty_rod = new RRItem("rod").setModelName(ModInfo.modid + ":rod").setCreativeTab(nuclear_rr);
        nuclear_rod = new ItemRodNuclear("nuke_rod").setModelName(ModInfo.modid + ":nuke_rod").setCreativeTab(nuclear_rr).setContainerItem(empty_rod).setMaxStackSize(1);
        hydro_rod = new RRItemRod("hydrogen_rod").setModelName(ModInfo.modid + ":hydrogen_rod").setContainerItem(empty_rod).setCreativeTab(nuclear_rr).setMaxStackSize(1);
        redstone_rod = new RRItem("redstone_rod").setModelName(ModInfo.modid + ":redstone_rod").setContainerItem(empty_rod).setCreativeTab(nuclear_rr).setMaxStackSize(1);
        battery = new BatteryItem("battery").setModelName(ModInfo.modid + ":" + "battery").setCreativeTab(nuclear_rr);
        //lord_vertice_lemonade = new VerticeLemonade("vertice_lemonade").setModelName(ModInfo.modid + ":lemonade").setCreativeTab(nuclear_rr);
        //nuclear_lemonade = new RRItem("nuclear_lemonade").setModelName(ModInfo.modid + ":nuke_lemon").setCreativeTab(nuclear_rr);
         
        if (RRConfigx.old_pill_design) {
            lord_vertice_lemonade = new VerticeLemonade("safe_pill").setModelName(ModInfo.modid + ":safe_pill").setCreativeTab(nuclear_rr);
            nuclear_lemonade = new RRItem("experimental_pill").setModelName(ModInfo.modid + ":experimental_pill").setCreativeTab(nuclear_rr);
        } else {
            lord_vertice_lemonade = new VerticeLemonade("vertice_lemonade").setModelName(ModInfo.modid + ":lemonade").setCreativeTab(nuclear_rr);
            nuclear_lemonade = new RRItem("nuclear_lemonade").setModelName(ModInfo.modid + ":nuke_lemon").setCreativeTab(nuclear_rr);
        }
        
        
        pliers = new Pliers("pliers").setModelName(ModInfo.modid + ":pliers").setCreativeTab(nuclear_rr);
        fuse = new RRItem("fuse").setModelName(ModInfo.modid + ":fuse").setCreativeTab(nuclear_rr);
        antennae = new RRItem("antennae").setModelName(ModInfo.modid + ":radio").setCreativeTab(nuclear_rr);
        detonator = new Remote("detonator").setModelName(ModInfo.modid + ":detonator").setCreativeTab(nuclear_rr);
        fuel = new RRItem("fuel").setModelName(ModInfo.modid + ":fuel").setCreativeTab(nuclear_rr);
        rocket = new RRItem("rocket").setModelName(ModInfo.modid + ":rocket").setCreativeTab(nuclear_rr);
        knife = new Cuchillo("knife").setCreativeTab(nuclear_rr).setFull3D();
        plasma_cannon = new PlasmaCannonItem("plasma_cannon").setCreativeTab(nuclear_rr).setFull3D();
        flamethrower = new FlameThrower("flamethrower").setModelName(ModInfo.modid + ":flamethrower").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        tesla = new Tesla("tesla").setModelName(ModInfo.modid + ":tesla").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        m202 = new M202("m202").setModelName(ModInfo.modid + ":m202").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        gas_grenade = new GasGrenade("gas_grenade").setModelName(ModInfo.modid + ":gas_grenade").setCreativeTab(nuclear_rr);

        copper_core = new RRItemCore("copper_core").setModelName(ModInfo.modid + ":copper_core").setCreativeTab(nuclear_rr);
        tungsten_core = new RRItemCore("tungsten_core").setModelName(ModInfo.modid + ":tungsten_core").setCreativeTab(nuclear_rr);
        titanium_core = new RRItemCore("titanium_core").setModelName(ModInfo.modid + ":titanium_core").setCreativeTab(nuclear_rr);

        /* ------------------------------------ Item-Blocks ----------------------------------------------- */

        i_loader = new RRItemBlock(RRBlocks.loader, "loader").setModelName(ModInfo.modid + ":loader").setCreativeTab(nuclear_rr);
        i_mine = new RRItemBlock(RRBlocks.mine, "mine").setModelName(ModInfo.modid + ":mine").setCreativeTab(nuclear_rr);

        /* --------------------------------------- Armour ------------------------------------------------- */

        troll_helmet = new ItemTrollHelmet("troll_helmet").setModelName(ModInfo.modid + ":troll").setCreativeTab(hydro_rr);
    }
}
