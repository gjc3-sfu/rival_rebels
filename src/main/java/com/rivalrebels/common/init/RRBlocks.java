package com.rivalrebels.common.init;

import java.util.ArrayList;
import java.util.List;

import com.rivalrebels.common.blocks.*;
import com.rivalrebels.common.blocks.autobuild.BlockBastion;
import com.rivalrebels.common.blocks.autobuild.BlockBunker;
import com.rivalrebels.common.blocks.autobuild.BlockMarioTrap;
import com.rivalrebels.common.blocks.autobuild.BlockMineTrap;
import com.rivalrebels.common.blocks.autobuild.BlockQuicksandTrap;
import com.rivalrebels.common.blocks.autobuild.BlockTower;
import com.rivalrebels.common.blocks.crate.LootCrate;
import com.rivalrebels.common.blocks.trap.ScorchedStone;
import com.rivalrebels.common.blocks.trap.TimedBomb;

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
    public static Block weapon_crate;
    public static Block supply_crate;
    public static Block explosives_crate;

    public static Block tower_crate;
    public static Block bunker_crate;
    public static Block bastion_crate;
    public static Block mario_trap;
    public static Block mine_trap;
    public static Block quicksand_trap;

    // Flags:
    public static Block flag_troll;

    public static void initBlocks() {
        mario = new Mario("mario", Material.GROUND).setSoundType(SoundType.GROUND).setCreativeTab(RRItems.nuclear_rr).setHardness(0.3f);
        quicksand = new Quicksand("quicksand", Material.SAND).setSoundType(SoundType.SAND).setCreativeTab(RRItems.nuclear_rr).setHardness(0.2f);

        // autobuilds:
        tower_crate = new BlockTower("tower_crate", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(0.5F);
        bunker_crate = new BlockBunker("bunker_crate", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(0.5F);
        bastion_crate = new BlockBastion("bastion_crate", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(0.5F);
        mario_trap = new BlockMarioTrap("mario_trap", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(0.5F).setResistance(10.0F);
        mine_trap = new BlockMineTrap("mine_trap", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(0.5F).setResistance(10.0F);
        quicksand_trap = new BlockQuicksandTrap("quicksand_trap", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(0.5F).setResistance(10.0F);

        steel = new Steel(Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(1.0F).setResistance(10.0F);
        nuke = new NukeBlock(Material.IRON).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        loader = new LoaderBlock(Material.IRON).setResistance(4.0F).setHardness(4.0F).setCreativeTab(RRItems.nuclear_rr);
        nuke_crate_top = new NukeCrate("nuke_crate_top", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        nuke_crate_bottom = new NukeCrate("nuke_crate_bottom", Material.WOOD).setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        plasma_explosion = new PlasmaExplosion(Material.PORTAL, "plasma_explosion").setLightLevel(1.0F).setBlockUnbreakable();
        toaster = new Toaster("toaster", Material.IRON).setSoundType(SoundType.ANVIL).setCreativeTab(RRItems.nuclear_rr).setHardness(4.0F);
        plastic_explosives = new PlasticExplosives("plastic_explosives", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(3.0F).setResistance(3.0F);
        timed_bomb = new TimedBomb("timed_bomb", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(1.0F).setResistance(1.0F);
        green_camo = new PlacedCamo("green_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setHardness(2.0F).setResistance(25.0F);
        brown_camo = new PlacedCamo("brown_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setHardness(2.0F).setResistance(25.0F);
        grey_camo = new PlacedCamo("grey_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setHardness(2.0F).setResistance(25.0F);
        smart_camo = new SmartCamo("smart_camo", Material.CLAY).setSoundType(SoundType.CLOTH).setCreativeTab(RRItems.nuclear_rr).setHardness(6.0F).setResistance(6.0F);
        mine = new Mine("mine", Material.IRON).setCreativeTab(RRItems.nuclear_rr).setHardness(6.0F).setResistance(6.0F);
        
        
        reactive = new Reactive("reactive", Material.IRON).setSoundType(SoundType.METAL).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0f).setResistance(100.0F);
        flare = new Flare("flare", Material.WOOD).setCreativeTab(RRItems.nuclear_rr).setLightLevel(0.5f);
        jumper = new Jumper("jumper", Material.ROCK).setSoundType(SoundType.METAL).setHardness(0.8F).setCreativeTab(RRItems.nuclear_rr);
        toxic_gas = new ToxicGas("toxic_gas", Material.CACTUS).setUnlocalizedName("toxic_gas").setCreativeTab(RRItems.nuclear_rr).setBlockUnbreakable();
        reactor = new ReactorBlock("reactor", Material.IRON).setUnlocalizedName("reactor").setHardness(10.0F).setResistance(10.0F).setLightLevel(1.0F);
        conduit = new ConduitBlock("conduit", Material.IRON).setCreativeTab(RRItems.nuclear_rr).setHardness(10.0F).setResistance(10.0F);
        scorched_stone_n = new ScorchedStone("scorched_stone_n", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_n").setHardness(1.5F).setResistance(10.0F);
        scorched_stone_e = new ScorchedStone("scorched_stone_e", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_e").setHardness(1.5F).setResistance(10.0F);
        scorched_stone_s = new ScorchedStone("scorched_stone_s", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_s").setHardness(1.5F).setResistance(10.0F);
        scorched_stone_w = new ScorchedStone("scorched_stone_w", Material.ROCK).setCreativeTab(RRItems.nuclear_rr).setUnlocalizedName("scorched_stone_w").setHardness(1.5F).setResistance(10.0F);

        // crates:
        ammo_crate = new LootCrate("ammo_crate", Material.WOOD, "ammunition_crate").setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        weapon_crate = new LootCrate("weapon_crate", Material.WOOD, "weapon_crate").setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        supply_crate = new LootCrate("supply_crate", Material.WOOD, "supply_crate").setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);
        explosives_crate = new LootCrate("explosives_crate", Material.WOOD, "explosives_crate").setSoundType(SoundType.WOOD).setCreativeTab(RRItems.nuclear_rr).setHardness(2.0F);

        // Flags:
        flag_troll = new BlockFlag("flag_troll", Material.CLOTH).setSoundType(SoundType.CLOTH).setCreativeTab(RRItems.hydro_rr).setHardness(0.0F);


    }
}
