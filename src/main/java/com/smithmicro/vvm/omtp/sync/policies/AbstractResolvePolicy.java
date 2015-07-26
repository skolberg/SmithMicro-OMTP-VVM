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
package com.smithmicro.vvm.omtp.sync.policies;

import java.util.List;

import com.smithmicro.vvm.omtp.logging.Logger;
import com.smithmicro.vvm.omtp.sync.VvmStore;
import com.smithmicro.vvm.omtp.sync.VvmStoreActions;
import com.smithmicro.vvm.omtp.sync.VvmStoreResolver.ResolvePolicy;
import com.smithmicro.vvm.omtp.voicemail.LocalVoicemailProvider;
import com.smithmicro.vvm.omtp.voicemail.Voicemail;
import com.smithmicro.vvm.omtp.voicemail.database.MirrorVoicemailProvider;

public abstract class AbstractResolvePolicy implements ResolvePolicy {

	private static Logger logger = Logger.getLogger(AbstractResolvePolicy.class);

	private LocalVoicemailProvider mLocalDb;
	private MirrorVoicemailProvider mMirrorDb;

	public AbstractResolvePolicy(LocalVoicemailProvider localDb, MirrorVoicemailProvider mirrorDb) {
		mLocalDb = localDb;
		mMirrorDb = mirrorDb;
	}

	@Override
	public abstract void resolveLocalOnlyMessage(Voicemail localMessage,
			List<VvmStore.Action> localActions, List<VvmStore.Action> remoteActions);

	/**
	 * Resolve a voicemail that is present on the remote VVM store but not locally. Two cases can
	 * occur:
	 * 
	 * <ul>
	 * <li>
	 * The message exists in the Mirror VVM Store, which means that the message has been deleted by
	 * the user.
	 * <li>
	 * The message does not exist in the Mirror VVM Store, the message has never been downloaded
	 * locally.
	 */
	@Override
	public void resolveRemoteOnlyMessage(Voicemail remoteMessage,
			List<VvmStore.Action> localActions, List<VvmStore.Action> remoteActions) {
		String sourceData = remoteMessage.getSourceData();
		if (sourceData != null) {
			if (mMirrorDb.findVoicemailBySourceData(sourceData) == null) {
				logger.d("[Remote Message] Inserting voicemail; Absent from Mirror Store.");
				localActions.add(VvmStoreActions.insert(remoteMessage));
			} else {
				// Double check with local repository.
				if (mLocalDb.findVoicemailBySourceData(sourceData) == null) {
					logger.d("[Remote Message] Deleting. Present in Mirror Store.");
					remoteActions.add(VvmStoreActions.delete(remoteMessage));
				} else {
					logger.d("[Remote Message] Nothing. Present in both Mirror and Local Stores.");
				}
			}
		} else {
			logger.w("[Remote Message] Voicemail does not have a source data.");
		}
	}

	/**
	 * Resolve a voicemail that is present both locally and remotely. Several cases can occur:
	 * 
	 * <ul>
	 * <li>
	 * The local message does not have content, fetch it from the remote store.
	 * <li>
	 * The local message is read but the remote is not, mark as read the voicemail on the remote
	 * store.
	 * <li>
	 * The remote message is read, but not the local one. Mark as read the local voicemail.
	 */
	@Override
	public void resolveBothLocalAndRemoteMessage(Voicemail localMessage, Voicemail remoteMessage,
			List<VvmStore.Action> localActions, List<VvmStore.Action> remoteActions) {
		if (!localMessage.hasContent()) {
			remoteActions.add(VvmStoreActions.fetchVoicemailContent(localMessage));
		}

		if (localMessage.isRead() && !remoteMessage.isRead()) {
			remoteActions.add(VvmStoreActions.markAsRead(remoteMessage));
		} else if (remoteMessage.isRead() && !localMessage.isRead()) {
			localActions.add(VvmStoreActions.markAsRead(localMessage));
		}
	}

	/**
	 * Resolve a voicemail that is present in both local and mirror stores. This method is used for
	 * read detection. If the message is read in the local repository, but not in the mirror, mark
	 * as read that voicemail in the remote store.
	 */
	@Override
	public void resolveBothLocalAndMirrorMessage(Voicemail localMessage, Voicemail mirrorMessage,
			List<VvmStore.Action> remoteActions) {
		if (localMessage.isRead() && !mirrorMessage.isRead()) {
			remoteActions.add(VvmStoreActions.markAsRead(localMessage));
		}
	}

	/**
	 * Resolve a voicemail that is present exclusively in the mirror store. Delete the voicemail
	 * from the remote store as it has been deleted by the user.
	 */
	@Override
	public void resolveMirrorOnlyMessage(Voicemail mirrorMessage,
			List<VvmStore.Action> remoteActions) {
		remoteActions.add(VvmStoreActions.delete(mirrorMessage));
	}

}
