package utd.persistentDataStore.datastoreServer.commands;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuilder;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class ReadCommand extends ServerCommand {

	public ReadCommand() {
		// TODO Auto-generated constructor stub
		// The read operation will retrieve from the server data previously saved using
		// the provided name. If the name is not found, the response message will
		// contain an error message. See the protocol section for a description of the
		// message structure.
	}

	@Override
	public void run() throws IOException, ServerException {
		// TODO Auto-generated method stub
		String title = StreamUtil.readLine(inputStream);
		logger.debug("Reading file: " + title);

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
		}catch(

	Exception e)
	{
		String temp = e.toString() + ":" + title + "\n";
		StreamUtil.writeLine(temp, outputStream);
		logger.debug(temp);
	}

}

}
