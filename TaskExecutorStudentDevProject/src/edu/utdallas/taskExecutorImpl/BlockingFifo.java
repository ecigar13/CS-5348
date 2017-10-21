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
		nextOut = nextIn = count = 0;
		notFull = new Object();
		notEmpty = new Object();
	}

	boolean isEmpty() {
		return count <= 0;
	}

	boolean isFull() {
		return count >= size;
	}

	public void put(Task task) {

		//This is the parallel part of the program.
		synchronized (this) {
			while (isFull()) {
				try {
					this.wait();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			boundedBuffer[nextIn] = task;
			count++;
			nextIn = (nextIn + 1) % size;

		}
		
		//Use sleep to test the case that producer is faster or slower than consumer thread.
		try {
			Thread.sleep(0);
		} catch (Exception e) {
		}

		
		//Once the add/take task is done, the thread can release the blocking array any time.
		synchronized (this) {
			this.notify();
		}

	}

	public Task take() {
		Task temp;

		// This is the parallel part
		synchronized (this) {
			while (isEmpty()) {
				try {
					this.wait();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			temp = boundedBuffer[nextOut];
			// boundedBuffer[nextOut] = null;
			nextOut = (nextOut + 1) % size;
			count--;

		}

		//Use sleep to test the case that producer is faster or slower than consumer thread.
		try {
			Thread.sleep(40);
		} catch (Exception e) {
		}

		//Once the add/take task is done, the thread can release the blocking array any time.
		//Potential issue is: if there are only 2 threads, this can lead to deadlock. 
//However, since we are working on only 1 object, 
		synchronized (this) {
			this.notify();
		}

		return temp;

	}
}
