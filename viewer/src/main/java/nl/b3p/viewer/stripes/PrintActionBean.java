/*
 * Copyright (C) 2012-2013 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.b3p.viewer.stripes;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import nl.b3p.viewer.config.ClobElement;
import nl.b3p.viewer.config.app.Application;
import nl.b3p.viewer.print.Legend;
import nl.b3p.viewer.print.PrintExtraInfo;
import nl.b3p.viewer.print.PrintGenerator;
import nl.b3p.viewer.print.PrintInfo;
import nl.b3p.viewer.print.PrintUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.xmlgraphics.util.MimeConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 * @author Meine Toonen
 * @author Eddy Scheper
 * @author Mark Prins
 */
@UrlBinding("/action/print")
@StrictBinding
public class PrintActionBean implements ActionBean {
    private static final Log log = LogFactory.getLog(PrintActionBean.class);     
    protected static Logger fopLogger = Logger.getLogger("org.apache.fop");
    public static final String A5_Landscape = "A5_Landscape.xsl";
    public static final String A5_Portrait = "A5_Portrait.xsl";
    public static final String A4_Landscape = "A4_Landscape.xsl";
    public static final String A4_Portrait = "A4_Portrait.xsl";
    public static final String A3_Landscape = "A3_Landscape.xsl";
    public static final String A3_Portrait = "A3_Portrait.xsl";
    public static final String A0_Landscape = "A0_Landscape.xsl";
    public static final String A0_Portrait = "A0_Portrait.xsl";
    public static final String DEFAULT_TEMPLATE_PATH = "/WEB-INF/xsl/print/";
    public static final String A5 = "a5";
    public static final String A4 = "a4";
    public static final String A3 = "a3";
    public static final String A0 = "a0";
    public static final String LANDSCAPE = "landscape";
    public static final String PORTRAIT = "portrait";
    public static SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", new Locale("NL"));
    @Validate
    private String params;
    
    private ActionBeanContext context;
    
    @DefaultHandler
    public Resolution print() throws JSONException, Exception {
        boolean mailprint=false;
        JSONObject jRequest = new JSONObject(params);
        
        //get the appId:
        Long appId = jRequest.optLong("appId");
        Application app = Stripersist.getEntityManager().find(Application.class, appId);
        
        //get the image url:
        String imageUrl = PrintUtil.getImageUrl(params, context.getRequest().getRequestURL().toString(), context.getRequest().getSession().getId());
        
        //get the form settings
        final PrintInfo info = new PrintInfo();
        if (jRequest.has("title")){
            info.setTitle(jRequest.getString("title"));
        }
        if (jRequest.has("subtitle")){
            info.setSubtitle(jRequest.getString("subtitle"));
        }
        String username = context.getRequest().getRemoteUser();
        if (!StringUtils.isEmpty(username)){
            info.setUsername(username);
        }

        info.setDate(df.format(new Date()));        
        info.setImageUrl(imageUrl);
        if (jRequest.has("bbox")){
            info.setBbox(jRequest.getString("bbox"));
        }
        
        if (jRequest.has("overview")){
            String url = PrintUtil.getOverviewUrl(params, context.getRequest().getRequestURL().toString(), context.getRequest().getSession().getId());
            info.setOverviewUrl(url);
        }
        if (jRequest.has("extraTekst")){
            info.setRemark(jRequest.getString("extraTekst"));
        }
        if(jRequest.has("quality")){
            info.setQuality(jRequest.getInt("quality"));
        }
        
        /* !!!!temp skip the legend, WIP*/
        if (jRequest.has("includeLegend") && jRequest.getBoolean("includeLegend")){
            if(jRequest.has("legendUrl")){
                JSONArray jarray=null;
                Object o = jRequest.get("legendUrl");
                if (o instanceof JSONArray){
                    jarray= (JSONArray)o;
                }else if (o instanceof String){
                    jarray = new JSONArray();
                    jarray.put(o);
                }
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject legendJson;
                    try {
                        legendJson = jarray.getJSONObject(i);
                    } catch (JSONException jse) {
                        // for some historic reason the print component sends double encoded json for legend url
                        legendJson = new JSONObject(jarray.getString(i));
                    }
                    Legend legend = new Legend(legendJson);
                    info.getLegendUrls().add(legend);
                }
            }
            info.cacheLegendImagesAndReadDimensions();
        }
        
        if (jRequest.has("angle")){
            int angle = jRequest.getInt("angle");
            angle = angle % 360;
            //because the map viewport is rotated the actual map rotation is negative
            angle = 360-angle;
            info.setAngle(angle);
        }
        
        final String mimeType;
//        if (jRequest.has("action") && jRequest.getString("action").equalsIgnoreCase("saveRTF")){
//            mimeType=MimeConstants.MIME_RTF;
//        }else
        if (jRequest.has("action") && jRequest.getString("action").equalsIgnoreCase("savePDF")) {
            mimeType=MimeConstants.MIME_PDF;
        }else if(jRequest.has("action") && jRequest.getString("action").equalsIgnoreCase("mailPDF")){
            mimeType=MimeConstants.MIME_PDF;
            mailprint=true;
        }else{
            throw new Exception("Unidentified action: " + jRequest.getString("action"));
        }
        
