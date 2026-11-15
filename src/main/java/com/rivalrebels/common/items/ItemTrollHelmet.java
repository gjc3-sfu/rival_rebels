
package com.rivalrebels.common.items;

import com.rivalrebels.common.init.RRBlocks;
import com.rivalrebels.RivalRebels;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTrollHelmet extends RRArmourItem {

    private static final ArmorMaterial TROLL_ARMOR =
        EnumHelper.addArmorMaterial(
                "TROLL",
                "rivalrebels:troll",
                5000,
                new int[] {0, 0, 0, 0},
                1000,
                SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                0.0F
        );

    public ItemTrollHelmet(String name) {
        super(name, TROLL_ARMOR, 0, EntityEquipmentSlot.HEAD);

        //this.setCreativeTab(RivalRebels.rrarmortab);
        this.setMaxDamage(5000);
        this.setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(
            ItemStack stack,
            Entity entity,
            EntityEquipmentSlot slot,
            String type) {

        return "rivalrebels:textures/armour/troll_armor.png";
    }

    @Override
    public EnumActionResult onItemUse(
            EntityPlayer player,
            World world,
            BlockPos pos,
            EnumHand hand,
            EnumFacing facing,
            float hitX,
            float hitY,
            float hitZ) {

        ItemStack stack = player.getHeldItem(hand);

        if (stack.isEmpty()) {
            return EnumActionResult.FAIL;
        }

        Block block = RRBlocks.flag_troll;

        // If the clicked block is replaceable, place into its position.
        // Otherwise, place against the clicked face.
        if (!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
            pos = pos.offset(facing);
        }

        if (!player.canPlayerEdit(pos, facing, stack)) {
            return EnumActionResult.FAIL;
        }

        if (!block.canPlaceBlockAt(world, pos)) {
            return EnumActionResult.FAIL;
        }

        if (!world.mayPlace(block, pos, false, facing, player)) {
            return EnumActionResult.FAIL;
        }

        IBlockState state = block.getStateForPlacement(
                world,
                pos,
                facing,
                hitX,
                hitY,
                hitZ,
                stack.getMetadata(),
                player
        );

        // The flag rejects UP and DOWN placement.
        if (state == null) {
            return EnumActionResult.FAIL;
        }

        if (!world.setBlockState(pos, state, 3)) {
            return EnumActionResult.FAIL;
        }

        SoundEvent sound = block.getSoundType(state, world, pos, player)
                .getPlaceSound();

        world.playSound(
                player,
                pos,
                sound,
                SoundCategory.BLOCKS,
                (block.getSoundType(state, world, pos, player).getVolume() + 1.0F) / 2.0F,
                block.getSoundType(state, world, pos, player).getPitch() * 0.8F
        );

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        return EnumActionResult.SUCCESS;
    }
}
