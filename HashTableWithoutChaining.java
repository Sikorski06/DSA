public abstract class HashTableWithoutChaining<T extends Comparable<T>>{

    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor;
    private boolean[] deleted;
    private int putCounter = 0;
    private int resizeCounter = 0;
    private int hashFuncCounter = 0;

    HashTableWithoutChaining() {
        this(2039); // initial size as random prime number
    }

    HashTableWithoutChaining(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = createTable(this.size);
        this.correctLoadFactor = 0.75;
        this.deleted = new boolean[size];
    }

    public void put(T newElem) {
        putCounter++;
        validateInputElem(newElem);
        resizeIfNeeded();

        int key = newElem.hashCode();
        int i = 0;
        long hashId = hashFunc(key, i);
        hashFuncCounter++;
        long firstDeletedId = -1;

        while (hashElems[(int) hashId] != nil) {
            if (hashElems[(int) hashId].equals(newElem) && !deleted[(int) hashId]) {
                return;
            }
            if (deleted[(int) hashId] && firstDeletedId == -1) {
                firstDeletedId = hashId;
            }
            if (i + 1 == size) {
                doubleResize();

                i = -1;
                firstDeletedId = -1;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            hashFuncCounter++;
        }
        if (firstDeletedId != -1) {
            hashId = firstDeletedId;
        }
        hashElems[(int) hashId] = newElem;
        deleted[(int) hashId] = false;
        nElems++;
    }
        
    public T get(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        long hashId = hashFunc(key, i);
        hashFuncCounter++;
        long startId = hashId;

        while (hashElems[(int) hashId] != nil) {
            if (hashElems[(int) hashId].equals(elem) && !deleted[(int) hashId]) {
                return hashElems[(int) hashId];
        } 
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            hashFuncCounter++; 
        
        if (hashId == startId) {
            break;
        }
        }
        return nil;
    }

    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        long hashId = hashFunc(key, i);
        hashFuncCounter++;
        long startId = hashId;

        while (hashElems[(int) hashId] != nil) {
            if (hashElems[(int) hashId].equals(elem) && !deleted[(int) hashId]) {
                deleted[(int) hashId] = true;
                nElems--;
                break;
            } 
            i = (i + 1) % size;
            hashId = hashFunc(key, i); 
            hashFuncCounter++;
            if (hashId == startId) {
                return;
            }
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract long hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private T[] createTable(int size) {
        return (T[]) new Comparable[size];
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        resizeCounter++;
        this.size *= 2;

        T[] oldElems = hashElems;
        boolean[] oldDel = deleted;
        hashElems = createTable(size);
        deleted = new boolean[size];
        nElems = 0;

        T currentElem;
        for (int i = 0; i < oldElems.length; i++) {
            currentElem = oldElems[i];
            if (currentElem != nil && !oldDel[i]) {
                put(currentElem);
            }
        }
    }
    public int getPutCounter(){
        return this.putCounter;
    }
    public int getResizeCounter(){
        return this.resizeCounter;
    }
    public int getHashFuncCounter(){
        return this.hashFuncCounter;
    }
    public double getCountLoadFactor(){
        return countLoadFactor();
    }
}
