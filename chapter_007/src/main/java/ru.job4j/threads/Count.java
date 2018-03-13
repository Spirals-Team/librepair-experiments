package ru.job4j.threads;

public class Count {

    public static void main(String[] args) throws InterruptedException {

        String text = Count.getText();

        Thread threadCont = new Thread(new CountChar(text));

        Thread threadCurrTime = new Thread(new Time(10));

        long startTime = System.currentTimeMillis();


        threadCurrTime.start();

        threadCont.start();


        //WORK ON BUGS task 1019
//        хорошо, можно закрывать задачу.
//
//        Вместо засыпания в main можно было сделать join.
//        while (threadCont.isAlive() && threadCurrTime.isAlive()) {
//            try {
//                Thread.sleep(1L); // выключаем поток main, чтобы в threadCont.run() что-то успело посчитаться
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        threadCurrTime.join(); //действительно главный поток ждет ждет окончания выполнения потока threadCurrTime



        threadCont.interrupt();

        long endTime = System.currentTimeMillis();

        long timeConsumedMillis = endTime - startTime;

        System.out.println("all time: " + timeConsumedMillis + "ms");
    }

    public static final class Time implements Runnable {

        public static int time;

        public Time(int time) {

            this.time = time;
        }

        @Override
        public void run() {
            System.out.println("Замер начинается на время: " + time + "ms");
            sleepSomeTime(this.time);
            System.out.println("Замер закончился на время " + time + "ms");
            System.out.println("Завершение работы потока замера времени");
        }

        private void sleepSomeTime(int time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                System.out.println("Замер прерван");
            }
        }
    }

    public static final class CountChar extends Thread {
        private final String text;

        public CountChar(String text) {
            this.text = text;
        }

        @Override
        public void run() {

            char[] chars = text.toCharArray();

            for (int ind = 0; ind < chars.length; ind++) {
                if (!Thread.interrupted()) {
                    System.out.println("Thread Count chars = " + ind);
                } else {
                    System.out.println("CountChar Interrupted!");
                    return;
                }

            }
        }
    }

    public static final class ShowStart implements Runnable {

        @Override
        public void run() {
            System.out.println("start");
        }
    }

    public static final class ShowFinish implements Runnable {

        @Override
        public void run() {
            System.out.println("finish");
        }
    }

    public static final class CountTheNumberOfWords implements Runnable {
        private final String text;

        public CountTheNumberOfWords(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            char[] chars = text.toCharArray();

            for (int ind = 0; ind < chars.length; ind++) {
                System.out.println("Thread 1 CountTheNumberOfWords = " + ind);
            }
        }
    }

    public static final class CountTheNumberOfSpaces implements Runnable {
        private final String text;

        public CountTheNumberOfSpaces(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            char[] chars = text.toCharArray();

            for (int ind = 0; ind < chars.length; ind++) {
                System.out.println("Thread 2 CountTheNumberOfSpaces = " + ind);
            }
        }
    }

    public static String getText() {

        return

                "Описание[править | править вики-текст]\n"
                        +
                        "Сутью многопоточности является квазимногозадачность на уровне одного исполняемого процесса, то есть все потоки выполняются в адресном пространстве процесса. Кроме этого, все потоки процесса имеют не только общее адресное пространство, но и общие дескрипторы файлов. Выполняющийся процесс имеет как минимум один (главный) поток.\n"
                        +
                        "Многопоточность (как доктрину программирования) не следует путать ни с многозадачностью, ни с многопроцессорностью, несмотря на то, что операционные системы, реализующие многозадачность, как правило, реализуют и многопоточность.\n"
                        +
                        "К достоинствам многопоточности в программировании можно отнести следующее:\n"
                        +
                        "Упрощение программы в некоторых случаях за счёт использования общего адресного пространства.\n"
                        +
                        "Меньшие относительно процесса временны́е затраты на создание потока.\n"
                        +
                        "Повышение производительности процесса за счёт распараллеливания процессорных вычислений и операций ввода-вывода.\n"
                        +
                        "Аппаратная реализация[править | править вики-текст]\n"
                        +
                        "На обычном процессоре управление потоками осуществляется операционной системой. Поток исполняется до тех пор, пока не произойдёт аппаратное прерывание, системный вызов или пока не истечёт отведённое для него операционной системой время. После этого процессор переключается на код операционной системы, который сохраняет состояние потока (его контекст) или переключается на состояние другого потока, которому тоже выделяется время на исполнение. При такой многопоточности достаточно большое количество тактов процессора тратится на код операционной системы, переключающий контексты. Если поддержку потоков реализовать аппаратно, то процессор сам сможет переключаться между потоками, а в идеальном случае - выполнять несколько потоков одновременно за каждый такт. Для операционной системы и пользователя один такой физический процессор будет виден как несколько логических процессоров.\n"
                        +
                        "Различают две формы многопоточности, которые могут быть реализованы в процессорах аппаратно:\n"
                        +
                        "Временная многопоточность (англ. Temporal multithreading)\n"
                        +
                        "Одновременная многопоточность (англ. Simultaneous multithreading)";
    }
}