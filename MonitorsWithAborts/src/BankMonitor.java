import java.io.IOException;
import java.io.Serializable;

class BankMonitor extends MonitorsWithAborts implements Serializable {
	private int balance;

	BankMonitor(int arg1) {
		balance = arg1;
	}

	public synchronized void deposit(int amount) throws InterruptedException, IOException, ClassNotFoundException {
		this.saveState();
		while (balance + amount > 100) {
			this.wait();
		}
		System.out.println("Balance saved: " + balance);
		balance = balance + amount;
		System.out.println("Balance before abort: " + balance);
		if (amount == 5) {
			System.out.println("ABORT ISSUED");
			this.abort();
			System.out.println("Balance after abort: " + balance);
		}
		if (balance > 0) {
			notifyAll();
		}
	}

	public synchronized void withdraw(int amount) throws InterruptedException, IOException, ClassNotFoundException {

		this.saveState();
		while(balance-amount<0)
		{
			this.wait();
		}
		System.out.println("Balance saved: " + balance);
		balance = balance - amount;
		System.out.println("Balance before abort: " + balance);
		if (amount == 5) {
			this.abort();
			System.out.println("Balance after abort: " + balance);
		}
		if (balance < 100) {
			notifyAll();
		}
	}

	public synchronized int getBalance() {
		return balance;
	}

	public synchronized void setBalance(int newBalance) {
		balance = newBalance;
	}

	@Override
	public void restore(Object object) {
		BankMonitor oldState = (BankMonitor) object;
		balance = oldState.getBalance();
	}
}
