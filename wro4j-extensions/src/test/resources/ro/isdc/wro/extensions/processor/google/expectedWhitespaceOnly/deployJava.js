var deployJava=function(){var hattrs={core:["id","class","title","style"],i18n:["lang","dir"],events:["onclick","ondblclick","onmousedown","onmouseup","onmouseover","onmousemove","onmouseout","onkeypress","onkeydown","onkeyup"],applet:["codebase","code","name","archive","object","width","height","alt","align","hspace","vspace"],object:["classid","codebase","codetype","data","type","archive","declare","standby","height","width","usemap","name","tabindex","align","border","hspace","vspace"]},object_valid_attrs=
hattrs.object.concat(hattrs.core,hattrs.i18n,hattrs.events),applet_valid_attrs=hattrs.applet.concat(hattrs.core),loc,i;function arHas(ar,attr){var len=ar.length;for(i=0;i<len;i++)if(ar[i]===attr)return true;return false}function isValidAppletAttr(attr){return arHas(applet_valid_attrs,attr.toLowerCase())}function isValidObjectAttr(attr){return arHas(object_valid_attrs,attr.toLowerCase())}var rv={debug:null,firefoxJavaVersion:null,myInterval:null,preInstallJREList:null,returnPage:null,brand:null,locale:null,
installType:null,EAInstallEnabled:false,EarlyAccessURL:null,getJavaURL:"http://java.sun.com/webapps/getjava/BrowserRedirect?host\x3djava.com",appleRedirectPage:"http://www.apple.com/support/downloads/",oldMimeType:"application/npruntime-scriptable-plugin;DeploymentToolkit",mimeType:"application/java-deployment-toolkit",launchButtonPNG:"http://java.sun.com/products/jfc/tsc/articles/swing2d/webstart.png",browserName:null,browserName2:null,getJREs:function(){var list=new Array;if(this.isPluginInstalled()){var plugin=
this.getPlugin();var VMs=plugin.jvms;for(i=0;i<VMs.getLength();i++)list[i]=VMs.get(i).version}else{var browser=this.getBrowser();if(browser==="MSIE")if(this.testUsingActiveX("1.7.0"))list[0]="1.7.0";else if(this.testUsingActiveX("1.6.0"))list[0]="1.6.0";else if(this.testUsingActiveX("1.5.0"))list[0]="1.5.0";else if(this.testUsingActiveX("1.4.2"))list[0]="1.4.2";else{if(this.testForMSVM())list[0]="1.1"}else if(browser==="Netscape Family"){this.getJPIVersionUsingMimeType();if(this.firefoxJavaVersion!==
null)list[0]=this.firefoxJavaVersion;else if(this.testUsingMimeTypes("1.7"))list[0]="1.7.0";else if(this.testUsingMimeTypes("1.6"))list[0]="1.6.0";else if(this.testUsingMimeTypes("1.5"))list[0]="1.5.0";else if(this.testUsingMimeTypes("1.4.2"))list[0]="1.4.2";else if(this.browserName2==="Safari")if(this.testUsingPluginsArray("1.7.0"))list[0]="1.7.0";else if(this.testUsingPluginsArray("1.6"))list[0]="1.6.0";else if(this.testUsingPluginsArray("1.5"))list[0]="1.5.0";else if(this.testUsingPluginsArray("1.4.2"))list[0]=
"1.4.2"}}if(this.debug)for(i=0;i<list.length;++i)alert("We claim to have detected Java SE "+list[i]);return list},installJRE:function(requestVersion,installCallback){var ret=false;if(this.isPluginInstalled())if(this.getPlugin().installJRE(requestVersion,installCallback)){this.refresh();if(this.returnPage!==null)document.location=this.returnPage;return true}else return false;else return this.installLatestJRE()},installLatestJRE:function(installCallback){if(this.isPluginInstalled())if(this.getPlugin().installLatestJRE(installCallback)){this.refresh();
if(this.returnPage!==null)document.location=this.returnPage;return true}else return false;else{var browser=this.getBrowser();var platform=navigator.platform.toLowerCase();if(this.EAInstallEnabled==="true"&&platform.indexOf("win")!==-1&&this.EarlyAccessURL!==null){this.preInstallJREList=this.getJREs();if(this.returnPage!==null)this.myInterval=setInterval("deployJava.poll()",3E3);location.href=this.EarlyAccessURL;return false}else{if(browser==="MSIE")return this.IEInstall();else if(browser==="Netscape Family"&&
platform.indexOf("win32")!==-1)return this.FFInstall();else location.href=this.getJavaURL+(this.returnPage!==null?"\x26returnPage\x3d"+this.returnPage:"")+(this.locale!==null?"\x26locale\x3d"+this.locale:"")+(this.brand!==null?"\x26brand\x3d"+this.brand:"");return false}}},runApplet:function(attributes,parameters,minimumVersion){if(minimumVersion==="undefined"||minimumVersion===null)minimumVersion="1.1";var regex="^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";var matchData=minimumVersion.match(regex);
if(this.returnPage===null)this.returnPage=document.location;if(matchData!==null){var browser=this.getBrowser();if(browser!=="?"&&"Safari"!==this.browserName2)if(this.versionCheck(minimumVersion+"+"))this.writeAppletTag(attributes,parameters);else{if(this.installJRE(minimumVersion+"+"))this.writeAppletTag(attributes,parameters)}else this.writeAppletTag(attributes,parameters)}else if(this.debug)alert("Invalid minimumVersion argument to runApplet():"+minimumVersion)},writeAppletTag:function(attributes,
parameters){var startApplet="\x3c"+"applet ";var params="";var endApplet="\x3c"+"/"+"applet"+"\x3e";var addCodeAttribute=true;if(null===parameters||typeof parameters!=="object")parameters=new Object;for(var attribute in attributes)if(!isValidAppletAttr(attribute))parameters[attribute]=attributes[attribute];else{startApplet+=" "+attribute+'\x3d"'+attributes[attribute]+'"';if(attribute==="code")addCodeAttribute=false}var codebaseParam=false;for(var parameter in parameters){if(parameter==="codebase_lookup")codebaseParam=
true;if(parameter==="object"||parameter==="java_object"||parameter==="java_code")addCodeAttribute=false;params+='\x3cparam name\x3d"'+parameter+'" value\x3d"'+parameters[parameter]+'"/\x3e'}if(!codebaseParam)params+='\x3cparam name\x3d"codebase_lookup" value\x3d"false"/\x3e';if(addCodeAttribute)startApplet+=' code\x3d"dummy"';startApplet+="\x3e";document.write(startApplet+"\n"+params+"\n"+endApplet)},versionCheck:function(versionPattern){index=0;var regex="^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?(\\*|\\+)?$";
var matchData=versionPattern.match(regex);if(matchData!==null){var familyMatch=true;var patternArray=new Array;for(i=1;i<matchData.length;++i)if(typeof matchData[i]==="string"&&matchData[i]!==""){patternArray[index]=matchData[i];index++}if(patternArray[patternArray.length-1]==="+"){familyMatch=false;patternArray.length--}else if(patternArray[patternArray.length-1]==="*")patternArray.length--;var list=this.getJREs();for(i=0;i<list.length;++i)if(this.compareVersionToPattern(list[i],patternArray,familyMatch))return true;
return false}else{alert("Invalid versionPattern passed to versionCheck: "+versionPattern);return false}},isWebStartInstalled:function(minimumVersion){var browser=this.getBrowser();if(browser==="?"||"Safari"===this.browserName2)return true;if(minimumVersion==="undefined"||minimumVersion===null)minimumVersion="1.4.2";var retval=false;var regex="^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";var matchData=minimumVersion.match(regex);if(matchData!==null)retval=this.versionCheck(minimumVersion+"+");
else{if(this.debug)alert("Invalid minimumVersion argument to isWebStartInstalled(): "+minimumVersion);retval=this.versionCheck("1.4.2+")}return retval},getJPIVersionUsingMimeType:function(){for(i=0;i<navigator.mimeTypes.length;++i){var s=navigator.mimeTypes[i].type;var m=s.match(/^application\/x-java-applet;jpi-version=(.*)$/);if(m!==null){this.firefoxJavaVersion=m[1];if("Opera"!==this.browserName2)break}}},launchWebStartApplication:function(jnlp){var uaString=navigator.userAgent.toLowerCase();this.getJPIVersionUsingMimeType();
if(this.isWebStartInstalled("1.7.0")===false)if(this.installJRE("1.7.0+")===false||this.isWebStartInstalled("1.7.0")===false)return false;var jnlpDocbase=null;if(document.documentURI)jnlpDocbase=document.documentURI;if(jnlpDocbase===null)jnlpDocbase=document.URL;var browser=this.getBrowser();var launchTag;if(browser==="MSIE")launchTag="\x3c"+'object classid\x3d"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" '+'width\x3d"0" height\x3d"0"\x3e'+"\x3c"+'PARAM name\x3d"launchjnlp" value\x3d"'+jnlp+'"'+"\x3e"+
"\x3c"+'PARAM name\x3d"docbase" value\x3d"'+jnlpDocbase+'"'+"\x3e"+"\x3c"+"/"+"object"+"\x3e";else if(browser==="Netscape Family")launchTag="\x3c"+'embed type\x3d"application/x-java-applet;jpi-version\x3d'+this.firefoxJavaVersion+'" '+'width\x3d"0" height\x3d"0" '+'launchjnlp\x3d"'+jnlp+'"'+'docbase\x3d"'+jnlpDocbase+'"'+" /\x3e";if(document.body==="undefined"||document.body===null){document.write(launchTag);document.location=jnlpDocbase}else{var divTag=document.createElement("div");divTag.id="div1";
divTag.style.position="relative";divTag.style.left="-10000px";divTag.style.margin="0px auto";divTag.className="dynamicDiv";divTag.innerHTML=launchTag;document.body.appendChild(divTag)}},createWebStartLaunchButtonEx:function(jnlp,minimumVersion){if(this.returnPage===null)this.returnPage=jnlp;var url="javascript:deployJava.launchWebStartApplication('"+jnlp+"');";document.write("\x3c"+'a href\x3d"'+url+"\" onMouseOver\x3d\"window.status\x3d''; "+'return true;"\x3e\x3c'+"img "+'src\x3d"'+this.launchButtonPNG+
'" '+'border\x3d"0" /\x3e\x3c'+"/"+"a"+"\x3e")},createWebStartLaunchButton:function(jnlp,minimumVersion){if(this.returnPage===null)this.returnPage=jnlp;var url="javascript:"+"if (!deployJava.isWebStartInstalled(\x26quot;"+minimumVersion+"\x26quot;)) {"+"if (deployJava.installLatestJRE()) {"+"if (deployJava.launch(\x26quot;"+jnlp+"\x26quot;)) {}"+"}"+"} else {"+"if (deployJava.launch(\x26quot;"+jnlp+"\x26quot;)) {}"+"}";document.write("\x3c"+'a href\x3d"'+url+"\" onMouseOver\x3d\"window.status\x3d''; "+
'return true;"\x3e\x3c'+"img "+'src\x3d"'+this.launchButtonPNG+'" '+'border\x3d"0" /\x3e\x3c'+"/"+"a"+"\x3e")},launch:function(jnlp){document.location=jnlp;return true},isPluginInstalled:function(){var plugin=this.getPlugin();if(plugin&&plugin.jvms)return true;else return false},isAutoUpdateEnabled:function(){if(this.isPluginInstalled())return this.getPlugin().isAutoUpdateEnabled();return false},setAutoUpdateEnabled:function(){if(this.isPluginInstalled())return this.getPlugin().setAutoUpdateEnabled();
return false},setInstallerType:function(type){this.installType=type;if(this.isPluginInstalled())return this.getPlugin().setInstallerType(type);return false},setAdditionalPackages:function(packageList){if(this.isPluginInstalled())return this.getPlugin().setAdditionalPackages(packageList);return false},setEarlyAccess:function(enabled){this.EAInstallEnabled=enabled},isPlugin2:function(){if(this.isPluginInstalled())if(this.versionCheck("1.6.0_10+"))try{return this.getPlugin().isPlugin2()}catch(err){}return false},
allowPlugin:function(){this.getBrowser();var ret="Safari"!==this.browserName2&&"Opera"!==this.browserName2;return ret},getPlugin:function(){var ret=null;if(this.allowPlugin())ret=document.getElementById("deployJavaPlugin");return ret},compareVersionToPattern:function(version,patternArray,familyMatch){var regex="^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+)(?:_(\\d+))?)?)?$";var matchData=version.match(regex);if(matchData!==null){index=0;var result=new Array;for(i=1;i<matchData.length;++i)if(typeof matchData[i]===
"string"&&matchData[i]!==""){result[index]=matchData[i];index++}var l=Math.min(result.length,patternArray.length);if(familyMatch){for(i=0;i<l;++i)if(result[i]!==patternArray[i])return false;return true}else{for(i=0;i<l;++i)if(result[i]<patternArray[i])return false;else if(result[i]>patternArray[i])return true;return true}}else return false},getBrowser:function(){if(this.browserName===null){var browser=navigator.userAgent.toLowerCase();if(this.debug)alert("userAgent -\x3e "+browser);if(browser.indexOf("msie")!==
-1){this.browserName="MSIE";this.browserName2="MSIE"}else if(browser.indexOf("iphone")!==-1){this.browserName="Netscape Family";this.browserName2="iPhone"}else if(browser.indexOf("firefox")!==-1){this.browserName="Netscape Family";this.browserName2="Firefox"}else if(browser.indexOf("chrome")!==-1){this.browserName="Netscape Family";this.browserName2="Chrome"}else if(browser.indexOf("safari")!==-1){this.browserName="Netscape Family";this.browserName2="Safari"}else if(browser.indexOf("mozilla")!==-1){this.browserName=
"Netscape Family";this.browserName2="Other"}else if(browser.indexOf("opera")!==-1){this.browserName="Netscape Family";this.browserName2="Opera"}else{this.browserName="?";this.browserName2="unknown"}if(this.debug)alert("Detected browser name:"+this.browserName+", "+this.browserName2)}return this.browserName},testUsingActiveX:function(version){var objectName="JavaWebStart.isInstalled."+version+".0";if(!ActiveXObject){if(this.debug)alert("Browser claims to be IE, but no ActiveXObject object?");return false}try{return new ActiveXObject(objectName)!==
null}catch(exception){return false}},testForMSVM:function(){var clsid="{08B0E5C0-4FCB-11CF-AAA5-00401C608500}";if(typeof oClientCaps!=="undefined"){var v=oClientCaps.getComponentVersion(clsid,"ComponentID");if(v===""||v==="5,0,5000,0")return false;else return true}else return false},testUsingMimeTypes:function(version){if(!navigator.mimeTypes){if(this.debug)alert("Browser claims to be Netscape family, but no mimeTypes[] array?");return false}for(i=0;i<navigator.mimeTypes.length;++i){s=navigator.mimeTypes[i].type;
var m=s.match(/^application\/x-java-applet\x3Bversion=(1\.8|1\.7|1\.6|1\.5|1\.4\.2)$/);if(m!==null)if(this.compareVersions(m[1],version))return true}return false},testUsingPluginsArray:function(version){if(!navigator.plugins||!navigator.plugins.length)return false;var platform=navigator.platform.toLowerCase();for(i=0;i<navigator.plugins.length;++i){s=navigator.plugins[i].description;if(s.search(/^Java Switchable Plug-in (Cocoa)/)!==-1){if(this.compareVersions("1.5.0",version))return true}else if(s.search(/^Java/)!==
-1)if(platform.indexOf("win")!==-1)if(this.compareVersions("1.5.0",version)||this.compareVersions("1.6.0",version))return true}if(this.compareVersions("1.5.0",version))return true;return false},IEInstall:function(){location.href=this.getJavaURL+(this.returnPage!==null?"\x26returnPage\x3d"+this.returnPage:"")+(this.locale!==null?"\x26locale\x3d"+this.locale:"")+(this.brand!==null?"\x26brand\x3d"+this.brand:"");return false},done:function(name,result){},FFInstall:function(){location.href=this.getJavaURL+
(this.returnPage!==null?"\x26returnPage\x3d"+this.returnPage:"")+(this.locale!==null?"\x26locale\x3d"+this.locale:"")+(this.brand!==null?"\x26brand\x3d"+this.brand:"")+(this.installType!==null?"\x26type\x3d"+this.installType:"");return false},compareVersions:function(installed,required){var a=installed.split(".");var b=required.split(".");for(i=0;i<a.length;++i)a[i]=Number(a[i]);for(i=0;i<b.length;++i)b[i]=Number(b[i]);if(a.length===2)a[2]=0;if(a[0]>b[0])return true;if(a[0]<b[0])return false;if(a[1]>
b[1])return true;if(a[1]<b[1])return false;if(a[2]>b[2])return true;if(a[2]<b[2])return false;return true},enableAlerts:function(){this.browserName=null;this.debug=true},poll:function(){this.refresh();var postInstallJREList=this.getJREs();if(this.preInstallJREList.length===0&&postInstallJREList.length!==0){clearInterval(this.myInterval);if(this.returnPage!==null)location.href=this.returnPage}if(this.preInstallJREList.length!==0&&postInstallJREList.length!==0&&this.preInstallJREList[0]!==postInstallJREList[0]){clearInterval(this.myInterval);
if(this.returnPage!==null)location.href=this.returnPage}},writePluginTag:function(){var browser=this.getBrowser();if(browser==="MSIE")document.write("\x3c"+'object classid\x3d"clsid:CAFEEFAC-DEC7-0000-0000-ABCDEFFEDCBA" '+'id\x3d"deployJavaPlugin" width\x3d"0" height\x3d"0"\x3e'+"\x3c"+"/"+"object"+"\x3e");else if(browser==="Netscape Family"&&this.allowPlugin())this.writeEmbedTag()},refresh:function(){navigator.plugins.refresh(false);var browser=this.getBrowser();if(browser==="Netscape Family"&&this.allowPlugin()){var plugin=
document.getElementById("deployJavaPlugin");if(plugin===null)this.writeEmbedTag()}},writeEmbedTag:function(){var written=false;if(navigator.mimeTypes!==null){for(i=0;i<navigator.mimeTypes.length;i++)if(navigator.mimeTypes[i].type===this.mimeType)if(navigator.mimeTypes[i].enabledPlugin){document.write("\x3c"+'embed id\x3d"deployJavaPlugin" type\x3d"'+this.mimeType+'" hidden\x3d"true" /\x3e');written=true}if(!written)for(i=0;i<navigator.mimeTypes.length;i++)if(navigator.mimeTypes[i].type===this.oldMimeType)if(navigator.mimeTypes[i].enabledPlugin)document.write("\x3c"+
'embed id\x3d"deployJavaPlugin" type\x3d"'+this.oldMimeType+'" hidden\x3d"true" /\x3e')}}};rv.writePluginTag();if(rv.locale==null){loc=null;if(loc==null)try{loc=navigator.userLanguage}catch(err){}if(loc==null)try{loc=navigator.systemLanguage}catch(err){}if(loc==null)try{loc=navigator.language}catch(err){}if(loc!=null){loc.replace("-","_");rv.locale=loc}}return rv}();
YUI.add("bfmodule-footer",function(Y){var namespace=Y.namespace("BF.Modules"),Footer,proto,CONST=Y.namespace("BF.Const");Footer={DOM_EVENTS:{"click":1,"keypress":1},"ATTRS":{}};proto={initializer:function(){Y.log("initializer","debug","bfmodule-footer")},renderUI:function(){this.set("started",true)},bindUI:function(){if(this.getModuleConfig("downloadableClient"))if(this.getModuleConfig("htcmdOnLoadDisabled")!="true")window.location="htcmd:pageloaded"},destructor:function(){Y.log("destructor","debug",
"bfmodule-footer")}};namespace.Footer=Y.Base.create("Footer",namespace.Module,[],proto,Footer)},"1",{requires:["bfmodule-module"]});
YUI.add("bfmodule-nemid",function(Y){var namespace=Y.namespace("BF.Modules"),NemId,proto;NemId={};proto={initializer:function(config){Y.log("initializer","debug","bfmodule-nemid");this.i13n=this.get("analytics.i13n")},destructor:function(){Y.log("destructor","debug","bfmodule-nemid")},renderUI:function(){Y.log("renderUI","debug","bfmodule-nemid");var warningNode,javaDeployed=deployJava.versionCheck("1.6+");if(!javaDeployed){warningNode=this.body.one(".java-warning");if(warningNode)warningNode.removeClass("hidden")}this._fireI13n({X:"Loaded"});
this.set("started",true)},bindUI:function(){Y.log("bindUI","debug","bfmodule-nemid")},syncUI:function(){Y.log("syncUI","debug","bfmodule-nemid")},_fireI13n:function(obj){Y.log("_fireI13n","debug","bfmodule-login-lightbox");obj=obj||{};this.i13n.makeCall(this.body,obj)}};namespace.NemId=Y.Base.create("nemid",namespace.Module,[],proto,NemId)},"1",{requires:["bfmodule-module"]});