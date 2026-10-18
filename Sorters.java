package Learning4AiSD.DSA;
import static java.util.Objects.isNull;

public class Sorters <T extends Comparable<T>>{

    public void validateParameters(T[] data){
        if(isNull(data)){
            throw new IllegalArgumentException("Input cant be null");
        }
    }

    public void InsertSort(T[] data){ // O(n), O(n^2),O(n^2), O(1)
        validateParameters(data);

        for(int keyIndex = 1; keyIndex < data.length; keyIndex++){
            int i = keyIndex - 1;
            T key = data[keyIndex];
            while(i >= 0 && key.compareTo(data[i]) < 0){
                data[i+1] = data[i];
                i--;
            }
            data[i+1] = key;
        }
    }
    public void MergeSort(T[] data){ // O(nlogn), pamniec O(n)
        int n = data.length;
        T[] auxData = (T[]) new Comparable[n]; // tablica pomocniczna

        int midId, rightId; // midId - koniec lewej tablicy, rightID koniec prawej tablicy;

        for(int wide = 1; wide < n; wide *= 2){ //szerokość scalanych fragmentów
            for(int left = 0; left < n; left += 2*wide){ // poczatek lewej ze scalanych tablic
                midId = left + wide -1;
                rightId = Math.min(left + 2*wide -1, n-1); // koniec prawej tabeli + uniknięcie wyjścia poza indeks

                merge(data, auxData, left, midId, rightId);
            }
        }
    }
    public void merge(T[] data, T[] auxData,int leftId, int midId, int rightId){
        System.arraycopy(data,leftId, auxData, leftId, rightId - leftId +1); //kopia danej części tablicy    
        
        int i = leftId; //aktualne id lewej tablicy
        int j = midId + 1; // -"- prawej tablicy

        for(int k = leftId; k <=rightId; k++){
            if(i > midId){ // jezeli lewa juz pusta
                data[k] = auxData[j++]; //najpierw nadaje wartosc potem j++
            }
            else if(j > rightId){ // jesli prawa pusta
                data[k] = auxData[i++];
            }
            else if(auxData[i].compareTo(auxData[j]) <= 0){ //jesli lewy mniejszy od prawego
                data[k] = auxData[i++];
            }
            else{
                data[k] = auxData[j++];
            }
        }
    }

    
}