        // The json structure is:
//            [{
//                className: <String> entry.component.$className,
//                componentName: <String>entry.component.name,
//                info: <JSONObject> info in JSONObject
//            }]
        if(jRequest.has("extra")){
            
            log.debug("Print Parse 'extra'");
            
            JSONArray jarray = jRequest.getJSONArray("extra");
            List<PrintExtraInfo> peis = new ArrayList<PrintExtraInfo>();
            for (int i=0; i < jarray.length();i++){
                JSONObject extraObj = jarray.getJSONObject(i);
                PrintExtraInfo pei = new PrintExtraInfo();
                String className = extraObj.getString("className");
                String componentName = extraObj.getString("componentName");
                Object infoObject = extraObj.get("info");
                pei.setClassName(className);
                if (componentName!=null) {
                    componentName = componentName.replaceAll("_", " ");
                }
                pei.setComponentName(componentName);
                if(infoObject instanceof JSONArray) {
                    pei.setInfoArray((JSONArray)infoObject);
                } else if(infoObject instanceof JSONObject) {
                    pei.setInfoText((JSONObject)infoObject);
                }
                peis.add(pei);
            }
            info.setExtra(peis);
        }

        //determine the correct template
        String pageFormat = jRequest.has("pageformat") ? jRequest.getString("pageformat") : A4;
        String orientation = jRequest.has("orientation") ? jRequest.getString("orientation") : PORTRAIT;
        // make it possible to override the template using a filename eg mytemplate.xsl which should be in one of the well known locations
        String xsltemplate = jRequest.has("xsltemplate") ? jRequest.getString("xsltemplate") : null;
        final String templateName = ((xsltemplate != null) ? xsltemplate : getTemplateName(pageFormat, orientation));
        log.debug(String.format("Request for print using pageFormat: %s, orientation %s with output format %s", pageFormat, orientation, mimeType));

        final String templateUrl;
        final boolean useMailer = mailprint;
        if (app!=null && app.getDetails()!=null && app.getDetails().get("stylesheetPrint")!=null){            
            ClobElement ce = app.getDetails().get("stylesheetPrint");
            templateUrl=ce.getValue()+templateName;
        }else{
            templateUrl=context.getServletContext().getRealPath(DEFAULT_TEMPLATE_PATH+templateName);
        }
        final String toMail = jRequest.getString("mailTo");
        final String fromMail = jRequest.has("fromAddress") ? jRequest.getString("fromAddress") : "";
        final String fromName = jRequest.has("fromName") ? jRequest.getString("fromName") : "";
        
        StreamingResolution res = new StreamingResolution(mimeType) {
            @Override
            public void stream(HttpServletResponse response) throws Exception {
                /* Set filename and extension */
                String filename = "Kaart_" + info.getDate();

                switch (mimeType) {
                    case MimeConstants.MIME_PDF:
                        filename += ".pdf";
                        break;
//                    case MimeConstants.MIME_RTF:
//                        filename += ".rtf";
//                        break;
                }
                if (templateUrl.toLowerCase().startsWith("http://") 
                        || templateUrl.toLowerCase().startsWith("https://") 
                        || templateUrl.toLowerCase().startsWith("ftp://")){
                    PrintGenerator.createOutput(info,mimeType, new URL(templateUrl),true,response,filename);
                }else{
                    File f = new File(templateUrl);
                    if (!f.exists()){
                        f = new File(context.getServletContext().getRealPath(templateUrl));
                    }
                    if (!f.exists()){
                        log.error("Can't find template: "+f.getAbsolutePath()+". Using the default templates");
                        f= new File(context.getServletContext().getRealPath(DEFAULT_TEMPLATE_PATH+templateName));
                    }
                    try {
                        if(useMailer){
                            this.setAttachment(false);
                            response.setContentType("plain/text");
                            Thread t = new Thread(new PrintGenerator(info, mimeType, f, filename,fromName, fromMail, toMail));
                            t.start();
                            response.getWriter().println("success");
                            response.getWriter().close();
                            response.getWriter().flush();
                        }else{
                            PrintGenerator.createOutput(info,mimeType, f,true,response,filename);
                        }
                    } finally {
                        info.removeLegendImagesCache();
                    }
                }
                
            }
        };
        return res;
    }    

    private String getTemplateName(String pageFormat, String orientation) {
        if (A5.equalsIgnoreCase(pageFormat) && LANDSCAPE.equalsIgnoreCase(orientation)){
            return A5_Landscape;
        }else if (A5.equalsIgnoreCase(pageFormat) && PORTRAIT.equalsIgnoreCase(orientation)){
            return A5_Portrait;
        }else if (A4.equalsIgnoreCase(pageFormat) && LANDSCAPE.equalsIgnoreCase(orientation)){
            return A4_Landscape;
        }else if (A3.equalsIgnoreCase(pageFormat) && PORTRAIT.equalsIgnoreCase(orientation)){
            return A3_Portrait;
        }else if (A3.equalsIgnoreCase(pageFormat) && LANDSCAPE.equalsIgnoreCase(orientation)){
            return A3_Landscape;
        }else if (A0.equalsIgnoreCase(pageFormat) && PORTRAIT.equalsIgnoreCase(orientation)){
            return A0_Portrait;
        }else if (A0.equalsIgnoreCase(pageFormat) && LANDSCAPE.equalsIgnoreCase(orientation)){
            return A0_Landscape;
        }else{
            return A4_Portrait;
        }       
    }
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ActionBeanContext getContext() {
        return context;
    }
    
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    //</editor-fold>


    


    
}
