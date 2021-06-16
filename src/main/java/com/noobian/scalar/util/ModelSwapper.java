package com.noobian.scalar.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.gamepiaynmo.custommodel.api.CustomModelApi;
import com.github.gamepiaynmo.custommodel.api.ModelPackInfo;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class ModelSwapper {

	public static boolean modelIdExists(ModelName mn) {
		return modelIdExists(ModelNameParser.generateModelName(mn));
	}

	public static boolean modelIdExists(String id) {
		return id != null && CustomModelApi.getModelIdList().contains(id);
	}
	
	public static String getModel(EntityPlayerMP mp) {
		return CustomModelApi.getCurrentModelOfPlayer(mp);
	}

	public static void toggleErect(EntityPlayerMP mp) {
		String currentModelStr = CustomModelApi.getCurrentModelOfPlayer(mp);
		ModelName currentModel = ModelNameParser.parseModelName(currentModelStr);
		if (currentModel == null)
			return; // If model name scheme is somehow invalid
		if (currentModel.mood == ModelMood.NORMAL)
			currentModel.mood = ModelMood.ERECT;
		else
			currentModel.mood = ModelMood.NORMAL;

		currentModel.stage = 0;
		currentModelStr = ModelNameParser.generateModelName(currentModel);

		if (!modelIdExists(currentModelStr))
			return;
		CustomModelApi.selectModelForPlayer(mp, currentModelStr);
	}

	public static void cycleArousalForward(EntityPlayerMP mp) {
		String currentModelStr = CustomModelApi.getCurrentModelOfPlayer(mp);
		ModelName currentModel = ModelNameParser.parseModelName(currentModelStr);
		if (currentModel == null)
			return; // If model name scheme is somehow invalid
		switch (currentModel.mood) {
		case NORMAL:
			currentModel.mood = ModelMood.AROUSED;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				return;
			}
			currentModel.mood = ModelMood.ERECT;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				return;
			}
			break;
		case AROUSED:
			currentModel.stage++;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				return;
			}
			currentModel.stage = 0;
			currentModel.mood = ModelMood.ERECT;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				return;
			}
			break;
		case ERECT:
			currentModel.stage++;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				return;
			}
			return;
		default:
			return;
		}
	}

	public static void cycleArousalBackward(EntityPlayerMP mp) {
		String currentModelStr = CustomModelApi.getCurrentModelOfPlayer(mp);
		ModelName currentModel = ModelNameParser.parseModelName(currentModelStr);
		if (currentModel == null)
			return; // If model name scheme is somehow invalid
		switch (currentModel.mood) {
		case NORMAL:
			// Did you expect your dick to start going backwards?!
			return;
		case AROUSED:
			if (currentModel.stage == 0) {
				currentModel.mood = ModelMood.NORMAL;
				if (modelIdExists(currentModel)) {
					CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				}
				return;
			}
			currentModel.stage--;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
			}
			return;
		case ERECT:
			if (currentModel.stage == 0) {
				currentModel.mood = ModelMood.AROUSED;
				while (modelIdExists(currentModel)) { // Get highest aroused stage
					currentModel.stage++;
				}
				currentModel.stage--; // The one before this iteration exists
				if (currentModel.stage == -1) { // No aroused stage
					currentModel.stage = 0;
					currentModel.mood = ModelMood.NORMAL;
					if (modelIdExists(currentModel)) {
						CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
					}
					return;
				}
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
				return;
			}
			currentModel.stage--;
			if (modelIdExists(currentModel)) {
				CustomModelApi.selectModelForPlayer(mp, ModelNameParser.generateModelName(currentModel));
			}
			return;
		default:
			return;
		}
	}

	public static boolean isModelAllowed(EntityPlayerMP mp, ModelName mdl) {
		// Only include base models:
		if (mdl != null && mdl.mood == ModelMood.NORMAL && mdl.stage == 0 && mdl.skin == null && modelIdExists(mdl)) {
			// Check if model is private
			ModelPackInfo info = CustomModelApi.getModelPackInfo(ModelNameParser.generateModelName(mdl));
			if (info.version.equalsIgnoreCase("private") && !info.author.equals(mp.getName()))
				return false;
			return true;
		}
		return false;
	}

	public static List<String> getAllowedModels(EntityPlayerMP mp) {
		List<String> allowed = new ArrayList<String>();
		CustomModelApi.getModelIdList().forEach((s) -> {
			final ModelName mdl = ModelNameParser.parseModelName(s);
			if (isModelAllowed(mp, mdl))
				allowed.add(s);
		});
		return allowed;
	}

	public static void trySwapModel(EntityPlayerMP serverPlayer, String modelName) {
		ModelName mdl = ModelNameParser.parseModelName(modelName);
		if (isModelAllowed(serverPlayer, mdl))
			CustomModelApi.selectModelForPlayer(serverPlayer, modelName);
		else
			serverPlayer.sendMessage(new TextComponentString("The model either doesn't exist or is private."));
	}
	
	public static List<String> getModelSkins(String basename) {
		List<String> skins = new ArrayList<String>();
		CustomModelApi.getModelIdList().forEach((s) -> {
			final ModelName model = ModelNameParser.parseModelName(s);
			if (model.basename.equals(basename) && model.mood == ModelMood.NORMAL && model.stage == 0) {
				if (model.skin == null || model.skin.equals(""))
					skins.add("default");
				else
					skins.add(model.skin);
			}
		});
		return skins;
	}
	
	public static void trySwapSkin(EntityPlayerMP serverPlayer, String fullModelId) {
		if (modelIdExists(fullModelId))
			CustomModelApi.selectModelForPlayer(serverPlayer, fullModelId);
		else
			serverPlayer.sendMessage(new TextComponentString("That skin doesn't exist."));
	}
}
