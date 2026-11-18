package com.rivalrebels.common.blocks.crate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.rivalrebels.ModInfo;
import com.rivalrebels.common.blocks.RRBlock;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class LootCrate extends RRBlock {


    private static class ItemNameAlphabetically implements Comparator<ItemStack> {
        @Override
        public int compare(ItemStack o1, ItemStack o2) {
            return o1.getUnlocalizedName().compareToIgnoreCase(o2.getUnlocalizedName());
        }
    }

    private final ResourceLocation lootTableLocation;

    public LootCrate(String name, Material material, String lootTableName) {
        super(name, material);
        this.lootTableLocation =
            new ResourceLocation(ModInfo.modid, "blocks/" + lootTableName);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }
    
    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        if (world.isRemote) {
            return;
        }

        this.dropCrateLoot(world, pos.getX(), pos.getY(), pos.getZ(), player);
        world.setBlockToAir(pos);
    }

    private void dropCrateLoot(World world, int x, int y, int z, EntityPlayer player) {
        LootTable lootTable = world.getLootTableManager()
            .getLootTableFromLocation(this.lootTableLocation);

        LootContext context = new LootContext.Builder((WorldServer) world)
            .withPlayer(player)
            .build();


        player.sendStatusMessage(
            new TextComponentTranslation("rivalrebels.crate_contents")
                .setStyle(new Style().setColor(TextFormatting.DARK_GREEN)),
            false
        );


        List<String> already_mentioned = new ArrayList<>();
        List<ItemStack> sorted_loot_list =
            lootTable.generateLootForPools(world.rand, context);
            sorted_loot_list.sort(new ItemNameAlphabetically());

        for (ItemStack stack : sorted_loot_list) {
            EntityItem entity = new EntityItem(
                world,
                x + 0.5,
                y + 0.5,
                z + 0.5,
                stack
            );
            String name_of_item = stack.getUnlocalizedName();
            if (!already_mentioned.contains(name_of_item)) {
                player.sendStatusMessage(
                    new TextComponentTranslation(name_of_item + ".name")
                        .setStyle(new Style().setColor(TextFormatting.GREEN)),
                    false
                );
                already_mentioned.add(name_of_item);
            }
            world.spawnEntity(entity);
        }
    }

}
