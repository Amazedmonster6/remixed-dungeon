package com.nyrds.pixeldungeon.mobs.npc;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.keys.SkeletonKey;
import com.watabou.pixeldungeon.levels.RegularLevel;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class NecromancerNPC extends ImmortalNPC {

	private static final String NODE       = "necromancernpc";
	private static final String INTRODUCED = "introduced";

	private boolean introduced = false;

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);

		Bundle node = new Bundle();
		node.put(INTRODUCED, introduced);

		bundle.put(NODE, node);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		Bundle node = bundle.getBundle(NODE);

		if(node.isNull()){
			return;
		}

		introduced = node.optBoolean(INTRODUCED, false);
	}

	@Override
	public boolean reset() {
		return true;
	}

	public static void spawn(RegularLevel level, Room room) {
		NecromancerNPC npc = new NecromancerNPC();
			int cell;
			do {
				cell = room.random(level);
			} while (level.map[cell] == Terrain.LOCKED_EXIT);
			npc.setPos(cell);
			level.spawnMob(npc);
	}

	@Override
	public boolean interact(final Hero hero) {
		getSprite().turnTo(getPos(), hero.getPos());

		if (!introduced) {
			GameScene.show(new WndQuest(this, Game.getVar(R.string.NecromancerNPC_Intro2)));
			introduced = true;

			SkeletonKey key = new SkeletonKey();

			if (key.doPickUp( Dungeon.hero )) {
				GLog.i( Hero.getHeroYouNowHave(), key.name() );
			} else {
				Dungeon.level.drop( key, Dungeon.hero.getPos() ).sprite.drop();
			}

		} else {
			final String TXT_MESSAGE1 = Game.getVar(R.string.NecromancerNPC_Message1);
			final String TXT_MESSAGE2 = Game.getVar(R.string.NecromancerNPC_Message2);
			final String TXT_MESSAGE3 = Game.getVar(R.string.NecromancerNPC_Message3);
			final String TXT_MESSAGE4 = Game.getVar(R.string.NecromancerNPC_Message4);

			final String[] TXT_PHRASES = {TXT_MESSAGE1, TXT_MESSAGE2, TXT_MESSAGE3, TXT_MESSAGE4};

			int index = Random.Int(0, TXT_PHRASES.length);
			say(TXT_PHRASES[index]);
		}
		return true;
	}
}
