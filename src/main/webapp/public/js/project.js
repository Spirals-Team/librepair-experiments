/* 
 * Copyright (C) 2018 B3Partners B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


$(document).ready(function () {

    $('#projecttype').change(function (event) {
        var type = event.target.value;
            $('#municipality').show();
        if(type === 'IMPORT_PLAYADVISOR'){
            $('#username').hide();
            $('#password').hide();
        }else if(type === 'IMPORT_PLAYMAPPING'){
            $('#username').show();
            $('#password').show();
        }else if(type === 'PLAYMAPPING_PLAYADVISOR'){
            $('#username').show();
            $('#password').show();
        }
    });

});
