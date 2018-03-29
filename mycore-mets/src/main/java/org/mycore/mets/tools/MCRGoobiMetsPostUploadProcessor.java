/*
 * This file is part of ***  M y C o R e  ***
 * See http://www.mycore.de/ for details.
 *
 * MyCoRe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCoRe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCoRe.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mycore.mets.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.mycore.common.content.MCRContent;
import org.mycore.common.content.MCRStreamContent;
import org.mycore.common.content.transformer.MCRXSLTransformer;
import org.mycore.frontend.fileupload.MCRPostUploadFileProcessor;

public class MCRGoobiMetsPostUploadProcessor extends MCRPostUploadFileProcessor {

    private final MCRXSLTransformer goobiMetsTransformer;

    public MCRGoobiMetsPostUploadProcessor() {
        goobiMetsTransformer = MCRXSLTransformer.getInstance("xsl/goobi-mycore-mets.xsl");
    }

    @Override
    public boolean isProcessable(String path) {
        return path.endsWith("mets.xml");
    }

    @Override
    public Path processFile(String path, Path tempFile, Supplier<Path> tempFileSupplier)
        throws IOException {
        try (InputStream in = Files.newInputStream(tempFile)) {
            MCRStreamContent streamContent = new MCRStreamContent(in);
            MCRContent transform = goobiMetsTransformer.transform(streamContent);
            Path result = tempFileSupplier.get();
            try (OutputStream out = Files.newOutputStream(result)) {
                out.write(transform.asByteArray());
                return result;
            }
        }
    }
}
