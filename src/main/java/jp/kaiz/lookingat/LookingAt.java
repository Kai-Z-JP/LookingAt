package jp.kaiz.lookingat;

import com.mojang.realmsclient.gui.ChatFormatting;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

@Mod(name = LookingAt.MOD_NAME, modid = LookingAt.MOD_ID, version = LookingAt.VERSION)
public class LookingAt {
    public static final String MOD_ID = "lookingat";
    public static final String MOD_NAME = "Looking At";
    public static final String VERSION = "1.1";

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        if (event.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings.showDebugInfo) {
            List<String> right = event.right;
            MovingObjectPosition mouseOver = mc.objectMouseOver;

            if (mouseOver == null) {
                return;
            }

            switch (mouseOver.typeOfHit) {
                case BLOCK:
                    right.add("");
                    right.add(ChatFormatting.UNDERLINE + String.format("Targeted Block: %s, %s, %s", mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ));
                    Block block = mc.theWorld.getBlock(mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ);
                    right.add(Block.blockRegistry.getNameForObject(block));
                    break;
                case ENTITY:
                    Entity entity = mouseOver.entityHit;
                    if (entity != null) {
                        right.add("");
                        right.add(ChatFormatting.UNDERLINE + "Targeted Entity");
                        right.add(EntityList.getEntityString(entity));
                    }
            }
        }
    }
}
