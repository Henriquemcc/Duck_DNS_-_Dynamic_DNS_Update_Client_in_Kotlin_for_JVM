import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Scanner;

class DynamicDNS
{
    private final String FILENAME = "DuckDNS.cfg";
    private String domain;
    private String token;

    /**
     * This method returns the value of the domain String.
     * @return Value of the domain String.
     */
    public String getDomain()
    {
        return this.domain;
    }

    /**
     * This method returns the value of the token String.
     * @return Value of the token String.
     */
    public String getToken()
    {
        return this.token;
    }

    /**
     * This method sets the value for the string subdomain.
     * @param domain New value for domain String.
     */
    public void setDomain(String domain)
    {
        this.domain=domain.toLowerCase();
    }

    /**
     * This method sets the value for the String token.
     * @param token New value for token String.
     */
    public void setToken(String token)
    {
        this.token=token;
    }

    /**
     * This method updates Duck DNS Domain booth IPv4 and IPv6 addresses.
     * @return Byte that indicates whether it was successful to update IPv6 & IPv4 (10), IPv6 only (6), IPv4 only (4) or none (0).
     */
    public byte update()
    {
        byte success=0;
        if(updateIPv4())
            success+=4;
        if(updateIPv6())
            success+=6;

        return success;
    }

    /**
     * This method updates the Duck DNS Domain IPv6 address.
     * @return Boolean value whether it was successful to update IPv6.
     */
    public boolean updateIPv6()
    {
        boolean success=false;
        //Updating IPv6 address
        try
        {
            String IPv6=getIPv6();
            String addressUrlIPv6="https://www.duckdns.org/update?domains=" + domain + "&token=" + token + "&ipv6=" + IPv6;
            URL url=new URL(addressUrlIPv6);
            HttpsURLConnection uc=(HttpsURLConnection)url.openConnection();
            BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
            StringBuilder sb=new StringBuilder();
            sb.append(br.readLine());
            if(sb.toString().equals("OK"))
                success=true;


        }
        catch(SocketException e)
        {
            e.printStackTrace();
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * This method updates the Duck DNS Domain IPv4 address.
     * @return Boolean value whether it was successful to update IPv4.
     */
    public boolean updateIPv4()
    {
        boolean success=false;
        //Updating IPv4 address
        try
        {
            String addressUrlIPv4="https://www.duckdns.org/update?domains=" + domain + "&token=" + token + "&ip=";
            URL url=new URL(addressUrlIPv4);
            HttpsURLConnection uc=(HttpsURLConnection)url.openConnection();
            BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
            StringBuilder sb=new StringBuilder();
            sb.append(br.readLine());
            if(sb.toString().equals("OK"))
                success=true;
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return success;
    }

    /**
     * This method retrieves and returns the IPv6 Address of the machine.
     * @return String containing the IPv6 address of the machine
     * @throws SocketException if an I/O error occurs in the method getNetworkInterfaces() from the class NetworkInterface, or if the platform does not have at least one configured network interface.
     */
    private String getIPv6() throws SocketException
    {
        String IPv6=null;
        Enumeration <NetworkInterface> networkInterfaces=NetworkInterface.getNetworkInterfaces();
        while(((Enumeration) networkInterfaces).hasMoreElements() && IPv6==null)
        {
            Enumeration<InetAddress> inetAddresses=networkInterfaces.nextElement().getInetAddresses();
            while(inetAddresses.hasMoreElements() && IPv6==null)
            {
                InetAddress inetAddress=inetAddresses.nextElement();
                if(inetAddress.getClass()==Inet6Address.class)
                {
                    if((!inetAddress.isMulticastAddress()) && (!inetAddress.isLinkLocalAddress()) && (!inetAddress.isSiteLocalAddress()) && (!inetAddress.isLoopbackAddress()))
                    {
                        IPv6=inetAddress.getCanonicalHostName();
                        try
                        {
                            IPv6=IPv6.substring(0, IPv6.indexOf("%"));
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }
        return IPv6;
    }

    /**
     * This method loads the configuration file.
     * @return Boolean value whether it was successful to load configuration file.
     */
    public boolean Load()
    {
        boolean success=false;
        File configurationFile=new File(FILENAME);

        if(configurationFile.exists() && configurationFile.canRead())
        {
            try
            {
                Scanner dataFile=new Scanner(configurationFile);
                domain=dataFile.nextLine();
                token=dataFile.nextLine();
                dataFile.close();
                success=true;
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return success;
    }

    /**
     * This method saves the configuration to the configuration file.
     * @return Boolean value whether it was successful to save configuration file.
     */
    public boolean Save()
    {
        boolean success=false;
        File configurationFile=new File(FILENAME);

        if(!configurationFile.exists())
            try
            {
                configurationFile.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        if(configurationFile.canWrite())
        {
            try
            {
                Formatter dataFile=new Formatter(configurationFile, "UTF-8");
                dataFile.format("%s\n", domain);
                dataFile.format("%s\n", token);
                dataFile.close();
                success=true;
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return success;
    }
}