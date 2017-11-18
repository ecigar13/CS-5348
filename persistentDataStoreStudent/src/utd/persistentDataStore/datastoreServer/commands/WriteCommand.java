package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;
import java.io.PrintWriter;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class WriteCommand extends ServerCommand {

	public WriteCommand() {
		// TODO Auto-generated constructor stub
		// The write operation will save the data contained in the byte[] associated
		// with the given name. See the protocol section for a description of the
		// message structure. The server will store the data in a file using the data’s
		// name for later retrieval.
	}

	@Override
	public void run() throws IOException, ServerException {
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
	}

}
