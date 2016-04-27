import java.util.*;
import java.io.Serializable;

class StoreMonitor extends MonitorsWithAborts implements Serializable {

	private ArrayList<String> database;
	private ArrayList<Items> inventory;

	public StoreMonitor () {
		database = new ArrayList<String>();
		inventory = new ArrayList<Items>();
		populateInventory();
	}
	
	public void populateInventory () {
		inventory.add(new Items("cookie", 100));
		inventory.add(new Items("brownie", 100));
		inventory.add(new Items("chicken", 100));
	}

	public synchronized void addCustomer(String customer) {
		save();
		Boolean dup = database.contains(customer);
		database.add(customer);
		System.out.println("Added customer: " + customer);

		if (dup) {
			System.out.println("Customers before abort: ");
			printCustomers();
			try { this.abort(); } catch (Exception e) {}
			System.out.println("Customers after abort: ");
			printCustomers();
		}	
	}

	public synchronized void buyItem (String item, int number) {
		save();
		
		Items inven = null;	
		for (Items i: inventory) {
			if (i.getName().equals(item)) {
				inven = i;
				break;
			}
		}

		if (inven == null) {	
			System.out.println("Error: no matching item in inventory");
			return;		
		}


		inven.setNumber(inven.getNumber() - number);
		System.out.println("Purchased " + number + " " + item + "s");
		
		if (inven.getNumber() < 0) {
			System.out.println("No such thing as negative inventory so rejected!");
			System.out.println("inventory before abort: ");
			printInventory();
			System.out.println("inventory after abort: ");
			try { this.abort(); } catch (Exception e) {}
			printInventory();
		}
	
	} 

	public synchronized void printInventory () {
		save();
		for (Items i: inventory) {
			System.out.println(i.getName() + ", " + i.getNumber());
		}
	}

	public synchronized void removeCustomer(String customer) {
		save();
		database.remove(customer);
	}

	public synchronized void print() { printCustomers(); }

	private synchronized void printCustomers() {
		for (String s: database) {
			System.out.print(s + ", ");
		}	
		System.out.println();
	}

	private synchronized void save() {
		try {
			this.saveState();
		} 
		catch (Exception e) {}
		
	}

	@Override
	public void restore(Object object) {
		StoreMonitor oldState = (StoreMonitor) object;
		this.database = oldState.database;
		this.inventory = oldState.inventory;
	}
}
