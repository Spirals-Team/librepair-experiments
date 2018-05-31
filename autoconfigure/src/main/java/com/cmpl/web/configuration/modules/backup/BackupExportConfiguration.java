package com.cmpl.web.configuration.modules.backup;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.cmpl.web.backup.BackupExporterJob;
import com.cmpl.web.backup.CSVGeneratorImpl;
import com.cmpl.web.backup.writer.ArchiveManager;
import com.cmpl.web.backup.writer.ArchiveManagerImpl;
import com.cmpl.web.backup.writer.AssociationUserRoleCSVWriter;
import com.cmpl.web.backup.writer.CSVGenerator;
import com.cmpl.web.backup.writer.CarouselCSVWriter;
import com.cmpl.web.backup.writer.CarouselItemCSVWriter;
import com.cmpl.web.backup.writer.CommonWriter;
import com.cmpl.web.backup.writer.DataManipulator;
import com.cmpl.web.backup.writer.MediaCSVWriter;
import com.cmpl.web.backup.writer.MenuCSVWriter;
import com.cmpl.web.backup.writer.NewsContentCSVWriter;
import com.cmpl.web.backup.writer.NewsEntryCSVWriter;
import com.cmpl.web.backup.writer.NewsImageCSVWriter;
import com.cmpl.web.backup.writer.PageCSVWriter;
import com.cmpl.web.backup.writer.PrivilegeCSVWriter;
import com.cmpl.web.backup.writer.RoleCSVWriter;
import com.cmpl.web.backup.writer.StyleCSVWriter;
import com.cmpl.web.backup.writer.UserCSVWriter;
import com.cmpl.web.backup.writer.WidgetCSVWriter;
import com.cmpl.web.backup.writer.WidgetPageCSVWriter;
import com.cmpl.web.core.association_user_role.AssociationUserRole;
import com.cmpl.web.core.carousel.Carousel;
import com.cmpl.web.core.carousel.CarouselItem;
import com.cmpl.web.core.media.Media;
import com.cmpl.web.core.menu.Menu;
import com.cmpl.web.core.news.NewsContent;
import com.cmpl.web.core.news.NewsEntry;
import com.cmpl.web.core.news.NewsImage;
import com.cmpl.web.core.page.Page;
import com.cmpl.web.core.role.Privilege;
import com.cmpl.web.core.role.Role;
import com.cmpl.web.core.style.Style;
import com.cmpl.web.core.user.User;
import com.cmpl.web.core.widget.Widget;
import com.cmpl.web.core.widget.WidgetPage;
import com.cmpl.web.google.DriveAdapter;

@Configuration
@PropertySource("classpath:/backup/backup.properties")
public class BackupExportConfiguration {

  @Value("${backupFilePath}")
  String backupFilePath;

  @Value("${actualitesFilePath}")
  String actualitesFilePath;

  @Value("${pagesFilePath}")
  String pagesFilePath;

