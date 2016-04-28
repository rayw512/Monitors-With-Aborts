import java.io.IOException;
import java.util.Random;

public class WithdrawTest implements Runnable {
	BankMonitor account;

	WithdrawTest(BankMonitor arg1) {
		account = arg1;
	}

	public void run() {
		Random rand=new Random(24);
		while (true) {
			try {
				
				account.withdraw(rand.nextInt(10));
				// System.out.println("Bank Account Balance:
				// "+account.getBalance());
			} catch (InterruptedException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
