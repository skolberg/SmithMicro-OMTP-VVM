package com.smithmicro.vvm.omtp;

import android.app.Application;
import android.util.Log;

import com.smithmicro.vvm.omtp.account.OmtpAccountInfo;
import com.smithmicro.vvm.omtp.dependency.StackDependencyResolverImpl;
import com.smithmicro.vvm.omtp.protocol.Omtp;
import com.smithmicro.vvm.omtp.provider.OmtpProviderInfo;

/**
 * Created by kandreads on 26/7/2015.
 */
public class SmithMicroVVMApplication extends Application {

    private static final String PROVIDER_NAME = "TestProvider";
    private static final Omtp.ProtocolVersion PROTOCOL_VERSION = Omtp.ProtocolVersion.V1_1;
    private static final String CLIENT_TYPE = "TestClient";
    private static final String SMS_DESTINATION_NUMBER = "0123456789";
    private static final short SMS_DESTINATION_PORT = 20481;
    private static final String SMS_SERVICE_CENTER = "+449604303495";
    private static final String DATE_FORMAT = "TestDateFormat";
    private static final String NETWORK_OPERATOR = "20201";
    private static final boolean IS_CURRENT_PROVIDER = true;

    private static final String ACCOUNT_ID = "6973980985";
    private static final String IMAP_USER_NAME = "TestUser";
    private static final String IMAP_PASSWORD = "TestPassword";
    private static final String IMAP_SERVER = "TestImapServer";
    private static final String IMAP_PORT = "1234";
    private static final String SMS_NUMBER = "9876";
    private static final String TUI_NUMBER = "5432";
    private static final String SUBSCRIPTION_URI = "TestSubscriptionUri";
    private static final Omtp.ProvisioningStatus PROVISSIONING_STATUS = Omtp.ProvisioningStatus.SUBSCRIBER_READY;

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            // Initialize StackDependencyResolver
            StackDependencyResolverImpl.initialize(this);
            // Initialize AccountInfo
            OmtpAccountInfo.Builder builder = new OmtpAccountInfo.Builder();
            OmtpAccountInfo accountInfo = builder.setAccountId(ACCOUNT_ID)
                    .setImapUsername(IMAP_USER_NAME).setImapPassword(IMAP_PASSWORD)
                    .setImapServer(IMAP_SERVER).setImapPort(IMAP_PORT).setSmsNumber(SMS_NUMBER)
                    .setTuiNumber(TUI_NUMBER).setSubscriptionUrl(SUBSCRIPTION_URI)
                    .setProvisionningStatus(PROVISSIONING_STATUS)
                    .build();
            // Initialize OMPT Provider
            OmtpProviderInfo.Builder builder1 = new OmtpProviderInfo.Builder();
            builder1.setProviderName(PROVIDER_NAME)
                    .setProtocolVersion(PROTOCOL_VERSION)
                    .setClientType(CLIENT_TYPE)
                    .setSmsDestinationNumber(SMS_DESTINATION_NUMBER)
                    .setSmsDestinationPort(SMS_DESTINATION_PORT)
                    .setSmsServiceCenter(SMS_SERVICE_CENTER)
                    .setDateFormat(DATE_FORMAT)
                    .setNetworkOperator(NETWORK_OPERATOR)
                    .setIsCurrentProvider(IS_CURRENT_PROVIDER).build();

        } catch (IllegalStateException ise){
            Log.w("SmithMicro", "Error initializing StackDependncyResolver in Application object");
        }
    }

}
