/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.eclipse.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * To detect the DRL files in the project
 */
public class DRLProjectDetector {

    private List<IFile> resources;

    public DRLProjectDetector() {
        resources = new ArrayList<IFile>();
    }

    public List<IFile> detect(IProject project) throws CoreException {
        detect(project.members());
        return resources;
    }

    private void detect(IResource[] members) throws CoreException {
        if (members == null) {
            return;
        }
        for (int i = 0; i < members.length; i++) {
            if (members[i] instanceof IFolder) {
                IFolder folder = (IFolder) members[i];
                if (!folder.isDerived())
                    detect(folder.members());
            } else if (members[i] instanceof IFile) {
                IFile file = (IFile) members[i];
                if (file.getFileExtension() != null && file.getFileExtension().equalsIgnoreCase("drl"))
                    if (file.isAccessible() && !file.isReadOnly() && !file.isDerived())
                        resources.add(file);
            }
        }
    }

}
