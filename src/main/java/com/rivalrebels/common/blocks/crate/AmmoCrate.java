package com.rivalrebels.common.blocks.crate;

import java.util.Random;

import com.rivalrebels.ModInfo;
import com.rivalrebels.common.blocks.RRBlock;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public class AmmoCrate extends RRBlock {

    public AmmoCrate(String name, Material material) {
        super(name, material);
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
        ResourceLocation lootLocation =
            new ResourceLocation(ModInfo.modid, "blocks/ammunition_crate");

        LootTable lootTable = world.getLootTableManager()
            .getLootTableFromLocation(lootLocation);

        LootContext context = new LootContext.Builder((WorldServer) world)
            .withPlayer(player)
            .build();


        //player.sendStatusMessage(new TextComponentString(I18n.translateToLocal("RivalRebels.crate_contents")), true);

        for (ItemStack stack : lootTable.generateLootForPools(world.rand, context)) {
            EntityItem entity = new EntityItem(
                world,
                x + 0.5,
                y + 0.5,
                z + 0.5,
                stack
            );

            //I18n.translateToLocal("RivalRebels.crate_contents")

            world.spawnEntity(entity);
        }
    }

}
