package com.vzaar;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class UploadSignature {
    private String https;
    private String key;
    private String accesskeyid;
    private String policy;
    private String guid;
    private String bucket;
    private String acl;
    private String expirationdate;
    private String signature;

    public String https() {
        return https;
    }

    public void https(String https) {
        this.https = https;
    }

    public String key() {
        return key;
    }

    public void key(String key) {
        this.key = key;
    }

    public String accesskeyid() {
        return accesskeyid;
    }

    public void accesskeyid(String accesskeyid) {
        this.accesskeyid = accesskeyid;
    }

    public String policy() {
        return policy;
    }

    public void policy(String policy) {
        this.policy = policy;
    }

    public String guid() {
        return guid;
    }

    public void guid(String guid) {
        this.guid = guid;
    }

    public String bucket() {
        return bucket;
    }

    public void bucket(String bucket) {
        this.bucket = bucket;
    }

    public String acl() {
        return acl;
    }

    public void acl(String acl) {
        this.acl = acl;
    }

    public String expirationdate() {
        return expirationdate;
    }

    public void expirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String signature() {
        return signature;
    }

    public void signature(String signature) {
        this.signature = signature;
    }

    public UploadSignature() {
    }

    public UploadSignature(String guid, String key, String https, String acl, String bucket, String policy, String expirationDate, String accessKeyId, String signature) {
        this.guid = guid;
        this.key = key;
        this.https = https;
        this.acl = acl;
        this.bucket = bucket;
        this.policy = policy;
        this.expirationdate = expirationDate;
        this.accesskeyid = accessKeyId;
        this.signature = signature;
    }

    public static UploadSignature fromJson(String data) {
        UploadSignature signature = null;
        Gson gson = new Gson();
        signature = gson.fromJson(data, UploadSignature.class);
        return signature;
    }

    public static UploadSignature fromXml(String data) {
        UploadSignature signature = null;
        try {

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(data));
            Document document = domFactory.newDocumentBuilder().parse(is);

            XPath xpath = XPathFactory.newInstance().newXPath();
            signature = new UploadSignature();
            signature.https((String) xpath.compile("/vzaar-api/https/text()").evaluate(document, XPathConstants.STRING));
            signature.key((String) xpath.compile("/vzaar-api/key/text()").evaluate(document, XPathConstants.STRING));
            signature.accesskeyid((String) xpath.compile("/vzaar-api/accesskeyid/text()").evaluate(document, XPathConstants.STRING));
            signature.policy((String) xpath.compile("/vzaar-api/policy/text()").evaluate(document, XPathConstants.STRING));
            signature.guid((String) xpath.compile("/vzaar-api/guid/text()").evaluate(document, XPathConstants.STRING));
            signature.bucket((String) xpath.compile("/vzaar-api/bucket/text()").evaluate(document, XPathConstants.STRING));
            signature.acl((String) xpath.compile("/vzaar-api/acl/text()").evaluate(document, XPathConstants.STRING));
            signature.expirationdate((String) xpath.compile("/vzaar-api/expirationdate/text()").evaluate(document, XPathConstants.STRING));
            signature.signature((String) xpath.compile("/vzaar-api/signature/text()").evaluate(document, XPathConstants.STRING));
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return signature;
    }

    @Override
    public String toString() {
        return "UploadSignature ["
                + (https != null ? "https=" + https + ", " : "")
                + (key != null ? "key=" + key + ", " : "")
                + (accesskeyid != null ? "accesskeyid=" + accesskeyid + ", "
                : "")
                + (policy != null ? "policy=" + policy + ", " : "")
                + (guid != null ? "guid=" + guid + ", " : "")
                + (bucket != null ? "bucket=" + bucket + ", " : "")
                + (acl != null ? "acl=" + acl + ", " : "")
                + (expirationdate != null ? "expirationdate=" + expirationdate
                + ", " : "")
                + (signature != null ? "signature=" + signature : "") + "]";
    }


}
