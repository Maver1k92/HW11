public class T1 extends Thread {
    private Main2 main2;
    private int time;
    T1 (Main2 main2) { this.main2 = main2; }
    public void run()
    {
        while(true) {
            try { Thread.sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            main2.setSharedTime (++time);
            System.out.print(time + " ");
        }
    }
}

class T2 extends Thread {
    private Main2 main2;
    T2 (Main2 main2) { this.main2 = main2; }
    public void run()
    {
        while(true) {
            main2.getSharedTime ();
            System.out.println();
            System.out.println ("Прошло 5 секунд");
        }
    }
}

class Main2 {
    private int time;
    private boolean share = true;

    public static void main(String[] args) {
        Main2 s = new Main2();
        new T1 (s).start();
        new T2 (s).start();

    }

   public synchronized void setSharedTime (int s) {
        while (!share) {
            try { wait (); }
            catch (InterruptedException e) { e.printStackTrace();}
        }
        this.time = s;
        if(s % 5 == 0)
            share = false;
        notify ();
    }

  public synchronized int getSharedTime () {
        while (share) {
            try { wait (); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        share = true;
        notify ();
        return time;
    }
}
