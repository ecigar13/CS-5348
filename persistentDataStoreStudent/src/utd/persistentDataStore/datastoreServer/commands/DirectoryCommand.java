package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import java.io.File;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class DirectoryCommand extends ServerCommand {

	public DirectoryCommand() {
		// TODO Auto-generated constructor stub
		// /The directory operation will respond with a list of names that are currently
		// stored by the service. See the protocol section for a description of the
		// message structure.
	}

	@Override
	public void run() throws IOException, ServerException {
		// directory() implementation
		logger.debug("Directory request.\n");

		// get the path
		String dir = System.getProperty("user.dir");
		
		// create array of File objects
		File[] files = new File(dir).listFiles();

		// write to stream in specified format
		this.sendOK();
		StreamUtil.writeLine(String.valueOf(files.length), outputStream);
		logger.debug("Files: "+files.length);
		for (File file : files) {
			StreamUtil.writeLine(file.getName(), outputStream);
			logger.debug(file.getName());
		}
		
		logger.debug("Finished directory request.");

		// End of implementation
	}

}
