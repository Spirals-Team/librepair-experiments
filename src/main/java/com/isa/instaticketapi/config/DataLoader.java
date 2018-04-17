package com.isa.instaticketapi.config;

import java.util.HashSet;
import java.util.Set;

import com.isa.instaticketapi.domain.identity.FriendsIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.domain.VoteForEvent;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.FriendsRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.SeatRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForPlaceRepository;
import com.isa.instaticketapi.repository.VoteForEventRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;

/**
 * Class for making initial seed data
 *
 * @author sansajn
 */
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	FriendsRepository friendsRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	@Autowired
	private VoteForPlaceRepository voteForPlaceRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private VoteForEventRepository voteForProjectionRepository;

	private final Logger log = LoggerFactory.getLogger(DataLoader.class);

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		seedAuthorities();
		seedUsers();
		seedCinema();
		seedTheater();
		seedEvent();
		seedFanZone();
		seedHall();
		seedFriends();
		seedRepertory();
		seedProjection();
	}

	/**
	 * Setting up authorities for common init database
	 */
	public void seedAuthorities() {
		log.info("Starting seed for authority");
		Authority superAdmin = new Authority();
		Authority admin = new Authority();
		Authority user = new Authority();
		Authority guest = new Authority();
		Authority fanZoneAdmin = new Authority();

		superAdmin.setName(AuthoritiesConstants.SUPER_ADMIN);
		admin.setName(AuthoritiesConstants.ADMIN);
		user.setName(AuthoritiesConstants.USER);
		guest.setName(AuthoritiesConstants.ANONYMOUS);
		fanZoneAdmin.setName(AuthoritiesConstants.FANZONE_ADMIN);

		try {
			authorityRepository.save(superAdmin);
			authorityRepository.save(admin);
			authorityRepository.save(user);
			authorityRepository.save(guest);
			authorityRepository.save(fanZoneAdmin);
		} catch (Exception e) {
			log.debug("items (authorities) are already in database");
		}

		log.info("Seeds for authority are completed");
	}

	/**
	 * Setting up users for common init database
	 */
	public void seedUsers() {
		log.info("Strting seed for users");
		Set<Authority> authoritiesSuperAdmin = new HashSet<>();
		Set<Authority> authoritiesAdmin = new HashSet<>();
		Set<Authority> authoritiesUser = new HashSet<>();
		Set<Authority> authoritiesGuest = new HashSet<>();
		Set<Authority> authoritiesFanZoneAdmin = new HashSet();

		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
		Authority authorityAdmin = authorityRepository.findOne(AuthoritiesConstants.ADMIN);
		Authority authorityUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		Authority authorityGuest = authorityRepository.findOne(AuthoritiesConstants.ANONYMOUS);
		Authority authorityFanZoneAdmin = authorityRepository.findOne(AuthoritiesConstants.FANZONE_ADMIN);

		authoritiesSuperAdmin.add(authoritySuperAdmin);
		authoritiesSuperAdmin.add(authorityAdmin);
		authoritiesSuperAdmin.add(authorityAdmin);
		authoritiesSuperAdmin.add(authorityUser);
		authoritiesSuperAdmin.add(authorityGuest);

		authoritiesAdmin.add(authorityAdmin);
		authoritiesAdmin.add(authorityUser);
		authoritiesAdmin.add(authorityGuest);

		authoritiesFanZoneAdmin.add(authorityFanZoneAdmin);
		authoritiesFanZoneAdmin.add(authorityUser);
		authoritiesFanZoneAdmin.add(authorityUser);

		authoritiesUser.add(authorityUser);
		authoritiesUser.add(authorityGuest);

		User superAdmin = new User();
		User sansajn = new User();
		User milica = new User();
		User dejan = new User();

		superAdmin.setUsername("super");
		superAdmin.setPassword(passwordEncoder.encode("super"));
		superAdmin.setFirstName("SUPER_ADMIN");
		superAdmin.setLastName("SUPER");
		superAdmin.setAuthorities(authoritiesSuperAdmin);
		superAdmin.setActivated(true);
		superAdmin.setEmail("super@super.com");
		superAdmin.setCreatedBy("sansajn");
		superAdmin.setCity("Novi Sad");
		superAdmin.setAddress("petrovaradin");
		superAdmin.setNumber("2131231333");

		sansajn.setUsername("sansajn");
		sansajn.setPassword(passwordEncoder.encode("sansajn"));
		sansajn.setFirstName("Nemanja");
		sansajn.setLastName("Mudrinic");
		sansajn.setAuthorities(authoritiesAdmin);
		sansajn.setActivated(true);
		sansajn.setEmail("sansajn@super.com");
		sansajn.setCreatedBy("sansajn");
		sansajn.setCity("Novi Sad");
		sansajn.setAddress("petrovaradin");
		sansajn.setNumber("2131231333");

		dejan.setUsername("dejan");
		dejan.setPassword(passwordEncoder.encode("dejan"));
		dejan.setFirstName("Dejan");
		dejan.setLastName("Mijic");
		dejan.setAuthorities(authoritiesAdmin);
		dejan.setActivated(true);
		dejan.setEmail("dejan@super.com");
		dejan.setCreatedBy("sansajn");
		dejan.setCity("Novi Sad");
		dejan.setAddress("petrovaradin");
		dejan.setNumber("2131231333");

		milica.setUsername("milica");
		milica.setPassword(passwordEncoder.encode("milica"));
		milica.setFirstName("Milica");
		milica.setLastName("Kovacevic");
		milica.setAuthorities(authoritiesAdmin);
		milica.setActivated(true);
		milica.setEmail("milica@super.com");
		milica.setCreatedBy("sansajn");
		milica.setCity("Novi Sad");
		milica.setAddress("petrovaradin");
		milica.setNumber("2131231333");

		try {
			userRepository.save(superAdmin);
			userRepository.save(sansajn);
			userRepository.save(milica);
			userRepository.save(dejan);
		} catch (Exception e) {
			log.debug("items (users) are already in database");
		}

		log.info("Seeds for users are completed");

	}

	/**
	 * Setting up cinemas for common init database
	 */
	public void seedCinema() {

		try {

		} catch (Exception e) {
			log.debug("items (place) are already in database");
		}

		log.info("Seeds for place are completed");
	}

	/**
	 * Setting up theaters for common init database
	 */
	public void seedTheater() {
		log.info("Starting seed for theater");
		Place place = new Place();
		Place place1 = new Place();

		place.setName("Srpsko narodno pozoriste");
		place.setType("Pozoriste");
		place.setCreatedBy("Milica");
		place.setAddress("Позоришни трг 1");
		place.setDescripton(
				"Српско народно позориште је основано 16/28. јула 1861. године у Новом Саду, у тадашњој Царевини Аустрији (од 1867. Аустроугарска монархија). У Војводини је до тада већ постојала дуга позоришна традиција, од ђачких дилетантских представа, па све до приватних професионалних позоришних трупа. Позориште је оснивано у време буђења националне свести и борбе за националну слободу. У то доба у Новом Саду већина житеља је српске народности, велики број је високообразован, три четвртине имања и трговина били су у рукама Срба, тако да није случајно што је у Новом Саду основано Српско народно позориште. ");

		place1.setName("Pozoriste mladih");
		place1.setType("Pozoriste");
		place1.setCreatedBy("Milica");
		place1.setAddress("Ignjata Pavlasa 4");
		place1.setDescripton(
				"Pozorište mladih osnovano je 1932. godine kao Lutkarsko pozorište, pri Sokolskom društvu u Novom Sadu. Pozorište je nastalo iz Sokolske sekcije lutkara, koja je formirana 1930. godine, uz veliku podršku starešina Sokola, dr Vladimira Belajčića i dr Ignjata Pavlasa.");

		try {

			placeRepository.save(place);
			placeRepository.save(place1);

		} catch (Exception e) {
			log.debug("items (place) are already in database");
		}

		log.info("Seeds for place are completed");
	}

	/**
	 * Setting up event for common init database
	 */
	public void seedEvent() {
		log.info("Starting seed for event");

		try {

		} catch (Exception e) {
			log.debug("items (event) are already in database");
		}

		log.info("Seeds for event are completed");
	}

	public void seedFanZone() {

		Item it1 = new Item();
		it1.setName("Rekvizit 1");
		it1.setCreatedBy("Dejan");
		it1.setDescription("Opis opis");
		it1.setPrice("500");

		Item it2 = new Item();
		it2.setName("Rekvizit 2");
		it2.setCreatedBy("Dejan");
		it2.setDescription("Opis 2 bla bla");
		it2.setPrice("1200");

		try {

			itemRepository.save(it1);
			itemRepository.save(it2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void seedFriends() {

		User u1 = userRepository.findOne(Long.parseLong("2"));
		User u2 = userRepository.findOne(Long.parseLong("3"));
		User u3 = userRepository.findOne(Long.parseLong("4"));

		Friends friends1 = new Friends(new FriendsIdentity(u1.getId().toString(),u2.getId().toString()),u1,u2);
		Friends friends2 = new Friends(new FriendsIdentity(u2.getId().toString(),u1.getId().toString()),u2,u1);
		Friends friends3 = new Friends(new FriendsIdentity(u1.getId().toString(),u3.getId().toString()),u1,u3);
		Friends friends4 = new Friends(new FriendsIdentity(u3.getId().toString(),u1.getId().toString()),u3,u1);
		Friends friends5 = new Friends(new FriendsIdentity(u2.getId().toString(),u3.getId().toString()),u2,u3);
		Friends friends6 = new Friends(new FriendsIdentity(u3.getId().toString(),u2.getId().toString()),u3,u2);

		try {

			friendsRepository.save(friends1);
			friendsRepository.save(friends2);
			friendsRepository.save(friends3);
			friendsRepository.save(friends4);
			friendsRepository.save(friends5);
			friendsRepository.save(friends6);

		} catch (Exception e) {

		}
	}

	public void seedHall() {

		log.info("Starting seed for hall");

		try {

			log.info("Starting seed for hall");

		} catch (Exception e) {
			log.debug("items (hall) are already in database");
		}

		log.info("Seeds for hall are completed");
	}

	public void seedRepertory() {

		/*
		 * Repertory repertory = new Repertory();
		 * repertory.setCreatedBy("milica"); repertory.setDate("2018-03-14");
		 * repertory.setPlace(place);
		 * 
		 * Repertory repertory1 = new Repertory();
		 * repertory1.setCreatedBy("milica"); repertory1.setDate("2018-03-14");
		 * repertory1.setPlace(place1);
		 * 
		 * try { repertoryRepository.save(repertory);
		 * repertoryRepository.save(repertory1); log.info(
		 * "Starting seed for repertory");
		 * 
		 * } catch (Exception e) { log.debug(
		 * "items (repertory) are already in database"); }
		 * 
		 * log.info("Seeds for repertory are completed");
		 */
	}

	public void seedProjection() {
		Authority authorityUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		Authority authorityGuest = authorityRepository.findOne(AuthoritiesConstants.ANONYMOUS);

		Set<Authority> authoritiesUser = new HashSet<>();
		Set<Authority> authoritiesGuest = new HashSet<>();

		authoritiesUser.add(authorityUser);
		authoritiesUser.add(authorityGuest);

		User comi = new User();

		comi.setUsername("comi");
		comi.setPassword(passwordEncoder.encode("comi"));
		comi.setFirstName("Comi");
		comi.setLastName("Kovacevic");
		comi.setAuthorities(authoritiesUser);
		comi.setActivated(true);
		comi.setEmail("milicaaaaa@comi.com");
		comi.setCreatedBy("sansajn");
		comi.setCity("Novi Sad");
		comi.setAddress("petrovaradin");
		comi.setNumber("2131231333");

		Place place = new Place();
		Place place1 = new Place();
		Place place2 = new Place();

		place.setName("Arena Cineplex");
		place.setType("Bioskop");
		place.setCreatedBy("Milica");
		place.setAddress("Bul. Mihajla pupina 3");
		place.setDescripton(
				"Arena Cineplex je kompletno renovirana 2010. godine u skladu sa najnovijim svetskim standardima. Rekonstrukcijom i adaptacijom starog bioskopskog prostora dobijen je prvi multipleks u Vojvodini, sa šest vrhunski opremljenih sala, ukupnog kapaciteta od skoro 1.000 mesta, kao i dva ugostiteljska objekta -"
						+ " Cinema i The End cafe.Pored redovnog filmskog repertoara, u Areni Cineplex se organizuju svečane premijere domaćih filmova, kao i festivali FEST, Cinema City, Cinemania i Kids Fest. Važan segment naše ponude čine i mogućnost kupovine koncesija (kokice, naćosi i razna bezalkoholna pića) koje publika može da konzumira tokom trajanja projekcije."
						+ "");
		place.setVote(5);
		place1.setName("Cinestar Pancevo");
		place1.setType("Bioskop");
		place1.setCreatedBy("Milica");
		place1.setAddress("Miloša Obrenovića 12");
		place1.setDescripton(
				"Brend CineStar razvio se iz bioskopa kompanije Kieft & Kieft Filmtheater GmbH, koja je, posle više od četiri decenije rada kao operater klasičnog bioskopa, još 1993. otvorila svoj prvi multipleks u Nemačkoj, pa brend od tada predstavlja veličanstveni spoj jedinstvene arhitekture, vrhunskog komfora, sofisticiranog enterijera, kao i inovativnih tehnologija zvuka i slike – sa više od 100 multipleksa - i to ne samo u Nemačkoj, nego u više evropskih zemalja (Češka, Švajcarska, Hrvatska, Bosna i Hercegovina i dr.)");

		place1.setVote(3);
		place2.setName("Cinestar NS");
		place2.setType("Bioskop");
		place2.setCreatedBy("Milica");
		place2.setAddress("Big Soping Centar");
		place2.setDescripton(
				"Multipleksi CineStar potpuno su digitalizovani bioskopi opremljeni prema najsavremenijim standardima koji uključuju wall-to-wall platna najrenomiranijih svetskih proizvođača (Harkness Screens) i najbolju svetsku audio-vizuelnu tehnologiju, koja uključuje Barco Series-II digitalne projektore, Doremi i Barco ICMP Alchemy servere, RealD 3D tehnologiju, Dolby zvučne procesore, Crown audio pojačala i JBL zvučnike. ");

		Event event1 = new Event();
		Event event2 = new Event();
		Event event3 = new Event();
		Event event4 = new Event();
		Event event5 = new Event();
		Event event6 = new Event();
		Event event7 = new Event();
		Event event8 = new Event();
		Event event9 = new Event();

		event1.setName("Tomb Raider");
		event1.setActors("Alicia Vikander, Walton Goggins");
		event1.setDescription(
				"Lara Kroft je strastvena, samostalna devojka i ćerka ekscentričnog pustolova koji je nestao dok je ona još bila tinejdžerka. Sada, nakon sedam godina, Lara nema nikakav stvaran cilj ili svrhun ona odbija da preuzme uzde carstva svog oca jednako kao što odbija da prizna da je stvarno otišao.");
		event1.setDirector("Roar Uthaug");
		event1.setType("Akcioni, Avantura");
		event1.setDuration(118);
		event1.setCreatedBy("Milica");
		event1.setImageUrl("x");
		event1.setPlace(place);
		event1.setVote(5);

		event3.setName("Tomb Raider");
		event3.setActors("Alicia Vikander, Walton Goggins");
		event3.setDescription(
				"Lara Kroft je strastvena, samostalna devojka i ćerka ekscentričnog pustolova koji je nestao dok je ona još bila tinejdžerka. Sada, nakon sedam godina, Lara nema nikakav stvaran cilj ili svrhun ona odbija da preuzme uzde carstva svog oca jednako kao što odbija da prizna da je stvarno otišao.");
		event3.setDirector("Roar Uthaug");
		event3.setType("Akcioni, Avantura");
		event3.setDuration(118);
		event3.setCreatedBy("Milica");
		event3.setImageUrl("x");
		event3.setPlace(place1);
		event3.setVote(4);

		event4.setName("Tomb Raider");
		event4.setActors("Alicia Vikander, Walton Goggins");
		event4.setDescription(
				"Lara Kroft je strastvena, samostalna devojka i ćerka ekscentričnog pustolova koji je nestao dok je ona još bila tinejdžerka. Sada, nakon sedam godina, Lara nema nikakav stvaran cilj ili svrhun ona odbija da preuzme uzde carstva svog oca jednako kao što odbija da prizna da je stvarno otišao.");
		event4.setDirector("Roar Uthaug");
		event4.setType("Akcioni, Avantura");
		event4.setDuration(118);
		event4.setCreatedBy("Milica");
		event4.setImageUrl("x");
		event4.setPlace(place2);
		event4.setVote(4);

		event2.setName("Deadpool 2");
		event2.setActors("Ryan Reynolds, Josh Brolin");
		event2.setDescription(
				"Ne bi li nekako sebi zabiberio život, a u potrazi za kondenzatorom fluksa, Vejd mora da se bori protiv nindži, jakuza i čopora seksualno agresivnih pasa, putujući po svetu i otkrivajući važnost porodice, prijatelja i ukusa.");
		event2.setDirector("David Leitch");
		event2.setType("Akcioni, Komedija, Avantura");
		event2.setDuration(130);
		event2.setCreatedBy("Milica");
		event2.setImageUrl("x");
		event2.setPlace(place);
		event2.setVote(3);

		event5.setName("Deadpool 2");
		event5.setActors("Ryan Reynolds, Josh Brolin");
		event5.setDescription(
				"Ne bi li nekako sebi zabiberio život, a u potrazi za kondenzatorom fluksa, Vejd mora da se bori protiv nindži, jakuza i čopora seksualno agresivnih pasa, putujući po svetu i otkrivajući važnost porodice, prijatelja i ukusa.");
		event5.setDirector("David Leitch");
		event5.setType("Akcioni, Komedija, Avantura");
		event5.setDuration(130);
		event5.setCreatedBy("Milica");
		event5.setImageUrl("x");
		event5.setPlace(place1);

		event6.setName("The Bride");
		event6.setActors("Viktoriya Agalakova, Vyacheslav Chepurchenko");
		event6.setDescription(
				"U davnoj prošlosti, jedan fotograf je održavao tradiciju fotografisanja umrlih članova svoje porodice. Kada mu je ženaumrla, sahranio je pored tela device kako bi oživeo duh svoje žene u telu mlade devojke.");
		event6.setDirector(" Svyatoslav Podgaevskiy");
		event6.setType("Horor, Triler");
		event6.setDuration(93);
		event6.setCreatedBy("Milica");
		event6.setImageUrl("x");
		event6.setPlace(place2);
		event6.setVote(4);

		event7.setName("The Bride");
		event7.setActors("Viktoriya Agalakova, Vyacheslav Chepurchenko");
		event7.setDescription(
				"U davnoj prošlosti, jedan fotograf je održavao tradiciju fotografisanja umrlih članova svoje porodice. Kada mu je ženaumrla, sahranio je pored tela device kako bi oživeo duh svoje žene u telu mlade devojke.");
		event7.setDirector(" Svyatoslav Podgaevskiy");
		event7.setType("Horor, Triler");
		event7.setDuration(93);
		event7.setCreatedBy("Milica");
		event7.setImageUrl("x");
		event7.setPlace(place);
		event7.setVote(2);

		event8.setName("A Quiet Place");
		event8.setActors("Emily Blunt, John Krasinski");
		event8.setCreatedBy(
				"U modernom hororu i trileru TIHO MESTO, četvoročlana porodica mora da vodi život u apsolutnoj tišini, nakon što njihov opstanak ugroze misteriozna bića koja love onog ko ispusti i najmanji zvuk. Ako te čuju, uloviće te!");
		event8.setDirector("John Krasinski");
		event8.setType("Horor, Triler");
		event8.setDuration(90);
		event8.setCreatedBy("Milica");
		event8.setImageUrl("x");
		event8.setPlace(place2);

		event9.setName("A Quiet Place");
		event9.setActors("Emily Blunt, John Krasinski");
		event9.setCreatedBy(
				"U modernom hororu i trileru TIHO MESTO, četvoročlana porodica mora da vodi život u apsolutnoj tišini, nakon što njihov opstanak ugroze misteriozna bića koja love onog ko ispusti i najmanji zvuk. Ako te čuju, uloviće te!");
		event9.setDirector("John Krasinski");
		event9.setType("Horor, Triler");
		event9.setDuration(90);
		event9.setCreatedBy("Milica");
		event9.setImageUrl("x");
		event9.setPlace(place);
		event9.setVote(2);

		Hall hall1 = new Hall();
		Hall hall2 = new Hall();
		Hall hall3 = new Hall();
		Hall hall4 = new Hall();
		Hall hall5 = new Hall();
		Hall hall6 = new Hall();
		Hall hall7 = new Hall();
		Hall hall8 = new Hall();

		hall1.setName("Sala 1.");
		hall1.setCreatedBy("Milica");
		hall1.setCol(10);
		hall1.setRow(10);
		hall1.setPlace(place);

		hall2.setName("Sala 2.");
		hall2.setCreatedBy("Milica");
		hall2.setCol(12);
		hall2.setRow(8);
		hall2.setPlace(place);

		hall3.setName("Sala 3.");
		hall3.setCreatedBy("Milica");
		hall3.setCol(10);
		hall3.setRow(6);
		hall3.setPlace(place);

		hall4.setName("Sala-1");
		hall4.setCreatedBy("Milica");
		hall4.setCol(7);
		hall4.setRow(7);
		hall4.setPlace(place1);

		hall5.setName("Sala-2");
		hall5.setCreatedBy("Milica");
		hall5.setCol(8);
		hall5.setRow(8);
		hall5.setPlace(place1);

		hall6.setName("Sala-3");
		hall6.setCreatedBy("Milica");
		hall6.setCol(10);
		hall6.setRow(10);
		hall6.setPlace(place1);

		hall7.setName("Sala 1");
		hall7.setCreatedBy("Milica");
		hall7.setCol(8);
		hall7.setRow(8);
		hall7.setPlace(place2);

		hall8.setName("Sala 2");
		hall8.setCreatedBy("Milica");
		hall8.setCol(10);
		hall8.setRow(10);
		hall8.setPlace(place2);

		Repertory repertory = new Repertory();
		repertory.setCreatedBy("milica");
		repertory.setDate("23-4-2018");
		repertory.setPlace(place);

		Repertory repertory1 = new Repertory();
		repertory1.setCreatedBy("milica");
		repertory1.setDate("24-4-2018");
		repertory1.setPlace(place);

		Repertory repertory2 = new Repertory();
		repertory2.setCreatedBy("milica");
		repertory2.setDate("23-4-2018");
		repertory2.setPlace(place1);

		Repertory repertory3 = new Repertory();
		repertory3.setCreatedBy("milica");
		repertory3.setDate("24-4-2018");
		repertory3.setPlace(place2);

		Repertory repertory4 = new Repertory();
		repertory4.setCreatedBy("milica");
		repertory4.setDate("25-4-2018");
		repertory4.setPlace(place2);

		Projection projection = new Projection();
		projection.setCreatedBy("milica");
		projection.setReperotry(repertory);
		projection.setStartTime("15:00");
		projection.setEndTime("17:10");
		projection.setHall(hall1);
		projection.setEvent(event2);
		projection.setDate(repertory.getDate());
		projection.setSale(20);
		projection.setRegularPrice(470);

		Projection projection2 = new Projection();
		projection2.setCreatedBy("milica");
		projection2.setReperotry(repertory);
		projection2.setStartTime("12:00");
		projection2.setEndTime("14:00");
		projection2.setHall(hall2);
		projection2.setEvent(event1);
		projection2.setDate(repertory.getDate());
		projection2.setSale(20);
		projection2.setRegularPrice(350);

		Projection projection3 = new Projection();
		projection3.setCreatedBy("milica");
		projection3.setReperotry(repertory1);
		projection3.setStartTime("12:00");
		projection3.setEndTime("14:00");
		projection3.setHall(hall2);
		projection3.setEvent(event1);
		projection3.setDate(repertory1.getDate());

		Projection projection4 = new Projection();
		projection4.setCreatedBy("milica");
		projection4.setReperotry(repertory1);
		projection4.setStartTime("21:00");
		projection4.setEndTime("22:35");
		projection4.setHall(hall3);
		projection4.setEvent(event5);
		projection4.setDate(repertory1.getDate());
		projection4.setSale(20);
		projection4.setRegularPrice(500);

		Projection projection5 = new Projection();
		projection5.setCreatedBy("milica");
		projection5.setReperotry(repertory2);
		projection5.setStartTime("16:00");
		projection5.setEndTime("18:00");
		projection5.setHall(hall4);
		projection5.setEvent(event3);
		projection5.setDate(repertory2.getDate());
		projection5.setSale(20);
		projection5.setRegularPrice(500);

		Projection projection6 = new Projection();
		projection6.setCreatedBy("milica");
		projection6.setReperotry(repertory3);
		projection6.setStartTime("21:00");
		projection6.setEndTime("23:00");
		projection6.setHall(hall7);
		projection6.setEvent(event4);
		projection6.setDate(repertory3.getDate());

		Projection projection7 = new Projection();
		projection7.setCreatedBy("milica");
		projection7.setReperotry(repertory3);
		projection7.setStartTime("21:00");
		projection7.setEndTime("22:35");
		projection7.setHall(hall8);
		projection7.setEvent(event6);
		projection7.setDate(repertory3.getDate());

		Projection projection8 = new Projection();
		projection8.setCreatedBy("milica");
		projection8.setReperotry(repertory3);
		projection8.setStartTime("18:00");
		projection8.setEndTime("19:30");
		projection8.setHall(hall8);
		projection8.setEvent(event8);
		projection8.setDate(repertory3.getDate());

		Projection projection9 = new Projection();
		projection9.setCreatedBy("milica");
		projection9.setReperotry(repertory3);
		projection9.setStartTime("18:00");
		projection9.setEndTime("19:30");
		projection9.setHall(hall8);
		projection9.setEvent(event8);
		projection9.setDate(repertory3.getDate());

		VoteForPlace vote1 = new VoteForPlace();
		vote1.setUser(comi);
		vote1.setPlace(place1);
		vote1.setVote(4);

		VoteForPlace vote2 = new VoteForPlace();
		vote2.setUser(comi);
		vote2.setPlace(place1);
		vote2.setVote(2);

		VoteForPlace vote3 = new VoteForPlace();
		vote3.setUser(comi);
		vote3.setPlace(place);
		vote3.setVote(5);

		VoteForEvent v1 = new VoteForEvent();
		v1.setUser(comi);
		v1.setEvent(event1);
		v1.setVote(3);

		VoteForEvent v2 = new VoteForEvent();
		v2.setUser(comi);
		v2.setEvent(event1);
		v2.setVote(3);

		VoteForEvent v3 = new VoteForEvent();
		v3.setUser(comi);
		v3.setEvent(event3);
		v3.setVote(5);

		VoteForEvent v4 = new VoteForEvent();
		v4.setUser(comi);
		v4.setEvent(event2);
		v4.setVote(4);

		VoteForEvent v5 = new VoteForEvent();
		v5.setUser(comi);
		v5.setEvent(event6);
		v5.setVote(4);

		Seat seat1 = new Seat();
		seat1.setHall(hall1);
		seat1.setCordX(1);
		seat1.setCordY(1);
		seat1.setProjection(projection);
		seat1.setReserved(false);
		seat1.setSeat(true);
		seat1.setSeatType("Brza rezervacija");

		Seat seat2 = new Seat();
		seat2.setHall(hall1);
		seat2.setCordX(1);
		seat2.setCordY(1);
		seat2.setProjection(projection);
		seat2.setReserved(true);
		seat2.setSeat(true);
		seat2.setSeatType("Brza rezervacija");

		Seat seat3 = new Seat();
		seat3.setHall(hall3);
		seat3.setCordX(5);
		seat3.setCordY(1);
		seat3.setProjection(projection4);
		seat3.setReserved(false);
		seat3.setSeat(true);
		seat3.setSeatType("Brza rezervacija");

		Seat seat4 = new Seat();
		seat4.setHall(hall4);
		seat4.setCordX(4);
		seat4.setCordY(4);
		seat4.setProjection(projection5);
		seat4.setReserved(false);
		seat4.setSeat(true);
		seat4.setSeatType("Brza rezervacija");

		try {

			placeRepository.save(place);
			placeRepository.save(place1);
			placeRepository.save(place2);

			eventRepository.save(event1);
			eventRepository.save(event2);
			eventRepository.save(event3);
			eventRepository.save(event4);
			eventRepository.save(event5);
			eventRepository.save(event6);
			eventRepository.save(event7);
			eventRepository.save(event8);
			eventRepository.save(event9);

			hallRepository.save(hall1);
			hallRepository.save(hall2);
			hallRepository.save(hall3);
			hallRepository.save(hall4);
			hallRepository.save(hall5);
			hallRepository.save(hall6);
			hallRepository.save(hall7);
			hallRepository.save(hall8);

			repertoryRepository.save(repertory);
			repertoryRepository.save(repertory1);
			repertoryRepository.save(repertory2);
			repertoryRepository.save(repertory3);
			repertoryRepository.save(repertory4);

			projectionRepository.save(projection);
			projectionRepository.save(projection2);
			projectionRepository.save(projection3);
			projectionRepository.save(projection4);
			projectionRepository.save(projection5);
			projectionRepository.save(projection6);
			projectionRepository.save(projection7);
			projectionRepository.save(projection8);
			projectionRepository.save(projection9);

			userRepository.save(comi);
			voteForPlaceRepository.save(vote1);
			voteForPlaceRepository.save(vote2);
			voteForPlaceRepository.save(vote3);

			voteForProjectionRepository.save(v1);
			voteForProjectionRepository.save(v2);
			voteForProjectionRepository.save(v3);
			voteForProjectionRepository.save(v4);
			voteForProjectionRepository.save(v5);

			seatRepository.save(seat1);
			seatRepository.save(seat2);
			seatRepository.save(seat3);
			seatRepository.save(seat4);
			log.info("Starting seed for projection");

		} catch (Exception e) {
			log.debug("items (projection) are already in database");
		}

		log.info("Seeds for projection are completed");

	}

}
