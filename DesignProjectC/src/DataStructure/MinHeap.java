package DataStructure;

public class MinHeap {
   private Researcher[] Heap;
   private int size;
   private int maxsize;

   private static final int FRONT = 1;

   public MinHeap(int maxsize) {
      this.maxsize = maxsize;
      this.size = 0;
      Heap = new Researcher[this.maxsize + 1];
      Researcher r = new Researcher();
      Heap[0] = r;   
   }

   /**
    * @param pos
    */
   public int parent(int pos) {
      return pos / 2;
   }
   
   /**
    * 
    * @param pos
    */
   public int leftChild(int pos) {
      return (2 * pos);
   }

   /**
    * @param pos
    */
   public int rightChild(int pos) {
      return (2 * pos) + 1;
   }

   /**
    * @param pos
    */
   public boolean isLeaf(int pos) {
      if (pos > (size / 2) && pos <= size) {
         return true;
      }
      return false;
   }
   
   /**
    * @param spos,spos
    */
   public void swap(int fpos, int spos) {
      Researcher tmp = new Researcher();
      tmp = Heap[fpos];
      Heap[fpos] = Heap[spos];
      Heap[spos] = tmp;
   }

   /**
    * @param pos
    */
   public void minHeapify(int pos) {
      if (!isLeaf(pos)) {
         if (Heap[pos].getresearchCnt() > Heap[leftChild(pos)].getresearchCnt()
               || Heap[pos].getresearchCnt() > Heap[rightChild(pos)].getresearchCnt()) {
            if (Heap[leftChild(pos)].getresearchCnt() < Heap[rightChild(pos)].getresearchCnt()) {
               swap(pos, leftChild(pos));
               minHeapify(leftChild(pos));
            } else {
               swap(pos, rightChild(pos));
               minHeapify(rightChild(pos));
            }
         }
      }
   }

   /**
    * @param element
    */
   public void insert(Researcher element) {
      Heap[++size] = element;
      int current = size;

      while (Heap[current].getresearchCnt() < Heap[parent(current)].getresearchCnt()) {
         swap(current, parent(current));
         current = parent(current);
      }
   }

   /**
    */
   public void print() {
      System.out.println("Root Node: "+ Heap[FRONT].getname());
      for (int i = 1; i <= size / 2; i++) {
         if((2*i+1) <=size){
         System.out.print(
               " PARENT Node: " + Heap[i].getname()
               + "    LEFT CHILD Node: " + Heap[2 * i].getname()
               + "    RIGHT CHILD Node:" + Heap[2 * i + 1].getname());
         }else{
            System.out.println(
                  " PARENT Node: " + Heap[i].getname()
                  + "    LEFT CHILD Node: " + Heap[2 * i].getname() 
                  + "    RIGHT CHILD Node: None");
         }
         System.out.println();
      }
   }

   public void minHeap() {
      for (int pos = (size / 2); pos >= 1; pos--) {
         minHeapify(pos);
      }
   }

   /**
    */
   public Researcher remove() {
      Researcher pop = Heap[FRONT];
      Heap[FRONT] = Heap[size--];
      minHeapify(FRONT);
      return pop;
   }
   
   /**
    * @param data
    */
   public void replace(Researcher data){
      Heap[FRONT] = data;
      minHeapify(FRONT);
   }
   
   /**
    */
   public Researcher getMin(){
      return Heap[FRONT];
   }
   
   public void printArray(){
      for(int i = 1; i <=this.maxsize; i++){
         System.out.print(this.Heap[i].getname()+" ");
      }
   }
   public Researcher[] getHeapArray()
   {
      return Heap;
   }
}