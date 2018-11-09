import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedList<E> implements IList<E> {
    private Node firstNode;
    private int numberOfEntries;
    private Node lastNode;
    private IList nyliste;

//    Lag en 2 konstruktører for initialiseringen av en ny liste. En konstruktør som tar
//    imot et nytt element som blir det første elementet i listen og en konstruktør som
//    tar et element som blir det første elementet og en liste (som potensielt kan være
//            null).


    public LinkedList(){
        firstNode = null;
        lastNode =null;
        numberOfEntries = 0;
    }

    public LinkedList(E nyelem){
        this(nyelem, null);
    }

    public LinkedList(E nyelem, IList<? extends E> nyliste){
        numberOfEntries = 1;
        firstNode = new Node(nyelem);
        lastNode = firstNode;
        this.append(nyliste);
    }

    @Override @SuppressWarnings("unchecked")

    public E first() throws NoSuchElementException {
        if (getFirstNode() == null){
            throw new NoSuchElementException("Ingen element her");
        }
        else {
            return getFirstNode().data;
        }
    }

    @Override
    public IList<E> rest() {

        Node go = firstNode;
        LinkedList<E> listen = new LinkedList<E>();
        if(!isEmpty()){
            while (go.next != null){
                go = go.next;
                listen.add(go.data);
            }
        }
        return listen;
    }


    //add at end of list
    @Override @SuppressWarnings("unchecked")
    public boolean add(E elem){
        if(!isEmpty()) {
            Node newNode = new Node(elem, null);
            lastNode.next = newNode;
            lastNode = newNode;
            numberOfEntries++;
            return true;
        }
        else {
            firstNode = new Node(elem, null);
            lastNode = firstNode;
            numberOfEntries++;
            return true;
        }
    }

    @Override
    public boolean put(E elem) {
        Node temp = new Node(elem,firstNode);
        firstNode = temp;
        lastNode = temp;
        numberOfEntries++;
        return true;
    }

    @Override
    public E remove() throws NoSuchElementException {
        E temp2;
        if (getFirstNode() == null){
            throw new NoSuchElementException("Ingen element å slette");
        }
        else {
            temp2 = getFirstNode().data;
            firstNode = firstNode.next;
            numberOfEntries--;
        }
        return temp2;
    }


    public boolean remove(Object o) {
        if(getFirstNode() == null){
            return false;
        }
        if(firstNode.data.equals(o)) {
            firstNode = firstNode.next;
            numberOfEntries--;
            return true;
        }
        Node pointer = firstNode;
        Node minus1pointer = null;

        while (pointer !=null && !pointer.data.equals(o)){
            minus1pointer = pointer;
            pointer = pointer.next;
        }
        if(pointer == null){
            return false;
        }
        minus1pointer.next = pointer.next;
        numberOfEntries--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null))
        {
            if (o.equals(currentNode.getData()))
                found = true;
            else
                currentNode = currentNode.getNextNode();
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }


    @Override
    public void append(IList<? extends E> list) {


        if (list == null){
            System.out.println("Success, but list was null");
        }
        else{
            for(E elem : list) {
                add(elem);
            }
        }
    }
    @Override
    public void prepend(IList<? extends E> list) {
        Stack<E> stak = new Stack<>();
        if (list == null) {
            System.out.println("Success, but list was null");
        }
        else {
            for (E elem : list) {
                stak.add(elem);}
        }
        while(!stak.empty())
        {
            put(stak.pop());
        }
    }


    public IList<E> concat(IList<? extends E>... lists) {
        LinkedList<E> result = new LinkedList<E>();

        for (E elem2: this){
            result.add(elem2);
        }
        for(IList<? extends E> list : lists) {
            for(E elem : list){
                result.add(elem);
            }

        }

        return result;
    }
















    //
