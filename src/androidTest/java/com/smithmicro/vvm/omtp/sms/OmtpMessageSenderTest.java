package com.smithmicro.vvm.omtp.sms;

import android.telephony.SmsManager;
import android.test.AndroidTestCase;

import com.smithmicro.vvm.omtp.account.OmtpAccountInfoTest;
import com.smithmicro.vvm.omtp.dependency.StackDependencyResolver;
import com.smithmicro.vvm.omtp.dependency.StackDependencyResolverImpl;
import com.smithmicro.vvm.omtp.provider.OmtpProviderInfoTest;
import com.smithmicro.vvm.omtp.proxy.OmtpSmsManagerProxyImpl;
import com.smithmicro.vvm.omtp.sms.timeout.SmsTimeoutHandlerImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OmtpMessageSenderTest extends AndroidTestCase {

	private StackDependencyResolver omtpDependencyResolver = null;
	private OmtpMessageSender omtpMessageSender = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		OmtpAccountInfoTest omtpAccountInfoTest = new OmtpAccountInfoTest();
		omtpAccountInfoTest.testAccountInfoCreation();
		OmtpProviderInfoTest omtpProviderInfoTest = new OmtpProviderInfoTest();
		omtpProviderInfoTest.testProviderInfoCreation();
		try{
			StackDependencyResolverImpl.initialize(getContext());
		} catch (IllegalStateException ise){
			// do nothing
		}
		ExecutorService executorService = Executors.newCachedThreadPool();
		omtpDependencyResolver = StackDependencyResolverImpl.getInstance();

		omtpMessageSender = new OmtpMessageSenderImpl(
                new OmtpSmsManagerProxyImpl(SmsManager.getDefault()),
                new SmsTimeoutHandlerImpl(),
                omtpDependencyResolver.getAccountStore(),
				omtpDependencyResolver.getProviderStore().getProviderInfo(),
				omtpDependencyResolver.getSourceNotifier(),
                getContext(),
                executorService);
	}

	public void testRequestVvmActivation() {
		omtpMessageSender.requestVvmActivation();
		fail("Not yet implemented");
	}

	public void testRequestVvmDeactivation() {
		omtpMessageSender.requestVvmDeactivation();
		fail("Not yet implemented");
	}

	public void testRequestVvmStatus() {
		omtpMessageSender.requestVvmStatus();
		fail("Not yet implemented");
	}

}
