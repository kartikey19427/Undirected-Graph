package com.company;

import com.sun.security.jgss.GSSUtil;

import javax.naming.directory.Attributes;
import java.util.*;

public class Graphwh {
    public HashMap<String,Vertex> vtcs=new HashMap<String,Vertex>();
    int time;


    static class Vertex {
        HashMap<String,Integer> nbrs=new HashMap<String,Integer>();


        public Vertex(){
            HashMap<String,Integer> nbrs=new HashMap<String,Integer>();

        }
    }





    public Graphwh(){
        HashMap<String,Vertex> vtcs=new HashMap<String,Vertex>();
        this.time = 0;

    }
    public void addVertex(String vname){
        Vertex x=new Vertex();
        vtcs.put(vname,x);

    }
    public void addEdge(String vname1, String vname2, int cost){
        Vertex vt1=vtcs.get(vname1);
        Vertex vt2=vtcs.get(vname2);
        if(containsEdge(vname1,vname2) || vt1==null || vt2==null){
            return;

        }
        vt1.nbrs.put(vname2,cost);
        vt2.nbrs.put(vname1,cost);


    }

    public void removeEdge(String vname1, String vname2){
        Vertex vt1=vtcs.get(vname1);
        Vertex vt2=vtcs.get(vname2);
        if(!containsEdge(vname1,vname2) || vt1==null || vt2==null){
            return;


        }
        vt1.nbrs.remove(vname2);
        vt2.nbrs.remove(vname1);


    }
    public void removeVertex(String vname){
        Vertex vt=vtcs.get(vname);
        if(vt==null){
            return;
        }
        ArrayList<String> keys=new ArrayList<>(vt.nbrs.keySet());
        for(String key:keys){
            Vertex i=vtcs.get(key);
            i.nbrs.remove(vname);

        }
        vtcs.remove(vname);



    }
    public boolean containsEdge(String vname1, String vname2){
        if(vtcs.get(vname1)==null || vtcs.get(vname2)==null){
            return false;

        }
        Vertex c=vtcs.get(vname1);

        if(c.nbrs.get(vname2)!=null) {
            return true;
        }
        else {
            return false;
        }



    }
    public int numEdges(){
        int count=0;
        ArrayList<String> keys=new ArrayList<>(vtcs.keySet());
        for(String key:keys){
            Vertex c=vtcs.get(key);
            count=count+c.nbrs.size();



        }
        return count/2;

    }

    public int numVertex(){
            return vtcs.size();
    }
    public void display(){
        ArrayList<String> arr=new ArrayList<>(vtcs.keySet());
        for(String key:arr){
            Vertex a=vtcs.get(key);
            System.out.println(key+" "+a.nbrs);
        }


    }

    public String bfsnonweighted(String vname1,String vname2,Graphwh g, HashMap<String, Boolean> processed){

        if(g.containsEdge(vname1,vname2)){
            return vname1;

        }

        LinkedList<Qent> queue=new LinkedList<>();
        Qent f=new Qent();
        f.vtx=vname1;
        f.psf=vname1;
        queue.addLast(f);
        while(!queue.isEmpty()){
            Qent a=queue.removeFirst();
            if(g.containsEdge(a.vtx,vname2)){
                return a.psf;
            }
            if(processed.containsKey(a.vtx)){
                continue;
            }
            processed.put(a.vtx,true);
            Vertex vtc=g.vtcs.get(a.vtx);
            ArrayList<String> arr=new ArrayList<>(vtc.nbrs.keySet());
            for(String key:arr){
                if(processed.containsKey(key)){
                    continue;
                }
                Qent b=new Qent();
                b.vtx=key;
                b.psf=a.psf+key;
                queue.addLast(b);
            }


        }
        return "No Path";


    }

