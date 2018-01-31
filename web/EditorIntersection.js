/**
 *  @(#)EditorIntersection.js 0.01 11/10/2017
 *  Copyright (C) 2017 MER-C
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.

 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *  Toggles between the different modes of the article-editor intersection tool.
 */
document.addEventListener('DOMContentLoaded', function() 
{
    document.getElementById('radio_cat').addEventListener('click', function()
    {
        var el = document.getElementById('pages');
        el.disabled = true;
        el.required = false;
        el = document.getElementById('user');
        el.disabled = true;
        el.required = false;
        el = document.getElementById('category');
        el.disabled = false;
        el.required = true;
    });
    
    document.getElementById('radio_user').addEventListener('click', function()
    {
        var el = document.getElementById('pages');
        el.disabled = true;
        el.required = false;
        el = document.getElementById('user');
        el.disabled = false;
        el.required = true;
        el = document.getElementById('category');
        el.disabled = true;
        el.required = false;
    });
    
    document.getElementById('radio_pages').addEventListener('click', function()
    {
        var el = document.getElementById('pages');
        el.disabled = false;
        el.required = true;
        el = document.getElementById('user');
        el.disabled = true;
        el.required = false;
        el = document.getElementById('category');
        el.disabled = true;
        el.required = false;
    });
});
