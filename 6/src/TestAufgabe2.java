public class TestAufgabe2 {
    Aufgabe2 ab;
    int buffsize;

    public TestAufgabe2(int buffsize){
        this.buffsize = buffsize;
        ab = new Aufgabe2(buffsize);
    }

    public boolean testProducer() {
        /**
         * Test if Producer falls into waiting state after writing N buffer elements
         */
        // Start only producer and see if it stops after buff is filled
        ab.Pro.start();
        try{
            Thread.sleep(buffsize * 100);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        boolean isWaiting = ab.Pro.getState() == Thread.State.WAITING;
        ab.Pro.interrupt();

        return isWaiting && ab.Pro.Buff.buff.length == ab.Pro.Buff.length;
    }

    public boolean testConsumer() {
        /**
         * Test, if consumer falls into waiting state after reading N buffer elements
         */
        ab.Cs.start();
        try{
            Thread.sleep(buffsize * 100);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        boolean isWaiting = ab.Cs.getState() == Thread.State.WAITING;
        ab.Cs.interrupt();

        return isWaiting && ab.Cs.read.size() == ab.Cs.Buff.length;
    }

    public static void main(String[] args) {
        TestAufgabe2 t = new TestAufgabe2(10);
        boolean producerIsWaiting = t.testProducer();
        boolean consumerIsWaiting = t.testConsumer();
        if (producerIsWaiting && consumerIsWaiting) {
		System.out.println("\n\n All tests passed! \n");
	}

    }
}
