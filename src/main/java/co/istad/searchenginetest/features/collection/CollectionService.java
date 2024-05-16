package co.istad.searchenginetest.features.collection;


import co.istad.searchenginetest.features.collection.dto.DataImportRequest;
import org.typesense.model.MultiSearchResult;

import java.io.IOException;

public interface CollectionService {

    void getDataImport(DataImportRequest dataImportRequest) throws Exception;

    MultiSearchResult multiSearch(String searchField) throws Exception;
}
