import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.Random;
import java.util.logging.Logger;
import java.net.URL;
import java.util.Properties;

public class openURLscript {
    public static final Logger log = Logger.getLogger(openURLscript.class.getName());

    public static void main(String[] args) throws IOException{
        Random r = new Random();
        Properties prop = System.getProperties();

        anonimo(r, prop);
    }

    public static int anonimo( Random r, Properties prop) throws IOException {
        if(detectOS(prop).equals("Linux")){
            try {
                String[] s = {"/usr/bin/firefox", "--private", choose(r)};
                String ss = new StringBuilder().append(s[2]).toString();

                while (urlValidator(ss) == false) {
                    s[2] = choose(r);
                    ss = new StringBuilder().append(s[2]).toString();
                }

                final Process proc = new ProcessBuilder(s).start();


            } catch (IOException ioe) {
                log.severe("Error while opening browser (by default, it's firefox) : " + ioe.getMessage());

            } finally {
                System.exit(0);

            }
        }else if(detectOS(prop).equals("Windows")){
            try{
                String[] ss = {"C:/users/default/Downloads/Chrome.exe", "-e", choose(r)}; //This is just a base for a future solution, at the moment it doesn't work on windows
                Process proc2 = new ProcessBuilder(ss).start();

            }catch(IOException ioee){

            }
        }
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

}
