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

    public String getDomain()
    {
        return this.domain;
    }

    public String getToken()
    {
        return this.token;
    }

    public void setDomain(String domain)
    {
        this.domain=domain.toLowerCase();
    }

    public void setToken(String token)
    {
        this.token=token;
    }

    public byte update()
    {
        byte success=0;
        if(updateIPv4())
            success+=4;
        if(updateIPv6())
            success+=6;

        return success;
    }

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
                        IPv6=IPv6.substring(0, IPv6.indexOf("%"));
                    }
                }

            }
        }
        return IPv6;
    }

    public boolean Load()
    {
        boolean success=false;
        File settingsFile=new File(FILENAME);

        if(settingsFile.exists() && settingsFile.canRead())
        {
            try
            {
                Scanner dataFile=new Scanner(settingsFile);
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

    public boolean Save()
    {
        boolean success=false;
        File settingsFile=new File(FILENAME);

        if(!settingsFile.exists())
            try
            {
                settingsFile.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        if(settingsFile.canWrite())
        {
            try
            {
                Formatter dataFile=new Formatter(settingsFile, "UTF-8");
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