package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;
import utd.persistentDataStore.utils.FileUtil;
import org.apache.log4j.Logger;

/* The write operation will save the data contained in the byte[] associated
 * with the given name. See the protocol section for a description of the
 * message structure. The server will store the data in a file using the data’s
 * name for later retrieval.
 */
public class WriteCommand extends ServerCommand {
	
	private static Logger logger = Logger.getLogger(WriteCommand.class);
	
	@Override
	public void run() throws IOException, ServerException {
		
		String record_name = StreamUtil.readLine(inputStream);			//Obtain the name of the record from the inputstream
		int data_size = Integer.parseInt(StreamUtil.readLine(inputStream));	//Obtain the number of bytes that are supposed to be received
		byte[] write_data = StreamUtil.readData(data_size, inputStream);	//will throw an exception if socket was not able to read as many bytes as promised 

		//write the record and the corresponding satellite data to the database
		FileUtil.writeData(record_name, write_data);
		
		//Send an OK message if the operation was successful
		sendOK();
		
		logger.debug("Finished writing message to " + record_name + "\n");
		
		/*
		try {
			// printWriter rarely throw exceptions. Only FileNotFound or SecurityException
			// create the file, will overwrite current file
			// can be improved by checking if the file exist
			String title = StreamUtil.readLine(inputStream);
			logger.debug("Write request to file: " + title);
			PrintWriter writer = new PrintWriter(title, "UTF-8");

			// read number of bytes to write
			String l = StreamUtil.readLine(inputStream);
			int length = Integer.parseInt(l);
			logger.debug("Writing bytes: " + l);
			writer.println(l);
			
			// write N bytes above as N lines
			byte[] temp = StreamUtil.readData(length, inputStream);
			for (byte i : temp) {
				// logger.debug("Writing: "+Byte.toString(i));
				writer.println(Byte.toString(i));
			}

			writer.close();

			// if everything is good then write response message
			this.sendOK();
			logger.debug("Finished writing message to " + title + "\n");

		} catch (Exception e) {
			StreamUtil.writeLine(e.toString(), outputStream);
		}
		*/
	}

}
