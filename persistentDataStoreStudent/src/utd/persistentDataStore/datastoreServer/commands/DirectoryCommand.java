package utd.persistentDataStore.datastoreServer.commands;

import java.io.IOException;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;
import utd.persistentDataStore.utils.FileUtil;
import org.apache.log4j.Logger;
import java.util.List;

/* The directory operation will respond with a list of names that are currently
 * stored by the service. See the protocol section for a description of the
 * message structure.
 */
public class DirectoryCommand extends ServerCommand {
	
	private static Logger logger = Logger.getLogger(DirectoryCommand.class);

	@Override
	public void run() throws IOException, ServerException {
		
		logger.debug("Directory request.\n");
		
		//Obtain the list of all the records
		List<String> file_list = FileUtil.directory();
		
		sendOK();						//Append OK to the output stream if the operation was successful
		StreamUtil.writeLine(file_list.size() + "", outputStream);	//Append the number of records to the output stream next
		//Append names of all the records successively, present in the list
		for(String s : file_list){
			StreamUtil.writeLine(s, outputStream);
			logger.debug("Listing " + s);
		}
		
		logger.debug("Finished directory request.");
			
		/*
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
		*/
		
		// End of implementation
	}

}
