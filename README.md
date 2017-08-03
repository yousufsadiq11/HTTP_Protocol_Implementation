# HTTP_Protocol_Implementation
HTML CLient and Server that runs a Simplified version of HTTP/1.1.
Implemented both GET and PUT Commands using JAVA Socket programming.

GET: 
1. Connect to the Server via a TCP connection.
2. Submit a valid GET request to the server.
3. Read the server's response and display it.

PUT:
1. Connect to the Server via a TCP connection.
2. Submit a valid PUT request to the server.
3. Send the file to the server.
4. Wait for the server's reply.
5. Read the Server's response and display it.

Multithreaded Server has an infinite loop to listen for connections. Server can
be shut down with an termination Signal.
Server closes all the sockets beforing termination.

Client.java:
It implements the Client functionality for GET and PUT Commands using JAVA Socket programming.

Server.java:
Server Functionality has been implemented in it using JAVA Socket programming.

Running the program:
Client <Web URL> <port number> <Command either GET or PUT> <File name>
Ex: Client www.amazon.com 80 GET index.html
