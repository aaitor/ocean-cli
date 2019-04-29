package com.bigchaindb.ocean.cli.modules.assets;

import com.bigchaindb.ocean.cli.AssetsCLI;
import com.bigchaindb.ocean.cli.models.CommandResult;
import com.bigchaindb.ocean.cli.models.exceptions.CLIException;
import com.oceanprotocol.squid.exceptions.DDOException;
import com.oceanprotocol.squid.models.DDO;
import com.oceanprotocol.squid.models.aquarius.SearchResult;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "search",
        description = "Searching for assets")
public class AssetsSearch implements Callable {

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

    CommandResult search() throws CLIException {

        SearchResult searchResult;
        try {
            System.out.println("Searching " + query);

            parent.cli.progressBar.start();

            searchResult= parent.cli.getOceanAPI().getAssetsAPI()
                    .search(query, offset, page);

            System.out.println("Total results: " + searchResult.total_results
                    + " - page: " + searchResult.page
                    + " - total pages: " + searchResult.total_pages);

            searchResult.getResults().forEach( ddo -> printSimplifiedDDO(ddo));

        } catch (DDOException e) {
            throw new CLIException("Error with DDO " + e.getMessage());

        } finally {
            parent.cli.progressBar.doStop();
        }

        return CommandResult.successResult().setResult(searchResult);

    }

    private void printSimplifiedDDO(DDO ddo)    {
        System.out.println("{" +
                "\n\tdid: " + ddo.id + ", " +
                "\n\ttitle:" + ddo.getMetadataService().metadata.base.name  + ", " +
                "\n\tprice:" + ddo.getMetadataService().metadata.base.price  + " " +
                "\n}");
    }

    @Override
    public Object call() throws CLIException {
        return search();
    }
}
