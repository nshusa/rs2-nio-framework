package com.astraeus.core.net.channel.packet.outgoing;

import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.net.channel.packet.OutgoingPacket;
import com.astraeus.core.net.channel.packet.PacketBuilder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteOrder;
import com.astraeus.core.net.channel.protocol.codec.game.ByteValue;

/**
 * The {@link OutgoingPacket} that updates a region for a player.
 * 
 * @author SeVen
 */
public class RegionalUpdatePacket extends OutgoingPacket {

	/**
	 * Creates a new {@link RegionalUpdatePacket}.
	 */
	public RegionalUpdatePacket() {
		super(73, 5);
	}

	@Override
	public PacketBuilder dispatch(Player player) {
		player.getContext().prepare(this, builder);
		builder.putShort(player.getPosition().getRegionalX() + 6,
				ByteValue.ADDITION, ByteOrder.BIG);
		builder.putShort(player.getPosition().getRegionalY() + 6);
		player.getLastPosition().setPosition(player.getPosition());
		return builder;
	}

}
