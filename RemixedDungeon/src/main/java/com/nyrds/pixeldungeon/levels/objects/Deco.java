package com.nyrds.pixeldungeon.levels.objects;

import com.nyrds.Packable;
import com.nyrds.android.util.JsonHelper;
import com.nyrds.android.util.ModError;
import com.nyrds.android.util.Util;
import com.watabou.noosa.Animation;
import com.watabou.noosa.Group;
import com.watabou.noosa.StringsManager;
import com.watabou.noosa.TextureFilm;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.utils.Bundle;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

/**
 * Created by mike on 01.07.2016.
 */
public class Deco extends LevelObject {


	private static final String ANIMATIONS = "animations";
	static private Map<String, JSONObject> defMap = new HashMap<>();

	private JSONObject animations;

	private String name;
	private String desc;

	private Animation basic;

	private Group effect = new Group();

	@Packable
	protected String object_desc;

	private int width = 16;
	private int height = 16;

	private boolean canStepOn   = false;
	private boolean nonPassable = true;

	private String effectName = Utils.EMPTY_STRING;

	public Deco(){
		super(Level.INVALID_CELL);
	}

	public Deco(int pos) {
		super(pos);
	}

	public boolean stepOn(Char hero) {
		return canStepOn;
	}

	@Override
	public boolean nonPassable(Char ch) {
		return nonPassable;
	}

	@Override
	void setupFromJson(Level level, JSONObject obj) throws JSONException {
		super.setupFromJson(level,obj);

		object_desc = obj.optString("object_desc",object_desc);

		readObjectDesc();
	}


	@SneakyThrows
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		readObjectDesc();
	}

	private void readObjectDesc() throws JSONException {
		if (!defMap.containsKey(object_desc)) {
			defMap.put(object_desc, JsonHelper.readJsonFromAsset("levelObjects/"+object_desc+".json"));
		}

		JSONObject objectDesc = defMap.get(object_desc);
		JSONObject appearance  = objectDesc.getJSONObject("appearance");

		effectName = appearance.optString("particles", effectName);

		JSONObject desc = appearance.getJSONObject("desc");

		this.name = desc.optString("name","smth");
		this.desc = desc.optString("desc","smth");

		canStepOn = desc.optBoolean("canStepOn",canStepOn);
		nonPassable = desc.optBoolean("nonPassable",nonPassable);

		JSONObject sprite = appearance.getJSONObject("sprite");

		textureFile = sprite.optString("textureFile",textureFile);
		imageIndex  = sprite.optInt("imageIndex",imageIndex);

		width = sprite.optInt("width", width);
		height = sprite.optInt("height", height);

		if(sprite.has(ANIMATIONS)) {
			animations = sprite.getJSONObject(ANIMATIONS);
		}
	}

	@Override
	public String desc() {
		return StringsManager.maybeId(desc);
	}

	@Override
	public String name() {
		return StringsManager.maybeId(name);
	}


	@Nullable
	protected Animation loadAnimation(String kind) {
		if(animations!=null) {
			try {
				return JsonHelper.readAnimation(animations,
						kind,
						new TextureFilm(textureFile, width, height),
						0);
			} catch (JSONException e) {
				throw new ModError("Deco:" + name + "|" + kind + ":", e);
			}
		}
		return null;
	}

	private void updateEffect() {
		effect.killAndErase();
		effect = GameScene.particleEffect(effectName,pos);
	}

	@Override
	public void setPos(int pos) {
		super.setPos(pos);
		updateEffect();
	}

	@Override
	public void resetVisualState() {
		if(basic==null) {
			basic = loadAnimation("basic");
		}

		sprite.playAnim(basic, Util.nullCallback);
		updateEffect();
	}

	@Override
	public int getSpriteXS() {
		return width;
	}

	@Override
	public int getSpriteYS() {
		return height;
	}
}
