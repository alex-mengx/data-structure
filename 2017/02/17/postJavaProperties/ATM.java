public class ATM {
	private String password = "p@ssw0rd";
	private int balance = 0;

	public ATM() {
	}

	public void withdraw(String password, int howMuch) {
		if (this.password.equals(password)) {
			if (balance > howMuch) {
				balance -= howMuch;
				System.out.println("Withdraw " + howMuch + ", you have remaining balance " + balance);
			} else
				System.out.println("Insufficient balance! Only has " + balance + " left!");
		} else
			System.out.println("Authorization Error!");
	}

	public void save(String password, int howMuch) {
		if (this.password.equals(password)) {
			balance += howMuch;
			System.out.println("Save " + howMuch + ", you have remaining balance " + balance);
		} else
			System.out.println("Authorization Error!");
	}

	public int query(String password) {
		if (this.password.equals(password)) {
			return balance;
		} else
			System.out.println("Authorization Error!");
		return -1;
	}

	public void changePassword(String password, String newPassword) {
		if (this.password.equals(password)) {
			this.password = newPassword;
			System.out.println("Password updated!");
		} else
			System.out.println("Authorization Error!");
	}
}
