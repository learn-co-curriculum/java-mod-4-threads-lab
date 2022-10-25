import java.util.stream.IntStream;

//DO NOT MODIFY
public class Dog {
    private String name;
    private boolean isWaggingTail;
    private boolean likesBaths;

    public Dog(String name, boolean likesBaths) {
        this.name = name;
        this.likesBaths = likesBaths;
    }

    public void eatTreats(int numTreats) {
        IntStream.rangeClosed(1,numTreats).forEach( i -> System.out.println(name + " eats treat #" + i) );
        isWaggingTail = true;
        System.out.println("After treats " + name + " wagging is " + isWaggingTail);
    }

    public void takeBath() {
        isWaggingTail = likesBaths;
        System.out.println("After bath " + name + " wagging is " + isWaggingTail);
    }

}