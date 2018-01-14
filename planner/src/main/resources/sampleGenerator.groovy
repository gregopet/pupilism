import java.util.concurrent.ThreadLocalRandom

// A very simple generator of sample data for pupilism

def classes = ["1A", "1B","2A", "2B","3A","4A", "4B","5A", "5B","6A","7A", "7B", "7C", "8A", "8B","9A", "9B"]
def firstNames = ["Franc", "Janez", "Ivan", "Anton", "Marko", "Andrej", "Jožef", "Jože", "Marjan", "Peter", "Luka", "Matej", "Milan", "Tomaž", "Branko", "Aleš", "Bojan", "Robert", "Rok", "Boštjan", "Stanislav", "Matjaž", "Gregor", "Miha", "Martin", "David", "Igor", "Boris", "Dejan", "Dušan", "Jan", "Alojz", "Žiga", "Nejc", "Jure", "Uroš", "Blaž", "Mitja", "Simon", "Žan", "Matic", "Klemen", "Darko", "Drago", "Jernej", "Primož", "Aleksander", "Gašper", "Miran", "Anže", "Štefan", "Roman", "Tadej", "Denis", "Aljaž", "Jaka", "Jakob", "Vladimir", "Srečko", "Nik", "Damjan", "Slavko", "Borut", "Janko", "Matija", "Mirko", "Miroslav", "Tilen", "Zoran", "Alen", "Danijel", "Domen", "Stanko", "Filip", "Mihael", "Goran", "Vid", "Alojzij", "Sašo", "Matevž", "Iztok", "Marijan", "Jurij", "Leon", "Urban", "Vinko", "Andraž", "Tim", "Mark", "Viktor", "Rudolf", "Zvonko", "Zdravko", "Benjamin", "Dragan", "Samo", "Danilo", "Pavel", "Erik", "Rajko", "Gorazd", "Maks", "Edvard", "Zlatko", "Bogdan", "Sandi", "Kristjan", "Gal", "Lovro", "Sebastjan", "Emil", "Franci", "Vojko", "Ludvik", "Josip", "Patrik", "Silvo", "Maj", "Timotej", "Anej", "Ciril", "Željko", "Damir", "Aljoša", "Damijan", "Dominik", "Albin", "Daniel", "Božidar", "Miloš", "Lan", "Frančišek", "Niko", "Nikola", "Silvester", "Leopold", "Viljem", "Stojan", "Tine", "Tomislav", "Saša", "Mario", "Davorin", "Aleks", "Aleksandar", "Karel", "Valentin", "Grega", "Vincenc", "Kristijan", "Mladen", "Vlado", "Franjo", "Davor", "Zdenko", "Marcel", "Ladislav", "Bogomir", "Sebastijan", "Jasmin", "Rene", "Ivo", "Elvis", "Bor", "Oskar", "Enej", "Karl", "Jani", "Nenad", "Stjepan", "Edin", "Rado", "Maksimiljan", "Ernest", "Valter", "Ervin", "Tian", "Izidor", "Nikolaj", "Nino", "Petar", "Sergej", "Avgust", "Metod", "Senad", "Teo", "Val", "Renato", "Radovan", "Ignac", "Mirsad", "Vito", "Bruno", "Adolf", "Slobodan", "Samir", "Bernard", "Alex", "Rudi", "Joško", "Marija", "Ana", "Maja", "Irena", "Mojca", "Mateja", "Nina", "Nataša", "Barbara", "Andreja", "Jožica", "Petra", "Jožefa", "Katja", "Anja", "Eva", "Sonja", "Tatjana", "Katarina", "Milena", "Tanja", "Sara", "Alenka", "Tina", "Ivana", "Vesna", "Martina", "Majda", "Frančiška", "Urška", "Nika", "Špela", "Terezija", "Tjaša", "Helena", "Anica", "Dragica", "Nada", "Kristina", "Darja", "Simona", "Danica", "Olga", "Marjeta", "Zdenka", "Suzana", "Angela", "Antonija", "Lidija", "Vida", "Neža", "Marta", "Ivanka", "Lara", "Sabina", "Janja", "Ema", "Veronika", "Silva", "Ljudmila", "Darinka", "Karmen", "Alojzija", "Maša", "Aleksandra", "Anita", "Stanislava", "Brigita", "Štefanija", "Metka", "Jana", "Monika", "Zala", "Cvetka", "Kaja", "Klara", "Lana", "Lucija", "Elizabeta", "Natalija", "Lea", "Nevenka", "Jasmina", "Slavica", "Marjana", "Renata", "Branka", "Tamara", "Saša", "Pavla", "Klavdija", "Vera", "Bernarda", "Manca", "Danijela", "Bojana", "Erika", "Julija", "Jasna", "Romana", "Hana", "Teja", "Rozalija", "Mira", "Polona", "Valentina", "Jelka", "Laura", "Mirjana", "Sandra", "Ajda", "Tadeja", "Valerija", "Sanja", "Maruša", "Nuša", "Ines", "Živa", "Patricija", "Mihaela", "Breda", "Ida", "Ksenija", "Karolina", "Gabrijela", "Neja", "Pia", "Vanja", "Albina", "Viktorija", "Julijana", "Vlasta", "Marjetka", "Magdalena", "Melita", "Marina", "Zoja", "Matilda", "Alja", "Ljubica", "Gordana", "Amalija", "Taja", "Marinka", "Zofija", "Nadja", "Cecilija", "Marica", "Polonca", "Ela", "Karin", "Urša", "Emilija", "Tea", "Nastja", "Mia", "Brina", "Damjana", "Tinkara", "Larisa", "Milka", "Doroteja", "Justina", "Jerneja", "Gaja", "Milica", "Marijana", "Vita", "Nives", "Jelena", "Lina", "Štefka", "Tia", "Rebeka", "Žana", "Dušanka", "Slavka", "Iva", "Andrejka", "Stanka", "Marjanca", "Lilijana", "Mirjam", "Irma", "Ana Marija", "Zlatka", "Miroslava", "Iris", "Zvonka", "Jolanda", "Daša", "Ula", "Ivica", "Blanka", "Anamarija", "Erna", "Liljana", "Meta", "Alma", "Zora"]
def lastNames = ["Novak", "Horvat", "Kovačič", "Krajnc", "Zupančič", "Potočnik", "Kovač", "Mlakar", "Kos", "Vidmar", "Golob", "Turk", "Božič", "Kralj", "Korošec", "Bizjak", "Zupan", "Hribar", "Kotnik", "Kavčič", "Rozman", "Kastelic", "Oblak", "Žagar", "Petek", "Hočevar", "Kolar", "Košir", "Koren", "Klemenčič", "Zajc", "Knez", "Medved", "Petrič", "Zupanc", "Pirc", "Hrovat", "Pavlič", "Kuhar", "Lah", "Uršič", "Zorko", "Tomažič", "Erjavec", "Babič", "Sever", "Jereb", "Jerman", "Majcen", "Pušnik", "Kranjc", "Breznik", "Rupnik", "Lesjak", "Perko", "Furlan", "Pečnik", "Dolenc", "Močnik", "Kovačević", "Vidic", "Pavlin", "Logar", "Jenko", "Ribič", "Žnidaršič", "Tomšič", "Janežič", "Jelen", "Marolt", "Blatnik", "Maček", "Petrović", "Pintar", "Dolinar", "Černe", "Gregorič", "Mihelič", "Kokalj", "Lešnik", "Cerar", "Zadravec", "Hren", "Fras", "Leban", "Bezjak", "Čeh", "Jug", "Kocjančič", "Rus", "Vidovič", "Kobal", "Kolenc", "Bogataj", "Primožič", "Lavrič", "Kolarič", "Lazar", "Kodrič", "Mrak", "Hodžić", "Jovanović", "Nemec", "Kosi", "Tavčar", "Debeljak", "Žižek", "Ivančič", "Miklavčič", "Žibert", "Marković", "Krivec", "Jarc", "Zver", "Vovk", "Likar", "Vodopivec", "Hribernik", "Kramberger", "Toplak", "Gorenc", "Leskovar", "Skok", "Jazbec", "Stopar", "Železnik", "Meglič", "Sitar", "Simonič", "Ilić", "Šinkovec", "Eržen", "Blažič", "Demšar", "Petrovič", "Popović", "Ramšak", "Javornik", "Jamnik", "Hozjan", "Nikolić", "Filipič", "Kočevar", "Bregar", "Čuk", "Gorjup", "Podgoršek", "Pintarič", "Volk", "Koželj", "Kramar", "Bukovec", "Kokol", "Sušnik", "Gajšek", "Rajh", "Rutar", "Pogačnik", "Godec", "Resnik", "Savić", "Šmid", "Rožman", "Hafner", "Lebar", "Rožič", "Mohorič", "Bergant", "Mavrič", "Kumer", "Gomboc", "Povše", "Gashi", "Zemljič", "Mlinar", "Bajc", "Ambrožič", "Bevc", "Tratnik", "Zakrajšek", "Kristan", "Pavlović", "Cvetko", "Zorman", "Kalan", "Markovič", "Babić", "Mlinarič", "Pogačar", "Begić", "Zalokar", "Jerič", "Kaučič", "Trček", "Gorišek", "Zalar", "Humar", "Založnik", "Škof", "Štrukelj"]
def activities = ["Judo 1", "Judo 2", "Gimnastika 1", "Gimnastika 2", "Šah", "Nogomet 1", "Nogomet 2", "Košarka", "Lego", "Računalništvo", "Planinski", "Angleščina", "Knjižnjičarski", "Novinarski", "Zgodovinarski", "Kemijski", "Igrajmo se znanost", "Pevski zbor najmlajši", "Pevski zbor mlajši", "Pevski zbor starejši", "Plesne urice"]
def days = ["Ponedeljek", "Torek", "Sreda", "Četrtek", "Petek"]
def periods = [ [13, 10, 14, 0], [14, 10, 15, 0], [15, 10, 16, 0], [16, 10, 17, 0] ]

