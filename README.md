# Objektorienteeritud programmeerimise projekt kevad 2026

Autorite nimed: Iris Alexandra Tikerpuu ja Ralf Talts

Antud projekt aitab tudengitel harjutada ja kinnistada õpitud teemasid, lisades sellele mängulise aspekti.

Õpilane loob endale faili "kysimused.txt", kus on kirjas küsimused, vastused neile ja õige vastus kujul:
"Mis on pärilus?;ülemklassi omaduste kandumine alamklassile;ülemklassi konstruktori käivitamine;piiritleja määramine;objekti väljade väärtustamine;1"

Programm loeb failist küsimused sisse ja kasutades abimeetodit random valib suvalise küsimuse ja kuvab selle ekraanile.

Iga õige vastuse eest saab 1p ja kui kõigile küsimustele on vastatud, kuvab programm ekraanile, mitu punkti sa said.

Klass Mang - sisaldab endas mängu loogikat. laeFailist() loeb küsimusi failist ja loob klassi Kysimus objektid. juhuslikKysimus() tagastab juhuslikult küsimuse, mida pole juba küsitud. kontrolliVastys() kontrollib, kas vastus on õige.

Klass Mangija - see loob mängija, hoiab tema nime ja punktisummat. lisaPunkt() suurendab mängija punktiarvu ühe võrra, getPunktid() tagastab mängija hetke punktisumma, getNimi() tagastab mängija nime.

Klass Peaklass - main klass, mis seob kõik klassid ühtseks programmiks. Ta küsib mängija nime, loob seejärel klasside Mangija ja Mang objektid, küsib mängijalt küsimused ja lõpus prindib lõppskoori.

Klass Kysimus - talletab endas hetkel aktiivse küsimuse vastusevarianti ja õige vastuse indeksit. kuva() abil kuvatakse küsimus ja variandid ekraanile, onOige(int vastus) kontrollib vastuse õigsust, getOigeVastus() tagastab õige vastuse teksti.

iga klassi kohta eraldi selle eesmärk ja olulisemad meetodid;

projekti tegemise protsessi kirjeldus (erinevad etapid ja rühmaliikmete osalemine neis);
iga rühmaliikme panus (sh tehtud klassid/meetodid) ja ajakulu (orienteeruvalt);


tegemise mured (nt millistest teadmistest/oskustest tundsite projekti tegemisel puudust);
hinnang oma töö lõpptulemusele (millega saite hästi hakkama ja mis vajab arendamist);
selgitus ja/või näited, kuidas programmi osi eraldi ja programmi tervikuna testisite ehk kuidas veendusite, et programm töötab korrektselt.
