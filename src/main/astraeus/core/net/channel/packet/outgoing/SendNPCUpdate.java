package main.astraeus.core.net.channel.packet.outgoing;

import main.astraeus.core.game.model.Position;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.core.game.model.entity.mobile.update.UpdateFlags.UpdateFlag;
import main.astraeus.core.net.channel.packet.OutgoingPacket;
import main.astraeus.core.net.channel.packet.PacketBuilder;
import main.astraeus.core.net.channel.packet.PacketHeader;
import main.astraeus.core.net.channel.protocol.codec.game.ByteOrder;

/**
 * The {@link OutgoingPacket} that updates a non-player character.
 * 
 * @author SeVen
 */
public class SendNPCUpdate extends OutgoingPacket {

	/**
	 * Creates a new {@link SendNPCUpdate}.
	 */
	public SendNPCUpdate() {
		super(65, PacketHeader.VARIABLE_SHORT, 16384);
	}

	@Override
	public PacketBuilder dispatch(Player player) {

		return builder;
	}

	/**
	 * Updates an npc's movement queue.
	 * 
	 * @param npc
	 *            The npc that will be updated.
	 * 
	 * @param builder
	 *            The builder used to place data into a buffer.
	 */
	public static void updateMovement(Npc npc, PacketBuilder builder) {
		if (npc.getWalkingDirection() == -1) {
			if (npc.getUpdateFlags().isUpdateRequired()) {
				builder.putBit(true);
				builder.putBits(2, 0);
			} else {
				builder.putBit(false);
			}
		} else {
			builder.putBit(true);
			builder.putBits(2, 1);
			builder.putBits(3, npc.getWalkingDirection());
			builder.putBit(npc.getUpdateFlags().isUpdateRequired());
		}
	}

	/**
	 * Displays an NPC on a players client.
	 * 
	 * @param npc
	 *            The npc to display.
	 * 
	 * @param player
	 *            The player to display the npc for.
	 * 
	 * @param builder
	 *            The builder used to place data into a buffer.
	 * 
	 */
	public static void addNPC(Npc npc, Player player, PacketBuilder builder) {
		player.getLocalNpcs().add(npc);
		builder.putBits(12, npc.getIndex());
		builder.putBits(5, npc.getPosition().getY() - player.getPosition().getY());
		builder.putBits(5, npc.getPosition().getX() - player.getPosition().getY());
		builder.putBit(npc.getUpdateFlags().isUpdateRequired());
		builder.putBits(12, npc.getId());
		builder.putBit(true);
	}

	/**
	 * Appends a mask update for an npc.
	 * 
	 * @param npc
	 *            The npc that the update masks are for.
	 * 
	 * @param update
	 *            The update buffer to place data in.
	 */
	public static void appendUpdates(Npc npc, PacketBuilder update) {

		int mask = 0x0;

		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			mask |= 0x4;
		}

		update.putByte(mask);

		if (npc.getUpdateFlags().get(UpdateFlag.FACE_COORDINATE)) {
			final Position position = (Position) npc.getAttributes().get(Attributes.FACE_COORDINATE);
			update.putShort(position.getX() * 2 + 1, ByteOrder.LITTLE);
			update.putShort(position.getY() * 2 + 1, ByteOrder.LITTLE);
		}
	}

}