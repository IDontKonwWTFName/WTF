package sepim.server;

import sipim.server.function.SocketConnectNetty;


public class Test {
	public static void main(String[] args)
	{
		new SocketConnectNetty().connect("[SG*5678901234*000A*WHITELIST2,222]");
	}
}