//    private int mergeSort(Comparator<? super E> comp){
//
//        E a=this.getFirstNode().getData();
//        Node b = this.getFirstNode().getNextNode();
//        E c = b.getData();
//
//        if (comp.compare(a,c) > 0){
//            int middle = comp.compare(a,c)/2;
//            mergeSort(a, middle);
//            mergeSort(middle+1, b);
//            merge(a, middle, b);
//        }
//        return mergeSort();
//
//    }
    private static Comparable[] merge(Comparable[] arr1, Comparable[] arr2){
        int c1=0;
        int c2=0;
        Comparable[] result = new Comparable[arr1.length+arr2.length];
        int nextFree=0;
        // iteratively select the lowest value from either array:
        while(c1 < arr1.length && c2 < arr2.length){
            if(arr1[c1].compareTo(arr2[c2]) < 0){
                result[nextFree]=arr1[c1];
                c1++;
            }else{
                result[nextFree]=arr2[c2];
                c2++;
            }
            nextFree++;
        }
        // copy rest (arrays might be different size)
        while(c1 < arr1.length){
            result[nextFree]=arr1[c1];
            c1++;
            nextFree++;
        }
        while(c2 < arr2.length){
            result[nextFree]=arr2[c2];
            c2++;
            nextFree++;
        }
        return result;



        //       firsthalf = first;
//       lasthalf = middle;
//       firsthalf2= middle+1;
//       lasthalf2 = last;

    }

    @Override
    public void sort(Comparator<? super E> c) {

        Node denne = firstNode;
        Node hale = null;

        while (denne !=null && hale !=firstNode){
            Node neste = denne;

            for (; neste.next !=hale; neste=neste.next){
                if(c.compare(neste.data,neste.next.data)>0){
                    E temp = neste.data; //største objekt
                    neste.data = neste.next.data;
                    neste.next.data = temp;
                }
            }
            hale = neste;
            denne = firstNode;

        }



    }

//
//
//
//        LinkedList<E> so = new LinkedList();
//        Iterator it = iterator();
//        while (it.hasNext()) {
//
//            Object a = it.next();
//
//            Collections.sort(this, (Comparator<E>) (lhs, rhs) -> {
//                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
//                return lhs. > rhs.customInt ? -1 : (lhs.customInt < rhs.customInt) ? 1 : 0;
//            });

//        void mergeSort ( int a[], int low, int high){
//            if (low < high) { // base case: low >= high (0 or 1 item)
//                int mid = (low + high) / 2;
//                mergeSort(a, low, mid); // divide into two halves
//                mergeSort(a, mid + 1, high); // then recursively sort them
//                merge(a, low, mid, high); // conquer: the merge routine
//            }
//        }


    @Override
    public void filter(Predicate<? super E> filter) {
        Iterator<E> itme = this.iterator();
        while(itme.hasNext()){
            if (filter.test(itme.next())){
                itme.remove();
            }
        }
    }

    @Override @SuppressWarnings("unchecked")
    public <U> IList<U> map(Function<? super E, ? extends U> f) {
        Iterator<E> itmap = this.iterator();
        IList<E> limap = new LinkedList<>();
        while(itmap.hasNext()){ limap.add((E) f.apply(itmap.next())); }
        return (IList < U>) limap;
    }

    @Override
    public <T> T reduce(T t, BiFunction<T, ? super E, T> f) {
        Iterator<E> itreduce = this.iterator();
        T te = t;
        while(itreduce.hasNext()){
            te = f.apply(te, itreduce.next());
        }
        return te;
    }

    @Override
    public int size() {

        return numberOfEntries;
    }

    @Override
    public void clear() {

        firstNode=null;
        numberOfEntries=0;
        lastNode =null;

    }

    @Override
    public Iterator<E> iterator() {


        Iterator<E> nameIterator = new Iterator<E>() {
            private Node currentNode;
            Node nextNode = firstNode;
            boolean calledNext = false;

            @Override
            public boolean hasNext() {
                return nextNode !=null;
            }

            @Override
            public E next() {
                calledNext = true;
                if(hasNext()){
                    currentNode = nextNode;
                    nextNode = nextNode.getNextNode();
                    return currentNode.data;
                }
                else
                    throw new NoSuchElementException("Make sure hasNext is true before running next");
            }

            @Override
            public void remove() {
                if (!calledNext){
                    throw new IllegalStateException();
                }
                LinkedList.this.remove(currentNode.data);
            }
        };
        return nameIterator;
    }





    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }
    public int getNumberOfEntries() {
        return numberOfEntries;
    }
    public Node getLastNode() {return lastNode; }
    public Node getFirstNode() { return firstNode;}
    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }
    public void setLastNode(Node newNode) {
        lastNode = newNode;
    }

    public class Node {
        private E data;
        private Node next;
        private Node(E dataPortion) {
            this(dataPortion, null);
        }

        public Node(E dataPortion, Node nextNode) {
            this.data = dataPortion;
            this.next = nextNode;
        }

        public E getData() {
            return data;
        }
        public void setData(E data) {
            this.data = data;
        }
        public Node getNextNode() {
            return next;
        }
        public void setNext(Node next) {
            this.next = next;
        }
    }
}
