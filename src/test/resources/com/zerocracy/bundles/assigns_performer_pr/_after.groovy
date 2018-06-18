/**
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
package com.zerocracy.bundles.assigns_performer_pr

import com.jcabi.xml.XML
import com.zerocracy.Project
import com.zerocracy.pm.in.Orders

def exec(Project project, XML xml) {
  def job = 'gh:test/test#1'
  assert new Orders(project).bootstrap().performer(job) == 'krzyk'
  // @todo #1015:30min Fix the problem when PR job is assigned manually it
  //  has role of DEV and 30 points award. After fixing it uncomment following
  //  two lines and the test should pass.
  //assert new Wbs(project).bootstrap().role(job) == 'REV'
  //assert new Boosts(project).bootstrap().factor(job) == 1
}
