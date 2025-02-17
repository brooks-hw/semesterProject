import java.util.Scanner;

public class InvestingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name: ");
        String name = scanner.nextLine();       //get string input

        System.out.println("Age: ");
        int age = scanner.nextInt();            //get int input

        System.out.println("Height: ");
        double height = scanner.nextDouble();   //get double input

        System.out.println(name + "  " + age + "  " + height);
        scanner.close();
    }
}
