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

import com.cmpl.web.backup.BackupImporterJob;
import com.cmpl.web.backup.reader.AssociationUserRoleCSVParser;
import com.cmpl.web.backup.reader.BackupImporter;
import com.cmpl.web.backup.reader.CSVReader;
import com.cmpl.web.backup.reader.CSVReaderImpl;
import com.cmpl.web.backup.reader.CarouselCSVParser;
import com.cmpl.web.backup.reader.CarouselItemCSVParser;
import com.cmpl.web.backup.reader.CommonParser;
import com.cmpl.web.backup.reader.MediaCSVParser;
import com.cmpl.web.backup.reader.MenuCSVParser;
import com.cmpl.web.backup.reader.NewsContentCSVParser;
import com.cmpl.web.backup.reader.NewsEntryCSVParser;
import com.cmpl.web.backup.reader.NewsImageCSVParser;
import com.cmpl.web.backup.reader.PageCSVParser;
import com.cmpl.web.backup.reader.PrivilegeCSVParser;
import com.cmpl.web.backup.reader.RoleCSVParser;
import com.cmpl.web.backup.reader.StyleCSVParser;
import com.cmpl.web.backup.reader.UserCSVParser;
import com.cmpl.web.backup.reader.WidgetCSVParser;
import com.cmpl.web.backup.reader.WidgetPageCSVParser;
import com.cmpl.web.backup.writer.DataManipulator;
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

@Configuration
@PropertySource("classpath:/backup/backup.properties")
public class BackupImportConfiguration {

  @Value("${backupFilePath}")
  String backupFilePath;

  @Value("${actualitesFilePath}")
  String actualitesFilePath;

  @Value("${pagesFilePath}")
  String pagesFilePath;

