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
package com.smithmicro.vvm.omtp.callbacks;

/**
 * Generic callback interface, represents the object to whom we send the results of a computation.
 *
 * @param <T> the type of the result expected if this callback is successful
 */
public interface Callback<T> {
    /**
     * Invoked with the appropriate result if the operation completed successfully.
     */
    public void onSuccess(T result);

    /**
     * Invoked if the operation failed.
     */
    public void onFailure(Exception error);
}

