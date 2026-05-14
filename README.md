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



-----
II OSA
-----

autorite nimed;
projekti põhjalik kirjeldus, kus on kirjas programmi eesmärk ja selgitus programmi üldisest tööst, vajadusel lühike kasutusjuhis;
iga klassi kohta eraldi selle eesmärk ja olulisemad meetodid;
projekti tegemise protsessi kirjeldus (erinevad etapid ja rühmaliikmete osalemine neis);
tehisintellekti kasutamise kirjeldus (milleks ja kuidas seda kasutati (kui seda kasutati), milliseid tööriistu kasutati ning milline osa lahendusest on tehisintellekti abil loodud);
iga rühmaliikme panus (sh tehtud klassid/meetodid) ja ajakulu (orienteeruvalt);
tegemise mured (nt millistest teadmistest/oskustest tundsite projekti tegemisel puudust);
hinnang oma töö lõpptulemusele (millega saite hästi hakkama ja mis vajab arendamist);
selgitus ja/või näited, kuidas programmi osi eraldi ja programmi tervikuna testisite ehk kuidas veendusite, et programm töötab korrektselt.


Autorid: Ralf Talts, Iris Alexandra Tikerpuu

Projekti eesmärk on kinnistada Objektorienteeritud programmeerimise kursusel omandatud teadmised viktoriini vormis. Kasutaja sisestab oma nime ning talle hakatakse tekstifailist väljastama erinevaid küsimusi koos vastusevariantidega. Programm loeb ka punkte vastavalt õigete vastuste arvule ning väljastab tulemuse mängu lõppedes ekraanile koos sisestatud vastuste ning ka õigete vastuste protsendiga.

Klass Kysimus sisaldab endas faili “kysimused.txt” küsimusi, vastusevariante ja õige vastuse indekseid ning nende get-meetodeid.

Klass Mängija käsitleb mängija infot - tema nime ja punkte. Klassis on nime ja punktide jaoks get-meetodid ja meetodid lisaPunkt() ja eemaldaPunkt().

Klass ManguLogi käsitleb logifaili, millesse salvestatakse kasutaja poolt antud vastused. Selle abil saab küsimuste vahel ka tagasi liikuda ning vastust uuesti sisestada. See sisaldab meetodeid salvesta(), mis salvestab mängija vastuse faili, onTagasivotmineVoimalik(), mis on boolean-tüüpi meetod, mis tagastab true, kui vähemalt üks vastus on sisestatud (ehk on võimalik tagasi liikuda), loeKoikKirjed(), mis loeb failist kirjed, ja kirjutaRida, mis lisab faili rea.

Klass ManguVaade käsitleb kasutaja vaadet JavaFX liidese abil. Seal on kogu info selle kohta, milline näeb välja graafiline kasutajaliides, kuhu tulevad vastusevariandid, kuhu kasutaja sisestab nime, mis on hetkene punktide seis jne. Erinevad selle klassi meetodid hõlmavad endas nt. laadijaKysimus(), mis valib küsimuse failist ja uuendab mängija vaadet, kui uus küsimus ekraanile kuvatakse, kasitleVastamine(), mis lahendab probleemi, kui kasutaja ei valinud vastust, kasitleTagasivotmine(), mis ManguLogi klassi abil võimaldab vajutada nuppu “tagasi”, kuvaLoppTulemus(), mis kuvab kasutajale mängu lõppseisu. Lisaks on ka abimeetodid, mis käsitlevad nt. tekstikasti raputamist, kui kasutaja oma nime ei sisesta.

Klass LoppVaade käsitleb seda, milline on kasutaja vaade mängu lõppedes. See näitab logifaili abil käikude ajalugu ning kuvab punktid nii arvuliselt kui ka protsentuaalselt, lisades vastava kommentaari (nt. “Harjuta edasi”).

Tehisintellekti kasutati antud projekti raames, et rakendada Java FX abil graafilist kasutajaliidest. Valdava enamuse ülejäänud metoodikast ja loogikas kirjutasid tudengid. Tehisaru aitas veenduda, et rakendus kompileerub ja oma ülesannet täidab. 

Ajakulu oli orienteeruvalt sama, mis esimeses osas (ca 6-8 tundi) ning otsest klasside tegemise jagamist ei olnud nagu esimeses osas, tegime mõlemad.

Projekti tegemise protsess kulges hästi, kuna praktikumides käsitleti paralleelselt samasid teemasid ning sai kohe õpitut projektis rakendada. Saime juba esimeses etapis aru, et soovime lahendada töö graafilise kasutajaliidesega, seega visioon asjast oli meil juba olemas. Mingisugust keerulist loogikat kasutajaliidese taga otseselt ei ole, kuna küsimuste ja vastuste kuvamine on lahendatud klassikalise valikvastuste süsteemi abil.
