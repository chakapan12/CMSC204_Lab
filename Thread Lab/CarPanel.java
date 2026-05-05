import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * This component draws two car shapes.
 */
public class CarPanel extends JComponent {
	private Car car1;
	private int x, y, delay;
	private CarQueue carQueue;
	private int direction;

	CarPanel(int x1, int y1, int d, CarQueue queue) {
		delay = d;
		x = x1;
		y = y1;
		car1 = new Car(x, y, this);
		carQueue = queue;
	}

	public void startAnimation() {

		class AnimationRunnable implements Runnable {
			public void run() {
				try {

					{
						// Keep moving the car while the program is running.
						while (true) {
							// Get one direction from the shared queue.
							direction = carQueue.deleteQueue();

							// Move the car based on the direction.
							// 0 = up
							// 1 = down
							// 2 = right
							// 3 = left

							if (direction == 0) {
								y = y - 10;
							} else if (direction == 1) {
								y = y + 10;
							} else if (direction == 2) {
								x = x + 10;
							} else if (direction == 3) {
								x = x - 10;
							}

							// Boundary checking
							// Car is about 60 wide and 30 tall
							if (x < 0) {
								x = x + 20;
							}

							if (x > getWidth() - 60) {
								x = x - 20;
							}

							if (y < 0) {
								y = y + 20;
							}

							if (y > getHeight() - 30) {
								y = y - 20;
							}

							repaint();
							Thread.sleep(delay * 1000);
						}
					}

				} catch (InterruptedException exception) {

				} finally {

				}
			}
		}

		Runnable r = new AnimationRunnable();
		Thread t = new Thread(r);
		t.start();

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		car1.draw(g2, x, y);
	}
}