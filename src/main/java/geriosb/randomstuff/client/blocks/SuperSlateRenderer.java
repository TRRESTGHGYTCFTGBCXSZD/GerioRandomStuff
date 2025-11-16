package geriosb.randomstuff.client.blocks;

import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.utils.HexUtils;
import at.petrak.hexcasting.client.render.PatternColors;
import at.petrak.hexcasting.client.render.WorldlyPatternRenderHelpers;
import at.petrak.hexcasting.common.blocks.circles.BlockSlate;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import geriosb.randomstuff.GeriorandomstuffMod;
import geriosb.randomstuff.common.blocks.cheaters.SuperSlateBlock;
import geriosb.randomstuff.common.blocks.cheaters.SuperSlateBlockEntity;
import geriosb.randomstuff.init.GeriorandomstuffModHexActions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.blockentity.PistonHeadRenderer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Objects;

import static geriosb.randomstuff.GeriorandomstuffMod.rl;
import static geriosb.randomstuff.utils.ColorUtils.TweenColor;

public class SuperSlateRenderer implements BlockEntityRenderer<SuperSlateBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;
    private static final ResourceLocation base = rl("textures/block/tric_side.png");
    private static final ResourceLocation face_screen_unassigned = rl("textures/block/tric/face.png");
    private static final ResourceLocation face_screen_assigned = rl("textures/block/tric/face_color.png");
    private static final ResourceLocation face_emotion_normal = rl("textures/block/tric/normal.png");
    private static final ResourceLocation face_emotion_huh = rl("textures/block/tric/huh.png");
    private static final ResourceLocation face_emotion_dumb = rl("textures/block/tric/dumb.png");
    private static final ResourceLocation face_emotion_sad = rl("textures/block/tric/sad.png");
    private static final ResourceLocation face_emotion_pain = rl("textures/block/tric/critical.png");
    private static final ResourceLocation face_emotion_braindead = rl("textures/block/tric/mindless.png");
    private static final ResourceLocation face_emotion_minty = rl("textures/block/tric/gerio.png");

    public SuperSlateRenderer(BlockEntityRendererProvider.Context ctx) {
        this.blockRenderer = ctx.getBlockRenderDispatcher();
    }
    private static final int[] WALL_ROTATIONS = {180,90,0,270};

    public static int CountIotas(Tag tag) {
        try {
            var listTag = HexUtils.downcast(tag, ListTag.TYPE);
            var out = 0;

            for (var sub : listTag) {
                var csub = HexUtils.downcast(sub, CompoundTag.TYPE);
                out += CountIotas(csub.get(HexIotaTypes.KEY_DATA)) + 1;
            }

            return out;
        } catch (IllegalArgumentException t) {
            return 0;
        }
    }

    public static int numComplexity(double heavy) {
        String dude = Double.toString(heavy);
        int numComplex = 0;
        for (int maekun = 0; maekun <= dude.length()-2; maekun++) {
            if (dude.substring(maekun,maekun+1).equals(dude.substring(maekun+1,maekun+2))) {
                numComplex += 1;
            }
        }
        return numComplex;
    }

    private static void vertex(Matrix4f mat, Matrix3f normal, int light, VertexConsumer verts, float x, float y,
                               float z, float u,
                               float v, float nx, float ny, float nz, int colorer) {
        verts.vertex(mat, x, y, z)
                .color(colorer)
                .uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                .normal(normal, nx, ny, nz)
                .endVertex();
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
        poseStack.translate(0.5f,0.5f,0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(WALL_ROTATIONS[facing % 4]));
        if (bs.getValue(SuperSlateBlock.ATTACH_FACE) == AttachFace.CEILING) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        } else if (bs.getValue(SuperSlateBlock.ATTACH_FACE) == AttachFace.FLOOR) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        } else {
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        }
        int color = 0xff222222;
        if (be.getIotaTag() != null) {
            if (IotaType.getTypeFromTag(be.getIotaTag()) == HexIotaTypes.BOOLEAN) {
                if (be.getIotaTag().getBoolean(HexIotaTypes.KEY_DATA)) {
                    color = 0xff00AA00;
                } else {
                    color = 0xffAA0000;
                }
            } else if (IotaType.getTypeFromTag(be.getIotaTag()) == GeriorandomstuffModHexActions.COLOR_IOTA.get()) {
                color = be.getIotaTag().getInt(HexIotaTypes.KEY_DATA);
            } else {
                color = Objects.requireNonNull(IotaType.getTypeFromTag(be.getIotaTag())).color();
            }
        }
        int origincolor = color;
        if (bs.getValue(SuperSlateBlock.ENERGIZED)) {
            double angle = (System.currentTimeMillis() % 500L) / 250. * Math.PI;
            double value = (Math.sin(angle)+1)/2;
            color = TweenColor(color,0xffffffff,value);
        }
        poseStack.translate(-0.5f,-0.5f,-0.5f);
        float dx = 1f, dy = 1f, dz = 15f / 16f;
        var last = poseStack.last();
        var mat = last.pose();
        var norm = last.normal();
        RenderType layer = RenderType.entityCutout(face_screen_assigned);

        var verts = buffer.getBuffer(layer);
        for (int ra = 1;ra<16;ra+=2) {
            float rx = (float) ((Math.sin((0)/8.)+(32./30.))/(2*(32./30.)));
            float ry = (float) ((Math.cos((0)/8.)+(32./30.))/(2*(32./30.)));
            vertex(mat, norm, light, verts, rx, ry, dz, rx, ry, 0, 0, -1, color);
            rx = (float) ((Math.sin((ra*Math.PI)/8.)+(32./30.))/(2*(32./30.)));
            ry = (float) ((Math.cos((ra*Math.PI)/8.)+(32./30.))/(2*(32./30.)));
            vertex(mat, norm, light, verts, rx, ry, dz, rx, ry, 0, 0, -1, color);
            rx = (float) ((Math.sin(((ra+1)*Math.PI)/8.)+(32./30.))/(2*(32./30.)));
            ry = (float) ((Math.cos(((ra+1)*Math.PI)/8.)+(32./30.))/(2*(32./30.)));
            vertex(mat, norm, light, verts, rx, ry, dz, rx, ry, 0, 0, -1, color);
            rx = (float) ((Math.sin(((ra+2)*Math.PI)/8.)+(32./30.))/(2*(32./30.)));
            ry = (float) ((Math.cos(((ra+2)*Math.PI)/8.)+(32./30.))/(2*(32./30.)));
            vertex(mat, norm, light, verts, rx, ry, dz, rx, ry, 0, 0, -1, color);
        }

        boolean dorenderface = true;
        ResourceLocation renderface = face_emotion_normal;
        if ((origincolor & 0xffffff) == 0xA2DBD4) {
            renderface = face_emotion_minty; //easter egg;
        } else {
            if (be.getIotaTag() != null) {
                if (IotaType.getTypeFromTag(be.getIotaTag()) == HexIotaTypes.LIST) {
                    int iotas = CountIotas(be.getIotaTag().get(HexIotaTypes.KEY_DATA));
                    if (iotas == 0) {
                        renderface = face_emotion_huh;
                    } else if (iotas <= 8) {
                        renderface = face_emotion_normal;
                    } else if (iotas <= 16) {
                        renderface = face_emotion_huh;
                    } else if (iotas <= 52) {
                        renderface = face_emotion_sad;
                    } else if (iotas <= 112) {
                        renderface = face_emotion_pain;
                    } else {
                        renderface = face_emotion_braindead;
                    }
                } else if (IotaType.getTypeFromTag(be.getIotaTag()) == HexIotaTypes.PATTERN) {
                    dorenderface = false;
                    WorldlyPatternRenderHelpers.renderPattern(HexPattern.fromNBT(be.getIotaTag().getCompound(HexIotaTypes.KEY_DATA)), WorldlyPatternRenderHelpers.WORLDLY_SETTINGS, PatternColors.DEFAULT_PATTERN_COLOR,
                            be.getBlockPos().hashCode(), poseStack, buffer, new Vec3(0, 0, -1), (15f / 16f) - (1f / 256f), light, 1);
                } else if (IotaType.getTypeFromTag(be.getIotaTag()) == HexIotaTypes.GARBAGE) {
                    renderface = face_emotion_dumb;
                } else if (IotaType.getTypeFromTag(be.getIotaTag()) == HexIotaTypes.NULL) {
                    renderface = face_emotion_huh;
                } else if (IotaType.getTypeFromTag(be.getIotaTag()) == HexIotaTypes.DOUBLE) {
                    if (Double.isNaN(be.getIotaTag().getDouble(HexIotaTypes.KEY_DATA))) { //shouldn't happen, but if somehow happens
                        renderface = face_emotion_dumb;
                    } else if (be.getIotaTag().getDouble(HexIotaTypes.KEY_DATA) == Double.POSITIVE_INFINITY) { //shouldn't happen, but if somehow happens
                        renderface = face_emotion_dumb;
                    } else if (be.getIotaTag().getDouble(HexIotaTypes.KEY_DATA) == Double.NEGATIVE_INFINITY) { //shouldn't happen, but if somehow happens
                        renderface = face_emotion_dumb;
                    } else {
                        int iotas = numComplexity(be.getIotaTag().getDouble(HexIotaTypes.KEY_DATA));
                        if (iotas <= 6) {
                            renderface = face_emotion_normal;
                        } else if (iotas <= 8) {
                            renderface = face_emotion_huh;
                        } else if (iotas <= 10) {
                            renderface = face_emotion_sad;
                        } else if (iotas <= 12) {
                            renderface = face_emotion_pain;
                        } else {
                            renderface = face_emotion_braindead;
                        }
                    }
                }
            }
        }
        if (dorenderface) {
            layer = RenderType.entityTranslucent(renderface);

            verts = buffer.getBuffer(layer);
            vertex(mat, norm, light, verts, 0, 0, dz-(1f/256f), 0, 0, 0, 0, -1, 0xffffffff);
            vertex(mat, norm, light, verts, 0, dy, dz-(1f/256f), 0, 1, 0, 0, -1, 0xffffffff);
            vertex(mat, norm, light, verts, dx, dy, dz-(1f/256f), 1, 1, 0, 0, -1, 0xffffffff);
            vertex(mat, norm, light, verts, dx, 0, dz-(1f/256f), 1, 0, 0, 0, -1, 0xffffffff);
        }

        poseStack.popPose();
    }
}