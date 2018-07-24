/*
 * Copyright (c) 2016-2018 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.bundles.elects_and_assigns_performer

import com.jcabi.xml.XML
import com.mongodb.client.model.Filters
import com.zerocracy.Farm
import com.zerocracy.Project
import com.zerocracy.pm.Footprint
import com.zerocracy.pm.in.Orders
import com.zerocracy.pm.staff.Elections
import org.cactoos.iterable.LengthOf

def exec(Project project, XML xml) {
  String job = 'gh:test/test#1'
  Orders orders = new Orders(project).bootstrap()
  assert orders.performer(job) == 'yegor256'
  Elections elections = new Elections(project).bootstrap()
  assert !elections.elected(job)
  Farm farm = binding.variables.farm
  assert new LengthOf(
    new Footprint(farm, project).collection().find(
      Filters.and(
        Filters.eq('project', project.pid()),
        Filters.eq('type', 'Performer was elected')
      )
    )
  ).value() == 1
}
