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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;


/**
 * <p>
 * Execution wrapper for class hierarchy example
 * </p>
 */
public class Main  {
    // Constants
    //////////////////////////////////

    // Static variables
    //////////////////////////////////

    // Instance variables
    //////////////////////////////////

    // Constructors
    //////////////////////////////////

    // External signature methods
    //////////////////////////////////

    public static void main( String[] args ) throws IOException {
    	System.out.println("WORKING ..........");
    	
    	final String str = "    <exclusion rdf:resource=\"&icd10no;B92\"/> ";
        //final Pattern pattern = Pattern.compile(">.*</");
    	//RIGHT TODO ->content final Pattern pattern = Pattern.compile(".*>(.*?)<.*");
    	// RIGHT TODO  -> name final Pattern pattern = Pattern.compile("<(.*?)\\s");
//    	final Pattern pattern = Pattern.compile(";(.*?)\"");
//    	final String[] result = (pattern).split(str);
//    	Matcher m = pattern.matcher(str);
////    	
//    	while(m.find())
//    	{
//    		System.out.println(m.group(1));
//    	}
//    	System.out.println();
//        System.out.println(Arrays.toString(result));
        OwlToSolr.parserOWL("newIcd.owl","jose.xml");
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );

        // we have a local copy of the wine ontology
//        m.getDocumentManager().addAltEntry( "http://www.w3.org/2001/sw/WebOnt/guide-src/wine",
//                                            "file:testing/reasoners/bugs/wine.owl" );
//        m.getDocumentManager().addAltEntry( "http://www.w3.org/2001/sw/WebOnt/guide-src/wine.owl",
//                                            "file:testing/reasoners/bugs/wine.owl" );
//        m.getDocumentManager().addAltEntry( "http://www.w3.org/2001/sw/WebOnt/guide-src/food",
//                                            "file:testing/reasoners/bugs/food.owl" );
//        m.getDocumentManager().addAltEntry( "http://www.w3.org/2001/sw/WebOnt/guide-src/food.owl",
//                                            "file:testing/reasoners/bugs/food.owl" );
//
//        m.getDocumentManager().addAltEntry( "http://www.w3.org/2001/sw/WebOnt/guide-src/icd10",
//              "file:owl/icd10no.owl" );
//        m.read( "http://www.w3.org/2001/sw/WebOnt/guide-src/icd10" );
//        new ClassHierarchy().showHierarchy( System.out, m );
//    	// create an empty model
//    	 Model model = ModelFactory.createDefaultModel();
//
//    	 // use the FileManager to find the input file
//    	 String inputFileName="owl/icd10no_turtle.owl";
//    	 InputStream in = FileManager.get().open( inputFileName );
//    	if (in == null) {
//    	    throw new IllegalArgumentException(
//    	                                 "File: " + inputFileName + " not found");
//    	}
//
//    	// read the RDF/XML file
//    	model.read(in, null);
//
//    	// write it to standard out
//    	model.write(System.out);
    }


    // Internal implementation methods
    //////////////////////////////////

    //==============================================================================
    // Inner class definitions
    //==============================================================================

}
