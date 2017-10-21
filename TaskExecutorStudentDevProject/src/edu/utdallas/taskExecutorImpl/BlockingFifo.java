package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class BlockingFifo {
	// Use array (requirement 4)
	private Task[] boundedBuffer;
	private int size, nextIn, nextOut, count;
	static Object notFull, notEmpty;

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
		return count == size;
	}

	public void put(Task task) {

		synchronized (notFull) {
			try {
				while (isFull()) {
					// Thread.yield();
					notFull.wait();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			boundedBuffer[nextIn] = task;
			count++;
			nextIn = (nextIn + 1) % size;
			//System.out.println("Count after add: " + count);
		}

		synchronized (this) {

		}

		try {
			Thread.sleep(0);
		} catch (Exception e) {
		}

		synchronized (notEmpty) {
			notEmpty.notify();
		}

	}

	public Task take() {
		Task temp;
		synchronized (notEmpty) {

			while (isEmpty()) {
				try {
					notEmpty.wait();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			temp = boundedBuffer[nextOut];
			// boundedBuffer[nextOut] = null;
			nextOut = (nextOut + 1) % size;
			count--;
			//System.out.println("Count after take: " + count);
		}

		synchronized (this) {

		}
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
		synchronized (notFull) {
			notFull.notify();
		}

		return temp;

	}
}
