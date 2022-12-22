package com.semweb.event.DAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.ErrorHandlerFactory;
import org.apache.jena.vocabulary.RDF;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetRDF {
    public static void GetFromDate(){

        try (RDFConnection conn = RDFConnection.connectPW(
                "https://territoire.emse.fr/ldp/maximeaurelien/",
                "ldpuser",
                "LinkedDataIsGreat"
        )) {

            Model model = conn.queryConstruct("CONSTRUCT {?s ?p ?o} WHERE {?s ?p ?o}");
            try {
                StmtIterator statements = model.listStatements();
                try {
                    statements.forEach(System.out::println);
                } finally {
                    statements.close();
                }

            } finally {
                model.close();
            }
        }

        // try (RDFConnection conn = RDFConnection.connectPW(
        //             "https://territoire.emse.fr/ldp/maximeaurelien/",
        //             "ldpuser",
        //             "LinkedDataIsGreat"
        //     )) {
        //         conn.delete();
        // }

    }
     
}
