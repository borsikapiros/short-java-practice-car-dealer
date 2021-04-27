BackStory:
Egy autókereskedésben eddig excel-ben kezelték az adatokat, de a tulaj rájön, hogy hosszú távon ez nem szerencsés.
csv-be exportálja az excel tábláját majd téged kér fel, hogy készíts neki egy programot ami adatbázisba importálja a csv-t.
Illetve arra is megkér, hogy néhány lekérdezést is el lehessen végezni adatbázisból. Az autókereskedő cégnek van kereskedése több városban is. A kereskedések a városuk alapján vannak elnevezve.

információk:
A tábla azt tartalmazza, hogy egyes városokban egyes autókból mennyit adtak el.
tábla neve:
sold_car
tábla oszlopai:
id bigint auto_increment
car_type VARCHAR(255)
dealer VARCHAR(255)
num_of_sold INT
csv felépítése:
autó típus,kereskedés,eladott autó darabszám
A testfile-ok között találsz rá példát src/test/testfiles mappában.

A feladatokon sorba haladj mert a testek is az importCSV-vel töltik fel a db-t.

feladatok:
- Implementáld a importCSV(String fileName) metódust. A metódus feladata, hogy a fileName paraméterben megadott csv-ben lévő adatokat betöltse a db-be.
- Implementáld a List<String> dealersWithMoreThanSpecifiedNumOfCarTypeSold(int numOfCarTypes) metódust. A metódus adja vissza azoknak a kereskedéseknek a listáját ahol paraméterben megadott számnál többféle autó típust adtak el. A vissza adott lista legyen ABC szerint csökkenő sorrendbe rendezve.
- Implementáld a String dealerWithMostCarSoldFromSpecificType(String carType) metódust. A metódus adja vissza annak a kereskedésnek a nevét ahol a paraméterben megadott autóból a legtöbbet adtak el az összes kereskedés közül.
