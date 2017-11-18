package utd.persistentDataStore.datastoreClient;

import java.net.InetAddress;
import java.util.List;
// Additional imports added by team
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
// End of additional imports

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;

public class DatastoreClientImpl implements DatastoreClient {
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;

	public DatastoreClientImpl(InetAddress address, int port) throws IOException {
		this.address = address;
		this.port = port;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.
	 * String, byte[])
	 */
	@Override
	public void write(String name, byte data[]) throws ClientException, ConnectionException {
		logger.debug("Executing Write Operation");
		// Team Write Implementation
		try {
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			StreamUtil.writeLine("write\n", output);
			StreamUtil.writeLine(name + "\n", output);
			StreamUtil.writeLine(data.length + "\n", output);
			StreamUtil.writeData(data, output);

			String response = StreamUtil.readLine(input);
			if (!response.equalsIgnoreCase("ok")) {
				socket.close();
				throw new ClientException(response);
			}
			socket.close();
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
	 */
	@Override
	public byte[] read(String name) throws ClientException, ConnectionException {
		logger.debug("Executing Read Operation");
		// Team Read Implementation
		try {
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);

			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			StreamUtil.writeLine("read\n", output);
			StreamUtil.writeLine(name + "\n", output);
			String response = StreamUtil.readLine(input);

			if (!response.equalsIgnoreCase("ok")) {

				socket.close();
				throw new ClientException(response);
			}
			
			int size = Integer.parseInt(StreamUtil.readLine(input));
			byte[] temp = StreamUtil.readData(size, input);
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
	 */
	@Override
	public void delete(String name) throws ClientException, ConnectionException {
		logger.debug("Executing Delete Operation");
		// Team Delete Implementation
		try {
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			StreamUtil.writeLine("delete\n", output);
			StreamUtil.writeLine(name, output);
			String response = StreamUtil.readLine(input);
			if (!response.equalsIgnoreCase("ok")) {
				throw new ClientException(response);
			}
			socket.close();
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage(), e);
		}
		// End of Delete Implementation
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
	 */
	@Override
	public List<String> directory() throws ClientException, ConnectionException {
		logger.debug("Executing Directory Operation");
		// Team Directory Implementation
		try {
			Socket socket = new Socket();
			SocketAddress socketAddr = new InetSocketAddress(address, port);
			socket.connect(socketAddr);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			StreamUtil.writeLine("directory\n", output);
			String response = StreamUtil.readLine(input);

			if (!response.equalsIgnoreCase("ok")) {
				throw new ClientException(response);
			}
			int numFiles = Integer.parseInt(StreamUtil.readLine(input));
			List<String> files = new ArrayList<>();
			for (int i = 0; i < numFiles; i++) {
				files.add(StreamUtil.readLine(input));
			}
			socket.close();
			return files;
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage(), e);
		}
		// End of Directory Implementation
	}

}

// package utd.persistentDataStore.datastoreClient;
//
// import java.net.InetAddress;
// import java.util.List;
//// Additional imports added by team
// import java.io.InputStream;
// import java.io.OutputStream;
// import java.io.IOException;
// import java.net.InetSocketAddress;
// import java.net.Socket;
// import java.net.SocketAddress;
// import java.util.ArrayList;
//// End of additional imports
//
// import org.apache.log4j.Logger;
//
// import utd.persistentDataStore.utils.StreamUtil;
//
// public class DatastoreClientImpl implements DatastoreClient
// {
// private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);
//
// private InetAddress address;
// private int port;
//
// public DatastoreClientImpl(InetAddress address, int port)
// {
// this.address = address;
// this.port = port;
// }
//
// /* (non-Javadoc)
// * @see
// utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String,
// byte[])
// */
// @Override
// public void write(String name, byte data[]) throws ClientException,
// ConnectionException
// {
// logger.debug("Executing Write Operation");
// // Team Write Implementation
// try {
// Socket socket = new Socket();
// SocketAddress socketAddr = new InetSocketAddress(address,port);
// socket.connect(socketAddr);
// InputStream input = socket.getInputStream();
// OutputStream output = socket.getOutputStream();
// StreamUtil.writeLine("write\n", output);
// StreamUtil.writeLine(name + "\n", output);
// StreamUtil.writeLine(data.length + "\n", output);
// StreamUtil.writeData(data, output);
// String response = StreamUtil.readLine(input);
// if(!response.equalsIgnoreCase("ok")) {
// throw new ClientException(response);
// }
// } catch(IOException e) {
// throw new ConnectionException(e.getMessage(), e);
// }
// // End of Write Implementation
// }
//
// /* (non-Javadoc)
// * @see
// utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
// */
// @Override
// public byte[] read(String name) throws ClientException, ConnectionException
// {
// logger.debug("Executing Read Operation");
// // Team Read Implementation
// try {
// Socket socket = new Socket();
// SocketAddress socketAddr = new InetSocketAddress(address,port);
// socket.connect(socketAddr);
// InputStream input = socket.getInputStream();
// OutputStream output = socket.getOutputStream();
// StreamUtil.writeLine("read\n", output);
// StreamUtil.writeLine(name + "\n",output);
// String response = StreamUtil.readLine(input);
// if(!response.equalsIgnoreCase("ok")) {
// throw new ClientException(response);
// }
// int size = Integer.parseInt(StreamUtil.readLine(input));
// return StreamUtil.readData(size, input);
// } catch(IOException e) {
// throw new ConnectionException(e.getMessage(), e);
// }
// // End of Read Implementation
// }
//
// /* (non-Javadoc)
// * @see
// utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
// */
// @Override
// public void delete(String name) throws ClientException, ConnectionException
// {
// logger.debug("Executing Delete Operation");
// // Team Delete Implementation
// try {
// Socket socket = new Socket();
// SocketAddress socketAddr = new InetSocketAddress(address,port);
// socket.connect(socketAddr);
// InputStream input = socket.getInputStream();
// OutputStream output = socket.getOutputStream();
// StreamUtil.writeLine("delete\n", output);
// StreamUtil.writeLine(name, output);
// String response = StreamUtil.readLine(input);
// if(!response.equalsIgnoreCase("ok")) {
// throw new ClientException(response);
// }
// } catch(IOException e) {
// throw new ConnectionException(e.getMessage(), e);
// }
// // End of Delete Implementation
// }
//
// /* (non-Javadoc)
// * @see utd.persistentDataStore.datastoreClient.DatastoreClient#directory()
// */
// @Override
// public List<String> directory() throws ClientException, ConnectionException
// {
// logger.debug("Executing Directory Operation");
// // Team Directory Implementation
// try {
// Socket socket = new Socket();
// SocketAddress socketAddr = new InetSocketAddress(address,port);
// socket.connect(socketAddr);
// InputStream input = socket.getInputStream();
// OutputStream output = socket.getOutputStream();
// StreamUtil.writeLine("directory\n", output);
// String response = StreamUtil.readLine(input);
// if(!response.equalsIgnoreCase("ok")) {
// throw new ClientException(response);
// }
// int numFiles = Integer.parseInt(StreamUtil.readLine(input));
// List<String> files = new ArrayList<>();
// for(int i=0; i<numFiles; i++) {
// files.add(StreamUtil.readLine(input));
// }
// return files;
// } catch(IOException e) {
// throw new ConnectionException(e.getMessage(), e);
// }
// // End of Directory Implementation
// }
//
// }
