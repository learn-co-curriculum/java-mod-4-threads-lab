# Threads Lab

## Learning Goals

- Implement concurrent programs using multiple threads.
- Coordinate the execution of multiple threads.
- Synchronize access to shared objects.

## Task #1: Do not modify the `RangePrinter` class.

`RangePrinter` implements the `Runnable` functional interface.  

The `run()` method prints a sequence of numbers between
`start` and `end` (inclusive).  We can't change
the `run()` method signature to accept parameters, so the
`start` and `end` values are stored in instance variables.

The `run()` method calls the `IntStream.rangeClosed` method,
which generates a stream of integers in the specified range (inclusive).  

```java
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
        IntStream.rangeClosed(start, end)
                .forEach( (i) -> System.out.println(Thread.currentThread().getName() + " i = " + i ));
    }

}
```

`Task1` creates two threads by passing instances of the `RangePrinter`
class into the `Thread` constructor.
There is an error in the `main` method that prevents the two threads from executing
concurrently:

```java
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
```

Run the program several times and notice it always produces the same output.
The print statements always print "main" as the thread name,
even though the program created 2 additional threads that should be executing
the `run()` method.

```text
main start
main printing range 5 to 10
main i = 5
main i = 6
main i = 7
main i = 8
main i = 9
main i = 10
main printing range 14 to 16
main i = 14
main i = 15
main i = 16
main finished
```

Fix the error in the `main` method so the threads execute the `run` method
in a concurrent way.  Do not modify the `RangePrinter` class.

The output should appear similar to the example shown below, although the order
may differ each time the program executes:

```text
main start
main finished
Thread-1 printing range 14 to 16
Thread-0 printing range 5 to 10
Thread-0 i = 5
Thread-0 i = 6
Thread-1 i = 14
Thread-1 i = 15
Thread-0 i = 7
Thread-0 i = 8
Thread-1 i = 16
Thread-0 i = 9
Thread-0 i = 10
```



## Task #2: Do not modify the `Dog` class.

The `Dog` class contains methods `eatTreats` and `takeBath` that modify the `isWaggingTail`
instance variable:

```java
import java.util.stream.IntStream;

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
```

`Task2` creates 3 `Dog` instances, 3 threads to call `eatTreats` on
each dog, and 1 thread to call `takeBath` on the dog named Snoopy:

```java
public class Task2 {
    public static void main(String[] args) {
        Dog snoopy = new Dog("Snoopy", false);      //does not like baths
        Dog fifi = new Dog("Fifi", true);           //likes baths
        Dog odie = new Dog("Odie", true);           //likes baths

        Thread snoopyEating = new Thread( () -> snoopy.eatTreats(3) );
        Thread fifiEating = new Thread( () -> fifi.eatTreats(4) );
        Thread odieEating = new Thread( () -> odie.eatTreats(5) );
        Thread snoopyBathing = new Thread( () -> snoopy.takeBath() );

        snoopyEating.start();
        fifiEating.start();
        odieEating.start();
        snoopyBathing.start();
    }
}
```

The output differs each time the program runs due to the concurrent thread execution.
For example:

```text
After bath Snoopy wagging is false
Snoopy eats treat #1
Snoopy eats treat #2
Odie eats treat #1
Odie eats treat #2
Odie eats treat #3
Fifi eats treat #1
Fifi eats treat #2
Snoopy eats treat #3
Fifi eats treat #3
Fifi eats treat #4
After treats Snoopy wagging is true
Odie eats treat #4
Odie eats treat #5
After treats Odie wagging is true
After treats Fifi wagging is true
```

It turns out Snoopy is a messy eater and should take a bath after eating their treats. Edit the `main` method:

- Call the appropriate `Thread` method to ensure Snoopy takes a bath **after** they finished eating their treats.
- **DO NOT** call `Thread.sleep` or `Thread.wait`. We don't know how long it
  takes the dogs to eat.
- Fifi and Odie should be able to eat treats at any point in time,
  so the change should not control their behavior.
- The `main` method should handle any exceptions that are thrown using a try-catch block.

Run the program several times to confirm Snoopy bathes after
consuming all of their treats.   The output may differ for Fifi
and Odie, but Snoopy's print statements should occur in the following order:

```text
Odie eats treat #1
Odie eats treat #2
Snoopy eats treat #1
Snoopy eats treat #2
Snoopy eats treat #3
Fifi eats treat #1
Fifi eats treat #2
Odie eats treat #3
Odie eats treat #4
Odie eats treat #5
After treats Odie wagging is true
Fifi eats treat #3
Fifi eats treat #4
After treats Snoopy wagging is true
After treats Fifi wagging is true
After bath Snoopy wagging is false
```


## Task #3: Do not modify the `Task3` class.

`SavingsAccount` has instance variables for the account `id` and `balance`.
The  `withdraw` method checks the balance before decrementing by the specified amount.

```java
public class SavingsAccount {
    private String id;
    private int balance;

    public SavingsAccount(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public  void withdraw(int amount) {
        if (amount <= balance)
            balance -= amount;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" + "id='" + id + '\'' + ", balance=" + balance + '}';
    }
}
```

`Task3` creates two accounts and uses several threads to repeatedly withdraw small amounts.

Threads `t0` and `t1` withdraw from `account1`, while threads `t2` and `t3` withdraw from
`account2`:

```java
import java.util.stream.IntStream;

//DO NOT MODIFY
public class Task3 {

    public static void main(String[] args) throws InterruptedException {

        SavingsAccount account1 = new SavingsAccount("abc", 7000);
        SavingsAccount account2 = new SavingsAccount("xyz", 10000);

        //each thread calls withdraw() 1000 times
        Thread t0 = new Thread( () -> IntStream.range(0, 1000).forEach((i) -> account1.withdraw(1)));  //$1000
        Thread t1 = new Thread( () -> IntStream.range(0, 1000).forEach((i) -> account1.withdraw(2)));  //$2000
        Thread t2 = new Thread( () -> IntStream.range(0, 1000).forEach((i) -> account2.withdraw(5)));  //$5000
        Thread t3 = new Thread( () -> IntStream.range(0, 1000).forEach((i) -> account2.withdraw(3)));  //$3000

        t0.start();
        t1.start();
        t2.start();
        t3.start();

        t0.join();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(account1);  //balance should be 4000
        System.out.println(account2);  //balance should be 2000
    }
}
```

The final balances should be `4000` and `2000`. 
However, we will probably see different amounts each time the program executes:

```text
SavingsAccount{id='abc', balance=4468}
SavingsAccount{id='xyz', balance=3686}
```

The problem lies in the `withdraw` method.
Multiple threads may attempt to execute the method on the same `SavingsAccount` object,
which can result in lost writes:

```java
public void withdraw(int amount) {
    if (amount <= balance)
        balance -= amount;
}
```

The `-=` operation consists of 3 steps (read, subtract, write), therefore it is not atomic.

Another issue is that a thread might be paused after executing the test `if (amount <= balance)`
but before executing the next line `balance -= amount;`.

Edit the `withdraw` method to enforce data consistency:

- Use synchronization to ensure only one thread executes the `withdraw` method on an object at a time.
- Ensure multiple threads can concurrently execute the method on different account objects.

Run `Task3` several times to confirm the correct result is produced:

```text
SavingsAccount{id='abc', balance=4000}
SavingsAccount{id='xyz', balance=2000}
```