  @Value("${mediaFilePath}")
  String mediaFilePath;

  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());

  @Bean
  public BackupImporter backupImporter(CSVReader csvReader) {
    return new BackupImporter(csvReader, backupFilePath, mediaFilePath, pagesFilePath, actualitesFilePath);
  }

  @Bean
  public MenuCSVParser menuCSVParser(DataManipulator<Menu> menuDataManipulator) {
    return new MenuCSVParser(dateFormatter, menuDataManipulator, backupFilePath);
  }

  @Bean
  public StyleCSVParser styleCSVParser(DataManipulator<Style> styleDataManipulator) {
    return new StyleCSVParser(dateFormatter, styleDataManipulator, backupFilePath);
  }

  @Bean
  public PageCSVParser pageCSVParser(DataManipulator<Page> pageDataManipulator) {
    return new PageCSVParser(dateFormatter, pageDataManipulator, backupFilePath);
  }

  @Bean
  public MediaCSVParser mediaCSVParser(DataManipulator<Media> mediaDataManipulator) {
    return new MediaCSVParser(dateFormatter, mediaDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselCSVParser carouselCSVParser(DataManipulator<Carousel> carouselDataManipulator) {
    return new CarouselCSVParser(dateFormatter, carouselDataManipulator, backupFilePath);
  }

  @Bean
  public CarouselItemCSVParser carouselItemCSVParser(DataManipulator<CarouselItem> carouselItemDataManipulator) {
    return new CarouselItemCSVParser(dateFormatter, carouselItemDataManipulator, backupFilePath);
  }

  @Bean
  public NewsEntryCSVParser newsEntryCSVParser(DataManipulator<NewsEntry> newsEntryDataManipulator) {
    return new NewsEntryCSVParser(dateFormatter, newsEntryDataManipulator, backupFilePath);
  }

  @Bean
  public NewsImageCSVParser newsImageCSVParser(DataManipulator<NewsImage> newsImageDataManipulator) {
    return new NewsImageCSVParser(dateFormatter, newsImageDataManipulator, backupFilePath);
  }

  @Bean
  public NewsContentCSVParser newsContentCSVParser(DataManipulator<NewsContent> newsContentDataManipulator) {
    return new NewsContentCSVParser(dateFormatter, newsContentDataManipulator, backupFilePath);
  }

  @Bean
  public WidgetCSVParser widgetCSVParser(DataManipulator<Widget> widgetDataManipulator) {
    return new WidgetCSVParser(dateFormatter, widgetDataManipulator, backupFilePath);
  }

  @Bean
  public WidgetPageCSVParser widgetPageCSVParser(DataManipulator<WidgetPage> widgetPageDataManipulator) {
    return new WidgetPageCSVParser(dateFormatter, widgetPageDataManipulator, backupFilePath);
  }

  @Bean
  public UserCSVParser userCSVParser(DataManipulator<User> userDataManipulator) {
    return new UserCSVParser(dateFormatter, userDataManipulator, backupFilePath);
  }

  @Bean
  public RoleCSVParser roleCSVParser(DataManipulator<Role> roleDataManipulator) {
    return new RoleCSVParser(dateFormatter, roleDataManipulator, backupFilePath);
  }

  @Bean
  public PrivilegeCSVParser privilegeCSVParser(DataManipulator<Privilege> privilegeDataManipulator) {
    return new PrivilegeCSVParser(dateFormatter, privilegeDataManipulator, backupFilePath);
  }

  @Bean
  public AssociationUserRoleCSVParser associationUserRoleCSVParser(
      DataManipulator<AssociationUserRole> associationUserRoleDataManipulator) {
    return new AssociationUserRoleCSVParser(dateFormatter, associationUserRoleDataManipulator, backupFilePath);
  }

  @Bean
  public CSVReader csvReader(UserCSVParser userCSVParser, RoleCSVParser roleCSVParser,
      PrivilegeCSVParser privilegeCSVParser, AssociationUserRoleCSVParser associationUserRoleCSVParser,
      MenuCSVParser menuCSVParser, StyleCSVParser styleCSVParser, PageCSVParser pageCSVParser,
      MediaCSVParser mediaCSVParser, CarouselCSVParser carouselCSVParser, CarouselItemCSVParser carouselItemCSVParser,

      NewsEntryCSVParser newsEntryCSVParser, NewsImageCSVParser newsImageCSVParser,
      NewsContentCSVParser newsContentCSVParser, WidgetCSVParser widgetCSVParser,
      WidgetPageCSVParser widgetPageCSVParser) {
    List<CommonParser<?>> parsers = new ArrayList<>();
    parsers.add(userCSVParser);
    parsers.add(roleCSVParser);
    parsers.add(associationUserRoleCSVParser);
    parsers.add(privilegeCSVParser);
    parsers.add(menuCSVParser);
    parsers.add(styleCSVParser);
    parsers.add(pageCSVParser);
    parsers.add(mediaCSVParser);
    parsers.add(carouselCSVParser);
    parsers.add(carouselItemCSVParser);
    parsers.add(newsEntryCSVParser);
    parsers.add(newsImageCSVParser);
    parsers.add(newsContentCSVParser);
    parsers.add(widgetCSVParser);
    parsers.add(widgetPageCSVParser);
    return new CSVReaderImpl(parsers);
  }

  @Bean
  @Qualifier("backupImportJob")
  public JobDetailFactoryBean backupImportJob() {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(BackupImporterJob.class);
    factoryBean.setGroup("backupImportJob");
    factoryBean.setName("backupImportJob");
    factoryBean.setDescription("Backup import of all the data");
    factoryBean.setDurability(true);
    return factoryBean;
  }

  @Bean
  @Qualifier("backupImportTrigger")
  public SimpleTriggerFactoryBean backupImportTrigger(JobDetail backupImportJob) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName("Application backup import");
    factoryBean.setDescription("Startup backup import of the data of the application");
    factoryBean.setJobDetail(backupImportJob);
    factoryBean.setStartDelay(10 * 1000l);
    factoryBean.setRepeatCount(0);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

}
