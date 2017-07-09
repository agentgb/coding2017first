package com.coderising.litestruts;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Struts.xml Config
 */
public class Configuration {

    private Map<String, Action> actionMap;

    public Configuration(String fileName) {
        actionMap = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(Struts.class.getClassLoader().getResourceAsStream(fileName));
            parseXML(document.getRootElement());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public String getClassName(String actionName) {
        Action action = actionMap.get(actionName);
        if (action == null) {
            return null;
        }
        return action.getClassName();
    }

    public String getResultJsp(String actionName, String result) {
        Action action = actionMap.get(actionName);
        if (action == null) {
            return null;
        }
        return action.getResult().get(result);
    }


    private void parseXML(Element root){
        for (Element node : root.elements()) {
            if ("action".equals(node.getName())) {
                String name = node.attributeValue("name");
                String className = node.attributeValue("class");
                Map<String, String> results = new HashMap<>();
                for (Iterator it = node.elementIterator(); it.hasNext(); ) {
                    Element element = (Element) it.next();
                    results.put(element.attributeValue("name"), element.getText());
                }
                Action action = new Action(name, className, results);
                actionMap.put(name, action);
            }
        }
    }
    private static class Action {
        private String name;
        private String className;
        private Map<String, String> result;


        public String getName() {
            return name;
        }

        public String getClassName() {
            return className;
        }

        public Map<String, String> getResult() {
            return result;
        }

        public Action(String name, String className, Map<String, String> result) {
            this.name = name;
            this.className = className;
            this.result = result;
        }
    }

}
