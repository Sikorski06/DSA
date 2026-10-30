public class HashTable<T extends Comparable<T>>{
    private static final int DEFAULT_SIZE = 127; // bez parametru
    private final Elem nil = null;
    private Elem[] hashElems;
    private int nElem; //wszystkie zapisane elementy
    final int size; // ilosc komorek tablicy

    public class Elem <T extends Comparable<T>>{
        private T value;
        private Elem<T> next;

        Elem(T value, Elem<T> next){
            this.value = value;
            this.next = next;
        }
    }

    public HashTable(int size) {
        this.size = size;
        this.hashElems = new Elem[size];
    }

    public HashTable(){
        this(DEFAULT_SIZE); //Odpali aktualna klase ale z DEFAULT_SIZE
    }

    public int countHashId(T value){
        return (value.hashCode() & Integer.MAX_VALUE) % size; //Operacja AND daje gwarancje że znak jest dodatni bo pierwszy bit w int oznacza znak 0 - plus
    }

    public Elem findElem(T value,int hashId){
        Elem currElem = hashElems[hashId];
        while(currElem != nil && !currElem.value.equals(value)){
            currElem = currElem.next;
        }

        return currElem;
    }

    public void add(T value){
        int hashId = countHashId(value);
        Elem<T> elem = findElem(value, hashId);

        if(elem != nil){ //istnieje juz ta wartosc w tablicy
            elem.value = value;
        }
        else{
            hashElems[hashId] = new Elem(value, hashElems[hashId]);
            nElem++;
        }
    }
    
    public T get(T value){
        int hashId = countHashId(value);
        Elem<T> elem = findElem(value, hashId);
        return elem != nil? elem.value : null;
    }

    public void delete(T value){
        int hashId = countHashId(value);
        Elem<T> elem = hashElems[hashId];
        Elem prevElem = nil;
        while(elem != nil){
            if(elem.value.equals(value)){
                if(prevElem != nil){
                    prevElem.next = elem.next;
                }
                else{
                    hashElems[hashId] = elem.next;
                }
                nElem--;
                break;
            }
            prevElem= elem;
            elem = elem.next;
        }
    }

    public double countLoadFactor(int size, int nElem){
        return (double) nElem / size;
    }

}