    public void DFTS(String vname1,HashMap<String, Boolean> processed){
        Vertex v1=vtcs.get(vname1);
        ArrayList<String> neighbours=new ArrayList<>(v1.nbrs.keySet());
        processed.put(vname1,true);
        time++;

        for(String key : neighbours){
            if(processed.get(key)!=null){
                continue;
            }
            DFTS(key,processed);
        }



    }
    public void DFTScycledetect(String vname1,HashMap<String, Boolean> processed){
        Vertex v1=vtcs.get(vname1);
        ArrayList<String> neighbours=new ArrayList<>(v1.nbrs.keySet());
        processed.put(vname1,true);
        time++;

        for(String key : neighbours){
            if(processed.get(key)!=null){
                System.out.println(1);
                break;
            }
            DFTS(key,processed);
        }
        System.out.println(0);



    }
    public void DFTScycledetectprop(String vname1,HashMap<String, Boolean> processed, String parent){
        Vertex v1=vtcs.get(vname1);
        ArrayList<String> neighbours=new ArrayList<>(v1.nbrs.keySet());
        processed.put(vname1,true);
        time++;

        for(String key : neighbours){
            if(key.equals(parent)){
               continue;
            }
            if(processed.get(key)!=null){
                System.out.println(1);
                break;
            }
            DFTS(key,processed);
        }
        System.out.println(0);



    }
    public void resetTime(){
        this.time=0;
    }

    public void primsconnected(HashMap<String, Boolean> processed, Graphwh mintree){

        ArrayList<String> S=new ArrayList<>(vtcs.keySet());
        Vertex v1=vtcs.get(S.get(0));
        ArrayList<Integer> v1value=new ArrayList<>(v1.nbrs.values());
        ArrayList<String> v1keyset=new ArrayList<>(v1.nbrs.keySet());


        PriorityQueue<PrimsEdge> minheap = new PriorityQueue<>(new Comparator<PrimsEdge>() {
            @Override
            public int compare(PrimsEdge o1, PrimsEdge o2) {
                return Integer.compare(o1.weight,o2.weight);
            }
        });

        for(String keys : v1keyset){
            minheap.add(new PrimsEdge(keys,S.get(0),v1.nbrs.get(keys)));
        }
        processed.put(S.get(0),true);
        mintree.addVertex(S.get(0));

        while(!minheap.isEmpty()){
            PrimsEdge v=minheap.poll();

            if(processed.get(v.vtx)!=null){
                continue;
            }
            processed.put(v.vtx,true);
            mintree.addVertex(v.vtx);
            mintree.addEdge(v.vtx,v.parent,v.weight);
            Vertex child=vtcs.get(v.vtx);
            ArrayList<String> keyset=new ArrayList<>(child.nbrs.keySet());

            for(String keys : keyset){
                if(processed.get(keys)!=null){
                    continue;
                }
                minheap.add(new PrimsEdge(keys,v.vtx,child.nbrs.get(keys)));
            }



        }




    }


    public int DFTScountdiscon(HashMap<String, Boolean> processed){

        ArrayList<String> vtcset=new ArrayList<>(vtcs.keySet());
        int count=0;
        for(String keys : vtcset){
            if(processed.get(keys)!=null){
                continue;
            }
            DFTS(keys,processed);
            count++;
        }
        return count;





    }
    public void numnodesvis(HashMap<String,Boolean> processed, String vname1, int k){
        LinkedList<String> queue=new LinkedList<>();
        queue.addLast(vname1);
        processed.put(vname1,true);
        int maxcount=0;
        while (!queue.isEmpty()){
            String a=queue.removeFirst();
            Vertex v=vtcs.get(a);
            ArrayList<String> arr=new ArrayList<>(v.nbrs.keySet());
            for(String keys: arr){
                if(processed.get(keys)!=null){
                    continue;
                }
                int cost=v.nbrs.get(keys);
                if(cost>=k){
                    maxcount=maxcount+cost;
                    processed.put(vname1,true);
                }
                else {
                    int remain=k-cost-1;
                }
            }

        }



    }


}
