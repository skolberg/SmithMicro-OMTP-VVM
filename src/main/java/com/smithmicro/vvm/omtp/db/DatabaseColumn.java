/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smithmicro.vvm.omtp.db;

/**
 * Base class for definitions of columns.
 */
public interface DatabaseColumn {
    /** The name of the column. */
    public String getColumnName();

    /** The type of the column in the SQLite database. */
    public String getColumnType();

    /** The version of the database in which this column was introduced. */
    public int getSinceVersion();
}
