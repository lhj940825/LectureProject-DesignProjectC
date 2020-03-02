package DataStructure;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyList {
   public class edge {
      int weight; // �ش� edge�� ����ġ
      vertex mainvertex = new vertex(); // �ش� edge�� ������ִ� vertex1
      vertex subvertex = new vertex(); // �ش� edge�� ������ִ� vertex2

      public edge() {
         weight = 1;
      }

      public vertex getmainvertex() {
         return mainvertex;
      }

      public vertex getsubvertex() {
         return subvertex;
      }

      public void setEdgevertexes(vertex v1, vertex v2) {
         mainvertex = v1;
         subvertex = v2;
      }

      public void changeWeight(int cnt) {
         weight += cnt;
      }
      //weight�� ��ȣ �ٲٴ� �޼ҵ�
      public void swapWeightsign()
      {
         weight = -weight;
      }

      public int getWeight() {
         return this.weight;
      }
      

      
   }
   

   public class vertex {
      ArrayList<edge> edges = new ArrayList<edge>(); // �ش� vertex�� ����� edge����
      // arraylist
      Researcher researcher = new Researcher(); // �ش� vertex�� ����Ǵ� researcher
      private int weightforChain;
      private boolean isVisited;
      
      
      // ��ü

      public vertex() {
         // none ������ �ʱ�ȭ
         researcher.setmajor("none");
         researcher.setname("none");
         isVisited = false;
         weightforChain=0;
      }
      public boolean getisVisited()
      {
         return isVisited;
      }
      public void setisVisited(boolean b)
      {
         isVisited = b;
      }
      
      public void addWeight(int weight)
      {
         weightforChain+=weight;
      }
      public int getWeight()
      {
         return weightforChain;
      }
      public void setWeight(int weight)
      {
         weightforChain=weight;
      }
      
      public int edgeSize() {
         return edges.size();
      }

      public void setvertex(Researcher r) {
         researcher = r;
      }

      public void addEdge(edge e) {
         edges.add(e);
      }

      public int getEdgeSize() {
         return edges.size();
      }

      public ArrayList<edge> getEdges() {
         return this.edges;
      }

      public Researcher getResearcher() {
         return this.researcher;
      }
/*      public edge findEdge(String name)
      {
         for(int i=0 ; i<edges.size() ; i++)
         {
            if(edges.get(i).getmainvertex().researcher.getname() == name || edges.get(i).getsubvertex().researcher.getname() == name )
            {
               return edges.get(i);
            }
         }
         //edge�� ��ã����
         System.out.println("edge��ã��");
         edge e = new edge();
         Researcher r = new Researcher();
          r.setmajor("none");
          r.setname("none");
         vertex v1 = new vertex();
         vertex v2 = new vertex();
         v1.setvertex(r);
         v2.setvertex(r);
         e.setEdgevertexes(v1, v2);
         return e;   //�ǹ̾��� edge����
      }*/
   }

   
   ArrayList<vertex> graph = new ArrayList<vertex>(); // vertex�� ������ list;
   HashMap<String, Integer> AlphabetIndex = new HashMap<>();   //�� ���ĺ����� �����ϴ� vertex�� �����ϱ����� �ε����� ������ִ�.
   
   //ArrayList �� ĭ�� ������� �� ���ĺ����� �����ϴ� ù��° ���ҿ� ������ �� �ִ� index���� hashmap�� �־��ش�.   
   public void SetAlphabetIndex()
   {
      AlphabetIndex.put("A", -1); AlphabetIndex.put("B", -1); AlphabetIndex.put("C", -1); AlphabetIndex.put("D", -1);
      AlphabetIndex.put("E", -1); AlphabetIndex.put("F", -1); AlphabetIndex.put("G", -1); AlphabetIndex.put("H", -1);
      AlphabetIndex.put("I", -1); AlphabetIndex.put("J", -1); AlphabetIndex.put("K", -1); AlphabetIndex.put("L", -1);
      AlphabetIndex.put("M", -1); AlphabetIndex.put("N", -1); AlphabetIndex.put("O", -1); AlphabetIndex.put("P", -1);
      AlphabetIndex.put("Q", -1); AlphabetIndex.put("R", -1); AlphabetIndex.put("S", -1); AlphabetIndex.put("T", -1);
      AlphabetIndex.put("U", -1); AlphabetIndex.put("V", -1); AlphabetIndex.put("W", -1); AlphabetIndex.put("X", -1);
      AlphabetIndex.put("Y", -1); AlphabetIndex.put("Z", -1);

      int cnt = 0; int j = 0;
      if (graph.get(j).researcher.getname().substring(0, 1).equals("A") && cnt == 0) {
         AlphabetIndex.put("A", j);
       //  System.out.println("A�� index�� ������: " + AlphabetIndex.get("A"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("A"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("A") && cnt == 0) {
        // System.out.println("A�ν����ϴ� ���� ����");
         cnt++;
      }

      if (graph.get(j).researcher.getname().substring(0, 1).equals("B") && cnt == 1) {
         AlphabetIndex.put("B", j);
       //  System.out.println("B�� index�� ������: " + AlphabetIndex.get("B"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("B"))
            j++;
      }

      else if (!graph.get(j).researcher.getname().substring(0, 1).equals("B") && cnt == 1) {
         //System.out.println("B�ν����ϴ� ���� ����");
         cnt++;
      }

      if (graph.get(j).researcher.getname().substring(0, 1).equals("C") && cnt == 2) {
         AlphabetIndex.put("C", j);
         //System.out.println("C�� index�� ������: " + AlphabetIndex.get("C"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("C"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("C") && cnt == 2) {
        // System.out.println("C�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("D") && cnt == 3) {
         AlphabetIndex.put("D", j);
        // System.out.println("D�� index�� ������: " + AlphabetIndex.get("D"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("D"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("D") && cnt == 3) {
         //System.out.println("D�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("E") && cnt == 4) {
         AlphabetIndex.put("E", j);
         //System.out.println("E�� index�� ������: " + AlphabetIndex.get("E"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("E"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("E") && cnt == 4) {
         //System.out.println("E�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("F") && cnt == 5) {
         AlphabetIndex.put("F", j);
        // System.out.println("F�� index�� ������: " + AlphabetIndex.get("F"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("F"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("F") && cnt == 5) {
        // System.out.println("F�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("G") && cnt == 6) {
         AlphabetIndex.put("G", j);
        // System.out.println("G�� index�� ������: " + AlphabetIndex.get("G"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("G"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("G") && cnt == 6) {
        // System.out.println("G�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("H") && cnt == 7) {
         AlphabetIndex.put("H", j);
        // System.out.println("H�� index�� ������: " + AlphabetIndex.get("H"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("H"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("H") && cnt == 7) {
        // System.out.println("H�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("I") && cnt == 8) {
         AlphabetIndex.put("I", j);
         //System.out.println("I�� index�� ������: " + AlphabetIndex.get("I"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("I"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("I") && cnt == 8) {
         //System.out.println("I�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("J") && cnt == 9) {
         AlphabetIndex.put("J", j);
         //System.out.println("J�� index�� ������: " + AlphabetIndex.get("J"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("J"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("J") && cnt == 9) {
         //System.out.println("J�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("K") && cnt == 10) {
         AlphabetIndex.put("K", j);
         //System.out.println("K�� index�� ������: " + AlphabetIndex.get("K"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("K"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("K") && cnt == 10) {
        // System.out.println("K�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("L") && cnt == 11) {
         AlphabetIndex.put("L", j);
         //System.out.println("L�� index�� ������: " + AlphabetIndex.get("L"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("L"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("L") && cnt == 11) {
         //System.out.println("L�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("M") && cnt == 12) {
         AlphabetIndex.put("M", j);
         //System.out.println("M�� index�� ������: " + AlphabetIndex.get("M"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("M"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("M") && cnt == 12) {
        // System.out.println("M�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("N") && cnt == 13) {
         AlphabetIndex.put("N", j);
         //System.out.println("N�� index�� ������: " + AlphabetIndex.get("N"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("N"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("N") && cnt == 13) {
         //System.out.println("N�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("O") && cnt == 14) {
         AlphabetIndex.put("O", j);
        // System.out.println("O�� index�� ������: " + AlphabetIndex.get("O"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("O"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("O") && cnt == 14) {
        // System.out.println("O�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("P") && cnt == 15) {
         AlphabetIndex.put("P", j);
        // System.out.println("P�� index�� ������: " + AlphabetIndex.get("P"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("P"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("P") && cnt == 15) {
        // System.out.println("P�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("Q") && cnt == 16) {
         AlphabetIndex.put("Q", j);
        // System.out.println("Q�� index�� ������: " + AlphabetIndex.get("Q"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("Q"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("Q") && cnt == 16) {
        // System.out.println("Q�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("R") && cnt == 17) {
         AlphabetIndex.put("R", j);
        // System.out.println("R�� index�� ������: " + AlphabetIndex.get("R"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("R"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("R") && cnt == 17) {
         //System.out.println("R�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("S") && cnt == 18) {
         AlphabetIndex.put("S", j);
        // System.out.println("S�� index�� ������: " + AlphabetIndex.get("S"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("S"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("S") && cnt == 18) {
         //System.out.println("R�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("T") && cnt == 19) {
         AlphabetIndex.put("T", j);
         //System.out.println("T�� index�� ������: " + AlphabetIndex.get("T"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("T"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("T") && cnt == 19) {
        // System.out.println("T�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("U") && cnt == 20) {
         AlphabetIndex.put("U", j);
         //System.out.println("U�� index�� ������: " + AlphabetIndex.get("U"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("U"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("U") && cnt == 20) {
       //  System.out.println("U�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("V") && cnt == 21) {
         AlphabetIndex.put("V", j);
       //  System.out.println("V�� index�� ������: " + AlphabetIndex.get("V"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("V"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("V") && cnt == 21) {
      //   System.out.println("V�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("W") && cnt == 22) {
         AlphabetIndex.put("W", j);
      //   System.out.println("W�� index�� ������: " + AlphabetIndex.get("W"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("W"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("W") && cnt == 22) {
      //   System.out.println("W�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("X") && cnt == 23) {
         AlphabetIndex.put("X", j);
        // System.out.println("X�� index�� ������: " + AlphabetIndex.get("X"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("X"))
            j++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("X") && cnt == 23) {
        // System.out.println("X�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("Y") && cnt == 24) {
         AlphabetIndex.put("Y", j);
       //  System.out.println("Y�� index�� ������: " + AlphabetIndex.get("Y"));
         cnt++;
         while (graph.get(j).researcher.getname().substring(0, 1).equals("Y"))
         {
            if(j>AlphabetIndex.size())
               break;
            j++;
         }   
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("Y") && cnt == 24) {
        // System.out.println("Y�ν����ϴ� ���� ����");
         cnt++;
      }
      
      if (graph.get(j).researcher.getname().substring(0, 1).equals("Z") && cnt == 25) {
         AlphabetIndex.put("Z", j);
        // System.out.println("Z�� index�� ������: " + AlphabetIndex.get("Z"));
         cnt++;
      } else if (!graph.get(j).researcher.getname().substring(0, 1).equals("Z") && cnt == 25) {
       //  System.out.println("Z�ν����ϴ� ���� ����");
         cnt++;
      }

   }


   public HashMap<String, Integer> getAlphabetIndex() {
        return AlphabetIndex;
   }
   //graph�� �� ��������� vertex���� researcher�� �̸��� ���ĺ� ������ �����Ұ��̴�.
   public void SortGraph()
   {
      for(int i=0 ; i<graph.size() ; i++)
      {
         for(int k=i ; k<graph.size() ; k++)
         {
            //z�� a���� ū���̴� -> (k)���� (i)������ ������ k�� ������ ������
            if(graph.get(i).researcher.getname().compareTo(graph.get(k).researcher.getname()) > 0 )
            {
               Collections.swap(graph, i, k);
            }
         }
      }
   }
   
   // �θ��� Researcher�� �Է¹޾� �ش� Researcher ��尡 ���ٸ� ��� �������ְ�
   // �߰��� �ش� Researcher���� edge�� ���ٸ� edge����, �ִٸ� ����ġ �������ִ� �޼ҵ�
   public void AddVertex(Researcher r1, Researcher r2) {
      vertex v1 = new vertex();
      v1.setvertex(r1);
      vertex v2 = new vertex();
      v2.setvertex(r2);

      boolean g1 = false;
      boolean g2 = false;
      // �̹� graph�� ���� �߰��Ϸ��� researcher�� ����Ǿ������� �߰�X, ������ �߰�
      for (int i = 0; i < graph.size(); i++) {
         // graph vertex�� r1�� �̹� �ִ���->������ g1�� true��
         if (graph.get(i).researcher.getname().equals(r1.getname())) {
            v1 = graph.get(i);
            g1 = true;
            break;
         }
      }
      for (int i = 0; i < graph.size(); i++) {
         // graph vertex�� r2�� �̹� �ִ���->������ g2�� true��
         if (graph.get(i).researcher.getname().equals(r2.getname())) {
            v2 = graph.get(i);
            g2 = true;
            break;
         }
      }
      // r1, r2�� ������ �ش� vertex�� �׷����� �߰�
      if (g1 == false)
         graph.add(v1);
      if (g2 == false)
         graph.add(v2);

      // �������ʹ� edge�� ���������� �ľ��ϰ� edge�� �߰��ϰų� ����ġ�� �ø�
      if (isEdgeExist(v1, v2)) {
         // �ش� edge�� ����ġ 1����
         getedge(r1.getname(), r2.getname()).changeWeight(+1);
         // return;
      } else if (isEdgeExist(v2, v1)) {
         // �ش� edge�� ����ġ 1����
         getedge(r2.getname(), r1.getname()).changeWeight(+1);
         // return;
      } else {
         // ���ο� edge�� ���� v1�� v2�� �߰�����
         edge e = new edge(); 
         e.setEdgevertexes(v1, v2);
         v1.addEdge(e);
         v2.addEdge(e);
         // return;
      }
      // �� vertex�� researcher���� coauthor�߰�. ���� �ִٸ� �ߺ����� ���� ����
      v1.researcher.AddCoauthor(r2);
      v2.researcher.AddCoauthor(r1);

      //System.out.println("***�׷��� ������: " + graph.size());
      
/*        for (int i = 0; i < graph.size(); i++) { System.out.println(i + 1 +
        "��° vertex��"); System.out.println( "researcher �̸�: " +
        graph.get(i).researcher.getname() + "/ �о�: " +
        graph.get(i).researcher.getmajor() + "/ �� ����Ƚ��: " +
        graph.get(i).researcher.getresearchCnt()); for (int k = 0; k <
        graph.get(i).researcher.getCoauthorlist().size(); k++) {
        System.out.println("/ coauthor: " +
        graph.get(i).researcher.getCoauthorlist().get(k).getname() + ' '); }
        
        
        System.out.println("���� vertex�� edge ����: " + graph.get(i).edgeSize());
        for (int k = 0; k < graph.get(i).edgeSize(); k++) {
        System.out.println(k + 1 + "��° edge: " +
        graph.get(i).edges.get(k).mainvertex.researcher .getname() + "----" +
        graph.get(i).edges.get(k).subvertex.researcher .getname() +
        " �� ���� ����ġ: " + graph.get(i).edges.get(k).weight); }
        System.out.println(""); } System.out .println(
        "-----------------------------------------------------------------");
*/       

   }

   //�ܵ����� ������ researcher vertex�߰�
   public void AddSoloVertex(Researcher r)
   {
      for (int i = 0; i < graph.size(); i++) {
         // �̹� �ִ� researcher���� Ȯ��, �̹� �ִٸ� �Լ� ����
         if (graph.get(i).researcher.getname().equals(r.getname())) {
            return;
         }
      }
      vertex v = new vertex();
       v.setvertex(r);
       graph.add(v);
   }
   
   // �������� name�� �Է��ؼ� �ش� vertex�� researcher�� ��ȯ�ؿ��� �޼ҵ�
   public vertex getvertex(String name) {
      for (int i = 0; i < graph.size(); i++) {
         if (graph.get(i).researcher.getname().equals(name)) {
            //System.out.println("vertexã�Ҵ�!");
            return graph.get(i);
         }
      }
      // ��ã����
      vertex v = new vertex();
      return v; // none������ �ʱ�ȭ�Ǿ��ִ� vertex�� ���ϵȴ�
   }

   // �������� name�� �ΰ��� �Է��ؼ� �ش� vertex�� �����ϴ� edge�� ��ȯ�ؿ��� �޼ҵ�
   public edge getedge(String name1, String name2) {
      for (int i = 0; i < graph.size(); i++) {
         if (graph.get(i).researcher.getname().equals(name1)) {
            for (int k = 0; k < graph.get(i).edgeSize(); k++)
               if (graph.get(i).edges.get(k).getsubvertex().researcher.getname().equals(name2)) {
                 // System.out.println("edge ã�Ҵ�!");
                  return graph.get(i).edges.get(k);
               }
         }
      }
      // ��ã����
      edge e = new edge();
      return e; // none�� vertex�� 0���� �ʱ�ȭ�� weight���� ���� edge�� ���ϵȴ�.
   }

   // v1�� v2�� �մ� edge�� ���翩�� ����(T/F)
   public boolean isEdgeExist(vertex v1, vertex v2) {
      for (int i = 0; i < graph.size(); i++) {
         if (graph.get(i).researcher.getname().equals(v1.researcher.getname())) {
            for (int k = 0; k < graph.get(i).edges.size(); k++) {
               if (graph.get(i).edges.get(k).subvertex.researcher.getname().equals(v2.researcher.getname())) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   public ArrayList<vertex> getTotalVertex() {
      return graph;
   }

   public void setAllvertexIsvisitedFalse()
   {
      for(int i=0 ; i<graph.size() ; i++)
      {
         graph.get(i).setisVisited(false);
      }
   }
}