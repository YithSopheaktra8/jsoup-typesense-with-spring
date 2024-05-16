package co.istad.searchenginetest.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;
import org.typesense.api.Client;
import org.typesense.api.Configuration;
import org.typesense.resources.Node;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@org.springframework.context.annotation.Configuration
public class JsoupScraping {

    @Bean
    public List<Node> nodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(
                "http",       // For Typesense Cloud use https
                "35.213.166.167",  // For Typesense Cloud use xxx.a1.typesense.net
                "8085"
        ));
        return nodes;
    }

    @Bean
    Client client(){
        Configuration configurationbean = new Configuration(nodes(), Duration.ofSeconds(2),"BmibWq2dWsThsfr4yDr0zKiIK1ighde0");
        return new Client(configurationbean);
    }

}
