package udp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.Socket;

public class Cliente {
	
	public static final int PORT_AUDIO = 9786;
	private Socket socketCliente;	
	private MulticastSocket dtSocket;
	private AudioUDPClient audioUdpClient;
	public static final String DIRECCION_MULTICAST = "229.5.6.7";
	
	
	//lo de adentro iría donde sea que se inicie la conexión con el servidor
	public void comunicacionConServidor() {
		AudioUDPClient hiloAudio = new AudioUDPClient (this);
		hiloAudio.start();			
	}
	
	public MulticastSocket getDtSocket() {
		return dtSocket;
	}

	public void setDtSocket(MulticastSocket dtSocket) {
		this.dtSocket = dtSocket;
	}
	
}
