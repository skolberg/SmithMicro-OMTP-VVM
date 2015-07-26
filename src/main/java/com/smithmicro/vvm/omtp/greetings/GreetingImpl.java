/*
 * Copyright (C) 2012 Orange Labs UK. All Rights Reserved.
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
package com.smithmicro.vvm.omtp.greetings;

import com.smithmicro.vvm.omtp.logging.Logger;
import com.smithmicro.vvm.omtp.voicemail.Voicemail;

/**
 * A simple immutable data object to represent a greeting.
 */
public final class GreetingImpl implements Greeting {
	
	private static final Logger logger = Logger.getLogger(GreetingImpl.class);
	private final GreetingType mGreetingType;
	private final Voicemail mVoicemail;
	private final boolean mIsActive;
	private final boolean mIsVoiceContentDownloaded;
	
	private GreetingImpl(GreetingType type, Voicemail voicemail, boolean isActive, 
			boolean isVoiceContentDownloaded) {
		mGreetingType = type;
		mVoicemail = voicemail;
		mIsActive = isActive;
		mIsVoiceContentDownloaded = isVoiceContentDownloaded;
	}
	
	public static Builder createFromFetch(String type, Voicemail voicemail,
			boolean isActive, boolean isVoiceContentDownlaoded) {
		logger.d(String.format(
				"Created new greeting object type:%s, isActive:%s, id=%s, downloaded=%s", type,
				isActive, voicemail.getSourceData(), isVoiceContentDownlaoded));
		return new Builder().setGreetingType(type).setVoicemail(voicemail).setIsActive(isActive)
				.setIsContentDownloaded(isVoiceContentDownlaoded);
	}

	@Override
	public boolean isActive() {
		return mIsActive;
	}
	
	@Override
	public GreetingType getGreetingType() {
		return mGreetingType;
	}

	@Override
	public boolean hasVoicemail() {
		return mVoicemail != null;
	}

	@Override
	public Voicemail getVoicemail() {
		return mVoicemail;
	}
	
	@Override
	public boolean isVoiceContentDownloaded() {
		return mIsVoiceContentDownloaded;
	}
	
	@Override
	public String toString() {
		return "GreetingImpl [mIsActive=" + mIsActive + ", mGreetingType="
				+ mGreetingType.getTypeString() + ", mVoicemailUid=" + mVoicemail.getSourceData()
				+ ", mIsVoiceContentDownlaoded=" + mIsVoiceContentDownloaded + "]";
	}

	  /**
     * Builder pattern for creating a {@link GreetingImpl}.
     * <p>
     * All fields are optional, and can be set with the various {@code setXXX} methods.
     * <p>
     * This class is <b>not thread safe</b>
     */
	public static class Builder {
		private GreetingType mGreetingType;
		private Voicemail mVoicemail;
		private boolean mIsActive;
		private boolean mIsContentDownlaoded;
		
		private Builder() {
		}
		
		public Builder setGreetingType(String type) {
			this.mGreetingType = getGreetingTypeFromString(type);
			return this;
		}


		public Builder setVoicemail(Voicemail voicemail) {
			this.mVoicemail = voicemail;
			return this;
		}


		public Builder setIsActive(boolean isActive) {
			this.mIsActive = isActive;
			return this;
		}
		
		public Builder setIsContentDownloaded(boolean isContentDownloaded) {
			this.mIsContentDownlaoded = isContentDownloaded;
			return this;
		}
		
		public GreetingImpl build() {
			return new GreetingImpl(mGreetingType, mVoicemail, mIsActive, mIsContentDownlaoded);
		}
		
		/**
		 * Returns GreetingType enum based on the input String value.
		 * @param type String value
		 * @return GreetingType object reflecting input String type
		 */
		private GreetingType getGreetingTypeFromString(String type) {
			if (type.equalsIgnoreCase(GreetingType.NORMAL.getTypeString())){
				return GreetingType.NORMAL;
			} else if (type.equalsIgnoreCase(GreetingType.VOICE_SIGNATURE.getTypeString())){
				return GreetingType.VOICE_SIGNATURE;
			} else {
				return GreetingType.UNKNOWN;
			}
			
		}
	}

}
