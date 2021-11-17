import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;
import java.util.Scanner;

public class openURLscript {
    public static final Logger log = Logger.getLogger(openURLscript.class.getName());

    public static void main(String[] args) throws IOException{
        Random r = new Random();
        Properties prop = System.getProperties();

        anonimo(r, prop);
    }

    public static int anonimo( Random r, Properties prop) throws IOException {
        Scanner sx = new Scanner(System.in);

        if(detectOS(prop).equals("Linux")){
            try {
                String[] s = {"/usr/bin/firefox", "--private", choose(r)};
                String ss = new StringBuilder().append(s[2]).toString();

                while (urlValidator(ss) == false) {
                    s[2] = choose(r);
                    ss = new StringBuilder().append(s[2]).toString();
                }
                System.out.println("Your link is: " + ss + " do you want to open it? [yes, no]");

                String yn = sx.next();

                if(yn.equals("yes")) {
                    final Process proc = new ProcessBuilder(s).start();

                }else{
                    System.out.println("Ok, exiting application");
                    System.exit(0);

                }

            } catch (IOException ioe) {
                log.severe("Error while opening browser (by default, it's firefox) : " + ioe.getMessage());

            } finally {
                System.exit(0);

            }
        }else if(detectOS(prop).equals("Windows 10")){
            windowsExecute(r);
        }

        sx.close();

        return 0;
    }

    public static String choose(Random r){
        int a = r.nextInt(99);
        int b = r.nextInt(99);
        int c = r.nextInt(99);

        String s = new StringBuilder().append("https://nhentai.net/g/").append(a).append(b).append(c).toString();


        return s;
    }

    public static boolean urlValidator(String ss)throws IOException, MalformedURLException {
        try {
            final URL url = new URL(ss);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }

    }

    public static String detectOS(Properties prop){
        String properties = prop.toString();
        String osName = System.getProperty("os.name");

        return osName;
    }

    public static void windowsExecute(Random r){
        String link = choose(r);
        String[] linkSplitted = link.split("https://"); 
        String twoPointsLink = new StringBuilder().append(":").append(linkSplitted[1]).toString();
        String[] comando = {"\"C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe\"", twoPointsLink, "-inprivate"}; //this should work

        try {
            Runtime.getRuntime().exec(comando);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
