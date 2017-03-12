import java.io.*;
import java.net.*;

public class Client {
	static Socket socket1;

	public static void main(String[] args) throws IOException {
		try 
		{
			if (args.length != 4) 
			{
				System.out.println("Enter hostname,port,command and filepath");
			} 
			else 
			{
				// Assigning filename or file path to temporary variable
				String temp = args[3];
				// Creating a socket with hostname and portnumber
				socket1 = new Socket(args[0], Integer.parseInt(args[1]));
				// Creating output stream to write data to the server
				PrintWriter out = new PrintWriter(Client.socket1
						.getOutputStream());
				// Checking if the first character of filepath is "/"
				// Adding '/' if filepath does not contain the slash
				if (args[3].charAt(0) != '/' && args[2].equals("GET"))
					temp = "/" + args[3];
				else
					temp = args[3];
				// Checking if command type is GET
				if (args[2].equals("GET")) 
				{
					// Writing to server the GET Command followed by path and HTTP version
					String pass = "GET " + temp + " HTTP/1.1";
					out.println(pass);
					out.flush();
					// Creating a buffered Reader to read input data from the server
					BufferedReader input = new BufferedReader(
							new InputStreamReader(socket1.getInputStream()));
					// Displaying the data on the console
					for (int i; (i = input.read()) != -1;)
						System.out.println((char) i + input.readLine());
				}
				if (args[2].equals("PUT")) 
				{
					// Writing to server the PUT Command followed by filepath and HTTP version
					String pass = "PUT " + args[3] + " HTTP/1.1";
					out.println(pass);
					// Passing the socket portnumber to server
					out.println("Host: localhost:" + socket1.getLocalPort());
					out.println("Content-Type: text/html");
					out.flush();
					// Creating a buffered Reader to read input data from the server
					BufferedReader input = new BufferedReader(
							new InputStreamReader(socket1.getInputStream()));
					// Displaying the data on the console
					for (int i; (i = input.read()) != -1;)
						System.out.println((char) i + input.readLine());
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}