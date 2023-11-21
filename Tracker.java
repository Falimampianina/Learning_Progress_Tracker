package tracker;

import java.util.Objects;
import java.util.Scanner;

public class Tracker {
    private final String begin = "Learning Progress Tracker";

    private final Scanner scanner = new Scanner(System.in);

    public String getBegin() {
        return begin;
    }

    public void waitForInput(){
        String input = scanner.nextLine();
        while (!Objects.equals(input, "exit")){
            if(Objects.equals(input,null) || (input.matches("\\s*"))){
                System.out.println("No input");

            }else{
                System.out.println("Error: unknown command!");
            }
            input=scanner.nextLine();
        }
        System.out.println("Bye!");
    }
}
