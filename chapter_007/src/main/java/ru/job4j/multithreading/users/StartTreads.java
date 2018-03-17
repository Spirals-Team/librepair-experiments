package ru.job4j.multithreading.users;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 08.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class StartTreads {
    public static void main(String[] args) throws InterruptedException {
        User user1 = new User(1, 100);
        User user2 = new User(2, 100);
        UserStorage storage = new UserStorage();
        storage.add(user1);
        storage.add(user2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    storage.transfer(1, 2, 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    storage.transfer(2, 1, 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1500);
        System.out.println(user1.getAmount());
        System.out.println(user2.getAmount());

    }
}
