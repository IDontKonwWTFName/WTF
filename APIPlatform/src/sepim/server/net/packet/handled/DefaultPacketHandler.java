package sepim.server.net.packet.handled;

import java.util.logging.Logger;

import sepim.server.clients.Client;
import sepim.server.net.packet.Packet;

public class DefaultPacketHandler {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void handle(Client client, Packet packet) {
		logger.info("Unhandled packet: " + packet.getOpcode());
	}

}
