package com.noobian.scalar.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

public class ModelSelectGui extends GuiScreen {

	private GuiButton mButtonClose;
	private GuiLabel mLabelSelectModel;
	private ScrollPane modelList;

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(
				mButtonClose = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 10, "Select"));
		this.labelList.add(mLabelSelectModel = new GuiLabel(mc.fontRenderer, 1, this.width / 2 - 20,
				this.height / 2 + 40, 300, 20, 0xFFFFFF));
		mLabelSelectModel.addLine("Select Model");
		modelList = new ScrollPane(this, 20, 20, 100, 50, 20) {

			@Override
			protected void drawImpl() {
				// TODO Auto-generated method stub

			}
		};
//		modelList.addButton(button);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == mButtonClose) {
			mc.player.closeScreen();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
