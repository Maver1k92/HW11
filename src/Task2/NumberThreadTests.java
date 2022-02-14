package Task2;

import java.util.ArrayList;
import java.util.List;

public class NumberThreadTests {
    public static void main(String[] args) {

        List<String> result = new ArrayList<>();

        ProcessThread fizzBuzz = new ProcessThread((n) -> {
            if (n%15 == 0) {
                //System.out.println("FizzBuzz");
                result.add("FizzBuzz");
            }
        });

        ProcessThread fizz = new ProcessThread((n) -> {
            if (n%3 == 0 && n% 15 !=0) {
                //System.out.println("Fizz");
                result.add("Fizz");
            }
        });

        ProcessThread buzz = new ProcessThread((n) -> {
            if(n%5 == 0 && n% 15 !=0) {
                //System.out.println("Buzz");
               result.add("Buzz");
            }
        });

        ProcessThread number = new ProcessThread((n) -> {
            if (n%3 != 0 && n%5 != 0) {
                //System.out.println(n);
                result.add(String.valueOf(n));
            }
        });

        List<ProcessThread> threads = new ArrayList<>();
        threads.add(fizzBuzz);
        threads.add(fizz);
        threads.add(buzz);
        threads.add(number);

        for (ProcessThread thread : threads) {
            thread.start();
        }

        for (int i = 1; i < 100; i++) {
            for (ProcessThread thread : threads) {
                thread.process(i);
            }

            while (true) {
                int processedCount = 0;
                for (ProcessThread thread : threads) {
                    if (thread.isProcessed()) {
                        processedCount++;
                    }
                }
                if (processedCount == threads.size()) {
                    break;
                }
                //System.out.println(result);
            }
            //System.out.println(result);
            String listString = "";

            for (String s : result)
            {
                listString += s + ", ";
            }
            System.out.println(listString);
        }
    }
}
