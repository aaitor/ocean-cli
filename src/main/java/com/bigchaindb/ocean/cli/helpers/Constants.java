package com.bigchaindb.ocean.cli.helpers;

import java.io.File;

public abstract class Constants {

    public static final String configFolder= System.getProperty("user.home") + File.separator + ".local" + File.separator + "share" + File.separator + "ocean-cli";

    public static final String networkFolder= configFolder + File.separator + "networks" + File.separator;

    public static final String mainConfigFile= configFolder + File.separator + "application.conf";

    public static final String logsConfigFile= configFolder + File.separator + "log4j2.properties";

    public static final String accountsFolder= configFolder + File.separator + "accounts" + File.separator;


//    public static final String internalMainConfig= "application.conf";
//
//    public static final String internalNetworkFolder= "networks/";

    public static final String TransactionSuccess= "0x1";

    public static final String metadataUri= "/api/v1/aquarius/assets/ddo/{did}";

    public static final String initializeUri= "/api/v1/brizo/services/access/initialize";

    public static final String consumeUri= "/api/v1/brizo/services/consume?consumerAddress=${consumerAddress}&serviceAgreementId=${serviceAgreementId}&url=${url}";

}
