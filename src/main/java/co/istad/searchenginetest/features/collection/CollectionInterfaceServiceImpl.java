package co.istad.searchenginetest.features.collection;


import co.istad.searchenginetest.domain.webCollection.HeaderCollection;
import co.istad.searchenginetest.features.collection.dto.DataImportRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.typesense.api.Client;
import org.typesense.api.FieldTypes;
import org.typesense.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionInterfaceServiceImpl implements CollectionService{

    private final Client client;

    @Override
    public void getDataImport(DataImportRequest dataImportRequest) throws Exception {

        Document document = Jsoup.connect(dataImportRequest.link()).get();

        HeaderCollection headerCollection = new HeaderCollection();

        // Select specific meta tags and set them to the HeaderCollection fields
        headerCollection.setTitle(document.title());
        headerCollection.setDescription(getMetaTagContent(document, "description"));
        headerCollection.setAuthor(getMetaTagContent(document, "author"));
        headerCollection.setKeyword(getMetaTagContent(document, "keywords"));
        headerCollection.setThumbnail(getMetaTagContent(document, "thumbnail"));
        headerCollection.setDomain(dataImportRequest.link());

        log.info("header collection : {}", headerCollection);

        CollectionSchema collectionSchema = new CollectionSchema();
        collectionSchema.name(document.title())
                        .addFieldsItem(new Field().name(".*").type(FieldTypes.AUTO));

        client.collections().create(collectionSchema);
        HashMap<String, Object> documentList = new HashMap<>();
        documentList.put("id","51");
        documentList.put("title",document.title());
        documentList.put("description",headerCollection.getDescription());
        documentList.put("author",headerCollection.getAuthor());
        documentList.put("keyword",headerCollection.getKeyword());
        documentList.put("thumbnail",headerCollection.getThumbnail());
        documentList.put("domain",headerCollection.getDomain());

        client.collections(document.title()).documents().create(documentList);

    }

    @Override
    public MultiSearchResult multiSearch(String searchField) throws Exception {
        CollectionResponse[] collectionResponses = client.collections().retrieve();
        List<MultiSearchCollectionParameters> searches = new ArrayList<>();
        for (CollectionResponse collectionResponse : collectionResponses){
            MultiSearchCollectionParameters multiSearchCollectionParameters = new MultiSearchCollectionParameters();
            multiSearchCollectionParameters.setCollection(collectionResponse.getName());
            multiSearchCollectionParameters.q(searchField);
            searches.add(multiSearchCollectionParameters);
        }

        HashMap<String,String> commonSearchParams = new HashMap<>();
        commonSearchParams.put("query_by","description");

        MultiSearchSearchesParameter multiSearchSearchesParameter = new MultiSearchSearchesParameter();
        multiSearchSearchesParameter.setSearches(searches);

        return client.multiSearch.perform(multiSearchSearchesParameter,commonSearchParams);
    }


    private String getMetaTagContent(Document document, String metaName) {
        Element metaTag = document.selectFirst("meta[name=" + metaName + "]");
        return metaTag != null ? metaTag.attr("content") : "";
    }
}
