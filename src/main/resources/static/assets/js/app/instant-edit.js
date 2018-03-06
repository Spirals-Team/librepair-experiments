define(["require", "exports", "utils", "jquery"], function (require, exports, utils_1, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var InstantEdit = /** @class */ (function () {
        function InstantEdit() {
            var _this = this;
            this._document = document;
            this._invalidFormClass = 'is-invalid';
            this._invalidMessageClass = 'invalid-feedback';
            var list = this._document.querySelectorAll('[data-edit]');
            var _loop_1 = function (i) {
                var li = list.item(i);
                var name_1 = li.dataset.edit;
                li.addEventListener('focusin', function () {
                    li.classList.remove(_this._invalidFormClass);
                });
                li.addEventListener('focusout', function () {
                    li.setSelectionRange(0, 0);
                    _this._saveItem(li, name_1);
                });
            };
            for (var i = 0; i < list.length; i++) {
                _loop_1(i);
            }
        }
        InstantEdit.prototype._validateItem = function (field, name) {
            var valid = true;
            var message = '';
            var value = field.value;
            var validationValue = field.dataset.editValidation;
            var validations = validationValue.split(',');
            for (var i = 0; i < validations.length && valid; i++) {
                var validation = validations[i];
                switch (validation) {
                    case 'required':
                        valid = value.length;
                        message = 'Field is required';
                        break;
                    case 'ethereum':
                        valid = value.length > 0 ? value.match(/^0x[a-fA-F0-9]{40}$/) : true;
                        message = 'Not a valid ethereum address';
                        break;
                    case 'telegram':
                        valid = value.length > 0;
                        message = 'Not a valid telegram name';
                        break;
                }
            }
            if (!valid) {
                this._showError(field, name, message);
            }
            else {
                this._hideError(field, name);
            }
            return valid;
        };
        InstantEdit.prototype._loadItem = function (field, name) {
            field.value = localStorage.getItem("fnd.values." + name);
        };
        InstantEdit.prototype._saveItem = function (field, name) {
            var isValid = this._validateItem(field, name);
            var self = this;
            if (!isValid) {
                return;
            }
            utils_1.Utils.showLoading();
            var error = false;
            $.post('/profile/etheraddress', {
                etheraddress: field.value,
                error: function () {
                    self._showError(field, name, 'Something went wrong.');
                },
                success: function () {
                    utils_1.Utils.hideLoading();
                    self._hideError(field, name);
                }
            });
        };
        InstantEdit.prototype._hideError = function (field, name) {
            this._showHideError(field, name);
        };
        InstantEdit.prototype._showError = function (field, name, message) {
            this._showHideError(field, name, true, message);
        };
        InstantEdit.prototype._showHideError = function (field, name, show, errorMessage) {
            if (show === void 0) { show = false; }
            if (errorMessage === void 0) { errorMessage = ''; }
            var messageField = this._document.querySelector("[data-edit-messages=\"" + name + "\"]");
            show ? messageField.classList.add(this._invalidMessageClass) : messageField.classList.remove(this._invalidMessageClass);
            show ? messageField.innerHTML = errorMessage : messageField.innerHTML = '';
            show ? field.classList.add(this._invalidFormClass) : field.classList.remove(this._invalidFormClass);
        };
        return InstantEdit;
    }());
    exports.InstantEdit = InstantEdit;
});
