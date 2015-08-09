package sepim.server;

import sipim.server.function.SocketConnectNetty;


public class Test {
	public static void main(String[] args)
	{
		new SocketConnectNetty().connect("18646725978","[3G*1506012101*000A*WHITELIST2]");
	}
}
