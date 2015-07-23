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
package com.smithmicro.vvm.omtp.notification;

import android.content.Context;
import android.os.Bundle;

import com.smithmicro.vvm.omtp.utils.NetworkManager;

public class DataChannelNotification extends AbstractNotification {

	private DataChannelNotification(Bundle bundle) {
		super(SourceNotification.DATA_CHANNEL_ACTION, bundle);
	}

	/**
	 * Generates a notification indicating that the connectivity is okay, ie. the connection was
	 * successful.
	 */
	public static DataChannelNotification connectivityOk() {
		return new Builder().setConnectivityStatus(ConnectivityStatus.CONNECTIVITY_OK).build();
	}
	
	/**
	 * Generates a notification indicating that the authentication has failed.
	 */
	public static DataChannelNotification authenticationFailure(AuthenticationError error) {
		return new Builder().setConnectivityStatus(ConnectivityStatus.CONNECTIVITY_KO)
				.setErrorCause(ErrorCause.AUTHENTICATION).setAuthenticationError(error).build();
	}

	/**
	 * Generates a notification indicating that the connectivity is KO, adding the cause of the
	 * failure.
	 * 
	 * @param context
	 * 			Used to determine the reason of the failure.
	 */
	public static DataChannelNotification connectivityKo(Context context) {
		Builder builder = new Builder().setConnectivityStatus(ConnectivityStatus.CONNECTIVITY_KO);
		addErrorCause(context, builder);
		return builder.build();
	}

	/**
	 * Investigate the cause of the IMAP connectivity error.
	 */
	private static void addErrorCause(Context context, Builder builder) {
		NetworkManager monitor = new NetworkManager(context);
		if (monitor.isInAirplaneMode()) {
			builder.setErrorCause(ErrorCause.AIRPLANE);
		} else if (monitor.isSimAbsent()) {
			builder.setErrorCause(ErrorCause.SIM_ABSENT);
		} else if (monitor.isWifiEnabled()) {
			builder.setErrorCause(ErrorCause.WIFI_ENABLED);
		} else if (monitor.isRoaming()) {
			builder.setErrorCause(ErrorCause.ROAMING);
		} else {
			builder.setErrorCause(ErrorCause.UNKNOWN);
		}
	}

	public static class Builder {

		private Bundle mBundle = new Bundle();

		public Builder setConnectivityStatus(ConnectivityStatus status) {
			mBundle.putSerializable(SourceNotification.CONNECTIVITY_STATUS_KEY, status);
			return this;
		}
		
		public Builder setErrorCause(ErrorCause cause) {
			mBundle.putSerializable(SourceNotification.ERROR_CAUSE_KEY, cause);
			return this;
		}
		
		public Builder setAuthenticationError(AuthenticationError error) {
			mBundle.putSerializable(SourceNotification.AUTH_ERROR_KEY, error);
			return this;
		}

		public DataChannelNotification build() {
			return new DataChannelNotification(mBundle);
		}

	}

}
