
class BankMonitor extends MonitorsWithAborts {
	private int balance;

	BankMonitor(int arg1) {
		balance = arg1;
	}

	public synchronized void deposit(int amount) throws InterruptedException {
		this.savestate();
		balance=balance+amount;
		
	}

	public synchronized void withdraw(int amount) throws InterruptedException {
		
		balance=balance-amount;
		
	}
	public synchronized int getBalance()
	{
		return balance;
	}
}