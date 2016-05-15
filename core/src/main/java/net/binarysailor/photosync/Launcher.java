package net.binarysailor.photosync;

import net.binarysailor.photosync.index.*;
import net.binarysailor.photosync.index.fsstored.FilesystemIndex;

/**
 * Created by binarysailor on 2016-05-01.
 */
public class Launcher {
    public static void main(final String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage: Launcher <photo_root_dir> <index_dir> [<count_limit>]");
            System.exit(1);
        }

        final String photoRootDirectory = args[0];
        final String indexRootDirectory = args[1];

        int batchSize = 100;
        if (args.length > 2) {
            try {
                batchSize = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                System.out.printf("Could not parse '%s' as count_limit. It must be a number\n", args[2]);
                System.exit(1);
            }
        }

        try {
            final FileSystemStorage storage = new FileSystemStorage(photoRootDirectory);
            final Index index = new FilesystemIndex(storage, indexRootDirectory);
            final HashCalculator calculator = new SHA1HashCalculator();

            final IndexingService indexingService = new IndexingService(storage, index, calculator);
            final IndexingCommand command = new IndexingCommand(storage.getRoot());
            command.setMaxBatchSize(batchSize);

            indexingService.runOnce(command);
            System.out.println("Done.");
        } catch (final DirectoryNotFoundException e) {
            System.out.printf("Directory %s could not be found", e.getRequestedPath());
            System.exit(1);
        }

    }
}
