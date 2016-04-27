import java.util.Scanner;

public class Populate implements Runnable {
	StoreMonitor store;
	
	public Populate (StoreMonitor store) {
		this.store = store;
	}

	public void run() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Enter command (add, print, buy, inventory) followed by customer name: ");
			String command = sc.next();

			if (command.equals("add")){
				String cus = sc.next();		
				store.addCustomer(cus);		
			} else if (command.equals("print")){
				store.print();
			} else if (command.equals("buy")) {
				String item = sc.next();
				int number = sc.nextInt();
				store.buyItem(item, number);
			} else if (command.equals("inventory")) {
				store.printInventory();
			} else { continue; }
		
			
		}
	}
}
