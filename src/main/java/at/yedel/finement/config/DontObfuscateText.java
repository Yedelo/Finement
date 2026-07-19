package at.yedel.finement.config;



public class DontObfuscateText {
    private static boolean configInitialized;

    public static boolean isEnabled() {
        if (configInitialized) {
            return FinementConfig.getInstance().enabled && FinementConfig.getInstance().dontObfuscateText;
        }
        else {
            return false;
        }
    }

    public static void configInitialized() {
        configInitialized = true;
    }
}
