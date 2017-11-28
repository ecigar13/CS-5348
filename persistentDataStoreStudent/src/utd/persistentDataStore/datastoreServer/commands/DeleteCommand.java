package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;
import utd.persistentDataStore.utils.FileUtil;
import org.apache.log4j.Logger;

/* The delete operation will remove data associated with the given name from the
 * service repository. See the protocol section for a description of the message
 * structure.
 */
public class DeleteCommand extends ServerCommand {
	
	private static Logger logger = Logger.getLogger(DeleteCommand.class);

	@Override
	public void run() throws IOException, ServerException {
		
		//Obtain the name of the record to be deleted
		String title = StreamUtil.readLine(inputStream);
		logger.debug("Delete request: "+title);
		
		//Attempt to delete the said record
		FileUtil.deleteData(title);	//it will throw an exception if the operation is unsuccessful
		
		sendOK();
		
		/*
		File f = new File(title);

		try {
			if (f.exists()) {

				// delete then write response message
				f.delete();
				this.sendOK();
				logger.debug("Finish deleting: " + title);
			} else {
				//if file doesn't exist, explain the issue.
				//another way to do this is throw an exception
				String temp = "File not found:" + title + "\n";
				StreamUtil.writeLine(temp, outputStream);
				logger.debug(temp);
			}
		} catch (SecurityException e) {
			//catch and write message explaining the issue
			StreamUtil.writeLine(e.getMessage(), outputStream);
		}
		*/

	}

}
