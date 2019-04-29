package udp;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioUDPClient extends Thread{
	
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	
	private Cliente cliente;
	
	public AudioUDPClient(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void run()
	{
		initiateAudio();
	}
	
	public AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate,sampleSizeInBits, channels, signed, bigEndian);
				
	}
	
	private void playAudio() {
		byte[] buffer = new byte[10000];
		try {
			int count;
			while((count = audioInputStream.read(buffer, 0, buffer.length))!= -1) {
				if(count >0) {
					sourceDataLine.write(buffer, 0, count);
				}				
			}
		}catch (Exception e) {			
		}
	}
	
	public void initiateAudio() {
		try {
			MulticastSocket socket = cliente.getDtSocket();
			///InetAddress inetAddress = InetAddress.getByName("229.5.6.7");
			///socket.joinGroup(inetAddress);
			byte[] audioBuffer = new byte[10000];
			
			while(true) {
				DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
				socket.receive(packet);
			
			try {
				byte audioData[] = packet.getData();
				InputStream byteInputStream = new ByteArrayInputStream(audioData);
				AudioFormat audioFormat = getAudioFormat();
				audioInputStream = new AudioInputStream(byteInputStream, audioFormat,audioData.length / audioFormat.getFrameSize());
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
				
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
				playAudio();							
				
			}catch (Exception e) {
				System.out.println("Error con los paquetes");
				}			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
