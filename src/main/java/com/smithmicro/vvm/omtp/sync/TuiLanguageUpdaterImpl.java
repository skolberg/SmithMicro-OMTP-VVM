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
package com.smithmicro.vvm.omtp.sync;

import android.content.Context;

import com.android.email.mail.MessagingException;
import com.smithmicro.vvm.omtp.account.OmtpAccountInfo;
import com.smithmicro.vvm.omtp.account.OmtpAccountStoreWrapper;
import com.smithmicro.vvm.omtp.callbacks.Callback;
import com.smithmicro.vvm.omtp.imap.OmtpImapStore;
import com.smithmicro.vvm.omtp.logging.Logger;

/**
 * Contains logic necessary to send IMAP commands to update TUI language.
 */
public class TuiLanguageUpdaterImpl implements TuiLanguageUpdater {

	private static Logger logger = Logger.getLogger(TuiLanguageUpdaterImpl.class);
	
	@Override
	public void updateTuiLanguage(Callback<Void> callback, int languageId, Context context,
			OmtpAccountStoreWrapper accountStore) {
		OmtpAccountInfo accountInfo = accountStore.getAccountInfo();
		OmtpImapStore store = null;
		try {
			store = OmtpImapStore.newInstance(accountInfo.getUriString(), context, null);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.e("Cannot change TUI language, Imap Store creation failed.");
		}
		
		if (store != null) {
			store.changeTuiLanguage(languageId, callback);
		} else {
			// Notify of the failure.
			callback.onFailure(new IllegalStateException("Impossible to instantiate a new" +
					" OMTP IMAP store."));
		}
		
	}


}
