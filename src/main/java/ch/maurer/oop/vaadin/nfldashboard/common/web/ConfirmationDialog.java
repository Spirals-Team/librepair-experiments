/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ch.maurer.oop.vaadin.nfldashboard.common.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.io.Serializable;
import java.util.function.Consumer;


class ConfirmationDialog<T extends Serializable> extends Dialog {

    private final H3 titleField = new H3();
    private final Div messageLabel = new Div();
    private final Div extraMessageLabel = new Div();
    private final Button confirmButton = new Button();
    private final Button cancelButton = new Button("Cancel");

    private Registration registrationForConfirm;
    private Registration registrationForCancel;

    ConfirmationDialog() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        addConfirmButton();
        addCancelButton();

        HorizontalLayout buttonBar = new HorizontalLayout(confirmButton, cancelButton);
        buttonBar.setClassName("buttons confirm-buttons");

        Div labels = new Div(messageLabel, extraMessageLabel);
        labels.setClassName("confirm-text");

        titleField.setClassName("confirm-title");

        add(titleField, labels, buttonBar);
    }

    private void addConfirmButton() {
        confirmButton.addClickListener(e -> close());
        confirmButton.getElement().setAttribute("theme", "tertiary");
        confirmButton.setAutofocus(true);
    }

    private void addCancelButton() {
        cancelButton.addClickListener(e -> close());
        cancelButton.getElement().setAttribute("theme", "tertiary");
    }

    public void open(String title, String message, String additionalMessage,
                     String actionName, boolean isDisruptive, T item, Consumer<T> confirmHandler,
                     Runnable cancelHandler) {
        titleField.setText(title);
        messageLabel.setText(message);
        extraMessageLabel.setText(additionalMessage);
        confirmButton.setText(actionName);

        if (registrationForConfirm != null) {
            registrationForConfirm.remove();
        }
        registrationForConfirm = confirmButton.addClickListener(e -> confirmHandler.accept(item));
        if (registrationForCancel != null) {
            registrationForCancel.remove();
        }
        registrationForCancel = cancelButton.addClickListener(e -> cancelHandler.run());
        this.addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                cancelHandler.run();
            }
        });
        if (isDisruptive) {
            confirmButton.getElement().setAttribute("theme", "tertiary error");
        }
        open();
    }
}
