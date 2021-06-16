package com.noobian.scalar.util;

import scala.actors.threadpool.Arrays;

public class ModelNameParser {

	public static String getBasename(String modelName) {
		return modelName.split("_")[0];
	}
	
	public static ModelName parseModelName(String modelName) {
		ModelName mn = new ModelName();
		String[] split = modelName.split("_");
		mn.basename = split[0];
		switch (split.length) {
		case 1:
			// basename only
			return mn;
		case 2:
			// either skin only or mood only
			if (split[1].equals("erect") || split[1].equals("aroused"))
				mn.mood = ModelMood.valueOf(split[1].toUpperCase());
			else
				mn.skin = split[1];
			return mn;
		case 3:
			// skin + mood, or mood + stage
			if (split[1].equals("erect") || split[1].equals("aroused")) {
				// mood + stage
				int stage;
				try {
					stage = Integer.valueOf(split[2]);
				} catch (NumberFormatException ex) {
					return null;
				}
				mn.mood = ModelMood.valueOf(split[1].toUpperCase());
				mn.stage = stage;
			} else {
				// skin + mood
				if (split[2].equals("erect") || split[2].equals("aroused"))
					mn.mood = ModelMood.valueOf(split[2].toUpperCase());
				else return null;
				mn.skin = split[1];
			}
			return mn;
		case 4:
			// everything, but validate first
			int stage;
			try {
				stage = Integer.valueOf(split[3]);
			} catch (NumberFormatException ex) {
				return null;
			}
			if (!split[2].equals("erect") && !split[2].equals("aroused"))
				return null;
			mn.skin = split[1];
			mn.mood = ModelMood.valueOf(split[2].toUpperCase());
			mn.stage = stage;
			return mn;
		default:
			// too many underscores?
			return null;
		}
	}
	
	public static String generateModelName(ModelName mn) {
		if (mn.basename == null || mn.basename.equals("")) 
			return null;
		
		String name = mn.basename;
		
		if (mn.skin != null && !mn.skin.equals(""))
			name += "_" + mn.skin;
		
		if (mn.mood != ModelMood.NORMAL)
			name += "_" + mn.mood.toString().toLowerCase();
		
		if (mn.stage > 0)
			name += "_" + String.valueOf(mn.stage);
			
		return name;
			
	}
}
