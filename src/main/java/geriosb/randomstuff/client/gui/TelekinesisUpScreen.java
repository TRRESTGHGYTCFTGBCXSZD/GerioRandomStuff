package geriosb.randomstuff.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import geriosb.randomstuff.common.world.inventory.TelekinesisUpMenu;
import geriosb.randomstuff.common.network.TelekinesisUpButtonMessage;
import geriosb.randomstuff.GeriorandomstuffMod;

import com.mojang.blaze3d.systems.RenderSystem;

public class TelekinesisUpScreen extends AbstractContainerScreen<TelekinesisUpMenu> {
	private final static HashMap<String, Object> guistate = TelekinesisUpMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	EditBox Number;
	Button button_set;

	public TelekinesisUpScreen(TelekinesisUpMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("geriorandomstuff:textures/screens/telekinesis_up.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		Number.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (Number.isFocused())
			return Number.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
		Number.tick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.geriorandomstuff.telekinesis_up.label_blockwiwiwiwiwiwiwiwiwiwiwiwiwi"), 4, 4, -12829636, false);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		Number = new EditBox(this.font, this.leftPos + 6, this.topPos + 16, 120, 20, Component.translatable("gui.geriorandomstuff.telekinesis_up.Number"));
		Number.setMaxLength(32767);
		guistate.put("text:Number", Number);
		this.addWidget(this.Number);
		button_set = Button.builder(Component.translatable("gui.geriorandomstuff.telekinesis_up.button_set"), e -> {
			if (true) {
				GeriorandomstuffMod.PACKET_HANDLER.sendToServer(new TelekinesisUpButtonMessage(0, x, y, z));
				TelekinesisUpButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 127, this.topPos + 16, 40, 20).build();
		guistate.put("button:button_set", button_set);
		this.addRenderableWidget(button_set);
	}
}
