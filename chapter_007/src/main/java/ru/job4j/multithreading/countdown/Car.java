package  ru.job4j.multithreading.countdown;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.11.17;
 * @version $Id$
 * @since 0.1
 */
public class Car implements Runnable {
    private int carNumber;
    private  int carSpeed;
    private Rase rase;

    public Car(int carNumber, int carSpeed, Rase rase) {
        this.carNumber = carNumber;
        this.carSpeed = carSpeed;
        this.rase = rase;
    }

    @Override
    public void run() {
        try {
            System.out.println(String.format("Автомобиль %d подьехалк стартовой прямой", carNumber));
            rase.getStart().countDown();
            rase.getStart().await();
            Thread.sleep(rase.getTrackLength() / carSpeed);
            System.out.println(String.format("Автомобиль %d финишировал", carNumber));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
