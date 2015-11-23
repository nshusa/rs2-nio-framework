package com.astraeus.core.utility.startup;

import java.util.Objects;

import com.astraeus.core.Configuration;
import com.astraeus.core.Server;
import com.astraeus.core.game.model.entity.Facing;
import com.astraeus.core.game.model.entity.Position;
import com.astraeus.core.game.model.entity.mobile.npc.NpcSpawn;
import com.astraeus.core.utility.JsonLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class NpcSpawnLoader extends JsonLoader {

	public NpcSpawnLoader() {
		super(Configuration.DATA + "json/npc_spawns.json");
		load();
		System.out.println("Loaded: " + Server.getUpdateProcessor().getNpcs().size() + " npc spawn.");
	}

	@Override
	public void load(JsonObject reader, Gson builder) {
		int id = reader.get("id").getAsInt();
		Position position = builder.fromJson(reader.get("position"), Position.class);
		boolean randomWalk = reader.get("randomWalk").getAsBoolean();
		String facing = Objects.requireNonNull(reader.get("facing").getAsString());
		NpcSpawn spawn = new NpcSpawn(id, position, randomWalk, Facing.valueOf(facing));
	}

}
