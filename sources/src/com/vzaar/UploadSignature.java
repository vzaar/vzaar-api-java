package com.vzaar;

import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadSignature {

    private String _signature;
    private String _toJson;

    public String guid;
    public String bucket;
    public Date expirationDate;
    public String signature;
    public String acl;
    public String key;
    public String accessKeyId;
    public String policy;
    public boolean https;
    public String uploadHostname;
    public String chunkSize;

    public UploadSignature(String response) {
        try {
            _signature = response;

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(response));
            Document document = domFactory.newDocumentBuilder().parse(is);

            XPath xpath = XPathFactory.newInstance().newXPath();
            https = Boolean.valueOf((String)xpath.compile("/vzaar-api/https/text()").evaluate(document, XPathConstants.STRING));
            key = (String) xpath.compile("/vzaar-api/key/text()").evaluate(document, XPathConstants.STRING);
            accessKeyId = (String) xpath.compile("/vzaar-api/accesskeyid/text()").evaluate(document, XPathConstants.STRING);
            policy = (String) xpath.compile("/vzaar-api/policy/text()").evaluate(document, XPathConstants.STRING);
            guid = (String) xpath.compile("/vzaar-api/guid/text()").evaluate(document, XPathConstants.STRING);
            bucket = (String) xpath.compile("/vzaar-api/bucket/text()").evaluate(document, XPathConstants.STRING);
            acl = (String) xpath.compile("/vzaar-api/acl/text()").evaluate(document, XPathConstants.STRING);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //2014-04-13T20:43:05.000Z
            expirationDate = simpleDateFormat.parse((String) xpath.compile("/vzaar-api/expirationdate/text()").evaluate(document, XPathConstants.STRING));
            signature = (String) xpath.compile("/vzaar-api/signature/text()").evaluate(document, XPathConstants.STRING);
            uploadHostname = (String) xpath.compile("/vzaar-api/upload_hostname/text()").evaluate(document, XPathConstants.STRING);
            chunkSize = (String) xpath.compile("/vzaar-api/chunk_size/text()").evaluate(document, XPathConstants.STRING);

            _toJson = new Gson().toJson(this);

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException | ParseException e) {
            e.printStackTrace();
        }

    }

    public String toXml() {
        return _signature;
    }

    public Document toXmlDocument() {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(false);
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(_signature));
        Document document = null;
        try {
            document = domFactory.newDocumentBuilder().parse(is);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    public String toJson() {
        return _toJson;
    }

    @Override
    public String toString() {
        return "UploadSignature{" +
                "guid=" + this.guid +
                ", bucket=" + bucket +
                ", expirationDate=" + expirationDate +
                ", signature=" + signature +
                ", acl=" + acl +
                ", key=" + key +
                ", accessKeyId=" + accessKeyId +
                ", policy=" + policy +
                ", https=" + https +
                ", uploadHostname=" + uploadHostname +
                ", chunkSize=\'" + chunkSize + "\'" +
                "}";
    }
}
