package com.cmpl.web.launcher;

import com.cmpl.web.core.carousel.Carousel;
import com.cmpl.web.core.carousel.CarouselItem;
import com.cmpl.web.core.carousel.CarouselItemRepository;
import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.media.Media;
import com.cmpl.web.core.media.MediaBuilder;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.menu.Menu;
import com.cmpl.web.core.menu.MenuRepository;
import com.cmpl.web.core.page.Page;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.widget.*;

public class PageFactory {

  public static void createPages(PageRepository pageRepository, MenuRepository menuRepository,

      CarouselRepository carouselRepository, CarouselItemRepository carouselItemRepository,
      MediaRepository mediaRepository, WidgetRepository widgetRepository, WidgetPageRepository widgetPageRepository) {
    createIndex(pageRepository, menuRepository, carouselRepository, carouselItemRepository, mediaRepository,
        widgetRepository, widgetPageRepository);
    createActualites(pageRepository, menuRepository, widgetRepository, widgetPageRepository);
    createAppointment(pageRepository, menuRepository);
    createCenter(pageRepository, menuRepository);
    createContact(pageRepository, menuRepository);
    createMedicalCare(pageRepository, menuRepository);

  }

  public static void createIndex(PageRepository pageRepository, MenuRepository menuRepository,

      CarouselRepository carouselRepository, CarouselItemRepository carouselItemRepository,
      MediaRepository mediaRepository, WidgetRepository widgetRepository, WidgetPageRepository widgetPageRepository) {

    Page index = new Page();
    index.setMenuTitle("Accueil");
    index.setName("accueil");
    index = pageRepository.save(index);
    String pageId = String.valueOf(index.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + index.getName());
    menu.setLabel(index.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(1);
    menu.setTitle(index.getMenuTitle());

    menuRepository.save(menu);

    Carousel carouselHome = new Carousel();
    carouselHome.setName("home");

    carouselHome = carouselRepository.save(carouselHome);
    String carouselId = String.valueOf(carouselHome.getId());

    Media firstMedia = MediaBuilder.create().build();
    firstMedia.setContentType("image/jpg");
    firstMedia.setExtension(".jpg");
    firstMedia.setName("epilation_verso.jpg");
    firstMedia.setSrc("/public/medias/epilation_verso.jpg");
    firstMedia.setSize(114688l);

    firstMedia = mediaRepository.save(firstMedia);

    CarouselItem firstImage = new CarouselItem();
    firstImage.setMediaId(String.valueOf(firstMedia.getId()));
    firstImage.setCarouselId(carouselId);
    firstImage.setOrderInCarousel(1);

    Media secondMedia = MediaBuilder.create().build();
    secondMedia.setContentType("image/jpg");
    secondMedia.setExtension(".jpg");
    secondMedia.setName("epilation_recto.jpg");
    secondMedia.setSrc("/public/medias/epilation_recto.jpg");
    secondMedia.setSize(61440l);

    secondMedia = mediaRepository.save(secondMedia);

    carouselItemRepository.save(firstImage);

    CarouselItem secondImage = new CarouselItem();
    secondImage.setMediaId(String.valueOf(secondMedia.getId()));
    secondImage.setOrderInCarousel(2);
    secondImage.setCarouselId(carouselId);

    carouselItemRepository.save(secondImage);

    Widget widgetCarouselHome = WidgetBuilder.create().type("CAROUSEL").name("carousel_home").entityId(carouselId)
        .build();
    widgetCarouselHome = widgetRepository.save(widgetCarouselHome);
    WidgetPage widgetPage = WidgetPageBuilder.create().widgetId(String.valueOf(widgetCarouselHome.getId()))
        .pageId(pageId).build();
    widgetPageRepository.save(widgetPage);

  }

  public static void createActualites(PageRepository pageRepository, MenuRepository menuRepository,

      WidgetRepository widgetRepository, WidgetPageRepository widgetPageRepository) {

    Page actualites = new Page();
    actualites.setMenuTitle("Actualites");
    actualites.setName("actualites");
    actualites = pageRepository.save(actualites);

    String pageId = String.valueOf(actualites.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + actualites.getName());
    menu.setLabel(actualites.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(7);
    menu.setTitle(actualites.getMenuTitle());

    menuRepository.save(menu);

    Widget blog = WidgetBuilder.create().name("blog").type("BLOG").build();
    blog = widgetRepository.save(blog);
    WidgetPage widgetPage = WidgetPageBuilder.create().pageId(pageId).widgetId(String.valueOf(blog.getId())).build();
    widgetPageRepository.save(widgetPage);

    Widget widgetMenu = WidgetBuilder.create().type("MENU").name("menu").build();
    widgetMenu = widgetRepository.save(widgetMenu);
    WidgetPage widgetPageMenu = WidgetPageBuilder.create().widgetId(String.valueOf(widgetMenu.getId())).pageId(pageId)
        .build();
    widgetPageRepository.save(widgetPageMenu);

  }

  public static void createAppointment(PageRepository pageRepository, MenuRepository menuRepository) {

    Page appointment = new Page();
    appointment.setMenuTitle("Prendre rendez-vous");
    appointment.setName("rendez_vous");
    appointment = pageRepository.save(appointment);
    String pageId = String.valueOf(appointment.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + appointment.getName());
    menu.setLabel(appointment.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(9);
    menu.setTitle(appointment.getMenuTitle());

    menuRepository.save(menu);

  }

  public static void createCenter(PageRepository pageRepository, MenuRepository menuRepository) {
    Page center = new Page();
    center.setMenuTitle("Le centre");
    center.setName("centre_medical");
    center = pageRepository.save(center);
    String pageId = String.valueOf(center.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + center.getName());
    menu.setLabel(center.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(2);
    menu.setTitle(center.getMenuTitle());

    menuRepository.save(menu);

  }

  public static void createContact(PageRepository pageRepository, MenuRepository menuRepository) {

    Page contact = new Page();
    contact.setMenuTitle("Contact");
    contact.setName("contact");
    contact = pageRepository.save(contact);
    String pageId = String.valueOf(contact.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + contact.getName());
    menu.setLabel(contact.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(8);
    menu.setTitle(contact.getMenuTitle());

    menuRepository.save(menu);

  }

  public static void createMedicalCare(PageRepository pageRepository, MenuRepository menuRepository) {

    Page medicalCare = new Page();
    medicalCare.setMenuTitle("Soins medicaux");
    medicalCare.setName("soins_medicaux");
    medicalCare = pageRepository.save(medicalCare);
    String pageId = String.valueOf(medicalCare.getId());

    Menu menu = new Menu();
    menu.setHref("/pages/" + medicalCare.getName());
    menu.setLabel(medicalCare.getMenuTitle());
    menu.setPageId(pageId);
    menu.setOrderInMenu(4);
    menu.setTitle(medicalCare.getMenuTitle());

    menuRepository.save(menu);

  }

}
