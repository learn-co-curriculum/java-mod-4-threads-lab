public class Task2 {

    //Edit the main method to ensure Snoopy takes a bath after they finish eating their treats
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

