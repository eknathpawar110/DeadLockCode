public class DeadlockExample {
    static class Resource {
        private final String name;

        public Resource(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Resource resource1 = new Resource("Resource1");
        final Resource resource2 = new Resource("Resource2");

        // Thread 1 locks resource1 then tries to lock resource2
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: locked " + resource1);

                try {
                    // Adding sleep to make sure the deadlock happens
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println("Thread 1: locked " + resource2);
                }
            }
        });

        // Thread 2 locks resource2 then tries to lock resource1
        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: locked " + resource2);

                try {
                    // Adding sleep to make sure the deadlock happens
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource1) {
                    System.out.println("Thread 2: locked " + resource1);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

/*      1) open the command line interface and then navigate to the directory
                -> cd /path/to/your/javafile
        2) compile the java file
                -> javac DeadlockExample.java
        3) run the compiled Java class
                -> java DeadlockExample   */
    }
}