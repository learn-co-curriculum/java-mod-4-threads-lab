import java.util.stream.IntStream;

//DO NOT MODIFY
public class RangePrinter implements Runnable {
    int start;
    int end;

    public RangePrinter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " printing range " + start + " to " + end );
        IntStream.rangeClosed(start, end).
                forEach( (i) -> System.out.println(Thread.currentThread().getName() + " i = " + i ));
    }

}
