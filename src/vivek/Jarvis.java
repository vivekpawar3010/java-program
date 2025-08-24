import java.io.IOException;

public class Jarvis {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Jarvis <website>");
            System.exit(1);
        }

        String website = args[0];
        openWebsite(website);
    }

    private static void openWebsite(String website) {
        try {
            String osName = System.getProperty("os.name").toLowerCase();

            if (osName.contains("win")) {
                Runtime.getRuntime().exec("cmd /c start " + website);
            } else if (osName.contains("mac")) {
                Runtime.getRuntime().exec("open " + website);
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                Runtime.getRuntime().exec("xdg-open " + website);
            } else {
                System.err.println("Unsupported operating system.");
            }
        } catch (IOException e) {
            System.err.println("Error opening website: " + e.getMessage());
        }
    }
}