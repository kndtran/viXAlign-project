/* 
 * @(#)       XMLTools.java
 * 
 * Created    Sat Feb 23 17:07:06 2002
 * 
 * Author     Nguyen Thi Minh Huyen
 *            UMR LORIA (Universities of Nancy, CNRS & INRIA)
 *           
 */
package vn.hus.mim.huyen;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import org.w3c.dom.*;

import org.apache.xerces.jaxp.*;
import org.apache.xerces.dom.*;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.*;
import org.xml.sax.*;

import javax.xml.parsers.*;

public class XMLTools {

    public static final String STD_HEADER
	= "<?xml version=\"1.0\" encoding = \"UTF-8\"?>\n\n";
    public static final String parserClass =  "org.apache.xerces.parsers.SAXParser";

    public XMLTools () {
    }
    
    public static String getTextValue(Node node) {
	Node item = node.getFirstChild();
	while (item != null && item.getNodeType() != Node.TEXT_NODE)
	    item = item.getNextSibling();
	if(item == null) 
	    return "";
	return new String(item.getNodeValue());
    }

    public static void saveDocument(String fileName, Document document, String enc) {
	try {
	    OutputFormat format = new OutputFormat(document);
	    //format.setLineWidth(70);
	    format.setIndenting(true);
	    format.setIndent(3);
	    format.setEncoding(enc);
	    //format.setDoctype("SYSTEM", "token.dtd"); 
	    format.setMediaType("application/xml");
	    format.setOmitComments(true);
	    format.setOmitXMLDeclaration(false);
	    format.setVersion("1.0");
	    format.setStandalone(false);

	    FileOutputStream fileOutStrm = new FileOutputStream(fileName);
	    XMLSerializer ser = new XMLSerializer(fileOutStrm , format);
	    ser.serialize(document);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static String toString(DocumentImpl xdoc) {
	String ret = null;
	text = "";
	walk(xdoc);
	ret = text;
	text = null;
	return ret;
    }

    public static String text = null;

    public static String getText(Node node)   {
	String ret = null;
	text = "";
	walkText(node);
	ret = text;
	text = null;
	return ret;
    }
    
    public static void  walkText(Node node)    {
        int type = node.getNodeType();

	if (type == Node.TEXT_NODE)
	    text += node.getNodeValue();

        //recurse
        for(Node child = node.getFirstChild(); child != null; child = child.getNextSibling())
        {
            walkText(child);
        }
        
    }//end of walkText
  
    public static void  walk(Node node)    {
        int type = node.getNodeType();
        switch(type)
        {
            case Node.ELEMENT_NODE:
            {
                text += '<' + node.getNodeName();
                NamedNodeMap nnm = node.getAttributes();
                if(nnm != null )
                {
                    int len = nnm.getLength() ;
                    Attr attr;
                    for ( int i = 0; i < len; i++ )
                    {
                        attr = (Attr)nnm.item(i);
                        text += ' ' 
                             + attr.getNodeName()
                             + "=\""
                             + attr.getNodeValue()
                             +  '"';
                    }
                }
                text += '>';
                
                break;
                
            }//end of element
            case Node.ENTITY_REFERENCE_NODE:
            {
               
               text += '&' + node.getNodeName() + ';' ;
               break;
                
            }//end of entity
            case Node.CDATA_SECTION_NODE:
            {
                    text +=  "<![CDATA[" 
                            + node.getNodeValue()
                            + "]]>" ;
                     break;       
                
            }
            case Node.TEXT_NODE:
            {
                text += node.getNodeValue();
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE:
            {
                text += "<?" 
                    + node.getNodeName();
                String data = node.getNodeValue();
                if ( data != null && data.length() > 0 ) {
                    text += ' ' + data ;
                }
                text += "?>";
                break;

             }
        }//end of switch
        
              
        //recurse
        for(Node child = node.getFirstChild(); child != null; child = child.getNextSibling())
        {
            walk(child);
        }
        
        //without this the ending tags will miss
        if ( type == Node.ELEMENT_NODE )
        {
            text += "</" + node.getNodeName() + ">";
        }
       
    }//end of walk
}