  @Value("${mediaFilePath}")
  String mediaFilePath;

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());;

  @Bean
  public ArchiveManager archiveManager(DriveAdapter driveAdapter) {
    return new ArchiveManagerImpl(backupFilePath, mediaFilePath, pagesFilePath, actualitesFilePath, driveAdapter);
  }

  @Bean
  public MenuCSVWriter menuCSVWriter(DataManipulator<Menu> menuDataManipulator) {
    return new MenuCSVWriter(dateFormatter, menuDataManipulator, backupFilePath);
  }

  @Bean
  public StyleCSVWriter styleCSVWriter(DataManipulator<Style> styleDataManipulator) {
    return new StyleCSVWriter(dateFormatter, styleDataManipulator, backupFilePath);
  }

  @Bean
  public PageCSVWriter pageCSVWriter(DataManipulator<Page> pageDataManipulator) {
    return new PageCSVWriter(dateFormatter, pageDataManipulator, backupFilePath);
  }

  @Bean
  public MediaCSVWriter mediaCSVWriter(DataManipulator<Media> mediaDataManipulator) {
    return new MediaCSVWriter(dateFormatter, mediaDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselCSVWriter carouselCSVWriter(DataManipulator<Carousel> carouselDataManipulator) {
    return new CarouselCSVWriter(dateFormatter, carouselDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselItemCSVWriter carouselItemCSVWriter(DataManipulator<CarouselItem> carouselItemDataManipulator) {
    return new CarouselItemCSVWriter(dateFormatter, carouselItemDataManipulator, backupFilePath);
  }

  @Bean
  public NewsEntryCSVWriter newsEntryCSVWriter(DataManipulator<NewsEntry> newsEntryDataManipulator) {
    return new NewsEntryCSVWriter(dateFormatter, newsEntryDataManipulator, backupFilePath);
  }

  @Bean
  public NewsImageCSVWriter newsImageCSVWriter(DataManipulator<NewsImage> newsImageDataManipulator) {
    return new NewsImageCSVWriter(dateFormatter, newsImageDataManipulator, backupFilePath);
  }

  @Bean
  public NewsContentCSVWriter newsContentCSVWriter(DataManipulator<NewsContent> newsContentDataManipulator) {
    return new NewsContentCSVWriter(dateFormatter, newsContentDataManipulator, backupFilePath);
  }

  @Bean
  public WidgetCSVWriter widgetCSVWriter(DataManipulator<Widget> widgetDataManipulator) {
    return new WidgetCSVWriter(dateFormatter, widgetDataManipulator, backupFilePath);
  }

  @Bean
  public WidgetPageCSVWriter widgetPageCSVWriter(DataManipulator<WidgetPage> widgetPageDataManipulator) {
    return new WidgetPageCSVWriter(dateFormatter, widgetPageDataManipulator, backupFilePath);
  }

  @Bean
  public UserCSVWriter userCSVWriter(DataManipulator<User> userDataManipulator) {
    return new UserCSVWriter(dateFormatter, userDataManipulator, backupFilePath);
  }

  @Bean
  public RoleCSVWriter roleCSVWriter(DataManipulator<Role> roleDataManipulator) {
    return new RoleCSVWriter(dateFormatter, roleDataManipulator, backupFilePath);
  }

  @Bean
  public PrivilegeCSVWriter privilegeCSVWriter(DataManipulator<Privilege> privilegeDataManipulator) {
    return new PrivilegeCSVWriter(dateFormatter, privilegeDataManipulator, backupFilePath);
  }

  @Bean
  public AssociationUserRoleCSVWriter associationUserRoleCSVWriter(
      DataManipulator<AssociationUserRole> associationUserRoleDataManipulator) {
    return new AssociationUserRoleCSVWriter(dateFormatter, associationUserRoleDataManipulator, backupFilePath);
  }

  @Bean
  public CSVGenerator csvGenerator(UserCSVWriter userCSVWriter, RoleCSVWriter roleCSVWriter,
      PrivilegeCSVWriter privilegeCSVWriter, AssociationUserRoleCSVWriter associationUserRoleCSVWriter,
      MenuCSVWriter menuCSVWriter, StyleCSVWriter styleCSVWriter, PageCSVWriter pageCSVWriter,
      MediaCSVWriter mediaCSVWriter, CarouselCSVWriter carouselCSVWriter, CarouselItemCSVWriter carouselItemCSVWriter,
      NewsEntryCSVWriter newsEntryCSVWriter, NewsImageCSVWriter newsImageCSVWriter,
      NewsContentCSVWriter newsContentCSVWriter, WidgetCSVWriter widgetCSVWriter,
      WidgetPageCSVWriter widgetPageCSVWriter) {
    List<CommonWriter<?>> writers = new ArrayList<>();
    writers.add(userCSVWriter);
    writers.add(roleCSVWriter);
    writers.add(privilegeCSVWriter);
    writers.add(associationUserRoleCSVWriter);
    writers.add(menuCSVWriter);
    writers.add(styleCSVWriter);
    writers.add(pageCSVWriter);
    writers.add(mediaCSVWriter);
    writers.add(carouselCSVWriter);
    writers.add(carouselItemCSVWriter);
    writers.add(newsEntryCSVWriter);
    writers.add(newsImageCSVWriter);
    writers.add(newsContentCSVWriter);
    writers.add(widgetCSVWriter);
    writers.add(widgetPageCSVWriter);
    return new CSVGeneratorImpl(writers);
  }

  @Bean
  @Qualifier("backupExportJob")
  public JobDetailFactoryBean backupExportJob() {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(BackupExporterJob.class);
    factoryBean.setGroup("backupExportJob");
    factoryBean.setName("backupExportJob");
    factoryBean.setDescription("Backup of all the data");
    factoryBean.setDurability(true);
    return factoryBean;
  }

  @Bean
  @Qualifier("backupExportTrigger")
  public SimpleTriggerFactoryBean backupExportTrigger(JobDetail backupExportJob) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName("Application backup export");
    factoryBean.setDescription("Periodic backup of the data of the application");
    factoryBean.setJobDetail(backupExportJob);
    factoryBean.setStartDelay(120 * 1000l);
    factoryBean.setRepeatInterval(24 * 60 * 60 * 1000l);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

}
