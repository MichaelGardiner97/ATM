package Lab07_AtmLab;

public class Accounts {

    private String userName;
    private int pin;
    private double bal;

    // Constructors
    public Accounts(String s, int x, double b){
        this.userName = s;
        this.pin = x;
        this.bal = b;
    }

    // Accessors
    public String getUserName() {return this.userName;}
    public int getPin() {return this.pin;}
    public double getBal() {return this.bal;}

    // Mutators
    public double withdraw(double amt) {
        if (amt > 0 && this.bal - amt > 0) {
            this.bal -= amt;
            return this.bal;
        } else {return -1;}
    }
    public double deposit(double amt) {
        if (amt > 0) {
            this.bal += amt;
            return this.bal;
        } else {return -1;}
    }

    // Print
    public String toString() {return this.userName + ", " + this.pin + ", " + this.bal;}

    public static void main(String[] args) {
        Accounts a1 = new Accounts("gardiner", 7, 8.0);
        a1.deposit(100);
        a1.withdraw(50);
        System.out.println(a1);
    }
}
