public class Task1 {

    //Fix the error in the main method
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " start");

        Thread printTask1 = new Thread(new RangePrinter(5, 10));
        Thread printTask2 = new Thread(new RangePrinter(14, 16));

        printTask1.run();
        printTask2.run();

        System.out.println(Thread.currentThread().getName() + " finished");

    }
}