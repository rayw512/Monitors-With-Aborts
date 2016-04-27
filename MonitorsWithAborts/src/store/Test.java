
class Test {

	public static void main(String[] args) {
		StoreMonitor store = new StoreMonitor();
		Populate pop = new Populate(store);
		Thread t = new Thread(pop);	
		t.start();
	}
}
