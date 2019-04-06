package com.github.aaitor.cli.utils;

public abstract class Constants {

    public static final String configFolder= System.getProperty("user.home") + "/.local/share/ocean-cli";

    public static final String networkFolder= configFolder + "/networks/";

    public static final String mainConfigFile= configFolder + "/application.conf";

    public static final String internalMainConfig= "application.conf";

    public static final String internalNetworkFolder= "networks/";

    public static final String TransactionSuccess= "0x1";

    public static final String metadataUri= "/api/v1/aquarius/assets/ddo/{did}";

    public static final String initializeUri= "/api/v1/brizo/services/access/initialize";

    public static final String consumeUri= "/api/v1/brizo/services/consume?consumerAddress=${consumerAddress}&serviceAgreementId=${serviceAgreementId}&url=${url}";

}
