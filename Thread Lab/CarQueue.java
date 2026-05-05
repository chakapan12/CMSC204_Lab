import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarQueue {

	private Queue<Integer> queue;
	private Random rand;
	private Lock queueLock;

	public CarQueue() {
		queue = new LinkedList<>();
		rand = new Random();
		queueLock = new ReentrantLock();

		// Add 5 random directions into the queue at the beginning.
		for (int i = 0; i < 5; i++) {
			queue.add(rand.nextInt(4));
		}
	}

	public void addToQueue() {
		class QueueRunnable implements Runnable {

			@Override
			public void run() {
				try {

					// Keep adding direction while the program is running
					while (true) {

						// Lock before using the shared queue.
						queueLock.lock();

						try {
							// Add a random direction to the queue.
							// 0 = up, 1 = down, 2 = right, 3 = left
							queue.add(rand.nextInt(4));
						} finally {

							// Always unlock the queue, even if an error happens.
							queueLock.unlock();
						}

						// pause the queue for 1 second
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					System.out.println("Queue thread interrupted.");
				}

			}

		}

		Runnable r = new QueueRunnable();
		Thread t = new Thread(r);
		t.start();
	}

	public int deleteQueue() {

		// Lock before using the shared queue.
		queueLock.lock();

		try {
			// If the queue is empty, return a random direction.
			if (queue.isEmpty()) {
				return rand.nextInt(4);
			}
			// Remove and return the first direction from the queue.
			return queue.remove();
		} finally {
			// Always unlock the queue, even if an error happens.
			queueLock.unlock();
		}
	}

}
