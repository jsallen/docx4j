/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */
package org.xlsx4j.jaxb;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

public class Context {
	
	/*
	 * Two reasons for having a separate class for this:
	 * 1. so that loading SML context does not slow
	 *    down docx4j operation on docx files
	 * 2. to try to maintain clean delineation between
	 *    docx4j and xlsx4j
	 */
	
	public static JAXBContext jcSML;
	
	private static Logger log = Logger.getLogger(Context.class);
	
	static {

		// Display diagnostic info about version of JAXB being used.
    	Class c;
    	try {
    		c = Class.forName("com.sun.xml.bind.marshaller.MinimumEscapeHandler");
    		System.out.println("JAXB: Using RI");
    	} catch (ClassNotFoundException cnfe) {
    		// JAXB Reference Implementation not present
    		System.out.println("JAXB: RI not present.  Trying Java 6 implementation.");
        	try {
				c = Class.forName("com.sun.xml.internal.bind.marshaller.MinimumEscapeHandler");
	    		System.out.println("JAXB: Using Java 6 implementation.");
			} catch (ClassNotFoundException e) {
				System.out.println("JAXB: neither Reference Implementation nor Java 6 implementation present?");
			}
    	}
		
		try {	
			
			// JBOSS might use a different class loader to load JAXBContext, which causes problems,
			// so explicitly specify our class loader.
			Context tmp = new Context();
			java.lang.ClassLoader classLoader = tmp.getClass().getClassLoader();
			//log.info("\n\nClassloader: " + classLoader.toString() );			
			
			log.info("loading Context jcSML");			
			jcSML = JAXBContext.newInstance("org.xlsx4j.sml",classLoader );
			log.info(".. loaded ..");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}				
	}
	
	
	public static org.xlsx4j.sml.ObjectFactory smlObjectFactory;
	public static org.xlsx4j.sml.ObjectFactory getsmlObjectFactory() {
		
		if (smlObjectFactory==null) {
			smlObjectFactory = new org.xlsx4j.sml.ObjectFactory();
		}
		return smlObjectFactory;
		
	}
}
