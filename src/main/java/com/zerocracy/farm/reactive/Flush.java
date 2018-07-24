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
package com.zerocracy.farm.reactive;

import com.zerocracy.Project;
import java.io.Closeable;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.xembly.Directive;

/**
 * The flush.
 *
 * @since 1.0
 * @todo #773:30min Use long-polling for claims instead of reactions.
 *  We are going to split application into web part and worker parts,
 *  so we have to submit new claims in both parts, but receive them only in
 *  worker parts via claims polling. Worker app will not be physically
 *  connected to web part, so web part will not be able to notify worker app
 *  about new claims as now via `Flush`es.
 */
interface Flush extends Proc<Project>, Closeable, Scalar<Iterable<Directive>> {
}
