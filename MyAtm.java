package Lab07_AtmLab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyAtm {

    // Data Members
    private ArrayList<Accounts> info;
    private Scanner scan;
    private double balance;
    private int num;

    // Constructor
    public MyAtm() throws Exception {
        this.info = new ArrayList<Accounts>();
        this.scan = new Scanner(System.in);
        this.num = 0;

        ArrayList<String> list = new ArrayList<>();
        Scanner in = new Scanner(new File("accounts.txt"));
        while (in.hasNextLine()) {
            String str = in.nextLine();
            list.add(str);
        }
        String[] newStr;
        for (int i = 0; i < list.size(); i++) {
            String newList = list.get(i);
            newStr = newList.split(", ");
            this.info.add(new Accounts(newStr[0], Integer.parseInt(newStr[1]), Double.parseDouble(newStr[2])));
            this.balance = this.info.get(i).getBal();
        }
    }

    public void checkBalance(int x) {System.out.println(this.info.get(x).getBal());}
    public void depositMoney(int x){
        while (true) {
            System.out.print("Please enter deposit amount:");
            double dep = this.scan.nextDouble();
            if (this.info.get(x).deposit(dep) == -1) {
                System.out.println("Invalid input amount.");
            }
            else {
                this.balance += dep;
                System.out.println("Your money has been deposited.\n");
                return;
            }
        }
    }
    public void withdrawMoney(int x){
        while (true) {
            System.out.print("Please enter withdraw amount:");
            double with = this.scan.nextDouble();
            if (this.info.get(x).withdraw(with) == -1) {
                System.out.println("Invalid input amount.");
            }
            else {
                this.balance -= with;
                System.out.println("Please take your money...\n");
                return;
            }
        }
    }

    public int getMenuOption() {
        System.out.println(" 1) Check Balance");
        System.out.println(" 2) Withdraw Money");
        System.out.println(" 3) Deposit Money");
        System.out.println(" 4) Logout\n");
        int option = 0;
        while (option < 1 || 4 < option) {
            System.out.print("What would you like to do? (1-4): ");
            try{
                option = this.scan.nextInt();
            } catch (InputMismatchException ime){
                System.out.println("Nahhhhhh Son Try Again");
                scan.nextLine();
                option = 0;
            }
            if (option < 1 || option > 4) {
                System.out.println("Your selection is out of range!");
            }
        }
        return option;
    }

    public boolean logIn(){
        boolean wasUserFound = false;

        while (true){
            System.out.print("\nEnter Username: ");
            String name = scan.next().trim();

            // Allows an admin account to shut down myAtm
            if(name.toLowerCase().equals("admin")){
                System.out.print("Enter Pin: ");
                int pin = scan.nextInt();
                if(pin == 0000){
                    System.out.println("Shutting Down");
                    wasUserFound = true;
                    return false;
                }
            }
            // Allows known-users to log in
            for (int i = 0; i < info.size(); i++) {
                if (name.toLowerCase().equals(this.info.get(i).getUserName())){
                    System.out.print("Enter Pin: ");
                    int pin = scan.nextInt();
                    if (pin == this.info.get(i).getPin()){
                        this.num = i;
                        wasUserFound = true;
                        return true;
                    } else {System.out.println("Incorrect Pin");}
                }
            }
            // Allows user to add a new account if they so wish
            if (!wasUserFound) {
                System.out.print("Your account could not be found, would you like to create an account? (yes/no): ");
                String ans = scan.next().trim();
                if (ans.toLowerCase().equals("yes")) {
                    System.out.print("\nEnter new username: ");
                    String newUser = scan.next().trim();
                    System.out.print("Re-enter new username: ");
                    String checkNewUser = scan.next().trim();
                    if (checkNewUser.equals(newUser)) {
                        System.out.print("Enter new pin: ");
                        int newPin = scan.nextInt();
                        System.out.print("Re-enter new pin number: ");
                        int checkPin = scan.nextInt();
                        if (newPin == checkPin) {
                            this.info.add(new Accounts(newUser, newPin, 1000));
                            this.returnValues();
                            continue;
                        } else {
                            System.out.println("Pins do not match up");
                            break;
                        }
                    } else{
                        System.out.println("Usernames do not match up");
                        break;
                    }
                } else {continue;}
            }
        }
        return wasUserFound;
    }

    public void run(){
        System.out.println("Welcome to my ATM!");

        while (this.logIn()) {

            while (true) {
                int option = this.getMenuOption();
                if (option == 1) {
                    this.checkBalance(this.num);
                } else if (option == 2) {
                    this.withdrawMoney(this.num);
                } else if (option == 3) {
                    this.depositMoney(this.num);
                } else if (option == 4) {
                    this.returnValues();
                    break;
                } else {
                    System.out.println("Invalid option");
                }
            }
        }
    }

    public void returnValues(){
        try {
            FileWriter writer = new FileWriter("accounts.txt",false);
            for (int i = 0; i < (info.size()); i++) {
                writer.write(this.info.get(i).getUserName() + ", " + this.info.get(i).getPin() + ", " +this.info.get(i).getBal() + "\n");
            }
            writer.close();
        } catch(IOException ioe) {System.err.println("IOException: " + ioe.getMessage());}
    }

    public static void main(String[] args) throws Exception{
        MyAtm atm = new MyAtm();
        atm.run();
    }

}
