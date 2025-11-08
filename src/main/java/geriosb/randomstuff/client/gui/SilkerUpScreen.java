package geriosb.randomstuff.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import geriosb.randomstuff.world.inventory.SilkerUpMenu;
import geriosb.randomstuff.network.SilkerUpButtonMessage;
import geriosb.randomstuff.GeriorandomstuffMod;

import com.mojang.blaze3d.systems.RenderSystem;

public class SilkerUpScreen extends AbstractContainerScreen<SilkerUpMenu> {
	private final static HashMap<String, Object> guistate = SilkerUpMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_give;
	Button button_kill;

	public SilkerUpScreen(SilkerUpMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("geriorandomstuff:textures/screens/silker_up.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.geriorandomstuff.silker_up.label_silker"), 4, 4, -12829636, false);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		button_give = Button.builder(Component.translatable("gui.geriorandomstuff.silker_up.button_give"), e -> {
			if (true) {
				GeriorandomstuffMod.PACKET_HANDLER.sendToServer(new SilkerUpButtonMessage(0, x, y, z));
				SilkerUpButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 27, this.topPos + 19, 40, 20).build();
		guistate.put("button:button_give", button_give);
		this.addRenderableWidget(button_give);
		button_kill = Button.builder(Component.translatable("gui.geriorandomstuff.silker_up.button_kill"), e -> {
			if (true) {
				GeriorandomstuffMod.PACKET_HANDLER.sendToServer(new SilkerUpButtonMessage(1, x, y, z));
				SilkerUpButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 104, this.topPos + 18, 45, 20).build();
		guistate.put("button:button_kill", button_kill);
		this.addRenderableWidget(button_kill);
	}
}
