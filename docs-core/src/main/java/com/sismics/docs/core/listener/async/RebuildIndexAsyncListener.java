package com.sismics.docs.core.listener.async;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;
import com.sismics.docs.core.dao.jpa.DocumentDao;
import com.sismics.docs.core.dao.jpa.FileDao;
import com.sismics.docs.core.dao.lucene.LuceneDao;
import com.sismics.docs.core.event.RebuildIndexAsyncEvent;
import com.sismics.docs.core.model.jpa.Document;
import com.sismics.docs.core.model.jpa.File;
import com.sismics.docs.core.util.TransactionUtil;

/**
 * Listener on rebuild index.
 * 
 * @author bgamard
 */
public class RebuildIndexAsyncListener {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(RebuildIndexAsyncListener.class);

    /**
     * Rebuild Lucene index.
     * 
     * @param rebuildIndexAsyncEvent Index rebuild event
     * @throws Exception
     */
    @Subscribe
    public void on(final RebuildIndexAsyncEvent rebuildIndexAsyncEvent) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("Rebuild index event: " + rebuildIndexAsyncEvent.toString());
        }
        
        // Fetch all documents and files
        TransactionUtil.handle(new Runnable() {
            @Override
            public void run() {
                // Fetch all documents
                DocumentDao documentDao = new DocumentDao();
                List<Document> documentList = documentDao.findAll();
                
                // Fetch all files
                FileDao fileDao = new FileDao();
                List<File> fileList = fileDao.findAll();
                
                // Rebuild index
                LuceneDao luceneDao = new LuceneDao();
                luceneDao.rebuildIndex(documentList, fileList);
            }
        });
    }
}
