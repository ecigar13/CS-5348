package edu.utdallas.taskExecutorImpl;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor {

	// Maintain a thread-safe buffer which can contain up to 100 tasks
	//private ArrayBlockingQueue<Task> boundedBuffer=new ArrayBlockingQueue<Task>(100);
	private BlockingFifo boundedBuffer = new BlockingFifo(100);
	//private MyBlockingFIFO boundedBuffer = new MyBlockingFIFO(100);
	// Consumer threads will run this runnable
	Runnable TaskRunner = new Runnable() {
		@Override
		public void run() {
			while (true) {
				

					// Block till the queue is not empty (in own FIFO implementation
					// TODO

					try {
						// Pick and execute a task from the queue if possible, block if not
						Task task = boundedBuffer.take();
						task.execute();
					} catch (Exception e) {
						 System.out.println("Ignoring error in task: " + e.getMessage());
					}
					Thread.yield();
				
			}
		}
	};

	@Override
	public void addTask(Task task) {
		// Block till the queue is not full (in own FIFO implementation)
		// TODO

		try {
			// Add a task to the queue if possible, block here if not
			boundedBuffer.put(task);
		} catch (Exception e) {
			System.out.println("Error adding the task to the bounded buffer");
		}
	}

	public TaskExecutorImpl(int totalWorkerThreads) {
		// Start as many consumer threads as requested
		for (int i = 1; i <= totalWorkerThreads; i++) {
			Thread newThread = new Thread(TaskRunner);
			newThread.start();
		}
	}

}

//
//package edu.utdallas.taskExecutorImpl;
//
//import java.util.Deque;
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.concurrent.ArrayBlockingQueue;
//
//import edu.utdallas.taskExecutor.Task;
//import edu.utdallas.taskExecutor.TaskExecutor;
//
//public class TaskExecutorImpl implements TaskExecutor
//{
//
//	//Maintain a thread-safe buffer which can contain up to 100 tasks
//	private ArrayBlockingQueue<Task> boundedBuffer = new ArrayBlockingQueue<Task>(100);
//	
//	//Consumer threads will run this runnable
//	Runnable TaskRunner = new Runnable()
//	{
//		@Override
//		public void run() {
//			while(true) {
//				//Block till the queue is not empty (in own FIFO implementation
//				//TODO
//				
//				try {
//					//Pick and execute a task from the queue if possible, block if not
//					Task task = boundedBuffer.take();
//					task.execute();
//				}
//				catch(Exception e){
//					System.out.println("Ignoring error within task: " + e.getMessage());
//				}
//				Thread.yield();
//			}	
//		}
//	};
//	
//
//	public TaskExecutorImpl(int totalWorkerThreads) {
//		//Start as many consumer threads as requested
//		for(int i = 1; i <= totalWorkerThreads; i++) {
//			Thread newThread = new Thread(TaskRunner);
//			newThread.start();
//		}
//	}
//
//	@Override
//	public void addTask(Task task)
//	{
//		//Block till the queue is not full (in own FIFO implementation)
//		//TODO
//		
//		try {
//			//Add a task to the queue if possible, block here if not
//			boundedBuffer.put(task);
//		}
//		catch(Exception e) {
//			System.out.println("Error while adding the task to the bounded buffer");
//		}
//	}
//
//}
//
//
//
