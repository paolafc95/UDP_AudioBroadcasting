package udp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static final int PORT_AUDIO = 9786;
	private ServerSocket dSock;
	private MulticastSocket dtSocketAudio;
	private AudioUDPServer audioUdpServer;
	public static final String DIRECCION_MULTICAST = "229.5.6.7";
	
	
	
	public ServerSocket getdSock() {
		return dSock;		
	}
	
	public void setdSock(ServerSocket dSock) {
		this.dSock = dSock;
	}
	
	public MulticastSocket getDtSocketAudio(){
		return dtSocketAudio;
	}
	
	public void setDtSocketAudio(MulticastSocket dtSocketAudio) {
		this.dtSocketAudio = dtSocketAudio;
	}
	
	
	

}

