package com.rivalrebels.common.init;

import java.util.ArrayList;
import java.util.List;

import com.rivalrebels.ModInfo;
import com.rivalrebels.common.config.RRConfigx;
import com.rivalrebels.common.creativetabs.HydroRR;
import com.rivalrebels.common.creativetabs.NuclearRR;
import com.rivalrebels.common.items.*;
import com.rivalrebels.common.items.weapons.Einsten;
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
    public static Item sapper_shovel;
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
    public static Item einsten;
    public static Item copper_core;
    public static Item tungsten_core;
    public static Item titanium_core;
    public static Item chip;

    //Item blocks go below here
    public static Item i_loader;
    public static Item i_mine;


    // Armour
    public static Item troll_helmet;


    public static void initItems() {
        empty_rod = new RRItem("rod").setModelName(ModInfo.modid + ":rod").setCreativeTab(nuclear_rr);
        nuclear_rod = new ItemRodNuclear("nuclear_rod").setModelName(ModInfo.modid + ":nuclear_rod").setCreativeTab(nuclear_rr).setContainerItem(empty_rod).setMaxStackSize(1);
        hydro_rod = new RRItemHydrogenRod("hydrogen_rod").setModelName(ModInfo.modid + ":hydrogen_rod").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        redstone_rod = new RRItemRedstoneRod("redstone_rod").setModelName(ModInfo.modid + ":redstone_rod").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        battery = new BatteryItem("battery").setModelName(ModInfo.modid + ":" + "battery").setCreativeTab(nuclear_rr);
        //lord_vertice_lemonade = new VerticeLemonade("vertice_lemonade").setModelName(ModInfo.modid + ":lemonade").setCreativeTab(nuclear_rr);
        //nuclear_lemonade = new RRItem("nuclear_lemonade").setModelName(ModInfo.modid + ":nuke_lemon").setCreativeTab(nuclear_rr);
         
        if (RRConfigx.old_pill_design) {
            lord_vertice_lemonade = new VerticeLemonade("safe_pill", false).setModelName(ModInfo.modid + ":safe_pill").setCreativeTab(nuclear_rr).setMaxStackSize(6);
            nuclear_lemonade = new VerticeLemonade("experimental_pill", true).setModelName(ModInfo.modid + ":experimental_pill").setCreativeTab(nuclear_rr).setMaxStackSize(6);
        } else {
            lord_vertice_lemonade = new VerticeLemonade("vertice_lemonade", false).setModelName(ModInfo.modid + ":lemonade").setCreativeTab(nuclear_rr).setMaxStackSize(6);
            nuclear_lemonade = new VerticeLemonade("nuclear_lemonade", true).setModelName(ModInfo.modid + ":nuke_lemon").setCreativeTab(nuclear_rr).setMaxStackSize(6);
        }
        
        
        pliers = new Pliers("pliers").setModelName(ModInfo.modid + ":pliers").setCreativeTab(nuclear_rr);
        sapper_shovel = new ItemSapperShovel("sapper_shovel").setModelName(ModInfo.modid + ":sapper_shovel").setCreativeTab(nuclear_rr);
        fuse = new RRItem("fuse").setModelName(ModInfo.modid + ":fuse").setCreativeTab(nuclear_rr);
        antennae = new RRItem("antennae").setModelName(ModInfo.modid + ":radio").setCreativeTab(nuclear_rr);
        detonator = new Remote("detonator").setModelName(ModInfo.modid + ":detonator").setCreativeTab(nuclear_rr);
        fuel = new RRItem("fuel").setModelName(ModInfo.modid + ":fuel").setCreativeTab(nuclear_rr);
        rocket = new RRItem("rocket").setModelName(ModInfo.modid + ":rocket").setCreativeTab(nuclear_rr);
        knife = new Cuchillo("knife").setCreativeTab(nuclear_rr).setFull3D().setMaxStackSize(5);
        plasma_cannon = new PlasmaCannonItem("plasma_cannon").setCreativeTab(nuclear_rr).setFull3D();
        flamethrower = new FlameThrower("flamethrower").setModelName(ModInfo.modid + ":flamethrower").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        tesla = new Tesla("tesla").setModelName(ModInfo.modid + ":tesla").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        m202 = new M202("m202").setModelName(ModInfo.modid + ":m202").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        einsten = new Einsten("einsten").setModelName(ModInfo.modid + ":einsten").setCreativeTab(nuclear_rr).setMaxStackSize(1);
        gas_grenade = new GasGrenade("gas_grenade").setModelName(ModInfo.modid + ":gas_grenade").setCreativeTab(nuclear_rr).setMaxStackSize(6);

        copper_core = new RRItemCore("copper_core", 0.25f, 4).setModelName(ModInfo.modid + ":copper_core").setCreativeTab(nuclear_rr);
        tungsten_core = new RRItemCore("tungsten_core", 0.5f, 2).setModelName(ModInfo.modid + ":tungsten_core").setCreativeTab(nuclear_rr);
        titanium_core = new RRItemCore("titanium_core", 1.0f, 1).setModelName(ModInfo.modid + ":titanium_core").setCreativeTab(nuclear_rr);

        chip = new ChipItem("chip").setModelName(ModInfo.modid + ":chip").setCreativeTab(nuclear_rr);

        /* ------------------------------------ Item-Blocks ----------------------------------------------- */

        i_loader = new RRItemBlock(RRBlocks.loader, "loader").setModelName(ModInfo.modid + ":loader").setCreativeTab(nuclear_rr);
        i_mine = new RRItemBlock(RRBlocks.mine, "mine").setModelName(ModInfo.modid + ":mine").setCreativeTab(nuclear_rr);

        /* --------------------------------------- Armour ------------------------------------------------- */

        troll_helmet = new ItemTrollHelmet("troll_helmet").setModelName(ModInfo.modid + ":troll_helmet").setCreativeTab(hydro_rr);
    }
}
