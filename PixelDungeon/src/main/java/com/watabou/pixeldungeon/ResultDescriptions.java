/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;

import java.util.HashMap;

public final class ResultDescriptions {

	// Debuffs & blobs
	public static final String IMMURED  = Game.getVar(R.string.ResultDescriptions_Immured);
	public static final String NECROTISM	= Game.getVar(R.string.ResultDescriptions_Necrotism);
	
	public static final String WIN	= Game.getVar(R.string.ResultDescriptions_Win);

	private static final HashMap<String, Integer> descriptionsMap;

	static {
		descriptionsMap = new HashMap<>();

		// Mobs
		descriptionsMap.put("Mob", R.string.ResultDescriptions_Mob);
		descriptionsMap.put("Boss", R.string.ResultDescriptions_Boss);

		// Items
		descriptionsMap.put("Wand", R.string.ResultDescriptions_Wand);
		descriptionsMap.put("Glyph", R.string.ResultDescriptions_Glyph);

		// Dungeon features
		descriptionsMap.put("Trap", R.string.ResultDescriptions_Trap);

		// Debuffs & blobs
		descriptionsMap.put("Burning", R.string.ResultDescriptions_Burning);
		descriptionsMap.put("Hunger", R.string.ResultDescriptions_Hunger);
		descriptionsMap.put("Poison", R.string.ResultDescriptions_Poison);
		descriptionsMap.put("Gas", R.string.ResultDescriptions_Gas);
		descriptionsMap.put("Bleeding", R.string.ResultDescriptions_Bleeding);
		descriptionsMap.put("Ooze", R.string.ResultDescriptions_Ooze);
		descriptionsMap.put("Fall", R.string.ResultDescriptions_Fall);
		descriptionsMap.put("Immured", R.string.ResultDescriptions_Immured);
		descriptionsMap.put("Necrotism", R.string.ResultDescriptions_Necrotism);

		// Win
		descriptionsMap.put("Win", R.string.ResultDescriptions_Win);
	}

	// Private constructor to avoid instantiation
	private ResultDescriptions() throws Exception{
		throw new Exception("Trying to instantiate a utility class ResultDescription.");
	}

	public static String getDescription(String reason){
		if(descriptionsMap.containsKey(reason)){
			return Game.getVar(descriptionsMap.get(reason));
		} else {
			return Game.getVar(R.string.ResultDescriptions_Unknown);	// Unknown reason provided
		}
	}
}
