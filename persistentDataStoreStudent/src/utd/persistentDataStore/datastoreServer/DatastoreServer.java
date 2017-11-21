package utd.persistentDataStore.datastoreServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.apache.log4j.Logger;


import utd.persistentDataStore.datastoreServer.commands.*;
import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DatastoreServer
{
	private static Logger logger = Logger.getLogger(DatastoreServer.class);

	static public final int port = 10023;

	public void startup() throws IOException
	{
		logger.debug("Starting Service at port " + port);

		ServerSocket serverSocket = new ServerSocket(port);

		InputStream inputStream = null;
		OutputStream outputStream = null;
		while (true) {
			try {
				logger.debug("Waiting for request");
				// The following accept() will block until a client connection 
				// request is received at the configured port number
				Socket clientSocket = serverSocket.accept();
				logger.debug("Request received");

				inputStream = clientSocket.getInputStream();
				outputStream = clientSocket.getOutputStream();

				ServerCommand command = dispatchCommand(inputStream);
				logger.debug("Processing Request: " + command);
				command.setInputStream(inputStream);
				command.setOutputStream(outputStream);
				command.run();
				 
				StreamUtil.closeSocket(inputStream);
			}
			catch (ServerException ex) {
				String msg = ex.getMessage();
				logger.error("Exception while processing request. " + msg);
				StreamUtil.sendError(msg, outputStream);
				StreamUtil.closeSocket(inputStream);
			}
			catch (Exception ex) {
				logger.error("Exception while processing request. " + ex.getMessage());
				ex.printStackTrace();
				StreamUtil.closeSocket(inputStream);
			}
		}
	}

	private ServerCommand dispatchCommand(InputStream inputStream) throws ServerException, IOException
	{
		ServerCommand command;
		
		//Read the intent of the command from the first portion of the input stream
		String commandString = StreamUtil.readLine(inputStream);
		
		if ("read".equalsIgnoreCase(commandString)) {		//if the client intent is to read a specific record
			command = new ReadCommand();

		}
		else if ("write".equalsIgnoreCase(commandString)) {	//if the client intent is to write/update a specific record
			command = new WriteCommand();

		}
		else if ("directory".equalsIgnoreCase(commandString)) {	//if the client intent is to list all the stored records
			command = new DirectoryCommand();
			
		}
		else if ("delete".equalsIgnoreCase(commandString)) {	//if the client intent is to delete a specific record
			command = new DeleteCommand();
		}
		else {							//if the intent is not recognized by the server
			throw new ServerException("Unknown Request: " + commandString);
		}
		
		//return the appropriate command to be run
		return command;
	}

	public static void main(String args[])
	{

		DatastoreServer server = new DatastoreServer();
		try {
			server.startup();
		}
		catch (IOException ex) {
			logger.error("Unable to start server. " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
