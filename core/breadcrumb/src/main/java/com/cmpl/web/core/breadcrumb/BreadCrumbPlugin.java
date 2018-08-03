package com.cmpl.web.core.breadcrumb;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.Plugin;

import com.cmpl.web.core.page.BACK_PAGE;

@Qualifier(value = "breadCrumbs")
public interface BreadCrumbPlugin extends Plugin<BACK_PAGE> { 

}
