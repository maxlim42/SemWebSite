package com.semweb.event.Controlleur;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControlleurPage {
    
    @RequestMapping("")
    public String mainredirection(Model model) 
    {
        String queryString = "SELECT ?subject ?predictat ?object \n WHERE { \n ?subject ?predictat ?object \n}";
        Query query = QueryFactory.create(queryString);
        QueryEngineHTTP qexec = new QueryEngineHTTP("https://territoire.emse.fr/ldp/maximeaurelien/", query );
    
        ResultSet results = qexec.execSelect();        
        List resultsList = ResultSetFormatter.toList(results);
        qexec.close();

        
        return "main";
    }
    
}
