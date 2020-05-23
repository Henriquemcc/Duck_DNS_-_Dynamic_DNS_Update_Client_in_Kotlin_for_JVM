import java.util.Scanner;

public class Main
{
    private static DynamicDNS DuckDNS=new DynamicDNS();

    /**
     * This is the main method.
     * @param args Command line parameter.
     */
    public static void main(String[]args)
    {
        boolean loaded=DuckDNS.Load();
        if(!loaded)
        {
            System.out.println("File Not Loaded");

            System.out.println("Please, type:");
            Scanner scanner=new Scanner(System.in);

            System.out.print("Subdomain: ");
            DuckDNS.setDomain(scanner.next());

            System.out.print("Token: ");
            DuckDNS.setToken(scanner.next());

            DuckDNS.Save();
        }

        byte success=DuckDNS.update();
        if(success==10)
        {
            System.out.println("IPv4: Success");
            System.out.println("IPv6: Success");
        }
        else if(success==6)
        {
            System.out.println("IPv6: Success");
            System.out.println("IPv4: Failure");
        }
        else if(success==4)
        {
            System.out.println("IPv6: Failure");
            System.out.println("IPv4: Success");
        }
        else if(success==0)
        {
            System.out.println("IPv6: Failure");
            System.out.println("IPv4: Failure");
        }
    }
}
