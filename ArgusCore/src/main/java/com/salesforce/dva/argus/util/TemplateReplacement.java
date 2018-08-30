package com.salesforce.dva.argus.util;

import com.salesforce.dva.argus.entity.Metric;
import com.salesforce.dva.argus.service.alert.DefaultAlertService;
import com.salesforce.dva.argus.service.alert.notifier.DefaultNotifier;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateReplacement {

    private static final Logger _logger = LoggerFactory.getLogger(TemplateReplacement.class);
    private static TemplateReplacement singletonInstance = new TemplateReplacement();
    private static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss z";
    private static Configuration cfg;

    private TemplateReplacement() {
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    public static TemplateReplacement getInstance( ) {
        return singletonInstance;
    }

    private  Map<String, String> getLowerCaseTagMap(final Map<String, String> tags) {
        Map<String, String> lowerCaseTagMap = new HashMap<>();
        for (String originalTags: tags.keySet()) {
            lowerCaseTagMap.put(originalTags.toLowerCase(), tags.get(originalTags));
        }
        return lowerCaseTagMap;
    }

    private String replaceKeywordsToLowerCase(String templateString) {
        String lowercasedString = templateString;
        Matcher m = Pattern.compile("(?i)\\$\\{scope\\}|\\$\\{metric\\}|\\$\\{tag\\..*?\\}|\\sscope\\s|\\smetric\\s|\\stag\\..*?\\s").matcher(templateString);
        while (m.find()) {
            String currentSubstring = m.group();
            lowercasedString = lowercasedString.replace(currentSubstring, currentSubstring.toLowerCase());
        }
        return lowercasedString;
    }

    public static String applyTemplateChanges(DefaultAlertService.NotificationContext context, String templateString) {

        String generatedString = templateString;
        // Prepare Data.
        Map root = new HashMap();
        root.put("alert", context.getAlert());
        root.put("trigger", context.getTrigger());
        root.put("notification",context.getNotification());
        Metric triggeredMetric = context.getTriggeredMetric();
        root.put("scope", triggeredMetric.getScope());
        root.put("metric", triggeredMetric.getMetric());
        Map<String, String> lowerCaseTagMap = singletonInstance.getLowerCaseTagMap(triggeredMetric.getTags());
        root.put("tag", lowerCaseTagMap);
        root.put("triggerTimestamp", new Date(context.getTriggerFiredTime()));
        root.put("triggerValue", context.getTriggerEventValue());

        do {
            templateString = generatedString;
            templateString = singletonInstance.replaceKeywordsToLowerCase(templateString);
            Template configuredTemplate = null;
            try {
                configuredTemplate = new Template("configuredTemplate", new StringReader(templateString), cfg);
                StringWriter stringWriter = new StringWriter();
                configuredTemplate.process(root, stringWriter);
                generatedString = stringWriter.toString();
            } catch (Exception e) {
                _logger.error(MessageFormat.format("Exception occurred while applying template change on {0}, with error message {1}.", templateString, e.getMessage()));
                return null;
            }
        } while(!generatedString.equals(templateString)); // If we unwrap alert.name, it may also be templatize, we should replace that as well.

        return generatedString;
    }
}
