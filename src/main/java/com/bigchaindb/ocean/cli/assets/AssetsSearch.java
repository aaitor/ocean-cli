package com.bigchaindb.ocean.cli.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.aquarius.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

@CommandLine.Command(
        name = "search",
        description = "Searching for assets")
public class AssetsSearch implements Runnable {

    private static final Logger log = LogManager.getLogger(AssetsSearch.class);

    @CommandLine.ParentCommand
    AssetsCLI parent;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;


    @CommandLine.Parameters(index = "0")
    String query;

    @CommandLine.Option(names = { "-o", "--offset" }, required = false, description = "search offset")
    int offset= 10;

    @CommandLine.Option(names = { "-p", "--page" }, required = false, description = "page to show")
    int page= 1;

    void search() {
        try {
            log.info("Searching " + query);

            SearchResult searchResult= parent.cli.getOceanAPI().getAssetsAPI()
                    .search(query, offset, page);

            System.out.println("Total results: " + searchResult.total_results
                    + " - page: " + searchResult.page
                    + " - total pages: " + searchResult.total_pages);

            searchResult.getResults().forEach( ddo -> printSimplifiedDDO(ddo));

        } catch (DDOException e) {
            log.error(e.getMessage());
        }
    }

    private void printSimplifiedDDO(DDO ddo)    {
        System.out.println("{" +
                "\n\tdid: " + ddo.id + ", " +
                "\n\ttitle:" + ddo.getMetadataService().metadata.base.name  + ", " +
                "\n\tprice:" + ddo.getMetadataService().metadata.base.price  + " " +
                "\n}");
    }

    @Override
    public void run() {
        search();
    }
}
