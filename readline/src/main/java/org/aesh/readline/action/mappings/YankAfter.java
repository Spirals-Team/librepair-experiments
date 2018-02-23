/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.readline.action.mappings;

import org.aesh.readline.InputProcessor;
import org.aesh.readline.editing.EditMode;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class YankAfter extends ChangeAction {

    YankAfter() {
        super(EditMode.Status.YANK);
    }

    @Override
    public String name() {
        return "yank-after";
    }

    @Override
    public void accept(InputProcessor inputProcessor) {
        int[] pasteBuffer = inputProcessor.buffer().pasteManager().get(0);
        if(pasteBuffer != null) {

            if(inputProcessor.buffer().buffer().cursor() <=
                    inputProcessor.buffer().buffer().length()) {
                inputProcessor.buffer().addActionToUndoStack();
                //if we're at the end, we need to do some magic in vi-mode
                if(inputProcessor.editMode().mode() == EditMode.Mode.VI &&
                        inputProcessor.editMode().status() == EditMode.Status.COMMAND &&
                        inputProcessor.buffer().buffer().cursor() ==
                        inputProcessor.buffer().buffer().length()-1) {
                    inputProcessor.editMode().setStatus(EditMode.Status.EDIT);
                    inputProcessor.buffer().moveCursor(1);
                    inputProcessor.buffer().insert(pasteBuffer);
                    inputProcessor.buffer().moveCursor(-1);
                    inputProcessor.editMode().setStatus(EditMode.Status.COMMAND);
                }
                else {
                    inputProcessor.buffer().moveCursor(1);
                    inputProcessor.buffer().insert(pasteBuffer);
                    inputProcessor.buffer().moveCursor(-1);
                }
            }
        }
    }
}
