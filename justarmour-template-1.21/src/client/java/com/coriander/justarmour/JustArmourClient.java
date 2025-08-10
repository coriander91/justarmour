package com.coriander.justarmour;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class JustArmourClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register((context, delta) -> renderArmorHUD(context));
	}

	private void renderArmorHUD(DrawContext context) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null || client.options.hudHidden) return;

		int screenWidth = client.getWindow().getScaledWidth();
		int screenHeight = client.getWindow().getScaledHeight();

		int x = screenWidth - 30;  // Near bottom-right corner
		int y = screenHeight - 25;

		for (int i = 0; i < 4; i++) {
			ItemStack stack = client.player.getInventory().armor.get(i);
			if (!stack.isEmpty()) {
				context.drawItem(stack, x, y);

				int durability = stack.getMaxDamage() - stack.getDamage();
				int max = stack.getMaxDamage();
				int color;

				if (durability == max) {
					color = 0x55FF55;
				} else if (durability == max - 1) {
					color = 0xFFFFFF;
				} else if (durability <= 100) {
					color = 0xFF5555;
				} else if (durability <= 200) {
					color = 0xFFA500;
				} else if (durability <= 300) {
					color = 0xFFFF55;
				} else {
					color = 0xFFFFFF;
				}



				String text = String.valueOf(durability);
				int textWidth = client.textRenderer.getWidth(text);
				context.drawText(client.textRenderer, text, x - textWidth - 2, y + 6, color, true);

				y -= 17;
			}
		}
	}
}
