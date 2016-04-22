
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankMonitor testAccount = new BankMonitor(100);
		WithdrawTest withdrawTest = new WithdrawTest(testAccount);
		DepositTest depositTest = new DepositTest(testAccount);
		Thread withdrawThread = new Thread(withdrawTest);
		Thread depositThread=new Thread(depositTest);
		withdrawThread.start();
		depositThread.start();
	}

}
