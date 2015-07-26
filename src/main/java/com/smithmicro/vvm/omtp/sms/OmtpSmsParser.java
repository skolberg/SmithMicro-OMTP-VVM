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
package com.smithmicro.vvm.omtp.sms;

/**
 * OMTP SMS parser interface, for parsing SYNC and STATUS SMS sent by OMTP visual voicemail server.
 */
public interface OmtpSmsParser {
    /**
     * Parses the supplied SMS body and returns back a structured OMTP message.
     * @param mSmsOriginatorNumber 
     *
     * @throws OmtpParseException if failed to parse the message body
     */
    public OmtpMessage parse(String smsBody, String mSmsOriginatorNumber) throws OmtpParseException;
}