def randomDays(days, int from, int to) {
    def dayCopy = new ArrayList(days)
    Collections.shuffle(dayCopy)
    dayCopy[from..to]
}

def random(Collection list) {
    list[ThreadLocalRandom.current().nextInt(list.size())]
}

def getRandomLeaveTime() {
    "${ThreadLocalRandom.current().nextInt(12, 18)}:${ThreadLocalRandom.current().nextInt(0, 60).toString().padLeft(2, "0")}"
}

def getRandomTimeSlot(days) {
    """
- day: ${random(days)}
  from: ${}
"""
}

def period(p) {
"""\
from: ${p[0]}:${p[1].toString().padLeft(2, "0")}
  to: ${p[2]}:${p[3].toString().padLeft(2, "0")}"""
}

def dayPeriod(d, p, indent) {
"""\
day: $d
${indent}from: ${p[0]}:${p[1].toString().padLeft(2, "0")}
${indent}to: ${p[2]}:${p[3].toString().padLeft(2, "0")}"""
}

def out = new StringBuilder()

// classes
out << "classes:" << "\n"
classes.each {
    out << "- " << it << "\n"
}

// times
out << """\
times: 
- ${periods.collect {period(it)}.join("\n- ")}
"""

// activities
out << "activities:" << "\n"
activities.each {
    out << """\
- id: $it
  timeSlots:
  - ${randomDays(days, 1, ThreadLocalRandom.current().nextInt(3)).collect { day -> dayPeriod(day, random(periods), "    ") }.join("\n  - ") }
"""
}

// pupils
out << "pupils:" << "\n"
classes.each { klass ->
    ThreadLocalRandom.current().nextInt(25, 32).times {
        out << """\
- name: ${random(lastNames)} ${random(firstNames)}
  klass: $klass
  activities:
    - ${random(activities)}
  leaveTimes:
    Ponedeljek:
    Torek: $randomLeaveTime
    Sreda: $randomLeaveTime
    Četrtek: $randomLeaveTime
    Petek: $randomLeaveTime
"""
    }
}


println out


