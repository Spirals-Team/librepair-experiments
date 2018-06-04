package pro.taskana.ldap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pro.taskana.rest.resource.AccessIdResource;

/**
 * Implementation of LdapCache used for Unit tests.
 *
 * @author bbr
 */
public class LdapCacheTestImpl implements LdapCache {

    @Override
    public List<AccessIdResource> findMatchingAccessId(String searchFor) {
        List<AccessIdResource> result = accessIds.stream()
            .filter(t -> (t.getName().toLowerCase().contains(searchFor.toLowerCase())
                || t.getAccessId().toLowerCase().contains(searchFor.toLowerCase())))
            .collect(Collectors.toList());
        return result;
    }

    private static List<AccessIdResource> accessIds = new ArrayList<>(Arrays.asList(
        new AccessIdResource("Rojas, Miguel", "cn=Miguel Rojas,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lengl, Marcel", "cn=Marcel Lengl,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zorgati, Mustapha", "cn=Mustapha Zorgati,ou=people,o=TaskanaTest"),
        new AccessIdResource("Breier, Bernd", "cn=Bernd Breier,ou=people,o=TaskanaTest"),
        new AccessIdResource("Meyer, Dominik", "cn=Dominik Meyer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Hagen, Holger", "cn=Holger Hagen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Behrendt, Maximilian", "cn=Maximilian Behrendt,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ferrante, Elena", "cn=Elena Ferrante,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mueller, Simone", "cn=Simone Mueller,ou=people,o=TaskanaTest"),
        new AccessIdResource("Sirup, Aaron", "cn=Aaron Sirup,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kahn, Ada", "cn=Ada Kahn,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lass, Ada", "cn=Ada Lass,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tion, Addi", "cn=Addi Tion,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lette, Adi", "cn=Adi Lette,ou=people,o=TaskanaTest"),
        new AccessIdResource("Native, Alter", "cn=Alter Native,ou=people,o=TaskanaTest"),
        new AccessIdResource("Herum, Albert", "cn=Albert Herum,ou=people,o=TaskanaTest"),
        new AccessIdResource("Bert, Ali", "cn=Ali Bert,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mente, Ali", "cn=Ali Mente,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mater, Alma", "cn=Alma Mater,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nach, Alma", "cn=Alma Nach,ou=people,o=TaskanaTest"),
        new AccessIdResource("Gehzauch, Anders", "cn=Anders Gehzauch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Theke, Andi", "cn=Andi Theke,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kreuz, Andreas", "cn=Andreas Kreuz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kette, Anka", "cn=Anka Kette,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tiefsee, Anka", "cn=Anka Tiefsee,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fielmalz, Anke", "cn=Anke Fielmalz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fassen, Ann", "cn=Ann Fassen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Probe, Ann", "cn=Ann Probe,ou=people,o=TaskanaTest"),
        new AccessIdResource("Bolika, Anna", "cn=Anna Bolika,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ecke, Anna", "cn=Anna Ecke,ou=people,o=TaskanaTest"),
        new AccessIdResource("Hosi, Anna", "cn=Anna Hosi,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kronis-Tisch, Anna", "cn=Anna Kronis-Tisch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Logie, Anna", "cn=Anna Logie,ou=people,o=TaskanaTest"),
        new AccessIdResource("Luehse, Anna", "cn=Anna Luehse,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nass, Anna", "cn=Anna Nass,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thalb, Anna", "cn=Anna Thalb,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tomie, Anna", "cn=Anna Tomie,ou=people,o=TaskanaTest"),
        new AccessIdResource("Donnich, Anne", "cn=Anne Donnich,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kaffek, Anne", "cn=Anne Kaffek,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thek, Anne", "cn=Anne Thek,ou=people,o=TaskanaTest"),
        new AccessIdResource("Matoer, Anni", "cn=Anni Matoer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ragentor, Ansgar", "cn=Ansgar Ragentor,ou=people,o=TaskanaTest"),
        new AccessIdResource("Stoteles, Ari", "cn=Ari Stoteles,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thmetik, Ari", "cn=Ari Thmetik,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nuehm, Arno", "cn=Arno Nuehm,ou=people,o=TaskanaTest"),
        new AccessIdResource("Schocke, Artie", "cn=Artie Schocke,ou=people,o=TaskanaTest"),
        new AccessIdResource("Stoppel, Bart", "cn=Bart Stoppel,ou=people,o=TaskanaTest"),
        new AccessIdResource("Beitung, Bea", "cn=Bea Beitung,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ildich, Bea", "cn=Bea Ildich,ou=people,o=TaskanaTest"),
        new AccessIdResource("Vista, Bella", "cn=Bella Vista,ou=people,o=TaskanaTest"),
        new AccessIdResource("Utzer, Ben", "cn=Ben Utzer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zien, Ben", "cn=Ben Zien,ou=people,o=TaskanaTest"),
        new AccessIdResource("Stein, Bernd", "cn=Bernd Stein,ou=people,o=TaskanaTest"),
        new AccessIdResource("Deramen, Bill", "cn=Bill Deramen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Honig, Bine", "cn=Bine Honig,ou=people,o=TaskanaTest"),
        new AccessIdResource("Densatz, Bo", "cn=Bo Densatz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Densee, Bo", "cn=Bo Densee,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lerwagen, Bo", "cn=Bo Lerwagen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tail, Bob", "cn=Bob Tail,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ketta, Bruce", "cn=Bruce Ketta,ou=people,o=TaskanaTest"),
        new AccessIdResource("Terrie, Bud", "cn=Bud Terrie,ou=people,o=TaskanaTest"),
        new AccessIdResource("Biener-Haken, Cara", "cn=iener- Cara Haken,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ass, Caro", "cn=Caro Ass,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kaffee, Caro", "cn=Caro Kaffee,ou=people,o=TaskanaTest"),
        new AccessIdResource("Linger, Caro", "cn=Caro Linger,ou=people,o=TaskanaTest"),
        new AccessIdResource("tenSaft, Caro", "cn=Caro tenSaft,ou=people,o=TaskanaTest"),
        new AccessIdResource("Antheme, Chris", "cn=Chris Antheme,ou=people,o=TaskanaTest"),
        new AccessIdResource("Baum, Chris", "cn=Chris Baum,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tall, Chris", "cn=Chris Tall,ou=people,o=TaskanaTest"),
        new AccessIdResource("Reiniger, Claas", "cn=Claas Reiniger,ou=people,o=TaskanaTest"),
        new AccessIdResource("Grube, Claire", "cn=Claire Grube,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fall, Clara", "cn=Clara Fall,ou=people,o=TaskanaTest"),
        new AccessIdResource("Korn, Clara", "cn=Clara Korn,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lenriff, Cora", "cn=Cora Lenriff,ou=people,o=TaskanaTest"),
        new AccessIdResource("Schiert, Cora", "cn=Cora Schiert,ou=people,o=TaskanaTest"),
        new AccessIdResource("Hose, Cord", "cn=Cord Hose,ou=people,o=TaskanaTest"),
        new AccessIdResource("Onbleu, Cord", "cn=Cord Onbleu,ou=people,o=TaskanaTest"),
        new AccessIdResource("Umkleide, Damon", "cn=Damon Umkleide,ou=people,o=TaskanaTest"),
        new AccessIdResource("Affier, Dean", "cn=Dean Affier,ou=people,o=TaskanaTest"),
        new AccessIdResource("Orm, Dean", "cn=Dean Orm,ou=people,o=TaskanaTest"),
        new AccessIdResource("Platz, Dennis", "cn=Dennis Platz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Milch, Dick", "cn=Dick Milch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mow, Dina", "cn=Dina Mow,ou=people,o=TaskanaTest"),
        new AccessIdResource("Keil, Donna", "cn=Donna Keil,ou=people,o=TaskanaTest"),
        new AccessIdResource("Littchen, Donna", "cn=Donna Littchen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Wetter, Donna", "cn=Donna Wetter,ou=people,o=TaskanaTest"),
        new AccessIdResource("Was, Ed", "cn=Ed Was,ou=people,o=TaskanaTest"),
        new AccessIdResource("Khar, Ede", "cn=Ede Khar,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nut, Ella", "cn=Ella Nut,ou=people,o=TaskanaTest"),
        new AccessIdResource("Stisch, Ella", "cn=Ella Stisch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Diel, Emma", "cn=Emma Diel,ou=people,o=TaskanaTest"),
        new AccessIdResource("Herdamit, Emma", "cn=Emma Herdamit,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mitter-Uhe, Emma", "cn=Emma Mitter-Uhe,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tatt, Erich", "cn=Erich Tatt,ou=people,o=TaskanaTest"),
        new AccessIdResource("Drigend, Ernie", "cn=Ernie Drigend,ou=people,o=TaskanaTest"),
        new AccessIdResource("Poly, Esther", "cn=Esther Poly,ou=people,o=TaskanaTest"),
        new AccessIdResource("Trautz, Eugen", "cn=Eugen Trautz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Quiert, Eva", "cn=Eva Quiert,ou=people,o=TaskanaTest"),
        new AccessIdResource("Inurlaub, Fatma", "cn=Fatma Inurlaub,ou=people,o=TaskanaTest"),
        new AccessIdResource("Land, Finn", "cn=Finn Land,ou=people,o=TaskanaTest"),
        new AccessIdResource("Sternis, Finn", "cn=Finn Sternis,ou=people,o=TaskanaTest"),
        new AccessIdResource("Furt, Frank", "cn=Frank Furt,ou=people,o=TaskanaTest"),
        new AccessIdResource("Reich, Frank", "cn=Frank Reich,ou=people,o=TaskanaTest"),
        new AccessIdResource("Iskaner, Franz", "cn=Franz Iskaner,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nerr, Franziska", "cn=Franziska Nerr,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zafen, Friedrich", "cn=Friedrich Zafen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Pomm, Fritz", "cn=Fritz Pomm,ou=people,o=TaskanaTest"),
        new AccessIdResource("deWegs, Gera", "cn=Gera deWegs,ou=people,o=TaskanaTest"),
        new AccessIdResource("Staebe, Gitta", "cn=Gitta Staebe,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zend, Glenn", "cn=Glenn Zend,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fisch, Grete", "cn=Grete Fisch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zucker, Gus", "cn=Gus Zucker,ou=people,o=TaskanaTest"),
        new AccessIdResource("Muhn, Hanni", "cn=Hanni Muhn,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fermesse, Hanno", "cn=Hanno Fermesse,ou=people,o=TaskanaTest"),
        new AccessIdResource("Aplast, Hans", "cn=Hans Aplast,ou=people,o=TaskanaTest"),
        new AccessIdResource("Eart, Hans", "cn=Hans Eart,ou=people,o=TaskanaTest"),
        new AccessIdResource("Back, Hardy", "cn=Hardy Back,ou=people,o=TaskanaTest"),
        new AccessIdResource("Beau, Harry", "cn=Harry Beau,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kraut, Heide", "cn=Heide Kraut,ou=people,o=TaskanaTest"),
        new AccessIdResource("Witzka, Heide", "cn=Heide Witzka,ou=people,o=TaskanaTest"),
        new AccessIdResource("Buchen, Hein", "cn=Hein Buchen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lichkeit, Hein", "cn=Hein Lichkeit,ou=people,o=TaskanaTest"),
        new AccessIdResource("Suchung, Hein", "cn=Hein Suchung,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ellmann, Heinz", "cn=Heinz Ellmann,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ketchup, Heinz", "cn=Heinz Ketchup,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zeim, Hilde", "cn=Hilde Zeim,ou=people,o=TaskanaTest"),
        new AccessIdResource("Bilien, Immo", "cn=Immo Bilien,ou=people,o=TaskanaTest"),
        new AccessIdResource("Her, Inge", "cn=Inge Her,ou=people,o=TaskanaTest"),
        new AccessIdResource("Wahrsam, Inge", "cn=Inge Wahrsam,ou=people,o=TaskanaTest"),
        new AccessIdResource("Flamm, Ingo", "cn=Ingo Flamm,ou=people,o=TaskanaTest"),
        new AccessIdResource("Enzien, Ingrid", "cn=Ingrid Enzien,ou=people,o=TaskanaTest"),
        new AccessIdResource("Rohsch, Inken", "cn=Inken Rohsch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ihr, Insa", "cn=Insa Ihr,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nerda, Iska", "cn=Iska Nerda,ou=people,o=TaskanaTest"),
        new AccessIdResource("Eitz, Jens", "cn=Jens Eitz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nastik, Jim", "cn=Jim Nastik,ou=people,o=TaskanaTest"),
        new AccessIdResource("Gurt, Jo", "cn=Jo Gurt,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kurrth, Jo", "cn=Jo Kurrth,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kolade, Joe", "cn=Joe Kolade,ou=people,o=TaskanaTest"),
        new AccessIdResource("Iter, Johann", "cn=Johann Iter,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tick, Joyce", "cn=Joyce Tick,ou=people,o=TaskanaTest"),
        new AccessIdResource("Case, Justin", "cn=Justin Case,ou=people,o=TaskanaTest"),
        new AccessIdResource("Time, Justin", "cn=Justin Time,ou=people,o=TaskanaTest"),
        new AccessIdResource("Komp, Jutta", "cn=Jutta Komp,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mauer, Kai", "cn=Kai Mauer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Pirinja, Kai", "cn=Kai Pirinja,ou=people,o=TaskanaTest"),
        new AccessIdResource("Serpfalz, Kai", "cn=Kai Serpfalz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Auer, Karl", "cn=Karl Auer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ielauge, Karl", "cn=Karl Ielauge,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ifornjen, Karl", "cn=Karl Ifornjen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Radi, Karl", "cn=Karl Radi,ou=people,o=TaskanaTest"),
        new AccessIdResource("Verti, Karl", "cn=Karl Verti,ou=people,o=TaskanaTest"),
        new AccessIdResource("Sery, Karo", "cn=Karo Sery,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lisator, Katha", "cn=Katha Lisator,ou=people,o=TaskanaTest"),
        new AccessIdResource("Flo, Kati", "cn=Kati Flo,ou=people,o=TaskanaTest"),
        new AccessIdResource("Schenn, Knut", "cn=Knut Schenn,ou=people,o=TaskanaTest"),
        new AccessIdResource("Achse, Kurt", "cn=Kurt Achse,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zepause, Kurt", "cn=Kurt Zepause,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zerr, Kurt", "cn=Kurt Zerr,ou=people,o=TaskanaTest"),
        new AccessIdResource("Reden, Lasse", "cn=Lasse Reden,ou=people,o=TaskanaTest"),
        new AccessIdResource("Metten, Lee", "cn=Lee Metten,ou=people,o=TaskanaTest"),
        new AccessIdResource("Arm, Lene", "cn=Lene Arm,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thur, Linnea", "cn=Linnea Thur,ou=people,o=TaskanaTest"),
        new AccessIdResource("Bonn, Lisa", "cn=Lisa Bonn,ou=people,o=TaskanaTest"),
        new AccessIdResource("Sembourg, Luc", "cn=Luc Sembourg,ou=people,o=TaskanaTest"),
        new AccessIdResource("Rung, Lucky", "cn=Lucky Rung,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zafen, Ludwig", "cn=Ludwig Zafen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Hauden, Lukas", "cn=Lukas Hauden,ou=people,o=TaskanaTest"),
        new AccessIdResource("Hose, Lutz", "cn=Lutz Hose,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tablette, Lutz", "cn=Lutz Tablette,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fehr, Luzie", "cn=Luzie Fehr,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nalyse, Magda", "cn=Magda Nalyse,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ehfer, Maik", "cn=Maik Ehfer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Sehr, Malte", "cn=Malte Sehr,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thon, Mara", "cn=Mara Thon,ou=people,o=TaskanaTest"),
        new AccessIdResource("Quark, Marga", "cn=Marga Quark,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nade, Marie", "cn=Marie Nade,ou=people,o=TaskanaTest"),
        new AccessIdResource("Niert, Marie", "cn=Marie Niert,ou=people,o=TaskanaTest"),
        new AccessIdResource("Neese, Mario", "cn=Mario Neese,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nette, Marion", "cn=Marion Nette,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nesium, Mark", "cn=Mark Nesium,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thalle, Mark", "cn=Mark Thalle,ou=people,o=TaskanaTest"),
        new AccessIdResource("Diven, Marle", "cn=Marle Diven,ou=people,o=TaskanaTest"),
        new AccessIdResource("Fitz, Marle", "cn=Marle Fitz,ou=people,o=TaskanaTest"),
        new AccessIdResource("Pfahl, Marta", "cn=Marta Pfahl,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zorn, Martin", "cn=Martin Zorn,ou=people,o=TaskanaTest"),
        new AccessIdResource("Krissmes, Mary", "cn=Mary Krissmes,ou=people,o=TaskanaTest"),
        new AccessIdResource("Jess, Matt", "cn=Matt Jess,ou=people,o=TaskanaTest"),
        new AccessIdResource("Strammer, Max", "cn=Max Strammer,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mumm, Maxi", "cn=Maxi Mumm,ou=people,o=TaskanaTest"),
        new AccessIdResource("Morphose, Meta", "cn=Meta Morphose,ou=people,o=TaskanaTest"),
        new AccessIdResource("Uh, Mia", "cn=Mia Uh,ou=people,o=TaskanaTest"),
        new AccessIdResource("Rofon, Mike", "cn=Mike Rofon,ou=people,o=TaskanaTest"),
        new AccessIdResource("Rosoft, Mike", "cn=Mike Rosoft,ou=people,o=TaskanaTest"),
        new AccessIdResource("Liter, Milli", "cn=Milli Liter,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thär, Milli", "cn=hä Milli r,ou=people,o=TaskanaTest"),
        new AccessIdResource("Welle, Mirko", "cn=Mirko Welle,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thorat, Mo", "cn=Mo Thorat,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thor, Moni", "cn=Moni Thor,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kinolta, Monika", "cn=Monika Kinolta,ou=people,o=TaskanaTest"),
        new AccessIdResource("Mundhaar, Monika", "cn=Monika Mundhaar,ou=people,o=TaskanaTest"),
        new AccessIdResource("Munter, Monika", "cn=Monika Munter,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zwerg, Nat", "cn=Nat Zwerg,ou=people,o=TaskanaTest"),
        new AccessIdResource("Elmine, Nick", "cn=Nick Elmine,ou=people,o=TaskanaTest"),
        new AccessIdResource("Thien, Niko", "cn=Niko Thien,ou=people,o=TaskanaTest"),
        new AccessIdResource("Pferd, Nils", "cn=Nils Pferd,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lerweise, Norma", "cn=Norma Lerweise,ou=people,o=TaskanaTest"),
        new AccessIdResource("Motor, Otto", "cn=Otto Motor,ou=people,o=TaskanaTest"),
        new AccessIdResource("Totol, Otto", "cn=Otto Totol,ou=people,o=TaskanaTest"),
        new AccessIdResource("Nerr, Paula", "cn=Paula Nerr,ou=people,o=TaskanaTest"),
        new AccessIdResource("Imeter, Peer", "cn=Peer Imeter,ou=people,o=TaskanaTest"),
        new AccessIdResource("Serkatze, Peer", "cn=Peer Serkatze,ou=people,o=TaskanaTest"),
        new AccessIdResource("Gogisch, Peter", "cn=Peter Gogisch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Silje, Peter", "cn=Peter Silje,ou=people,o=TaskanaTest"),
        new AccessIdResource("Harmonie, Phil", "cn=Phil Harmonie,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ihnen, Philip", "cn=Philip Ihnen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Uto, Pia", "cn=Pia Uto,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kothek, Pina", "cn=Pina Kothek,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zar, Pit", "cn=Pit Zar,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zeih, Polly", "cn=Polly Zeih,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tswan, Puh", "cn=Puh Tswan,ou=people,o=TaskanaTest"),
        new AccessIdResource("Zufall, Rainer", "cn=Rainer Zufall,ou=people,o=TaskanaTest"),
        new AccessIdResource("Lien, Rita", "cn=Rita Lien,ou=people,o=TaskanaTest"),
        new AccessIdResource("Held, Roman", "cn=Roman Held,ou=people,o=TaskanaTest"),
        new AccessIdResource("Haar, Ross", "cn=Ross Haar,ou=people,o=TaskanaTest"),
        new AccessIdResource("Dick, Roy", "cn=Roy Dick,ou=people,o=TaskanaTest"),
        new AccessIdResource("Enplaner, Ruth", "cn=Ruth Enplaner,ou=people,o=TaskanaTest"),
        new AccessIdResource("Kommen, Ryan", "cn=Ryan Kommen,ou=people,o=TaskanaTest"),
        new AccessIdResource("Philo, Sophie", "cn=Sophie Philo,ou=people,o=TaskanaTest"),
        new AccessIdResource("Matisier, Stig", "cn=Stig Matisier,ou=people,o=TaskanaTest"),
        new AccessIdResource("Loniki, Tessa", "cn=Tessa Loniki,ou=people,o=TaskanaTest"),
        new AccessIdResource("Tralisch, Thea", "cn=Thea Tralisch,ou=people,o=TaskanaTest"),
        new AccessIdResource("Logie, Theo", "cn=Theo Logie,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ister, Thorn", "cn=Thorn Ister,ou=people,o=TaskanaTest"),
        new AccessIdResource("Buktu, Tim", "cn=Tim Buktu,ou=people,o=TaskanaTest"),
        new AccessIdResource("Ate, Tom", "cn=Tom Ate,ou=people,o=TaskanaTest"),
        new AccessIdResource("Pie, Udo", "cn=Udo Pie,ou=people,o=TaskanaTest"),
        new AccessIdResource("Aloe, Vera", "cn=Vera Aloe,ou=people,o=TaskanaTest"),
        new AccessIdResource("Hausver, Walter", "cn=Walter Hausver,ou=people,o=TaskanaTest"),
        new AccessIdResource("Schuh, Wanda", "cn=Wanda Schuh,ou=people,o=TaskanaTest"),
        new AccessIdResource("Rahm, Wolf", "cn=Wolf Rahm,ou=people,o=TaskanaTest"),
        new AccessIdResource("DevelopersGroup", "cn=DevelopersGroup,ou=groups,o=TaskanaTest"),
        new AccessIdResource("UsersGroup", "cn=UsersGroup,ou=groups,o=TaskanaTest"),
        new AccessIdResource("sachbearbeiter", "cn=sachbearbeiter,ou=groups,o=TaskanaTest"),
        new AccessIdResource("leben", "cn=leben,ou=groups,o=TaskanaTest"),
        new AccessIdResource("chirurgie", "cn=chirurgie,ou=groups,o=TaskanaTest"),
        new AccessIdResource("zahn", "cn=zahn,ou=groups,o=TaskanaTest"),
        new AccessIdResource("knie", "cn=knie,ou=groups,o=TaskanaTest"),
        new AccessIdResource("schaden", "cn=schaden,ou=groups,o=TaskanaTest"),
        new AccessIdResource("kapital", "cn=kapital,ou=groups,o=TaskanaTest"),
        new AccessIdResource("ausland", "cn=ausland,ou=groups,o=TaskanaTest"),
        new AccessIdResource("teamlead", "cn=teamlead,ou=groups,o=TaskanaTest"),
        new AccessIdResource("gesundheit", "cn=gesundheit,ou=groups,o=TaskanaTest"),
        new AccessIdResource("vip", "cn=vip,ou=groups,o=TaskanaTest"),
        new AccessIdResource("manager", "cn=manager,ou=groups,o=TaskanaTest"),
        new AccessIdResource("kfz", "cn=kfz,ou=groups,o=TaskanaTest"),
        new AccessIdResource("haftpflicht", "cn=haftpflicht,ou=groups,o=TaskanaTest"),
        new AccessIdResource("bauspar", "cn=bauspar,ou=groups,o=TaskanaTest")));

}
