import java.io.IOException;
import java.io.Serializable;

class BankMonitor extends MonitorsWithAborts implements Serializable {
	private int balance;

	BankMonitor(int arg1) {
		balance = arg1;
	}

	public synchronized void deposit(int amount) throws InterruptedException, IOException, ClassNotFoundException {
		this.savestate();
		System.out.println("Balance saved: " + balance);
		balance = balance + amount;
		System.out.println("Balance before abort: " + balance);
		BankMonitor saved = (BankMonitor) this.abort();
		balance = saved.getBalance();
		System.out.println("Balance after abort: " + balance);

	}

	public synchronized void withdraw(int amount) throws InterruptedException {

		balance = balance - amount;

	}

	public synchronized int getBalance() {
		return balance;
	}

	public synchronized void setBalance(int newBalance) {
		balance = newBalance;
	}
}