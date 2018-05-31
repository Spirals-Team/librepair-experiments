package com.cmpl.web.modules.facebook.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.menu.MenuItem;
import com.cmpl.web.core.menu.MenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.ImportablePost;
import com.cmpl.web.facebook.ImportablePostBuilder;

@RunWith(MockitoJUnitRunner.class)
public class FacebookDisplayFactoryImplTest {

  @Mock
  private FacebookAdapter facebookAdapter;

  @InjectMocks
  @Spy
  private FacebookDisplayFactoryImpl facebookDisplayFactoryImpl;

  @Test
  public void testIsAlreadyConnected_True() throws Exception {

    BDDMockito.doReturn(true).when(facebookAdapter).isAlreadyConnected();
    boolean result = facebookDisplayFactoryImpl.isAlreadyConnected();
    Assert.assertTrue(result);
  }

  @Test
  public void testIsAlreadyConnected_False() throws Exception {

    BDDMockito.doReturn(false).when(facebookAdapter).isAlreadyConnected();

    boolean result = facebookDisplayFactoryImpl.isAlreadyConnected();

    Assert.assertFalse(result);
  }

  @Test
  public void testComputeRecentFeeds_Ok() throws Exception {

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Arrays.asList(post);

    BDDMockito.doReturn(postsToReturn).when(facebookAdapter).getRecentFeed();

    List<ImportablePost> result = facebookDisplayFactoryImpl.computeRecentFeeds();

    Assert.assertEquals(postsToReturn, result);

  }

  @Test
  public void testComputeRecentFeeds_Exception_Should_Return_Empty_Array() throws Exception {

    BDDMockito.doReturn(Arrays.asList()).when(facebookAdapter).getRecentFeed();

    List<ImportablePost> result = facebookDisplayFactoryImpl.computeRecentFeeds();

    Assert.assertTrue(CollectionUtils.isEmpty(result));
  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Not_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookAccessPage(Locale.FRANCE);
    Assert.assertEquals("login", result.getModel().get("content"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeModelAndViewForFacebookImportPage(
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeRecentFeeds();

  }

  @Test
  public void testComputeModelAndViewForFacebookAccessPage_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Arrays.asList(post);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(postsToReturn).when(facebookDisplayFactoryImpl).computeRecentFeeds();
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookAccessPage(Locale.FRANCE);

    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeRecentFeeds();
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeModelAndViewForFacebookImportPage(
        BDDMockito.eq(Locale.FRANCE));
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Not_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));

    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(false).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookImportPage(Locale.FRANCE);
    Assert.assertEquals("login", result.getModel().get("content"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeModelAndViewForFacebookAccessPage(
        BDDMockito.eq(Locale.FRANCE));
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeRecentFeeds();
  }

  @Test
  public void testComputeModelAndViewForFacebookImportPage_Connected() throws Exception {

    String tile = "login";
    String href = "/";
    String label = "label";
    String title = "title";
    String decoratorBack = "decorator_back";
    List<MenuItem> subMenuItems = new ArrayList<MenuItem>();
    MenuItem index = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();
    MenuItem news = MenuItemBuilder.create().href(href).label(label).title(title).subMenuItems(subMenuItems).build();

    List<MenuItem> backMenu = Arrays.asList(index, news);

    ImportablePost post = new ImportablePostBuilder().facebookId("someFacebookId").build();
    List<ImportablePost> postsToReturn = Arrays.asList(post);

    BreadCrumb breadcrumb = BreadCrumbBuilder.create().build();
    BDDMockito.doReturn(breadcrumb).when(facebookDisplayFactoryImpl).computeBreadCrumb(BDDMockito.any(BACK_PAGE.class));
    BDDMockito.doReturn(postsToReturn).when(facebookDisplayFactoryImpl).computeRecentFeeds();
    BDDMockito.doReturn(decoratorBack).when(facebookDisplayFactoryImpl)
        .computeDecoratorBackTileName(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(tile).when(facebookDisplayFactoryImpl)
        .computeTileName(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(backMenu).when(facebookDisplayFactoryImpl)
        .computeBackMenuItems(BDDMockito.any(BACK_PAGE.class), BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(href).when(facebookDisplayFactoryImpl).computeHiddenLink(BDDMockito.eq(Locale.FRANCE));
    BDDMockito.doReturn(true).when(facebookDisplayFactoryImpl).isAlreadyConnected();

    ModelAndView result = facebookDisplayFactoryImpl.computeModelAndViewForFacebookImportPage(Locale.FRANCE);

    Assert.assertEquals(postsToReturn, result.getModel().get("feeds"));

    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(1)).computeRecentFeeds();
    BDDMockito.verify(facebookDisplayFactoryImpl, BDDMockito.times(0)).computeModelAndViewForFacebookAccessPage(
        BDDMockito.eq(Locale.FRANCE));
  }
}
