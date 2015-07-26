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
package com.smithmicro.vvm.omtp.config;

public final class StackStaticConfiguration {

    /**
     * Define if the stack is running in debug mode, which means a large amount of logs are written
     * to give feedback on what's going on under the hood.
     */
    public static final boolean DEBUG_MODE = true;

    /**
     * Define if we should accept messages that do not contain a voice payload.
     */
    public static final boolean VOICE_MESSAGES_ONLY = true;

    /**
     * Maximum number of retry attempts in case of failure.
     */
    public static final int MAX_IMAP_ATTEMPTS = 3;

    /**
     * Value that determinate the timeout after which we consider that the answer to the SMS message
     * sent by the stack has not been received default value 20 i.e. 20s
     */
    public static final int SMS_TIMEOUT = 30;

    /**
     * Defines if a full synchronisation should be performed after a new Sync Message has been
     * processed (successfully or not).
     */
    public static final boolean FULL_SYNC_ON_NEW_MSG = true;

    /**
     * Maximum number of attempts the stack should check that the HIPRI Mobile Data Connection has
     * been activated. The delay between two attempts is defined by HIPRI_ACTIVATION_DELAY.
     */
    public static final int HIPRI_ACTIVATION_MAX_ATTEMPTS = 15;

    /**
     * Delay between two attempts to verify that the HIPRI connection has been activated or not (in
     * ms).
     */
    public static final long HIPRI_ACTIVATION_DELAY = 1000;

    /**
     * Should the stack try to route the IMAP traffic through HIPRI while connected on Wi-Fi.
     */
    public static final boolean HIPRI_ON_WIFI = true;

    /**
     * Port on which OMTP SMS messages should be received by Voicemail application.
     * Binary messages with different port numbers should be ignored by the application.
     */
    public static final int SMS_PORT_FOR_OMTP_RECEIVER = 20481;

    /**
     * Name of INBOX folder used for communication with IMAP server and fetching voicemails.
     */
    public static final String INBOX_FOLDER_NAME = "inbox";

    /**
     * Name of GREETINGS folder used for communication with IMAP server and fetching greetings.
     */
    public static final String GREETINGS_FOLDER_NAME = "GREETINGS";
}
