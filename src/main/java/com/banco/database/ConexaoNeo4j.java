package com.banco.database;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class ConexaoNeo4j {

    private static final String URI = "bolt://localhost:7687";
    private static final String USUARIO = "neo4j";
    private static final String SENHA = "adminadmin";

    private static final Driver driver = GraphDatabase.driver(URI, AuthTokens.basic(USUARIO, SENHA));

    public static Driver getDriver() {
        return driver;
    }
}
