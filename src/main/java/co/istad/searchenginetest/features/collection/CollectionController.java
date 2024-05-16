package co.istad.searchenginetest.features.collection;

import co.istad.searchenginetest.features.collection.dto.DataImportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.typesense.model.MultiSearchResult;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping("/dataImport")
    public void dataImport(@RequestBody DataImportRequest dataImportRequest) throws IOException {
        try {
            collectionService.getDataImport(dataImportRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/multi_search")
    public MultiSearchResult multiSearch(@RequestBody String text) throws Exception {
        return collectionService.multiSearch(text);
    }

}
