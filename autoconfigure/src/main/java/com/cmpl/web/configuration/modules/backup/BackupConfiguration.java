package com.cmpl.web.configuration.modules.backup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.core.association_user_role.AssociationUserRole;
import com.cmpl.web.core.association_user_role.AssociationUserRoleRepository;
import com.cmpl.web.core.carousel.Carousel;
import com.cmpl.web.core.carousel.CarouselItem;
import com.cmpl.web.core.carousel.CarouselItemRepository;
import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.media.Media;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.menu.Menu;
import com.cmpl.web.core.menu.MenuRepository;
import com.cmpl.web.core.news.NewsContent;
import com.cmpl.web.core.news.NewsContentRepository;
import com.cmpl.web.core.news.NewsEntry;
import com.cmpl.web.core.news.NewsEntryRepository;
import com.cmpl.web.core.news.NewsImage;
import com.cmpl.web.core.news.NewsImageRepository;
import com.cmpl.web.core.page.Page;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.role.Privilege;
import com.cmpl.web.core.role.PrivilegeRepository;
import com.cmpl.web.core.role.Role;
import com.cmpl.web.core.role.RoleRepository;
import com.cmpl.web.core.style.Style;
import com.cmpl.web.core.style.StyleRepository;
import com.cmpl.web.core.user.User;
import com.cmpl.web.core.user.UserRepository;
import com.cmpl.web.core.widget.Widget;
import com.cmpl.web.core.widget.WidgetPage;
import com.cmpl.web.core.widget.WidgetPageRepository;
import com.cmpl.web.core.widget.WidgetRepository;

@Configuration
@PropertySource("classpath:/backup/backup.properties")
@EnableJpaRepositories(basePackageClasses = {MenuRepository.class, StyleRepository.class, PageRepository.class,
    MediaRepository.class, CarouselRepository.class, CarouselItemRepository.class, NewsEntryRepository.class,
    NewsContentRepository.class, NewsImageRepository.class, WidgetRepository.class, WidgetPageRepository.class,
    UserRepository.class, RoleRepository.class, AssociationUserRoleRepository.class, PrivilegeRepository.class})
public class BackupConfiguration {

  @Bean
  public DataManipulator<Menu> menuDataManipulator(MenuRepository menuRepository) {
    return new DataManipulator<>(menuRepository);
  }

  @Bean
  public DataManipulator<Style> styleDataManipulator(StyleRepository styleRepository) {
    return new DataManipulator<>(styleRepository);
  }

  @Bean
  public DataManipulator<Page> pageDataManipulator(PageRepository pageRepository) {
    return new DataManipulator<>(pageRepository);
  }

  @Bean
  public DataManipulator<Media> mediaDataManipulator(MediaRepository mediaRepository) {
    return new DataManipulator<>(mediaRepository);
  }

  @Bean
  public DataManipulator<Carousel> carouselDataManipulator(CarouselRepository carouselRepository) {
    return new DataManipulator<>(carouselRepository);
  }

  @Bean
  public DataManipulator<CarouselItem> carouselItemDataManipulator(CarouselItemRepository carouselItemRepository) {
    return new DataManipulator<>(carouselItemRepository);
  }

  @Bean
  public DataManipulator<NewsEntry> newsEntryDataManipulator(NewsEntryRepository newsEntryRepository) {
    return new DataManipulator<>(newsEntryRepository);
  }

  @Bean
  public DataManipulator<NewsImage> newsImageDataManipulator(NewsImageRepository newsImageRepository) {
    return new DataManipulator<>(newsImageRepository);
  }

  @Bean
  public DataManipulator<NewsContent> newsContentDataManipulator(NewsContentRepository newsContentRepository) {
    return new DataManipulator<>(newsContentRepository);
  }

  @Bean
  public DataManipulator<Widget> widgetDataManipulator(WidgetRepository widgetRepository) {
    return new DataManipulator<>(widgetRepository);
  }

  @Bean
  public DataManipulator<WidgetPage> widgetPageDataManipulator(WidgetPageRepository widgetPageRepository) {
    return new DataManipulator<>(widgetPageRepository);
  }

  @Bean
  public DataManipulator<User> userDataManipulator(UserRepository userRepository) {
    return new DataManipulator<>(userRepository);
  }

  @Bean
  public DataManipulator<Role> roleDataManipulator(RoleRepository roleRepository) {
    return new DataManipulator<>(roleRepository);
  }

  @Bean
  public DataManipulator<AssociationUserRole> associationUserRoleDataManipulator(
      AssociationUserRoleRepository associationUserRoleRepository) {
    return new DataManipulator<>(associationUserRoleRepository);
  }

  @Bean
  public DataManipulator<Privilege> privilegeDataManipulator(PrivilegeRepository privilegeRepository) {
    return new DataManipulator<>(privilegeRepository);
  }

}
