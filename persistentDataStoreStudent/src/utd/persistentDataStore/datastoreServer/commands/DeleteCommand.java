package utd.persistentDataStore.datastoreServer.commands;

import java.io.File;
import java.io.IOException;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DeleteCommand extends ServerCommand {

	public DeleteCommand() {
		// TODO Auto-generated constructor stub
		// The delete operation will remove data associated with the given name from the
		// service repository. See the protocol section for a description of the message
		// structure.
	}

	@Override
	public void run() throws IOException, ServerException {
		// TODO Auto-generated method stub
		String title = StreamUtil.readLine(inputStream);
		logger.debug("Delete request: "+title);
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

	}

}
