package udp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AudioServer extends Thread{
	public AudioServer() {
		
	}
	
	public void run() {
		FileInputStream soundFile = null;
		
		try {
			//Aquí se le puede cambiar el nombre de la canción que queramos
			soundFile = new FileInputStream("./Música/Audioslave_Like a Stone.mp3");
			
		}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		System.out.println("Server: "+ soundFile);
		
		try (ServerSocket serverSocket = new ServerSocket(6666);){
			if (serverSocket.isBound()) {
				Socket client = serverSocket.accept();
				OutputStream out = client.getOutputStream();
				
				byte buffer[] = new byte[2048];
				int count;
				while ((count = soundFile.read(buffer))!= -1)
					out.write(buffer,0,count);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server: shutdown");
	}

}

