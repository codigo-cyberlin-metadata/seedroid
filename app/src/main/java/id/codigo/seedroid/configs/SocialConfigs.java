package id.codigo.seedroid.configs;

/**
 * Created by Gayo on 1/27/2017.
 */

public class SocialConfigs {
    public static String twitterConsumerKey = "";
    public static String twitterConsumerSecret = "";

    public static void initialize(String consumerKey, String consumerKeySecret) {
        twitterConsumerKey = consumerKey;
        twitterConsumerSecret = consumerKeySecret;
    }
}
