package utd.persistentDataStore.datastoreServer.commands;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuilder;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;
import utd.persistentDataStore.utils.FileUtil;

/* The read operation will retrieve from the server data previously saved using
 * the provided name. If the name is not found, the response message will
 * contain an error message. See the protocol section for a description of the
 * message structure.
 */
public class ReadCommand extends ServerCommand {
	

	@Override
	public void run() throws IOException, ServerException {
		
		//Read the name of the record which needs to be read from the inputstream
		String title = StreamUtil.readLine(inputStream);
		logger.debug("Reading file: " + title);
		
		//read the record in a temporary byte array, an exception will be thrown by readData if the record is not found
		byte[] tmp = FileUtil.readData(title);

		sendOK();					//if the read was successful, append an OK to the output stream
		StreamUtil.writeLine(tmp.length, outputStream);	//append the length of the data corresponding to the record
		StreamUtil.writeData(tmp, outputStream);	//append the data corresponding to the record
		
		//Log the read parameters
		logger.debug("Bytes: "+ tmp.length);
		logger.debug("Message: "+ tmp);
		logger.debug("Finished reading message.\n");
		
		/*
		try {
			// will throw exception if file doesn't exist
			FileReader f = new FileReader(title);
			BufferedReader r = new BufferedReader(f);

			//if FileReader didn't throw exception, create response message
			String length = r.readLine();
			byte[] arr = new byte[Integer.valueOf(length)];
			for(int i = 0;i<arr.length;i++)
				arr[i]= Byte.valueOf(r.readLine());

			//write the message in specified format
			this.sendOK();
			StreamUtil.writeLine(length, outputStream);
			StreamUtil.writeData(arr, outputStream);
			logger.debug("Bytes: "+arr.length);
			logger.debug("Message: "+arr);
			
			//close file streams
			logger.debug("Finished reading message.\n");
			r.close();
			f.close();
		} catch(Exception e)
		{
			String temp = e.toString() + ":" + title + "\n";
			StreamUtil.writeLine(temp, outputStream);
			logger.debug(temp);
		}
		*/
		
	}
}
