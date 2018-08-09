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
package com.zerocracy.stk.pm.in.orders

import com.jcabi.xml.XML
import com.zerocracy.Farm
import com.zerocracy.Project
import com.zerocracy.claims.ClaimIn
import com.zerocracy.entry.ClaimsOf
import com.zerocracy.farm.Assume
import com.zerocracy.pm.in.Orders
import com.zerocracy.pm.qa.Reviews
import com.zerocracy.pm.scope.Wbs
import com.zerocracy.pm.staff.Elections

def exec(Project project, XML xml) {
  new Assume(project, xml).notPmo()
  new Assume(project, xml).type('Ping')
  ClaimIn claim = new ClaimIn(xml)
  Wbs wbs = new Wbs(project).bootstrap()
  Collection<String> orders = new Orders(project).bootstrap().iterate()
  Collection<String> reviews = new Reviews(project).bootstrap().iterate()
  Elections elections = new Elections(project).bootstrap()
  Farm farm = binding.variables.farm
  for (String job : wbs.iterate()) {
    if (orders.contains(job) || reviews.contains(job)) {
      continue
    }
    if (!elections.elected(job)) {
      continue
    }
    String winner = elections.winner(job)
    String reason = elections.reason(job)
    claim.copy()
      .type('Start order')
      .token("job;${job}")
      .param('job', job)
      .param('login', winner)
      .param('reason', reason)
      .param('public', true)
      .postTo(new ClaimsOf(farm, project))
  }
}

