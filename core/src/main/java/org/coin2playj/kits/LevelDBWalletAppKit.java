package org.coin2playj.kits;

import org.coin2playj.core.NetworkParameters;
import org.coin2playj.store.BlockStore;
import org.coin2playj.store.BlockStoreException;
import org.coin2playj.store.LevelDBBlockStore;
import org.coin2playj.store.SPVBlockStore;

import java.io.File;

/**
 * Created by Eric on 2/23/2016.
 */
public class LevelDBWalletAppKit extends WalletAppKit {
    public LevelDBWalletAppKit(NetworkParameters params, File directory, String filePrefix) {
        super(params, directory, filePrefix);
    }

    /**
     * Override this to use a {@link BlockStore} that isn't the default of {@link SPVBlockStore}.
     */
    protected BlockStore provideBlockStore(File file) throws BlockStoreException {
        return new LevelDBBlockStore(context, file);
    }
}
