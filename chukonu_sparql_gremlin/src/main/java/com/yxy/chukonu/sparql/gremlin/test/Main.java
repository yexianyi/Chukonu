package com.yxy.chukonu.sparql.gremlin.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tinkerpop.gremlin.sparql.process.traversal.dsl.sparql.SparqlTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;

public class Main {

    public static void main(String[] args) {
        Graph graph = TinkerFactory.createModern() ;
        try {
            SparqlTraversalSource g = graph.traversal(SparqlTraversalSource.class) ;
            List<LinkedHashMap> res =  g.sparql("SELECT ?id ?name ?age WHERE { ?person v:id ?id . ?person v:name ?name . ?person v:age ?age } ORDER BY ASC(?age)").fill(new ArrayList()) ;
            
            for(LinkedHashMap v : res) {
                System.out.println(v.toString()) ;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
