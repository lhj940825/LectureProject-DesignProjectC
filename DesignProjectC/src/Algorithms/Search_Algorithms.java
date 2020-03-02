package Algorithms;

import java.util.Collections;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import DataStructure.*;

public class Search_Algorithms {
   private ArrayList<AdjacencyList.vertex> researchers;
   private AdjacencyList adjlist;
   private boolean[] isVisitedVertexs;
   ArrayList<String> namesInChain = new ArrayList<String>();
   
   
   public Search_Algorithms() {
      this.researchers = null;
      this.isVisitedVertexs = null;
      cnt=0;
   }
   public Search_Algorithms(AdjacencyList a) {
      adjlist = a;
      this.isVisitedVertexs = null;
      cnt=0;
   }
   
   private int cnt;
   public void clear_namesInChain()
   {
      namesInChain.clear();
   }
    public ArrayList<String> find_Chain_BY_Dijkstra(String start, String target)
    {
       cnt++;
        for(int i=0 ; i<namesInChain.size() ; i++)
        {
           System.out.println(" --- " + namesInChain.get(i));
        }
        
        if(start.equals(target)){
           return namesInChain;
        }
        adjlist.getvertex(start).setisVisited(true);
        namesInChain.add(start);
   
        ArrayList<Researcher> r = adjlist.getvertex(start).getResearcher().getCoauthorlist();
        for(int i=0 ; i<r.size() ; i++)
        {
           for(int k=i ; k<r.size() ; k++)
           {              
              if(adjlist.getedge(start, r.get(i).getname()).getWeight()
                    < adjlist.getedge(start, r.get(k).getname()).getWeight())
              {
                 Collections.swap(r, i, k);
              }
           }         
        }
        String max = "";
        int k=0;
        for(int i=0 ; i<r.size() ; i++)
        {
           if(adjlist.getvertex(r.get(i).getname()).getisVisited()==false)
           {
                namesInChain.add(r.get(i).getname());
                max = r.get(i).getname();
                k++;
                break;      
           }
        }
        if(k==0)
        {
           namesInChain.clear();
           return namesInChain;
        }
        return find_Chain_BY_Dijkstra(max,target);
    }
    

   public ArrayList<String> find_Chain_BY_BFS(ArrayList<AdjacencyList.vertex> researchers, String start, String target) {
      this.researchers = researchers;

      ArrayList<String> namesInChain = new ArrayList<String>(); 
      Tree treeForChain = new Tree(); 
      Queue<AdjacencyList.vertex> BFS = new ArrayDeque<AdjacencyList.vertex>();

      AdjacencyList.vertex chainStart = null; 
      for (AdjacencyList.vertex temp : this.researchers) {
         if (temp.getResearcher().getname().equals(start)) {
            chainStart = temp;
            break;
         }
      }

      if (chainStart != null) {
         isVisitedVertexs = new boolean[this.researchers.size()];
         BFS.offer(chainStart);
         // root���� ����
         treeForChain.setRoot(new TreeNode(chainStart.getResearcher().getname(), null));
                                                                     

         while (!BFS.isEmpty()) {
            AdjacencyList.vertex tempVertex = BFS.poll();
            //
            //
            String tempVertexsName = tempVertex.getResearcher().getname();
            for (int i = 0; i < this.researchers.size(); i++) {
               if (tempVertexsName.equals((this.researchers).get(i).getResearcher().getname())) {
                  isVisitedVertexs[i] = true;
                  break;
               }
            }

            if (tempVertexsName.equals(target))
               break;

            
            TreeNode node = treeForChain.searchTree(treeForChain.getRoot(),tempVertexsName);
            
            ArrayList<Researcher> dummy = tempVertex.getResearcher().getCoauthorlist();
            for (int i = 0; i < dummy.size(); i++) {

               for (AdjacencyList.vertex dum : this.researchers) {
                  if (dum.getResearcher().getname().equals(dummy.get(i).getname())) {
                     if (!isVertexValid(dum)) {
                        BFS.offer(dum);
                        //
                        //
                        node.addChilds(dum.getResearcher().getname());

                        break;
                     }
                  }
               }
            }

         }
         TreeNode targetNode = treeForChain.searchTreeByBFS(treeForChain.getRoot(), target);
         while(targetNode!=null){
            System.out.println(targetNode.getName()+"->");
            namesInChain.add(targetNode.getName());
            targetNode = targetNode.getParent();
         }
         return namesInChain;
      } else
         return null;

   }
   public boolean isVertexValid(AdjacencyList.vertex vertex) {
      int i = this.researchers.indexOf(vertex);
      return this.isVisitedVertexs[i];
   }

   public MinHeap find_Top_K_Nodes(ArrayList<String> researchers) {
      MinHeap minHeap = null;
      // Algorithms here
      return minHeap;
   }

   public MinHeap find_Top_K_Nodes(ArrayList<String> researchers, String targetName) {
      MinHeap minHeap = null;
      // Algorithms here
      return minHeap;
   }

}