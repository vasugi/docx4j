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

package org.docx4j.openpackaging.parts;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.dom4j.Document;




public class DocPropsExtendedPart extends JaxbXmlPart {
	
	/*
	 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
	 * <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" 
	 * xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
		 * <Template>Normal.dotm</Template>
		 * <TotalTime>0</TotalTime>
		 * <Pages>1</Pages><Words>3</Words><Characters>20</Characters>
		 * <Application>Microsoft Office Word</Application>
		 * <DocSecurity>0</DocSecurity>
		 * <Lines>1</Lines><Paragraphs>1</Paragraphs>
		 * <ScaleCrop>false</ScaleCrop>
		 * <Company>Plutext Pty Ltd</Company>
		 * <LinksUpToDate>false</LinksUpToDate><CharactersWithSpaces>22</CharactersWithSpaces>
		 * <SharedDoc>false</SharedDoc>
		 * <HyperlinksChanged>false</HyperlinksChanged>
		 * <AppVersion>12.0000</AppVersion>
	 * </Properties>
	 */
	
	
	private static Logger log = Logger.getLogger(DocPropsExtendedPart.class);
	
	 /** 
	 * @throws InvalidFormatException
	 */
	public DocPropsExtendedPart(PartName partName) throws InvalidFormatException {
		super(partName);
		init();
	}

	public DocPropsExtendedPart() throws InvalidFormatException {
		super(new PartName("/docProps/app.xml"));
		init();
	}
	
	public void init() {
		
		jc = Context.jcDocPropsExtended;
		
		// Used if this Part is added to [Content_Types].xml 
		setContentType(new  org.docx4j.openpackaging.contenttype.ContentType( 
				org.docx4j.openpackaging.contenttype.ContentTypes.OFFICEDOCUMENT_EXTENDEDPROPERTIES));

		// Used when this Part is added to a rels 
		setRelationshipType(Namespaces.PROPERTIES_EXTENDED);
	}


    /**
     * Unmarshal XML data from the specified InputStream and return the 
     * resulting content tree.  Validation event location information may
     * be incomplete when using this form of the unmarshal API.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     * 
     * @param is the InputStream to unmarshal XML data from
     * @return the newly created root object of the java content tree 
     *
     * @throws JAXBException 
     *     If any unexpected errors occur while unmarshalling
     */
    public Object unmarshal( java.io.InputStream is ) throws JAXBException {
    	
		try {
			
//			if (jc==null) {
//				setJAXBContext(Context.jc);				
//			}
		    		    
			setJAXBContext(org.docx4j.jaxb.Context.jcDocPropsExtended);
			Unmarshaller u = jc.createUnmarshaller();
			
			//u.setSchema(org.docx4j.jaxb.WmlSchema.schema);
			u.setEventHandler(new org.docx4j.jaxb.JaxbValidationEventHandler());

			log.info("unmarshalling " + this.getClass().getName() + " \n\n" );									
						
			jaxbElement = u.unmarshal( is );
			
			
			log.info("\n\n" + this.getClass().getName() + " unmarshalled \n\n" );									

		} catch (Exception e ) {
			e.printStackTrace();
		}
    	
		return jaxbElement;
    	
    }
	
}

	
