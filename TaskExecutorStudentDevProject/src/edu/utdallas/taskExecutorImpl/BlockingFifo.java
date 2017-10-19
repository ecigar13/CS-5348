package edu.utdallas.taskExecutorImpl;

import java.util.concurrent.locks.ReentrantLock;

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

	boolean isEmpty() {
		if (count == 0)
			return true;
		else
			return false;
	}

	boolean isFull() {
		if (count == size)
			return true;
		else
			return false;
	}

	void put(Task task) {

		synchronized (this) {
			while (this.isFull()) {
				try {
					// Thread.yield();

					notFull.wait();

				} catch (InterruptedException e) {
					notEmpty.notify();

				}
			}

			boundedBuffer[nextIn] = task;
			count++;
			nextIn = (nextIn + 1) % size;
			try {
				Thread.sleep(50);
			} catch (Exception e) {
			}

			notify();

		}

	}

	Task take() {

		synchronized (this) {
			Task temp = boundedBuffer[nextOut];
			while (this.isEmpty()) {
				try {
					Thread.sleep(0);
					notEmpty.wait();
				} catch (Exception e) {
					notFull.notify();
				}
			}

			boundedBuffer[nextOut] = null;
			nextOut = (nextOut + 1) % size;
			count--;
			notify();

			return temp;

		}
	}
}