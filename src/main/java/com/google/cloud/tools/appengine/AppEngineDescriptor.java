/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.appengine;

import com.google.cloud.tools.appengine.api.AppEngineException;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** Utilities to obtain information from appengine-web.xml. */
public class AppEngineDescriptor {

  private static final String APP_ENGINE_NAMESPACE = "http://appengine.google.com/ns/1.0";
  private final Document document;

  // private to force use of parse method
  private AppEngineDescriptor(Document document) {
    this.document = document;
  }

  /**
   * Parses an appengine-web.xml file.
   *
   * @param in the contents of appengine-web.xml
   * @return a fully parsed object that can be queried
   * @throws IOException if parsing fails due to I/O errors
   * @throws SAXException malformed XML
   */
  public static AppEngineDescriptor parse(InputStream in) throws IOException, SAXException {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setNamespaceAware(true);
      return new AppEngineDescriptor(documentBuilderFactory.newDocumentBuilder().parse(in));
    } catch (ParserConfigurationException exception) {
      throw new SAXException("Cannot parse appengine-web.xml", exception);
    }
  }

  /**
   * Returns project ID from the &lt;application&gt; element of the appengine-web.xml or null if it
   * is missing.
   */
  public String getProjectId() {
    return getText(getNode(document, "appengine-web-app", "application"));
  }

  /**
   * Returns runtime from the &lt;runtime&gt; element of the appengine-web.xml or null if it is
   * missing.
   */
  public String getRuntime() {
    return getText(getNode(document, "appengine-web-app", "runtime"));
  }

  /**
   * Returns project version from the &lt;version&gt; element of the appengine-web.xml or null if it
   * is missing.
   */
  public String getProjectVersion() {
    return getText(getNode(document, "appengine-web-app", "version"));
  }

  /**
   * Returns service ID from the &lt;service&gt; element of the appengine-web.xml, or null if it is
   * missing. Will also look at module ID.
   */
  public String getServiceId() {
    String serviceId = getText(getNode(document, "appengine-web-app", "service"));
    if (serviceId != null) {
      return serviceId;
    }
    return getText(getNode(document, "appengine-web-app", "module"));
  }

  /** Returns true if the runtime read from appengine-web.xml is Java8. */
  public boolean isJava8() {
    String runtime = getRuntime();
    return "java8".equals(runtime) || "java8g".equals(runtime);
  }

  /**
   * Given the following structure:
   *
   * <pre>{@code
   * <env-variables>
   *   <env-var name="key" value="val" />
   * </env-variables>
   * }</pre>
   *
   * <p>This will construct a map of the form {[key, val], ...}.
   *
   * @return a map representing the environment variable settings in the appengine-web.xml
   */
  public Map<String, String> getEnvironment() {
    Node environmentParentNode = getNode(document, "appengine-web-app", "env-variables");
    return getAttributeMap(environmentParentNode, "env-var", "name", "value");
  }

  private static String getText(Node node) {
    if (node != null) {
      try {
        return node.getTextContent();
      } catch (DOMException ex) {
        throw new AppEngineException(
            "Failed to parse text content from node " + node.getNodeName());
      }
    }

    return null;
  }

  /** Returns a map formed from the attributes of the nodes contained within the parent node. */
  private static Map<String, String> getAttributeMap(
      Node parent, String nodeName, String keyAttributeName, String valueAttributeName) {
    if (parent != null) {
      Map<String, String> nameValueAttributeMap = Maps.newHashMap();

      if (parent.hasChildNodes()) {
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
          Node child = parent.getChildNodes().item(i);
          NamedNodeMap attributeMap = child.getAttributes();

          if (nodeName.equals(child.getNodeName()) && attributeMap != null) {
            Node keyNode = attributeMap.getNamedItem(keyAttributeName);

            if (keyNode != null) {
              Node valueNode = attributeMap.getNamedItem(valueAttributeName);
              try {
                nameValueAttributeMap.put(keyNode.getTextContent(), valueNode.getTextContent());
              } catch (DOMException ex) {
                throw new AppEngineException(
                    "Failed to parse value from attribute node " + keyNode.getNodeName());
              }
            }
          }
        }
      }

      return nameValueAttributeMap;
    }

    return null;
  }

  /** Returns the first node found matching the given name contained within the parent node. */
  private static Node getNode(Document doc, String parentNodeName, String targetNodeName) {
    NodeList parentElements = doc.getElementsByTagNameNS(APP_ENGINE_NAMESPACE, parentNodeName);
    if (parentElements.getLength() > 0) {
      Node parent = parentElements.item(0);
      if (parent.hasChildNodes()) {
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
          Node child = parent.getChildNodes().item(i);
          if (child.getNodeName().equals(targetNodeName)) {
            return child;
          }
        }
      }
    }
    return null;
  }
}
