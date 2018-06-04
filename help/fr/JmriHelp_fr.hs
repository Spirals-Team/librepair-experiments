<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!--generated by JHelpDev Version: 0.26-11/01/03, see www.mk-home.de--><!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN" "http://java.sun.com/products/javahelp/helpset_2_0.dtd">
<helpset version="1.0"> 

<title>Help </title>

<maps>
  <homeID>top</homeID>
  <mapref location="Map.jhm"/>
</maps>

<view>
  <name>TOC</name>
  <label>TOC</label>
  <type>javax.help.TOCView</type>
  <data>JmriHelp_frTOC.xml</data>
</view>

<view>
  <name>Index</name>
  <label>Index</label>
  <type>javax.help.IndexView</type>
  <data>JmriHelp_frIndex.xml</data>
</view>

<view>
  <name>Search</name>
  <label>Search</label>
  <type>javax.help.SearchView</type>
  <data engine="com.sun.java.help.search.DefaultSearchEngine">
  JavaHelpSearch</data>
</view>

<view>
  <name>Favorites</name>
  <label>Favorites</label>
  <type>javax.help.FavoritesView</type>
</view>

<presentation default="true" displayviewimages="true">
    <name>main window</name>
    <size height="500" width="700"/>
    <location x="100" y="100"/>
    <title>JMRI Help</title>
    <image>toplevelfolder</image>
    <toolbar>
	    <helpaction>javax.help.BackAction</helpaction>
	    <helpaction>javax.help.ForwardAction</helpaction>
	    <helpaction>javax.help.SeparatorAction</helpaction>
	    <helpaction>javax.help.HomeAction</helpaction>
	    <helpaction>javax.help.FavoritesAction</helpaction>
	    <helpaction>javax.help.ReloadAction</helpaction>
	    <helpaction>javax.help.SeparatorAction</helpaction>
	    <helpaction>javax.help.PrintAction</helpaction>
	    <helpaction>javax.help.PrintSetupAction</helpaction>
    </toolbar>
</presentation>

</helpset>
