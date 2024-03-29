import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Definer {
    //private volatile int num = 1;
    AtomicInteger atomicInteger = new AtomicInteger(1);
    private int num = atomicInteger.get();
    private int n;
    private volatile boolean running = false;
    private volatile boolean numberDefined = false;
    private List<String> result = new CopyOnWriteArrayList<>();


    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public List<String> getResult() {
        return result;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public synchronized boolean isNumberDefined() {
        return numberDefined;
    }

    public void setNumberDefined(boolean numberDefined) {
        this.numberDefined = numberDefined;
    }

    public boolean fizz(int num) {
        if (num % 3 == 0 && num % 5 != 0) {
            result.add("fizz");
            return true;
        } else {
            return false;
        }
    }

    public boolean buzz(int num) {
        if (num % 5 == 0 && num % 3 != 0) {
            result.add("buzz");
            return true;
        } else {
            return false;
        }
    }

    public boolean fizzbuzz(int num) {
        if (num % 5 == 0 && num % 3 == 0) {
            result.add("fizzbuzz");
            return true;
        } else {
            return false;
        }
    }

    public boolean number(int num) {
        if (num % 3 != 0 && num % 5 != 0) {
            result.add(num + "");
            return true;
        } else {
            return false;
        }
    }


    Thread A = new Thread(() -> {
        while (isRunning()) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setNumberDefined(fizz(atomicInteger.get()));
            if (isNumberDefined()) {
                atomicInteger.addAndGet(1);

            }
        }
    });

    Thread B = new Thread(() -> {
        while (isRunning()) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setNumberDefined(buzz(atomicInteger.get()));
            if (isNumberDefined()) {
                atomicInteger.addAndGet(1);

            }
        }
    });

    Thread C = new Thread(() -> {
        while (isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setNumberDefined(fizzbuzz(atomicInteger.get()));
            if (isNumberDefined()) {
                atomicInteger.addAndGet(1);
            }
        }
    });

    Thread D = new Thread(() -> {
        while (isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setNumberDefined(number(atomicInteger.get()));
            if (isNumberDefined()) {
                atomicInteger.addAndGet(1);

            }
        }
    });


    public void startProgram(int n) {
        Thread runner = new Thread(() -> {
            while (true) {
                if (getN() + 1 == atomicInteger.get()) {
                    setRunning(false);
                    System.out.println(getResult());
                    break;
                }
            }
        });
        runner.start();
        setN(n);
        setRunning(true);
        List<Thread> threads = new ArrayList<>();
        threads.add(A);
        threads.add(B);
        threads.add(C);
        threads.add(D);
        for (Thread th : threads) {
            th.start();
        }
    }
}


class DefinerTester {
    public static void main(String[] args) {
        Definer definer = new Definer();
        definer.startProgram(17);
    }
}
