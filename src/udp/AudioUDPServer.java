package udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioUDPServer extends Thread{
	private final byte audioBuffer[] = new byte[10000];
	private TargetDataLine targetDataLine;
	
	private Server server;
	
	public AudioUDPServer (Server server) {
		this.server = server;
	}
	//espacio para el posible puclic void run()
	
	private AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits= 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		
	}
	
	private void broadcastAudio(){
		try {
			MulticastSocket socket = server.getDtSocketAudio();
			InetAddress inetAddress = InetAddress.getByName(server.DIRECCION_MULTICAST);
			socket.joinGroup(inetAddress);
			
			while(true) {
				int count = targetDataLine.read(audioBuffer, 0, audioBuffer.length);
				if(count>0) {
					DatagramPacket packet = new DatagramPacket(audioBuffer,audioBuffer.length,inetAddress,server.PORT_AUDIO);
					socket.send(packet);							
				}
			}
			
		}catch (Exception e){
					
		}		
	}
	
	public void setupAudio() {
		try {
			AudioFormat audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
