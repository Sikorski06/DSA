package Learning4AiSD.DSA;

import java.util.ArrayList;
import java.util.List;
public class Sorters <T extends Comparable<T>>{

    public void validateParameters(T[] data){
        if(data == null){
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
    public void QuickSortHoare(T[] data){
        List<Integer> starts = new ArrayList<>(); // lista z poczatkami podtablic
        List<Integer> ends = new ArrayList<>(); // lista z koncami podtablic

        int left = 0;
        int right = data.length -1;

        starts.add(left);
        ends.add(right);    //dodanie calej tablicy do sortowania
        int n = 1; // licznik podproblemów - podtablic

        int pivot;

        if (left < right){
            while(n > 0){ // dopóki są podproblemy
                n--;
                left = starts.remove(n); // bierzemy kolejną podtablice ze stosu
                right = ends.remove(n);
                pivot = partition(data,left, right);

                if(left < pivot){   // jezeli sa w ogole liczby mniejsze od pivota to jest to kolejny podproblem
                    starts.add(left);
                    ends.add(pivot);
                    n++;
                }
                if(pivot+1 < right){ // jezeli sa w ogole liczby wieksze od pivota to jest to kolejny podproblem
                    starts.add(pivot+1);
                    ends.add(right);
                    n++;
                }
            }
        }
    }

    public int partition(T[] data, int start, int end){
        T pivot = data[start]; // pivto to poczatek tablicy
        // Random random = new Random();    // random pivot
        // int pivotId = random.nextInt(end-start + 1) + start;
        // T pivot = data[pivotId];
        // int mid = (start + end) / 2;
        // T pivot = medianOfThree(data[start], data[mid], data[end]);

        int left = start -1;
        int right = end + 1;

        while (true) { 
            while(data[++left].compareTo(pivot) < 0){} // od lewej az nie napotkamy wiekszej od pivot
            while(data[--right].compareTo(pivot)> 0){} // od prawej az nie napotkamy mnijeszej od pivota
            if(left < right){ // ukladanie - po lewej mniejsze od pivot po prawej wieksze od pivot
                swap(data, left, right);
            }
            else{
                break;
            }
        }
        return right;
    }

    public void swap(T[] data, int left, int right){
        T temp = data[left];
        data[left] = data[right];
        data[right] = temp;
    }

    private T medianOfThree(T a, T b, T c) {

    if (a.compareTo(b) <= 0 && b.compareTo(c) <= 0
            || c.compareTo(b) <= 0 && b.compareTo(a) <= 0) {
        return b;
    }

    if (b.compareTo(a) <= 0 && a.compareTo(c) <= 0
            || c.compareTo(a) <= 0 && a.compareTo(b) <= 0) {
        return a;
    }

    return c;
}
public void HeapSort(T[] data) {
        buildHeap(data); // budowa pierwszego kopca

        int n = data.length;

        for (int i = n - 1; i > 0; i--) { // przechodzimy całą tablice
            swap(data, 0, i); // szczyt kopca na koniec
            heapify(data, 0, i); //naprawienie kopca
        }
    }

    private void buildHeap(T[] data) {
        int n = data.length;

        for (int i = n / 2 - 1; i >= 0; i--) { //taki zakres bo liscie nie maja dzieci wiec nie trzeba ich naprawiac    
            heapify(data, i, n);
        }
    }

    private void heapify(T[] data, int parentId, int maxId) {
        while (true) {
            int leftChildId = 2 * parentId + 1;
            int rightChildId = 2 * parentId + 2;
            int largestValId = parentId; // zakladamy ze rodzic jest najwiekszy

            if (leftChildId < maxId // sprawdzamy czy lewe dziecko jest w dalej nieposortowanej czesci tablicy
                    && data[leftChildId].compareTo(data[largestValId]) > 0) {
                largestValId = leftChildId;
            }

            if (rightChildId < maxId // sprawdzamy czy prawe dziecko jest w dalej nieposortowanej czesci tablicy
                    && data[rightChildId].compareTo(data[largestValId]) > 0) {
                largestValId = rightChildId;
            }

            if (largestValId == parentId) { // jesli wszystko jest dobrze to zamykamy
                break;
            }

            swap(data, parentId, largestValId); // w innym wypadku zamieniamy miejscami zeby wieksze bylo rodzicem

            parentId = largestValId; // schodzimy niżej bo z powodu zmiany heap mógł się zepsuć
        }
    }
    
}