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
package com.orange.labs.uk.omtp.provider;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Interface defining the methods needed for inserting, retrieving and updating OMTP providers
 * from a store.
 */
public interface OmtpProviderStore {

	/**
	 * Update the stored provider information. Insert if necessary. Provider name field needs to be
	 * filled.
	 * If the provider is set as the current one, the others corresponding to the same operator will be set as non current
	 * 
	 * @param providerInfo	Provider Information
	 */
	public boolean updateProviderInfo(OmtpProviderInfo providerInfo);
	
	/**
	 * Gets the provider information based on its name. Returns null if no provider could be found.
	 * 
	 * @param	Provider Name
	 */
	@Nullable
	public OmtpProviderInfo getProviderInfo(String providerName);

	/**
	 * Retrieves the {@link OmtpProviderInfo} corresponding to the provided Network Operator. If
	 * none can be found, Null is returned.
	 */
	@Nullable
	public OmtpProviderInfo getProviderInfoWithNetworkOperator(String networkOperator);
	
	// TODO: Doc
	public OmtpProviderInfo getCurrentProviderInfoWithNetworkOperator(String networkOperator);

	/**
	 * Retrieves a {@link List} of {@link OmtpProviderInfo} compatible the provided Network Operator, If there is none, returns an empty list
	 * @param networkOperator Network operator name
	 * @return List of providers associated to the operator
	 */
	public List<OmtpProviderInfo> getProvidersInfoWithNetworkOperator(String networkOperator);
	
	/**
	 * Removes from the store the {@link OmtpProviderInfo}. A boolean that indicates the success of the operation is returned.
	 */
	public boolean removeProviderInfo(OmtpProviderInfo providerInfo);
}