package com.basho.riak.client.response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.basho.riak.client.RiakClient;
import com.basho.riak.client.RiakLink;
import com.basho.riak.client.RiakObject;
import com.basho.riak.client.util.ClientUtils;
import com.basho.riak.client.util.CollectionWrapper;
import com.basho.riak.client.util.Constants;
import com.basho.riak.client.util.Multipart;
import com.basho.riak.client.util.StreamedMultipart;

/**
 * @deprecated with the addition of a protocol buffers client in 0.14 all the
 *             existing REST client code should be in client.http.* this class
 *             has therefore been moved. Please use
 *             com.basho.riak.client.http.response.StreamedSiblingsCollections
 *             instead.
 *             <p>WARNING: This class will be REMOVED in the next version.</p>
 * @see com.basho.riak.client.http.response.StreamedSiblingsCollection
 */
@Deprecated
public class StreamedSiblingsCollection extends CollectionWrapper<RiakObject> {

    String bucket;
    String key;
    RiakClient riak;
    StreamedMultipart multipart;

    public StreamedSiblingsCollection(RiakClient riak, String bucket, String key, StreamedMultipart multipart) {
        this.bucket = bucket;
        this.key = key;
        this.riak = riak;
        this.multipart = multipart;
    }

    /**
     * Tries to read and cache another part of the multipart/mixed stream.
     * 
     * @throws RiakIORuntimeException
     *             If an error occurs during communication with the Riak server.
     */
    @Override protected boolean cacheNext() {
        if (multipart == null)
            return false;

        String vclock = null;

        if (multipart.getHeaders() != null) {
            vclock = multipart.getHeaders().get(Constants.HDR_VCLOCK);
        }

        Multipart.Part part;
        try {
             part = multipart.next();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                throw new RiakIORuntimeException(e);
            }
            throw e;
        }
        
        if (part != null) {
            Map<String, String> headers = part.getHeaders();
            List<RiakLink> links = ClientUtils.parseLinkHeader(headers.get(Constants.HDR_LINK));
            Map<String, String> usermeta = ClientUtils.parseUsermeta(headers);
            String location = headers.get(Constants.HDR_LOCATION);
            String partBucket = bucket;
            String partKey = key;

            if (location != null) {
                String[] locationParts = location.split("/");
                if (locationParts.length >= 2) {
                    partBucket = locationParts[locationParts.length - 2];
                    partKey = locationParts[locationParts.length - 1];
                }
            }

            RiakObject o = new RiakObject(riak, partBucket, partKey, null, headers.get(Constants.HDR_CONTENT_TYPE),
                                 links, usermeta, vclock, headers.get(Constants.HDR_LAST_MODIFIED),
                                 headers.get(Constants.HDR_ETAG));
            o.setValueStream(part.getStream());
            cache(o);
            return true;
        }

        return false;
    }

    @Override protected void closeBackend() {
        riak = null;
        multipart = null;
    }
}
