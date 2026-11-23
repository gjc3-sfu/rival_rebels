package com.rivalrebels;

import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.common.init.RRItems;
import com.rivalrebels.common.init.RRSounds;
import com.rivalrebels.common.items.RRItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

@SuppressWarnings(value = "unused")
@Mod.EventBusSubscriber(modid = ModInfo.modid)
public class ModEventHandler {

    @SuppressWarnings(value = "unused")
    @SubscribeEvent
    public static void itemRegisterStuff(RegistryEvent.Register<Item> event) {
        for(Item item : RRItems.itemListx){
            if(item != null){
                event.getRegistry().register(item);
            }
        }

        for(Item item : RivalRebels.other_items){
            if(item != null){
                event.getRegistry().register(item);
            }
        }

        for(Block block : RRBlocks.blockListx){
            Block blockIn = (Block) block;
            if(blockIn != null) {
                event.getRegistry().register(new RRItemBlock(blockIn, blockIn.getUnlocalizedName().substring(5))
                        .setModelName(Objects.requireNonNull(blockIn.getRegistryName()).toString())
                        .setCreativeTab(blockIn.getCreativeTabToDisplayOn()));
            }
        }
        event.getRegistry().registerAll(RRItems.i_loader, RRItems.i_mine);
    }

    @SuppressWarnings(value = "unused")
    @SubscribeEvent
    public static void blockRegisterStuff(RegistryEvent.Register<Block> gameRegistry) {
        for(Block block : RRBlocks.blockListx){
            if(block != null){
                gameRegistry.getRegistry().register(block);
            }
        }

        for(Block block : RivalRebels.other_blocks){
            if(block != null){
                gameRegistry.getRegistry().register(block);
            }
        }
    }


    @SubscribeEvent
    public static void soundStuff(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
            RRSounds.gulp,
            RRSounds.toyguy,
            RRSounds.plasma_explosion,
            RRSounds.plasma_prime,
            RRSounds.plasma_fire,
            RRSounds.generic_explosion,
            RRSounds.explosives_place,
            RRSounds.weird_noise,
            RRSounds.quicksand,
            RRSounds.glass_break,
            RRSounds.knife_throw,
            RRSounds.gas_fizz,
            RRSounds.flamethrower_fire,
            RRSounds.tesla_fire,

            RRSounds.m202_fire,
            RRSounds.m202_rocket_explosion,
            RRSounds.m202_rocket_water_splash,
            RRSounds.m202_rocket_water_explode,

            RRSounds.einsten_warmup,
            RRSounds.einsten_fire_A,
            RRSounds.einsten_fire_B,
            RRSounds.einsten_pop,

            RRSounds.autobuild_deploy,

            RRSounds.reactor_startup,
            RRSounds.reactor_hum,
            RRSounds.reactor_warning,

            RRSounds.b2_ambient,
            RRSounds.b2_explode
        );
    }
}
