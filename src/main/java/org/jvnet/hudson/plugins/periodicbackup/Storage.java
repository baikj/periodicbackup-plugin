/*
 * The MIT License
 *
 * Copyright (c) 2010 Tomasz Blaszczynski, Emanuele Zattin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jvnet.hudson.plugins.periodicbackup;

import hudson.DescriptorExtensionList;
import hudson.model.AbstractModelObject;
import hudson.model.Describable;
import hudson.model.Hudson;
import org.codehaus.plexus.archiver.ArchiverException;

import java.io.File;
import java.io.IOException;

public abstract class Storage extends AbstractModelObject implements Describable<Storage> {
    /**
     * This method compressed the files and folders that, at this point, must be already
     * determined by a FileManager plugin
     *
     * @param filesToCompress The files and folders to archive
     * @param tempDirectory String with a path to temporary directory, where the archive(s) will be created
     * @param fileNameBase first part of the file name common to both - archive(s) and backup object file
     * @return File object(s) of the archive
     * @throws IOException  IO Error
     * @throws org.codehaus.plexus.archiver.ArchiverException  Archiver Error
     */
    public abstract Iterable<File> archiveFiles(Iterable<File> filesToCompress, String tempDirectory, String fileNameBase) throws IOException, ArchiverException;

    /**
     * This method un-compressed the archive to a temporary location.
     * The actual file restoring is done by the FileManager plugin
     *
     * @param archives The archive(s) to un-compress
     * @param tempDir The temporary directory for extracting files
     * @return The files and folders un-compressed
     */
    public abstract void unarchiveFiles(Iterable<File> archives, File tempDir);

    public StorageDescriptor getDescriptor() {
        return (StorageDescriptor) Hudson.getInstance().getDescriptor(getClass());
    }

    public String getSearchUrl() {
        return "Storage";
    }

    /**
     * This will allow to retrieve the list of plugins at runtime
     *
     * @return Collection of FileManager Descriptors
     */
    public static DescriptorExtensionList<Storage, StorageDescriptor> all() {
        return Hudson.getInstance().getDescriptorList(Storage.class);
    }

}
