import java.io.IOException;

public class DepositTest implements Runnable {
	BankMonitor account;

	DepositTest(BankMonitor arg1) {
		account = arg1;
	}

	public void run() {
		while (true) {
			try {
				account.deposit(1);
				//System.out.println("Bank Account Balance: "+account.getBalance());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
