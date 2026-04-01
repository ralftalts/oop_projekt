# Objektorienteeritud programmeerimise projekt kevad 2026

Autorite nimed: Iris Alexandra Tikerpuu ja Ralf Talts

Antud projekt aitab tudengitel harjutada ja kinnistada õpitud teemasid, lisades sellele mängulise aspekti.

Õpilane loob endale faili "kysimused.txt", kus on kirjas küsimused, vastused neile ja õige vastus kujul:
"Mis on pärilus?;ülemklassi omaduste kandumine alamklassile;ülemklassi konstruktori käivitamine;piiritleja määramine;objekti väljade väärtustamine;1"

Programm loeb failist küsimused sisse ja kasutades abimeetodit random valib suvalise küsimuse ja kuvab selle ekraanile.

Iga õige vastuse eest saab 1p ja kui kõigile küsimustele on vastatud, kuvab programm ekraanile, mitu punkti sa said.

Klass Mang - sisaldab endas mängu loogikat. laeFailist() loeb küsimusi failist ja loob klassi Kysimus objektid. juhuslikKysimus() tagastab juhuslikult küsimuse, mida pole juba küsitud. kontrolliVastus() kontrollib, kas vastus on õige.

Klass Mangija - see loob mängija, hoiab tema nime ja punktisummat. lisaPunkt() suurendab mängija punktiarvu ühe võrra, getPunktid() tagastab mängija hetke punktisumma, getNimi() tagastab mängija nime.

Klass Peaklass - main klass, mis seob kõik klassid ühtseks programmiks. Ta küsib mängija nime, loob seejärel klasside Mangija ja Mang objektid, küsib mängijalt küsimused ja lõpus prindib lõppskoori.

Klass Kysimus - talletab endas hetkel aktiivse küsimuse vastusevarianti ja õige vastuse indeksit. kuva() abil kuvatakse küsimus ja variandid ekraanile, onOige(int vastus) kontrollib vastuse õigsust, getOigeVastus() tagastab õige vastuse teksti.

Me otsustasime ära, millise programmi me luua tahame, arutasime läbi, mis klasse see vajaks ja seejärel jaotasime ära, kes millega tegeleb. Nädal peale seda vaatasime üle, mis me loonud olime ja redigeerimise oma koodijuppe, et need omavahel ühtiksid.

Iris lõi klassid Peaklass, Mangija ja tekstifaili, Ralf lõi klassid Mang ja Kysimus.
Orienteeruvalt võttis töö aega 6-8h.

Programmi kirjutamisel tundsime puudust sellest, et programm näeb suhteliselt algeline välja ning oleks olnud tore seda ka graafilise kasutajaliidesega lahendada, et mäng oleks kasutaja jaoks interaktiivsem ja ka visuaalselt paeluvam.

Saime hästi hakkama sellega, et tegu on programmiga, millest on õppimisel päriselt kasu ning endalgi jäid programmi katsetades vastused meelde, millest on siin kursusel ainult kasu. Arendamist vajaks nt. see, et kui mäng saab läbi, siis küsida informatsiooni kinnistamiseks uuesti valesti vastatud küsimusi (juhul, kui neid oli) nii kaua, kuni kasutaja vastab õigesti.  Lisada võiks realiseerida ka eelnevalt mainitud graafilise väljundi. Idee poolest oleks tore ka see, kui programm oleks võimeline meelde jätma eelnevalt mängitud mänge ja võrdlema tulemusi omavahel.

Programmi testimisel mängis olulist rolli see, et tegime kumbki klasse, mis üksteisest sõltuvad, seega pidime suhteliselt kiiresti oma eraldi kirjutatud klassid ühendama ning seejärel tegema vastavaid kohandusi, et need omavahel koos töötaksid. Kuna tegu ei ole iseenesest keerulise ülesehitusega programmiga, siis otseselt mingeid ületamatuid raskusi ette ei tulnud programmi töötamise osas, kuna kõik on varasemalt kursuse jooksul läbitud teemad ning abi sai ka otsida kursuse materjalidest / eelnevalt kirjutatud koodijuppidest.
