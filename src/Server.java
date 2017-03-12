import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	protected Socket client;
	String separate[];
	String commandType = null;
	String filepath = null;

	public static void main(String[] args) throws IOException 
	{
		try 
		{
			// Creating socket for server
			ServerSocket server = new ServerSocket(Integer.parseInt(args[0]),
					1, InetAddress.getByName("localhost"));
			// Printing on the console when the server is executed
			System.out.println("Server: Started");
			// Accepting multiple client connections
			while (true) 
			{
				// Looking for the client connections
				Socket clientSocket = server.accept();
				// Creating a thread for each client
				new Thread(new Server(clientSocket)).start();
			}
		}
		// Checking for array index out of bounds exception which arises when user has not given any port number for the server
		catch (ArrayIndexOutOfBoundsException a) 
		{
			a.printStackTrace();
			System.out
					.println("enter only port number of server while execution");
		} 
		// Specifying any other exceptions
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public Server(Socket client) 
	{
		this.client = client;
	}

	public void run() {
		try {
			String fileData = null;
			// Reading data from client
			BufferedReader clientinput = new BufferedReader(
					new InputStreamReader(this.client.getInputStream()));
			// Reading the GET or PUT command passed from the client
			String command = clientinput.readLine();
			// Separating the command obtained from client with spaces and assigning to an array
			separate = command.split(" ");
			// First index holds the command
			// Second index holds the file path
			// Third index holds the HTTP version
			commandType = separate[0];
			filepath = separate[1];
			// Verifying if the command is 'PUT'
			if (commandType.equals("PUT")) 
			{
				// Print writer object to write response to client
				PrintWriter out = new PrintWriter(client.getOutputStream());
				FileInputStream fis = null;
				Date date = new Date();
				filepath = separate[1];
				try 
				{
					commandType = clientinput.readLine();
					fileData = clientinput.readLine();
					FileWriter fileWriter;
					fileWriter = new FileWriter(new File(filepath));
					File check = new File(filepath);
					boolean existsfile = check.exists();
					fis = new FileInputStream(filepath);
					int content;
					// Checking if the specified file exists or not
					if (existsfile) { // Saving the file locally and writing the
										// response to client
						fileWriter.write(fileData);
						out
								.println("File saved successfully\nHTTP/1.1 200 OK \n"
										+ "Date: "
										+ date.toString()
										+ "\n"
										+ "Content-Type: text/html"
										+ "\n"
										+ "Content-Length: "
										+ fis.available()
										+ "\nConnection: Keep-Alive"
										+ "\n"
										+ "<!DOCTYPE html>\n<html>\n<body>\n<h1>\nFile Created");
						// Writing content of the file to client
						while ((content = fis.read()) != -1) 
						{
							out.print((char) content);
						}
						// Closing the tags
						out.println("</h1>\n</body>\n</html>");
						// Closing the writer objects and client connection
						out.flush();
						out.close();
						this.client.close();
						fileWriter.close();
					}
				} 
				// Displaying exceptions
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
            // Verifying if command type is GET
			if (commandType.equals("GET")) {
				// Writing response to client using print writer
				PrintWriter out = new PrintWriter(client.getOutputStream());
				FileInputStream fis = null;
				Date date = new Date();
				filepath = separate[1];
				try 
				{
					File check = new File(filepath);
					boolean exists = check.exists();
					fis = new FileInputStream(filepath);
					int content;
					// Verifying if the file exists 
					if (exists == true) {
						// Creating and passing HTTP response to the client
						out.println("HTTP/1.1 200 OK \n" + "Date: "
								+ date.toString() + "\n"
								+ "Content-Type: text/html" + "\n"
								+ "Content-Length: " + fis.available()
								+ "\nConnection: Keep-Alive" + "\n"
								+ "<!DOCTYPE html>\n<html>\n<body>\n<h1>");
						// Reading the content if the file exists and passing it to the client
						while ((content = fis.read()) != -1) 
						{
							out.print((char) content);
						}
						// Closing the tags
						out.println("</h1>\n</body>\n</html>");
						// Closing the writer and client connections
						out.flush();
						out.close();
						this.client.close();
					}
				} 
				// Displaying any connections
				catch (Exception e)
				{ // If file does not exist then passing the not found response to the client
					out
							.println("HTTP/1.1 404 OK \n"
									+ "Date: "
									+ date.toString()
									+ "\nHost: localhost:"
									+ client.getLocalPort()
									+ "\n"
									+ "Content-Type: text/html"
									+ "\n"
									+ "Connection: Closed"
									+ "\n"
									+ "<!DOCTYPE html>\n<html>\n<body>\n<h1>\nFile Not Found");
					out.println("</h1>\n</body>\n</html>");
					// Closing the writer and client connections
					out.flush();
					out.close();
					this.client.close();
				}
			}
		}
		// Displaying any Exceptions
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}