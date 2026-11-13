package com.rivalrebels.common.init;

import java.util.ArrayList;
import java.util.List;

import com.rivalrebels.common.blocks.*;
import com.rivalrebels.common.blocks.autobuild.BlockTower;
import com.rivalrebels.common.blocks.crate.AmmoCrate;
import com.rivalrebels.common.blocks.trap.ScorchedStone;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RRBlocks {

    public static List<Block> blockListx = new ArrayList<>();

    public static Block steel;
    public static Block nuke;
    public static Block loader;
    public static Block nuke_crate_bottom;
    public static Block nuke_crate_top;
    public static Block plasma_explosion;
    public static Block toaster;
    public static Block plastic_explosives;
    public static Block timed_bomb;
    public static Block green_camo;
    public static Block brown_camo;
    public static Block grey_camo;
    public static Block jumper;
    public static Block smart_camo;
    public static Block mine;
    public static Block mario;
    public static Block quicksand;
    public static Block reactive;
    public static Block flare;
    public static Block toxic_gas;
    public static Block reactor;
    public static Block scorched_stone_n;
    public static Block scorched_stone_e;
    public static Block scorched_stone_s;
    public static Block scorched_stone_w;
    public static Block conduit;
    public static Block ammo_crate;
    public static Block supply_crate;

    public static Block tower_crate;

    public static void initBlocks() {
        mario = new Mario("mario", Material.GROUND).setSoundType(SoundType.GROUND).setCreativeTab(RRItems.nuclear_rr).setHardness(0.3f);
        quicksand = new Quicksand("quicksand", Material.SAND).setSoundType(SoundType.SAND).setCreativeTab(RRItems.nuclear_rr).setHardness(0.2f);

        // autobuilds:
        tower_crate = new BlockTower("tower_crate", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);

        

        steel = new Steel(Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F).setResistance(2.0F);
        nuke = new NukeBlock(Material.IRON).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        loader = new LoaderBlock(Material.IRON).setResistance(4.0F).setHardness(4.0F).setCreativeTab(RRItems.nuclear_rr);
        nuke_crate_top = new NukeCrate("nuke_crate_top", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        nuke_crate_bottom = new NukeCrate("nuke_crate_bottom", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        plasma_explosion = new PlasmaExplosion(Material.PORTAL, "plasma_explosion").setLightLevel(1.0F).setBlockUnbreakable();
        toaster = new Toaster("toaster", Material.IRON).setSoundType(SoundType.ANVIL).setCreativeTab(RRItems.nuclear_rr).setHardness(4.0F);
        plastic_explosives = new PlasticExplosives("plastic_explosives", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(3.0F).setResistance(3.0F);
        green_camo = new PlacedCamo("green_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setHardness(6.0F).setResistance(6.0F);
        brown_camo = new PlacedCamo("brown_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setHardness(6.0F).setResistance(6.0F);
        grey_camo = new PlacedCamo("grey_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setHardness(6.0F).setResistance(6.0F);
        smart_camo = new SmartCamo("smart_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setCreativeTab(RRItems.nuclear_rr).setHardness(6.0F).setResistance(6.0F);
        mine = new Mine("mine", Material.IRON).setCreativeTab(RRItems.nuclear_rr).setHardness(6.0F).setResistance(6.0F);
        
        
        reactive = new Reactive("reactive", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0f);
        flare = new Flare("flare", Material.WOOD).setCreativeTab(RRItems.nuclear_rr).setLightLevel(0.5f);
        jumper = new Jumper("jumper", Material.ROCK).setSoundType(SoundType.METAL).setHardness(0.8F).setCreativeTab(RRItems.nuclear_rr);
        toxic_gas = new ToxicGas("toxic_gas", Material.CACTUS).setUnlocalizedName("toxic_gas").setCreativeTab(RRItems.nuclear_rr).setBlockUnbreakable();
        reactor = new ReactorBlock("reactor", Material.IRON).setUnlocalizedName("reactor").setHardness(10.0F).setResistance(10.0F).setLightLevel(1.0F);
        conduit = new ConduitBlock("conduit", Material.IRON).setUnlocalizedName("conduit").setHardness(10.0F).setResistance(10.0F);
        scorched_stone_n = new ScorchedStone("scorched_stone_n", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_n").setHardness(1.5F).setResistance(10.0F);
        scorched_stone_e = new ScorchedStone("scorched_stone_e", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_e").setHardness(1.5F).setResistance(10.0F);
        scorched_stone_s = new ScorchedStone("scorched_stone_s", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_s").setHardness(1.5F).setResistance(10.0F);
        scorched_stone_w = new ScorchedStone("scorched_stone_w", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_w").setHardness(1.5F).setResistance(10.0F);

        // crates:
        ammo_crate = new AmmoCrate("ammo_crate", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);


    }
}
