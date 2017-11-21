package utd.persistentDataStore.datastoreClient;

import java.net.InetAddress;
import java.util.List;
import org.apache.log4j.Logger;
import utd.persistentDataStore.utils.StreamUtil;

// Additional imports added by team
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
// End of additional imports


public class DatastoreClientImpl implements DatastoreClient {
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	//IP address of the server and its port number offering the corresponding services
	private InetAddress address;
	private int port;

	//The client only caters to a fixed set of services offered at a particular port on a particular server with a unique address
	public DatastoreClientImpl(InetAddress address, int port) throws IOException {
		this.address = address;
		this.port = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.
	 * String, byte[])
	 *
	 * @description The write functionality requests the server to add a new record with its satellite data from the function arguments
	 */
	@Override
	public void write(String name, byte data[]) throws ClientException, ConnectionException {
		logger.debug("Executing Write Operation");
		// Team Write Implementation
		try {
			//Establish a two-way socket connection with the server
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);
			
			//The client will write requests to output, and receive responses on input
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();
			
			//Write a formatted request to the output stream towards the server, with parameters separated by a delimiter
			StreamUtil.writeLine("write", output);	//A new-line delimiter will be automatically added by the writeLine method
			StreamUtil.writeLine(name, output);
			StreamUtil.writeLine(data.length, output);
			StreamUtil.writeData(data, output);s

			//Obtain the response (possibly acknowledgement) from the server for the corresponding request
			String response = StreamUtil.readLine(input);
			
			//Close the socket connection as we do not need it anymore
			socket.close();
			
			//Throw an exception if an unexpected response was received
			if (!response.equalsIgnoreCase("ok")) {
				throw new ClientException(response);
			}

		} catch (IOException e) {
			throw new ConnectionException(e.getMessage(), e);
		}
		// End of Write Implementation
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.
	 * String)
	 *
	 * @description The read functionality attempts to read the data contained within the record whose name is passed as an argument
	 */
	@Override
	public byte[] read(String name) throws ClientException, ConnectionException {
		logger.debug("Executing Read Operation");
		// Team Read Implementation
		try {
			//Establish a two-way socket connection to the server
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);

			//The client will write requests to output, and receive responses on input
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();
			
			//Write a formatted read-request on the output stream for the server to obtain
			StreamUtil.writeLine("read", output);
			StreamUtil.writeLine(name, output);
			
			//Obtain the server response on the input stream and throw an exception if an unexpected response is obtained from the server
			String response = StreamUtil.readLine(input);
			if (!response.equalsIgnoreCase("ok")) {
				socket.close();
				throw new ClientException(response);
			}
			
			//Obtain and package the required info from the response into a byte-array and return to the calling method
			int size = Integer.parseInt(StreamUtil.readLine(input));
			byte[] temp = StreamUtil.readData(size, input);
			
			//Close the socket connection before returning
			socket.close();
			
			return temp;

		} catch (IOException e) {
			throw new ConnectionException(e.getMessage(), e);
		}
		// End of Read Implementation
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.
	 * String)
	 *
	 * @description The delete functionality removes a record whose name is passed as an argument
	 */
	@Override
	public void delete(String name) throws ClientException, ConnectionException {
		logger.debug("Executing Delete Operation");
		// Team Delete Implementation
		try {
			//Establish a two-way socket connection to the server
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);
			
			//The client will write requests to output, and receive responses on input
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();
			
			//Write a formatted delete-request to be sent to the server at the other end of the output stream
			StreamUtil.writeLine("delete", output);
			StreamUtil.writeLine(name, output);
		
			//Obtain the response from the server on the input stream
			String response = StreamUtil.readLine(input);
			
			//Close the socket connection as we do not need it anymore
			socket.close();
			
			//Throw an exception if an unexpected response was obtained from the server
			if (!response.equalsIgnoreCase("ok")) {
				throw new ClientException(response);
			}
			
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage(), e);
		}
		// End of Delete Implementation
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 *
	 * @description The List functionality returns all the records stored at the server in a List of String-s
	 */
	@Override
	public List<String> directory() throws ClientException, ConnectionException {
		logger.debug("Executing Directory Operation");
		// Team Directory Implementation
		try {
			//Establish a two-way socket connection to the server
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);
			
			//The client will write requests to output, and receive responses on input
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();
			
			//Write a formatted listing-request to the output stream for the server to obtain
			StreamUtil.writeLine("directory\n", output);
			
			//Obtain the response of the server in return for the request
			String response = StreamUtil.readLine(input);

			//Throw an exception if an unexpected response is obtained from the server
			if (!response.equalsIgnoreCase("ok")) {
				socket.close();	//close the socket before throwing the exception
				throw new ClientException(response);
			}
			
			//Parse and package the info to be returned, from the server-response
			int numFiles = Integer.parseInt(StreamUtil.readLine(input));
			List<String> files = new ArrayList<>();
			for (int i = 0; i < numFiles; i++) {
				files.add(StreamUtil.readLine(input));
			}
			
			//Close the socket before returning
			socket.close();
			
			return files;
			
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage(), e);
		}
		// End of Directory Implementation
	}

}
