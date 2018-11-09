
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    private IList<String> test;

    //før hver test kjører dette
    @BeforeEach
    void setUp() {
        test = new LinkedList<>();
    }

    //etter hver test kjører dette
    @AfterEach
    void tearDown() {
        test.clear();

    }


    @Test
    void addputrestremovefirstempty() {

        //tester for at true returneres når en string legges til vha. add metoden.
        assertEquals(0, test.size());
        assertEquals(true, test.add("haii"));
        assertEquals(1, test.size());
        test.clear();


        //tester for at true returneres når en string legges til vha. put metoden.
        assertEquals(true, test.put("jas"));
        assertEquals(1, test.size());
        test.clear();

        //tester for at nosuchelementexception blir kastet når en henter første element i tom liste
        NoSuchElementException unntak = assertThrows(NoSuchElementException.class, () -> {
            test.first();
        });
        assertEquals(0, test.size());

        //tester at riktig feilmelding kommer
        assertEquals("Ingen element her", unntak.getMessage());

        //tester at remove kaster nosuchelementexception når det er ingen elementer å fjerne
        NoSuchElementException unntak2 = assertThrows(NoSuchElementException.class, () -> {
            test.remove();
        });

        //tester at riktig feilmelding kommer
        assertEquals("Ingen element å slette", unntak2.getMessage());


    }


    @Test
    void addputremovefirstone() {
        //Et element er i listen alltid. Testen sjekker at en kan fjerne det, legge til et nytt element på slutten
        // og begynnelsen og at en kan se det riktige første elementet. Og sjekker at ikke det er noen andre elementer
        //som kommer opp ved å kjøre rest() metoden.

        test.put("ja");
        assertEquals("ja", test.remove());
        test.put("ja");
        assertEquals(true, test.put("jas"));
        assertEquals("jas", test.first());
        assertEquals(2, test.size());
        assertEquals("jas", test.remove());
        assertEquals(1, test.size());
        assertEquals("ja", test.remove());
        assertThrows(NoSuchElementException.class, () -> {
            test.remove();});


        test.clear();
        test.add("ja");
        assertEquals(true, test.add("hai2"));

    }

    @Test
    void restempty() {
        //siden det er ingen element bør størrelsen på rest være 0.
        assertEquals(0, test.rest().size());
    }

    @Test
    void restone() {
        test.add("ja");
        //siden det er et element bør listen -1 være null.
        assertEquals(0, test.rest().size());
        test.add("ja1");
        assertEquals("ja1", test.rest().first());
    }

    @Test
    void addputrestremovemore() {

        //precondition
        test.add("hai2");
        test.add("hai2");
        test.add("hai3");
        test.add("hai2");
        test.put("ja99");
        test.put("ja88");
        test.put("ja77");
        test.put("ja66");

        //sjekker at første element som blir fjernet er ja66.
        assertEquals("ja66", test.remove());
        //sjekker at første elementet i listen er ja77 etter at ja66 er slettet
        assertEquals("ja77", test.first());
        //sjekker rest når det er mange objekter. Sjekker også at add og put faktisk har lagt til elementene til å begynne med.

        IList<String> resten = test.rest();
        assertEquals(7, test.size());
        assertEquals(6, resten.size());
        assertEquals(true, resten.contains("ja88"));
        assertEquals(true, resten.contains("hai3"));
        assertEquals(false, resten.contains("hai4"));
        //sjekker at test og add metodene fungerer.
        assertEquals(true, test.put("jaas"));
        assertEquals(true, test.add("hai2"));
    }

    @Test
    void firstmore() {

        test.put("ja77");
        test.put("ja66");
        //sjekker at rest metoden ikke viser at den er tom.
        assertEquals(false, test.rest().isEmpty());
        //sjekker at rest metoden gir 1 i size. Fordi 2 minus første element er 1.
        assertEquals(1, test.rest().size());

    }

    @Test
    void removeObject() {
        test.add("hai1");
        test.add("hai3");
        test.add("hai2");

        test.put("hai3");
        test.put("ja99");
        test.put("ja88");
        //tester at remove() sletter og returnerer true når den finner riktige elementet i listen.
        // tester også at remove() returnerer false når den prøver å slette string elementer som ikke er i listen
        assertEquals(true, test.remove("ja99"));
        assertEquals(false, test.remove("ja77"));
        assertEquals(true, test.remove("hai2"));
        assertEquals(true, test.remove("hai1"));
        assertEquals(false, test.remove("hai2"));
        test.clear();
        assertEquals(false, test.remove("something"));

        //tester at remove() også fungerer på integers og at elementene faktisk blir fjernet.
        LinkedList<Integer> tall = new LinkedList<>();
        tall.put(2);
        assertEquals(true, tall.remove(2));
        assertEquals(false, tall.remove(2));
    }

    @Test
    void contains() {
        //denne testmetoden sjekker at den finner elementet vha. contains både når det er brukt put og add metoden.
        //sjekker også at den gir false når den søker etter et element som ikke finnes.
        test.add("hai2");

        test.put("hai3");
        assertEquals(true, test.contains("hai3"));
        assertEquals(true, test.contains("hai2"));
        assertEquals(false, test.contains("ingenting her"));
    }

    @Test
    void sizeMe() {
        //sjekker at size gir riktig størrelse uavhengig av om put, add, remove blir brukt før testen.
        assertEquals(0, test.size());
        test.add("hai2");

        test.put("hai3");
        assertEquals(2, test.size());
        test.remove();
        assertEquals(1, test.size());
        test.put("jh");
        assertEquals(2, test.size());
    }

    @Test
    void emptyMe() {
        //sjekker at isempty() gir true når den er tom og false ellers, gitt ulike scenarioer.
        assertEquals(true, test.isEmpty());
        assertEquals(true, test.isEmpty());
        test.add("hai2");

        assertEquals(false, test.isEmpty());
        test.put("hai3");
        test.remove();
        assertEquals(false, test.isEmpty());

        test.put("hai3");
        assertEquals(false, test.isEmpty());
    }

    @Test
    void konstrukt() {
        try {
            //sjekker at konstruktørene fungerer som de skal ved å sammenligne første element med forventet innhold.
            LinkedList<String> a = new LinkedList<>();
            a.put("aa");
            LinkedList<String> b = new LinkedList<>("bb");
            LinkedList<String> c = new LinkedList<>("cc", b);
            assertEquals(c.first(), "cc");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void appenden() {
        IList<String> apppp3 = new LinkedList<String>();


        //sjekker å appende tom liste
        IList<String> apppp87 = new LinkedList<String>();
        test.append(apppp87);
        assertThrows(NoSuchElementException.class, () -> {
            test.first();});
        assertEquals(0, test.size());
//        Etter å ha lagd en ny liste app, og fylt den med data, legges den til i test. Så sjekkes at elementene fins i begge lister.
        // sjekkes også at første element ikke er det samme i begge listene. Dette fordi append skal legge til på slutten av andre listen, ikke til å begynne med.
        test.add("uu");
        apppp3.add("jj");
        apppp3.put("aa");

        test.append(apppp3);
        assertEquals(test.contains("jj"), apppp3.contains("jj"));
        assertNotEquals(test.first(), apppp3.first());
        System.out.print(test.first() + apppp3.first());
        apppp3.clear();
        //sjekker at programmet ikke klikker ved å legge til en tom liste.
        IList<String> apppp4 = new LinkedList<String>();
        test.append(apppp4);


    }


    @Test
    void prependen() {
        IList<String> apppp3 = new LinkedList<String>();
        IList<String> apppp45 = new LinkedList<String>();
        test.prepend(apppp45);
        assertThrows(NoSuchElementException.class, () -> {
            test.first();});
        assertEquals(0, test.size());
//        Etter å ha lagd en ny liste app, og fylt den med data, legges den til i test. Så sjekkes at elementene fins i begge lister.
        // sjekkes også at første element er det samme i begge listene. Dette fordi prepend skal legge til på begynnelsen av andre listen.
        test.put("uu");
        apppp3.put("00");
        apppp3.put("jj");
        apppp3.put("aa");

        test.prepend(apppp3);
        assertEquals(test.contains("jj"), apppp3.contains("jj"));
        assertEquals(test.first(), apppp3.first());

    }


    @Test
    void concaten() {
        //setter opp 3 linked list med data
        IList<String> app = new LinkedList<String>();
        IList<String> app1 = new LinkedList<String>();
        IList<String> app2 = new LinkedList<String>();
        IList<String> app3 = new LinkedList<String>();

        app.add("jj");
        app1.put("aa");
        app2.add("dd");
        //adder alle 3 til test vha. concat metoden. Sjekker så at test inneholder objektene i disse listene og at den ikke inneholder et vilkårlig object.

        IList<String> vara = test.concat(app, app1, app2);
        assertEquals(true, vara.contains("jj"));
        assertEquals(true, vara.contains("aa"));
        assertEquals(true, vara.contains("dd"));
        assertEquals(false, vara.contains("dsd"));
        assertEquals(3, vara.size());
        test.clear();
        //Sjekker resultat når en liste er tom.
        IList<String> varb = test.concat(app, app1, app2, app3);
        assertEquals(true, varb.contains("jj"));
        assertEquals(true, varb.contains("aa"));
        assertEquals(true, varb.contains("dd"));
        assertEquals(false, varb.contains("dsd"));
        assertEquals(3, varb.size());

        //sjekker når test allerede har elementer
        test.add("hhjhjhj");
        test.add("ok");
        test.add("ok2");
        IList<String> varc = test.concat(app, app1, app2, app3);
        assertEquals(true, varc.contains("jj"));
        assertEquals(true, varc.contains("aa"));
        assertEquals(true, varc.contains("dd"));
        assertTrue(varc.contains("hhjhjhj"));
        assertEquals(false, varc.contains("dsd"));
        assertEquals(6, varc.size());
    }

    @Test
    void iteratoren() {
        LinkedList<Integer> tall2 = new LinkedList<Integer>();

        tall2.add(2);
        tall2.add(3);
        tall2.add(4);

        tall2.put(1);

        Iterator it = tall2.iterator();
        // test av hasNext
        LinkedList<String> str = new LinkedList<>();
        Iterator itstr = str.iterator();
        assertFalse(itstr.hasNext());
        assertTrue(it.hasNext());
        // Etter å ha lagd en linkedlist med tall blir iterator metoden til linkedlist instansiated
        //Så lenge det fins et element til blir next verdien sammenlignet med tall verdien 1 som blir økt med 1 hver gang loopen kjører.
        //derfor forventes det at tall og it.next har verdiene 1, 2, 3, 4 etterhvert som loopen kjører.
        int tall = 1;
        while (it.hasNext()) {
            assertEquals(tall, it.next());
            tall++;
        }
        //Sjekker at NoSuchElementException blir kastet når det hasNext er false.
        Iterator tekst = test.iterator();
        assertThrows(NoSuchElementException.class, tekst::next);
//        //sjekker at remove kaster UnsupportedOperationException.
//        assertThrows(UnsupportedOperationException.class, tekst::remove);
//        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    @Test @SuppressWarnings("unchecked")
    void removeIterator(){ //sjekker at remove funksjonen på iterator fungerer ved å fjerne tallet 3 og så sjekke at listen ikke inneholder 3.
        LinkedList listens = new LinkedList();
        listens.add(2);
        listens.add(3);
        listens.add(4);


        Iterator itse = listens.iterator();

        while(itse.hasNext()){
            if(itse.next().equals(3)){
                itse.remove();
            }
        }
        if (listens.contains(3)) {
            fail("Tallet 3 ble ikke fjernet");
        }


    }

    @Test
    //sjekker om sort funksjonen fungerer ved å lage en løkke som produserer 1,2,3,4,5 og sammenligne det med output
    void testSort(){
        LinkedList<Integer> tall3 = new LinkedList<Integer>();
        tall3.put(2);
        tall3.put(5);
        tall3.put(1);
        tall3.put(3);
        tall3.put(4);
        tall3.sort(Comparator.naturalOrder());
        Iterator it = tall3.iterator();

        for (Integer num = 1; num <= 5 ; num++) {
            assertEquals(num, tall3.remove());
        }

    }




    @Test //test that filter will filter String aswell.
    void tfilter() {

        List<String> values = Arrays.asList("a","b","c","d");

        IList<String> list = new LinkedList<>();
        for (String value : values) {
            list.add(value);
        }
        //filtrerer ut objekter som heter "b". Kjører så igjennom en løkke
       // og fjerner alle elementer, hvis remove returnerer "b" så vet vi at filter ikke har fungert og testen vil faile.
        list.filter(n -> !Objects.equals(n, "b"));

        String n = list.remove();
        while (list.size() > 0) {
            if (Objects.equals(n,"b")) {
                fail("List contains filtered out elements.");
            }
            n = list.remove();

        }
    }

    @Test //test with nothing in list. Forventer at filter ikke stanser programmet og at NoSuchElementException blir kastet siden ingen elementer eksisterer
    void noMatchFilter() {

        List<String> values = Collections.emptyList();

        IList<String> list = new LinkedList<>();
        for (String value : values) {
            list.add(value);
        }

        list.filter(n -> !Objects.equals(n, "k"));

        assertThrows(NoSuchElementException.class, list::remove);
    }

    @Test
    void EmptyMap(){
        //Sjekker at nosuchelementexception blir kastet når jeg kjører remove etter å ha mappet en tom liste som ble omgjort fra string til int.

        List<String> moldova = Collections.emptyList();
        IList<String> ukraine = new LinkedList<>();

        for (String a : moldova){
        ukraine.add(a);
        }

        IList x = ukraine.map(Integer::parseInt);
        assertThrows(NoSuchElementException.class, x::remove);

    }

    @Test
    void Emptyreduce(){
        //kjører reduce på en tom Linkedliste hvor jeg ber om at strengene i listen blir slått sammen og gjort om til en String. Sjekker så at strengen er tom.
        List<String> chisinau = Collections.emptyList();
        IList<String> kyiv = new LinkedList<>();

        for (String a : chisinau){
            kyiv.add(a);
        }

        String x = kyiv.reduce("", String::concat);
        assertTrue(x::isEmpty);
    }






    @Test
    void oppg8_sortIntegers() {
// Se oppgave 8
        IList<Integer> list = new LinkedList<>();
        List<Integer> values = Arrays.asList(3, 8, 4, 7, 10, 6,
                1, 2, 9, 5);
        for (Integer value : values) {
            list.add(value);
        }
        list.sort(Comparator.comparingInt(x -> x));
        int n = list.remove();
        while (list.size() > 0) {
            int m = list.remove();
            if (n > m) {
                fail("Integer list is not sorted.");
            }
            n = m;
        }
    }
    @Test
    void oppg8_sortStrings() {
// Se oppgave 8
        IList<String> list = new LinkedList<>();
        List<String> values = Arrays.asList(
                "g", "f", "a", "c", "b", "d", "e", "i", "j", "h"
        );
        for (String value : values) {
            list.add(value);
        }
        list.sort(Comparator.naturalOrder());
        String n = list.remove();
        while (list.size() > 0) {
            String m = list.remove();
            if (n.compareTo(m) > 0) {
                fail("String list is not sorted.");
            }
            n = m;
        }
    }
    @Test
    void oppg9_filter() {
// Se oppgave 9
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7,
                8, 9, 10);
        IList<Integer> list = new LinkedList<>();
        for (Integer value : values) {
            list.add(value);
        }
        list.filter(n -> n % 2 == 1);
        while(list.size() > 0) {
            int n = list.remove();
            if (n % 2 == 1) {
                fail("List contains filtered out elements.");
            }
        }
    }
    @Test
    void oppg10_map() {
// Se oppgave 10
        List<String> values = Arrays.asList("1", "2", "3",
                "4", "5");
        IList<String> list = new LinkedList<>();
        for (String value : values) {
            list.add(value);
        }

        IList<Integer> result = list.map(Integer::parseInt);
        List<Integer> target = Arrays.asList(1, 2, 3, 4, 5);

        for (Integer t : target) {
            if (result.remove() != t) {
                fail("Result of map gives the wrong value.");
            }
        }
    }
    @Test
    void oppg11_reduceInts() {
// Se oppgave 11
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
        IList<Integer> list = new LinkedList<>();
        for (Integer value : values) {
            list.add(value);
        }
        int result = list.reduce(0, Integer::sum);
        assertEquals(result, 5*((1 + 5)/2));
    }
    @Test
    void oppg11_reduceStrings() {
        List<String> values = Arrays.asList("e", "s", "t");
        IList<String> list = new LinkedList<>();
        for (String s : values) {
            list.add(s);
        }
        String result = list.reduce("t", (acc, s) -> acc + s);
        assertEquals(result, "test");
    }

}
