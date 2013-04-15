/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Package
///////////////
package ntnu.tdt4215.project;

// Imports
///////////////
import java.io.IOException;

/**
 * <p>
 * Execution wrapper for class hierarchy example
 * </p>
 */
public class Main {
    // Constants
    // ////////////////////////////////

    // Static variables
    // ////////////////////////////////

    // Instance variables
    // ////////////////////////////////

    // Constructors
    // ////////////////////////////////

    // External signature methods
    // ////////////////////////////////
    private final static String DOCU_FOLDER = "./docu/";
    private final static String T_FOLDER = DOCU_FOLDER + "NLH/T/";

    public static void main(String[] args) throws IOException {
	System.out.println("WORKING ..........");

	final String str = "    <exclusion rdf:resource=\"&icd10no;B92\"/> ";
	// final Pattern pattern = Pattern.compile(">.*</");
	// RIGHT TODO ->content final Pattern pattern =
	// Pattern.compile(".*>(.*?)<.*");
	// RIGHT TODO -> name final Pattern pattern =
	// Pattern.compile("<(.*?)\\s");
	// Parser.parserOWL("newIcd.owl","jose.xml");
	System.out.println(Parser.parseHTM(T_FOLDER + "innhold.htm", ""));
    }

}
