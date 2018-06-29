ace.define("ace/ext/menu_tools/element_generator",["require","exports","module"],function(e,t,n){"use strict";n.exports.createOption=function(t){var n,r=document.createElement("option");for(n in t)t.hasOwnProperty(n)&&(n==="selected"?r.setAttribute(n,t[n]):r[n]=t[n]);return r},n.exports.createCheckbox=function(t,n,r){var i=document.createElement("input");return i.setAttribute("type","checkbox"),i.setAttribute("id",t),i.setAttribute("name",t),i.setAttribute("value",n),i.setAttribute("class",r),n&&i.setAttribute("checked","checked"),i},n.exports.createInput=function(t,n,r){var i=document.createElement("input");return i.setAttribute("type","text"),i.setAttribute("id",t),i.setAttribute("name",t),i.setAttribute("value",n),i.setAttribute("class",r),i},n.exports.createLabel=function(t,n){var r=document.createElement("label");return r.setAttribute("for",n),r.textContent=t,r},n.exports.createSelection=function(t,r,i){var s=document.createElement("select");return s.setAttribute("id",t),s.setAttribute("name",t),s.setAttribute("class",i),r.forEach(function(e){s.appendChild(n.exports.createOption(e))}),s}}),ace.define("ace/ext/modelist",["require","exports","module"],function(e,t,n){"use strict";function i(e){var t=a.text,n=e.split(/[\/\\]/).pop();for(var i=0;i<r.length;i++)if(r[i].supportsFile(n)){t=r[i];break}return t}var r=[],s=function(e,t,n){this.name=e,this.caption=t,this.mode="ace/mode/"+e,this.extensions=n;if(/\^/.test(n))var r=n.replace(/\|(\^)?/g,function(e,t){return"$|"+(t?"^":"^.*\\.")})+"$";else var r="^.*\\.("+n+")$";this.extRe=new RegExp(r,"gi")};s.prototype.supportsFile=function(e){return e.match(this.extRe)};var o={ABAP:["abap"],ABC:["abc"],ActionScript:["as"],ADA:["ada|adb"],Apache_Conf:["^htaccess|^htgroups|^htpasswd|^conf|htaccess|htgroups|htpasswd"],AsciiDoc:["asciidoc|adoc"],Assembly_x86:["asm"],AutoHotKey:["ahk"],BatchFile:["bat|cmd"],C9Search:["c9search_results"],C_Cpp:["cpp|c|cc|cxx|h|hh|hpp"],Cirru:["cirru|cr"],Clojure:["clj|cljs"],Cobol:["CBL|COB"],coffee:["coffee|cf|cson|^Cakefile"],ColdFusion:["cfm"],CSharp:["cs"],CSS:["css"],Curly:["curly"],D:["d|di"],Dart:["dart"],Diff:["diff|patch"],Dockerfile:["^Dockerfile"],Dot:["dot"],Dummy:["dummy"],DummySyntax:["dummy"],Eiffel:["e"],EJS:["ejs"],Elixir:["ex|exs"],Elm:["elm"],Erlang:["erl|hrl"],Forth:["frt|fs|ldr"],FTL:["ftl"],Gcode:["gcode"],Gherkin:["feature"],Gitignore:["^.gitignore"],Glsl:["glsl|frag|vert"],golang:["go"],Groovy:["groovy"],HAML:["haml"],Handlebars:["hbs|handlebars|tpl|mustache"],Haskell:["hs"],haXe:["hx"],HTML:["html|htm|xhtml"],HTML_Ruby:["erb|rhtml|html.erb"],INI:["ini|conf|cfg|prefs"],Io:["io"],Jack:["jack"],Jade:["jade"],Java:["java"],JavaScript:["js|jsm"],JSON:["json"],JSONiq:["jq"],JSP:["jsp"],JSX:["jsx"],Julia:["jl"],LaTeX:["tex|latex|ltx|bib"],Lean:["lean|hlean"],LESS:["less"],Liquid:["liquid"],Lisp:["lisp"],LiveScript:["ls"],LogiQL:["logic|lql"],LSL:["lsl"],Lua:["lua"],LuaPage:["lp"],Lucene:["lucene"],Makefile:["^Makefile|^GNUmakefile|^makefile|^OCamlMakefile|make"],Markdown:["md|markdown"],Mask:["mask"],MATLAB:["matlab"],MEL:["mel"],MUSHCode:["mc|mush"],MySQL:["mysql"],Nix:["nix"],ObjectiveC:["m|mm"],OCaml:["ml|mli"],Pascal:["pas|p"],Perl:["pl|pm"],pgSQL:["pgsql"],PHP:["php|phtml"],Powershell:["ps1"],Praat:["praat|praatscript|psc|proc"],Prolog:["plg|prolog"],Properties:["properties"],Protobuf:["proto"],Python:["py"],R:["r"],RDoc:["Rd"],RHTML:["Rhtml"],Ruby:["rb|ru|gemspec|rake|^Guardfile|^Rakefile|^Gemfile"],Rust:["rs"],SASS:["sass"],SCAD:["scad"],Scala:["scala"],Scheme:["scm|rkt"],SCSS:["scss"],SH:["sh|bash|^.bashrc"],SJS:["sjs"],Smarty:["smarty|tpl"],snippets:["snippets"],Soy_Template:["soy"],Space:["space"],SQL:["sql"],Stylus:["styl|stylus"],SVG:["svg"],Tcl:["tcl"],Tex:["tex"],Text:["txt"],Textile:["textile"],Toml:["toml"],Twig:["twig"],Typescript:["ts|typescript|str"],Vala:["vala"],VBScript:["vbs|vb"],Velocity:["vm"],Verilog:["v|vh|sv|svh"],VHDL:["vhd|vhdl"],XML:["xml|rdf|rss|wsdl|xslt|atom|mathml|mml|xul|xbl|xaml"],XQuery:["xq"],YAML:["yaml|yml"],Django:["html"]},u={ObjectiveC:"Objective-C",CSharp:"C#",golang:"Go",C_Cpp:"C and C++",coffee:"CoffeeScript",HTML_Ruby:"HTML (Ruby)",FTL:"FreeMarker"},a={};for(var f in o){var l=o[f],c=(u[f]||f).replace(/_/g," "),h=f.toLowerCase(),p=new s(h,c,l[0]);a[h]=p,r.push(p)}n.exports={getModeForPath:i,modes:r,modesByName:a}}),ace.define("ace/ext/themelist",["require","exports","module","ace/lib/fixoldbrowsers"],function(e,t,n){"use strict";e("ace/lib/fixoldbrowsers");var r=[["Chrome"],["Clouds"],["Crimson Editor"],["Dawn"],["Dreamweaver"],["Eclipse"],["GitHub"],["Solarized Light"],["TextMate"],["Tomorrow"],["XCode"],["Kuroir"],["KatzenMilch"],["Ambiance","ambiance","dark"],["Chaos","chaos","dark"],["Clouds Midnight","clouds_midnight","dark"],["Cobalt","cobalt","dark"],["idle Fingers","idle_fingers","dark"],["krTheme","kr_theme","dark"],["Merbivore","merbivore","dark"],["Merbivore Soft","merbivore_soft","dark"],["Mono Industrial","mono_industrial","dark"],["Monokai","monokai","dark"],["Pastel on dark","pastel_on_dark","dark"],["Solarized Dark","solarized_dark","dark"],["Terminal","terminal","dark"],["Tomorrow Night","tomorrow_night","dark"],["Tomorrow Night Blue","tomorrow_night_blue","dark"],["Tomorrow Night Bright","tomorrow_night_bright","dark"],["Tomorrow Night 80s","tomorrow_night_eighties","dark"],["Twilight","twilight","dark"],["Vibrant Ink","vibrant_ink","dark"]];t.themesByName={},t.themes=r.map(function(e){var n=e[1]||e[0].replace(/ /g,"_").toLowerCase(),r={caption:e[0],theme:"ace/theme/"+n,isDark:e[2]=="dark",name:n};return t.themesByName[n]=r,r})}),ace.define("ace/ext/menu_tools/add_editor_menu_options",["require","exports","module","ace/ext/modelist","ace/ext/themelist"],function(e,t,n){"use strict";n.exports.addEditorMenuOptions=function(n){var r=e("../modelist"),i=e("../themelist");n.menuOptions={setNewLineMode:[{textContent:"unix",value:"unix"},{textContent:"windows",value:"windows"},{textContent:"auto",value:"auto"}],setTheme:[],setMode:[],setKeyboardHandler:[{textContent:"ace",value:""},{textContent:"vim",value:"ace/keyboard/vim"},{textContent:"emacs",value:"ace/keyboard/emacs"},{textContent:"textarea",value:"ace/keyboard/textarea"},{textContent:"sublime",value:"ace/keyboard/sublime"}]},n.menuOptions.setTheme=i.themes.map(function(e){return{textContent:e.caption,value:e.theme}}),n.menuOptions.setMode=r.modes.map(function(e){return{textContent:e.name,value:e.mode}})}}),ace.define("ace/ext/menu_tools/get_set_functions",["require","exports","module"],function(e,t,n){"use strict";n.exports.getSetFunctions=function(t){var n=[],r={editor:t,session:t.session,renderer:t.renderer},i=[],s=["setOption","setUndoManager","setDocument","setValue","setBreakpoints","setScrollTop","setScrollLeft","setSelectionStyle","setWrapLimitRange"];return["renderer","session","editor"].forEach(function(e){var t=r[e],o=e;for(var u in t)s.indexOf(u)===-1&&/^set/.test(u)&&i.indexOf(u)===-1&&(i.push(u),n.push({functionName:u,parentObj:t,parentName:o}))}),n}}),ace.define("ace/ext/menu_tools/generate_settings_menu",["require","exports","module","ace/ext/menu_tools/element_generator","ace/ext/menu_tools/add_editor_menu_options","ace/ext/menu_tools/get_set_functions"],function(e,t,n){"use strict";var r=e("./element_generator"),i=e("./add_editor_menu_options").addEditorMenuOptions,s=e("./get_set_functions").getSetFunctions;n.exports.generateSettingsMenu=function(t){function o(){n.sort(function(e,t){var n=e.getAttribute("contains"),r=t.getAttribute("contains");return n.localeCompare(r)})}function u(){var e=document.createElement("div");e.setAttribute("id","ace_settingsmenu"),n.forEach(function(t){e.appendChild(t)});var t=e.appendChild(document.createElement("div")),r="1.1.9";return t.style.padding="1em",t.textContent="Ace version "+r,e}function a(e,n,i,s){var o,u=document.createElement("div");return u.setAttribute("contains",i),u.setAttribute("class","ace_optionsMenuEntry"),u.setAttribute("style","clear: both;"),u.appendChild(r.createLabel(i.replace(/^set/,"").replace(/([A-Z])/g," $1").trim(),i)),Array.isArray(s)?(o=r.createSelection(i,s,n),o.addEventListener("change",function(n){try{t.menuOptions[n.target.id].forEach(function(e){e.textContent!==n.target.textContent&&delete e.selected}),e[n.target.id](n.target.value)}catch(r){throw new Error(r)}})):typeof s=="boolean"?(o=r.createCheckbox(i,s,n),o.addEventListener("change",function(t){try{e[t.target.id](!!t.target.checked)}catch(n){throw new Error(n)}})):(o=r.createInput(i,s,n),o.addEventListener("change",function(t){try{t.target.value==="true"?e[t.target.id](!0):t.target.value==="false"?e[t.target.id](!1):e[t.target.id](t.target.value)}catch(n){throw new Error(n)}})),o.style.cssText="float:right;",u.appendChild(o),u}function f(e,n,r,i){var s=t.menuOptions[e],o=n[i]();return typeof o=="object"&&(o=o.$id),s.forEach(function(e){e.value===o&&(e.selected="selected")}),a(n,r,e,s)}function l(e){var r=e.functionName,i=e.parentObj,s=e.parentName,o,u=r.replace(/^set/,"get");if(t.menuOptions[r]!==undefined)n.push(f(r,i,s,u));else if(typeof i[u]=="function")try{o=i[u](),typeof o=="object"&&(o=o.$id),n.push(a(i,s,r,o))}catch(l){}}var n=[];return i(t),s(t).forEach(function(e){l(e)}),o(),u()}}),ace.define("ace/ext/menu_tools/overlay_page",["require","exports","module","ace/lib/dom"],function(e,t,n){"use strict";var r=e("../../lib/dom"),i="#ace_settingsmenu, #kbshortcutmenu {background-color: #F7F7F7;color: black;box-shadow: -5px 4px 5px rgba(126, 126, 126, 0.55);padding: 1em 0.5em 2em 1em;overflow: auto;position: absolute;margin: 0;bottom: 0;right: 0;top: 0;z-index: 9991;cursor: default;}.ace_dark #ace_settingsmenu, .ace_dark #kbshortcutmenu {box-shadow: -20px 10px 25px rgba(126, 126, 126, 0.25);background-color: rgba(255, 255, 255, 0.6);color: black;}.ace_optionsMenuEntry:hover {background-color: rgba(100, 100, 100, 0.1);-webkit-transition: all 0.5s;transition: all 0.3s}.ace_closeButton {background: rgba(245, 146, 146, 0.5);border: 1px solid #F48A8A;border-radius: 50%;padding: 7px;position: absolute;right: -8px;top: -8px;z-index: 1000;}.ace_closeButton{background: rgba(245, 146, 146, 0.9);}.ace_optionsMenuKey {color: darkslateblue;font-weight: bold;}.ace_optionsMenuCommand {color: darkcyan;font-weight: normal;}";r.importCssString(i),n.exports.overlayPage=function(t,n,i,s,o,u){function l(e){e.keyCode===27&&a.click()}i=i?"top: "+i+";":"",o=o?"bottom: "+o+";":"",s=s?"right: "+s+";":"",u=u?"left: "+u+";":"";var a=document.createElement("div"),f=document.createElement("div");a.style.cssText="margin: 0; padding: 0; position: fixed; top:0; bottom:0; left:0; right:0;z-index: 9990; background-color: rgba(0, 0, 0, 0.3);",a.addEventListener("click",function(){document.removeEventListener("keydown",l),a.parentNode.removeChild(a),t.focus(),a=null}),document.addEventListener("keydown",l),f.style.cssText=i+s+o+u,f.addEventListener("click",function(e){e.stopPropagation()});var c=r.createElement("div");c.style.position="relative";var h=r.createElement("div");h.className="ace_closeButton",h.addEventListener("click",function(){a.click()}),c.appendChild(h),f.appendChild(c),f.appendChild(n),a.appendChild(f),document.body.appendChild(a),t.blur()}}),ace.define("ace/ext/settings_menu",["require","exports","module","ace/ext/menu_tools/generate_settings_menu","ace/ext/menu_tools/overlay_page","ace/editor"],function(e,t,n){"use strict";function s(e){var t=document.getElementById("ace_settingsmenu");t||i(e,r(e),"0","0","0")}var r=e("./menu_tools/generate_settings_menu").generateSettingsMenu,i=e("./menu_tools/overlay_page").overlayPage;n.exports.init=function(t){var n=e("ace/editor").Editor;n.prototype.showSettingsMenu=function(){s(this)}}});
                (function() {
                    ace.require(["ace/ext/settings_menu"], function() {});
                })();
            