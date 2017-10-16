package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class BlockingFifo {
	// Use array (requirement 4)
	private Task[] boundedBuffer;
	private int size, nextIn, nextOut, count;
	Object notFull, notEmpty;

	BlockingFifo(int s) {
		// initialize list with 100 tasks
		size = s;
		boundedBuffer = new Task[size];
		nextOut = 0;
		nextIn = 0;
		count = 0;
	}

	void put(Task task) {
		try {
			if (count == size) {
				notFull.wait();
			}
			synchronized (this) {
				nextIn = (nextIn + 1) % size;
				boundedBuffer[nextIn] = task;
				count++;
				notEmpty.notify();
			}
		} catch (Exception e) {
			System.out.println("Has InterruptedException");
		}

	}

	Task take() {

		if (count <= 0) {
			notEmpty.wait();
		}
		synchronized (this) {
			Task temp = boundedBuffer[nextOut];
			boundedBuffer[nextOut] = null;
			nextOut = (nextOut + 1) % size;
			count--;
			notFull.notify();
			return temp;
		}
	}
}
