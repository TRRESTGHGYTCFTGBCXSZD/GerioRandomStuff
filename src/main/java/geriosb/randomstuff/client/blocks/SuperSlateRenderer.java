package geriosb.randomstuff.client.blocks;

import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.client.render.PatternColors;
import at.petrak.hexcasting.client.render.WorldlyPatternRenderHelpers;
import at.petrak.hexcasting.common.blocks.circles.BlockSlate;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import geriosb.randomstuff.GeriorandomstuffMod;
import geriosb.randomstuff.common.blocks.cheaters.SuperSlateBlock;
import geriosb.randomstuff.common.blocks.cheaters.SuperSlateBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

import static geriosb.randomstuff.GeriorandomstuffMod.rl;
import static geriosb.randomstuff.utils.ColorUtils.TweenColor;

public class SuperSlateRenderer implements BlockEntityRenderer<SuperSlateBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;
    private static final ResourceLocation base = rl("block/super_slate_base");
    private static final ResourceLocation face_screen_unassigned = rl("block/super_slate_face");
    private static final ResourceLocation face_screen_assigned = rl("block/super_slate_face_color");
    private static final ResourceLocation face_emotion_normal = rl("block/super_slate_face_normal");
    private static final ResourceLocation face_emotion_huh = rl("block/super_slate_face_huh");
    private static final ResourceLocation face_emotion_dumb = rl("block/super_slate_face_dumb");
    private static final ResourceLocation face_emotion_sad = rl("block/super_slate_face_sad");

    public SuperSlateRenderer(BlockEntityRendererProvider.Context ctx) {
        this.blockRenderer = ctx.getBlockRenderDispatcher();
    }
    private static final int[] WALL_ROTATIONS = {0,90,180,270};
    private static int CountIotas(ListIota scaniota) { // recursive function to count iotas
        if (scaniota.subIotas() == null) return 0; // empty lists count as 0
        if (!scaniota.getList().getNonEmpty()) return 0;
        int count = 0;
        for (Iota iota : Objects.requireNonNull(scaniota.subIotas())){
            if (iota instanceof ListIota nextscaniota){
                count += CountIotas(nextscaniota)+1;
            } else {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public void render(SuperSlateBlockEntity be, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer,
                       int light, int overlay) {
        // render your stuff here
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        BlockState bs = be.getBlockState();
        poseStack.pushPose();
        int facing = bs.getValue(SuperSlateBlock.FACING).get2DDataValue();
        poseStack.mulPose(Axis.YP.rotationDegrees(WALL_ROTATIONS[facing % 4]));
        if (bs.getValue(SuperSlateBlock.ATTACH_FACE) == AttachFace.CEILING) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        } else if (bs.getValue(SuperSlateBlock.ATTACH_FACE) == AttachFace.CEILING) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
        }
        int color = 0xff222222;
        if ((!(be.getIotaTag() == null)) && be.getLevel() instanceof ServerLevel warudo) {
            Iota deserial = IotaType.deserialize(be.getIotaTag(), warudo);
            if (deserial instanceof BooleanIota boolvalue) {
                if (boolvalue.getBool()) {
                    color = 0xff00AA00;
                } else {
                    color = 0xffAA0000;
                }
            } else {
                color = deserial.getType().color();
            }
        }
        if (bs.getValue(SuperSlateBlock.ENERGIZED)) {
            double angle = (System.currentTimeMillis() % 1_000_000L) / 250. * Math.PI;
            double value = (Math.sin(angle)+1)/2;
            color = TweenColor(color,0xffffffff,value);
        }

        blockRenderer.getModelRenderer().renderModel(
                poseStack.last(),           // BlockState to render
                buffer.getBuffer(RenderType.solid()),
                null,
                modelManager.getModel(new ModelResourceLocation(base,"")),
                255,
                255,
                255,
                light,
                overlay
        );

        blockRenderer.getModelRenderer().renderModel(
                poseStack.last(),           // BlockState to render
                buffer.getBuffer(RenderType.solid()),
                null,
                modelManager.getModel(new ModelResourceLocation(face_screen_assigned,"")),
                (color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                color & 0xff,
                light,
                overlay
        );
        poseStack.translate(0,0,1./256);
        if (be.getLevel() instanceof ServerLevel warudo) {
            if (IotaType.deserialize(be.getIotaTag(), warudo) instanceof ListIota lister) {
                int iotas = CountIotas(lister);
                BakedModel renderface = modelManager.getModel(new ModelResourceLocation(face_emotion_huh,""));
                if (iotas == 0) {
                    renderface = modelManager.getModel(new ModelResourceLocation(face_emotion_huh,""));
                } else if (iotas <= 10) {
                    renderface = modelManager.getModel(new ModelResourceLocation(face_emotion_normal,""));
                } else if (iotas <= 20) {
                    renderface = modelManager.getModel(new ModelResourceLocation(face_emotion_huh,""));
                } else {
                    renderface = modelManager.getModel(new ModelResourceLocation(face_emotion_sad,""));
                }
                blockRenderer.getModelRenderer().renderModel(
                        poseStack.last(),           // BlockState to render
                        buffer.getBuffer(RenderType.solid()),
                        null,
                        renderface,
                        255,
                        255,
                        255,
                        light,
                        overlay
                );
            } else if (IotaType.deserialize(be.getIotaTag(), warudo) instanceof PatternIota reface) {
                WorldlyPatternRenderHelpers.renderPattern(reface.getPattern(), WorldlyPatternRenderHelpers.WORLDLY_SETTINGS, PatternColors.DEFAULT_PATTERN_COLOR,
                        be.getBlockPos().hashCode(),poseStack,buffer,new Vec3(0, 0, -1),null,light,1);
            } else if (IotaType.deserialize(be.getIotaTag(), warudo) instanceof GarbageIota) {
                blockRenderer.getModelRenderer().renderModel(
                        poseStack.last(),           // BlockState to render
                        buffer.getBuffer(RenderType.solid()),
                        null,
                        modelManager.getModel(new ModelResourceLocation(face_emotion_dumb,"")),
                        255,
                        255,
                        255,
                        light,
                        overlay
                );
            } else if (IotaType.deserialize(be.getIotaTag(), warudo) instanceof NullIota) {
                blockRenderer.getModelRenderer().renderModel(
                        poseStack.last(),           // BlockState to render
                        buffer.getBuffer(RenderType.solid()),
                        null,
                        modelManager.getModel(new ModelResourceLocation(face_emotion_huh,"")),
                        255,
                        255,
                        255,
                        light,
                        overlay
                );
            } else {
                blockRenderer.getModelRenderer().renderModel(
                        poseStack.last(),           // BlockState to render
                        buffer.getBuffer(RenderType.solid()),
                        null,
                        modelManager.getModel(new ModelResourceLocation(face_emotion_normal,"")),
                        255,
                        255,
                        255,
                        light,
                        overlay
                );
            }
        }

        poseStack.popPose();
    }
}