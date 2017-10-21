package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class MyBlockingFIFO{
	
	/* Elements of the FIFO queue*/
	Task[] buffer;						// an array buffer for storing the elements
	int mIn, mOut;						// mIn and mOut respectively point to the both ends of the FIFO queue
	int mCapacity, mFifoSize;			// mCapacity is the total capacity of the queue, mFifoSize maintains the current size of the queue at any time
	static Object notfull, notempty;		// These monitors are utilized to block put and take calls when the queue is full or empty respectively
	
	
	
	/* FIFO class constructor:
	 * Initializes the queue primitives as well as the synchronization primitives
	 */
	public MyBlockingFIFO(int size) {
		// Queue elements initialization
		mCapacity = size;
		buffer = new Task[mCapacity];
		mIn = mOut = mFifoSize = 0;
		
		// Synchronization objects initialization
		notfull = new Object();
		notempty = new Object();
	}
	
	
	
	/* Return true if no elements are present in the queue, false if not*/
	private boolean isEmpty() {
		return (mFifoSize ==  0);
	}
	
	
	
	/* Return true if no more elements can be added to the queue without exceeding its initialized capacity, false otherwise*/
	private boolean isFull() {
		return (mFifoSize == mCapacity);
	}
	
	
	
	/* This removes and returns the element from the queue which was added to it the most recently
	 * If there are no elements in the queue, call to this function blocks until an element is added to the queue again
	 */
	public Task take() {
		
		//synchronized on the object to avoid IllegalMonitorStateException
		synchronized(notempty) {
			/*A while loop to ensure that execution is blocked on the object again, in case the 
			 *condition was violated while the process stayed in the ready queue, despite satisfying the condition
			 *earlier.
			 */
			while(isEmpty()) {
				//A try-catch block is required to call the wait() method
				try {
					//Wait on an object which gets notified, only when the producer thread adds an element to the queue
					notempty.wait();
				}catch(Exception e) {System.out.println(e);}
			}
		}
		
		
		Task taskToBeTaken;
		
		/* Synchronizing on the current instance of the queue, because concurrent multiple take requests are to be avoided, 
		 * as both can change the state of the variable out
		 */
		synchronized(this) {
			taskToBeTaken = buffer[mOut];
			mOut = (mOut + 1) % mCapacity;
			mFifoSize--;
		}
		
		
		/* Now that the variable out has been updated to make more space in the queue, notify that the queue is not full
		 * Tell the producer to try its luck by checking if it indeed stays not full, when it is scheduled
		 *
		 * Not included in the previous synchronized(this) block to avoid spurious wake-ups
		 * Even if the notfull.notify() call works below, no other thread synchronized on 'this' will work if current thread does not exits it
		 */
		synchronized(notfull) {
			notfull.notify();
		}

		
		//No synchronization required
		return taskToBeTaken;
	}
	
	
	
	/* This function lets an element add a task to the FIFO queue if it is not full
	 * If it is full however, the call blocks until an element is removed from the queue
	 */
	public void put(Task task) {

		//Synchronized on notfull to be able to call its wait() method without an IllegalMonitorStateException
		synchronized(notfull) {
			try {
				/* If block will suffice, as whenever notfull is notified, no thread can else can compete with the single producer thread
				 * to put a task to the queue
				 */
				if(isFull()) {
					notfull.wait();
				}
			} catch (Exception e) {System.out.println(e);}
		}
		
		
		/* At this point, the single producer thread can be certain that the queue is not full, as no thread else can fill it
		 * TODO: Check if this synchronized block can be removed, in a hope of increasing parallelism
		 */
		synchronized(this) {
			buffer[mIn] = task;
			mFifoSize++;
			mIn = (mIn + 1) % mCapacity;
		}
		
		
		//Notify a single consumer thread that the queue is now not empty
		synchronized(notempty) {
			notempty.notify();
		}		
	}
	
};