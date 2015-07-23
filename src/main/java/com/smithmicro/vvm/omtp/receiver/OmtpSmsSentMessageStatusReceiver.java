/*
 * Copyright (C) 2012 Orange Labs UK. All Rights Reserved.
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.smithmicro.vvm.omtp.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.smithmicro.vvm.omtp.dependency.StackDependencyResolver;
import com.smithmicro.vvm.omtp.dependency.StackDependencyResolverImpl;
import com.smithmicro.vvm.omtp.logging.Logger;
import com.smithmicro.vvm.omtp.notification.NotifChannelNotification;
import com.smithmicro.vvm.omtp.notification.SourceNotifier;

/**
 * Broadcast receiver used to handle status of send SMS messages returned by
 * {@link android.telephony.SmsManager#sendDataMessage} method in {@link android.app.PendingIntent}
 * sentIntent
 * 
 * <p>
 * if not NULL this PendingIntent is broadcast when the message is successfully sent, or failed. The
 * result code will be Activity.RESULT_OK for success, or one of these errors:
 * RESULT_ERROR_GENERIC_FAILURE RESULT_ERROR_RADIO_OFF RESULT_ERROR_NULL_PDU For
 * RESULT_ERROR_GENERIC_FAILURE the sentIntent may include the extra "errorCode" containing a radio
 * technology specific value, generally only useful for troubleshooting. The per-application based
 * SMS control checks sentIntent. If sentIntent is NULL the caller will be checked against all
 * unknown applications, which cause smaller number of SMS to be sent in checking period.
 * 
 */
public class OmtpSmsSentMessageStatusReceiver extends BroadcastReceiver {

	private static final Logger logger = Logger.getLogger(OmtpSmsSentMessageStatusReceiver.class);

	@Override
	public void onReceive(Context context, Intent intent) {

		logger.d(String.format("Received intent:%s", intent.getAction()));
		StackDependencyResolver resolver = StackDependencyResolverImpl.getInstance();
		SourceNotifier notifier = resolver.getSourceNotifier();

		if (getResultCode() == Activity.RESULT_OK) {
			notifier.sendNotification(NotifChannelNotification.connectivityOk());
			// Update the state in the SmsTimeoutHandler
            resolver.getSmsTimeoutHandler().setSentSmsState();
		} else {
			// report SMS connectivity error
			notifier.sendNotification(NotifChannelNotification.connectivityKo(context));
		}
	}

}
