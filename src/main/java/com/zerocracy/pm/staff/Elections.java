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
package com.zerocracy.pm.staff;

import com.jcabi.xml.XMLDocument;
import com.jcabi.xml.XSL;
import com.jcabi.xml.XSLDocument;
import com.zerocracy.Item;
import com.zerocracy.Project;
import com.zerocracy.Xocument;
import com.zerocracy.farm.fake.FkItem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.cactoos.io.LengthOf;
import org.cactoos.io.TeeInput;
import org.cactoos.time.DateAsText;
import org.cactoos.time.DateOf;
import org.xembly.Directives;

/**
 * Elections.
 *
 * <p>A project has a collection of elections, one per job. Each election
 * is an event that may happen at any moment of time (usually
 * triggered by {@code elect_performer.groovy}). An election has a
 * collection of user logins as an input and a collection of voters.
 * The point of the election is to send all users through all voters
 * and collect their opinions as floating point numbers in 0..1
 * intervals. Each voter has a weight, which are multiplied by their
 * opinions. The sum of all weighted votes becomes a total rank
 * for a particular user. The user with the largest rank wins.</p>
 *
 * <p>Elections may happen frequently. It's possible to call method elect()
 * multiple times. It will return TRUE only if the result of election
 * is different from the previous call, meaning that someone else
 * was elected.</p>
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Elections {

    /**
     * XSLT.
     * @todo #1553:30min This 'stylesheet' is not merely a document transform,
     *  but in fact the central mechanism for determining the result of the
     *  election. Not only is this approach confusing and convoluted, it's
     *  also very inefficient, since we have to do a XSL transform each and
     *  every time we want to determine the result of an election. Let's remove
     *  this XSL transform, and replace it with a programmatic version. What I
     *  am thinking is to create an inner type, Elections.Result, representing
     *  a single election. It should contain the following information:
     *  1) "elected", true if at least one person has a total non-zero vote
     *  2) "winner", username of person with the highest non-zero vote.
     *  3) "reason", the reason why the winner won.
     *  We should replace all usages of XSL Stylesheet with Elections.Result.
     *  Let's prioritize implementing "elected", since the repeated usage of
     *  this in assign_performer.groovy and elect_performer.groovy is not
     *  scalable for increasingly big projects.
     */
    private static final XSL STYLESHEET = XSLDocument.make(
        Elections.class.getResource("to-winner.xsl")
    );

    /**
     * Project.
     */
    private final Project project;

    /**
     * Ctor.
     * @param pkt Project
     */
    public Elections(final Project pkt) {
        this.project = pkt;
    }

    /**
     * Bootstrap it.
     * @return Itself
     * @throws IOException If fails
     */
    public Elections bootstrap() throws IOException {
        try (final Item team = this.item()) {
            new Xocument(team).bootstrap("pm/staff/elections");
        }
        return this;
    }

    /**
     * How old is it, in milliseconds?
     * @return Milliseconds since recent update
     * @throws IOException If failed
     */
    public long age() throws IOException {
        try (final Item roles = this.item()) {
            return System.currentTimeMillis() - new DateOf(
                new Xocument(roles).xpath("/elections/@updated").get(0)
            ).value().getTime();
        }
    }

    /**
     * Elect a performer.
     * @param job The job to elect
     * @param logins Candidates
     * @param voters Voters and weights
     * @return TRUE if a new election made some changes to the situation
     * @throws IOException If fails
     * @checkstyle ExecutableStatementCountCheck (200 lines)
     */
    public boolean elect(final String job, final Iterable<String> logins,
        final Map<Votes, Integer> voters) throws IOException {
        final String date = new DateAsText().asString();
        final Directives dirs = new Directives()
            .xpath(
                String.format(
                    "/elections[not(job[@id='%s'])]",
                    job
                )
            )
            .add("job")
            .attr("id", job)
            .xpath(
                String.format(
                    "/elections/job[@id='%s' ]",
                    job
                )
            )
            .strict(1)
            .add("election")
            .attr("date", date);
        final StringBuilder log = new StringBuilder(0);
        for (final Map.Entry<Votes, Integer> ent : voters.entrySet()) {
            dirs.add("vote")
                .attr("author", ent.getKey().toString())
                .attr("weight", ent.getValue());
            for (final String login : logins) {
                log.setLength(0);
                dirs.add("person")
                    .attr("login", login)
                    .attr("points", ent.getKey().take(login, log))
                    .set(log.toString())
                    .up();
            }
            dirs.up();
        }
        try (final Item item = this.item()) {
            final Path path = item.path();
            final Path temp = Files.createTempFile("elections", ".xml");
            new LengthOf(new TeeInput(path, temp)).intValue();
            final Project pkt = file -> new FkItem(temp);
            new Xocument(temp).modify(dirs);
            boolean modified = true;
            if (new Elections(pkt).state(job).equals(this.state(job))) {
                new Xocument(temp).modify(
                    new Directives().xpath(
                        String.format(
                            // @checkstyle LineLength (1 line)
                            "/elections/job[@id='%s']/election[@date='%s'][last()]",
                            job, date
                        )
                    ).remove()
                );
                modified = false;
            }
            if (modified) {
                new LengthOf(new TeeInput(temp, path)).intValue();
            }
            return modified;
        }
    }

    /**
     * Remove the entire election for the job.
     * @param job The job
     * @throws IOException If fails
     */
    public void remove(final String job) throws IOException {
        try (final Item roles = this.item()) {
            new Xocument(roles).modify(
                new Directives().xpath(
                    String.format(
                        "/elections/job[@id='%s']",
                        job
                    )
                ).remove()
            );
        }
    }

    /**
     * Iterate election jobs.
     * @return Job list
     * @throws IOException If failed
     */
    public List<String> jobs() throws IOException {
        try (final Item roles = this.item()) {
            return new Xocument(roles).xpath("/elections/job/@id");
        }
    }

    /**
     * Retrieve TRUE if at least one election happened already.
     * @param job The job
     * @return TRUE if an election exists
     * @throws IOException If fails
     */
    public boolean exists(final String job) throws IOException {
        try (final Item item = this.item()) {
            return !new XMLDocument(item.path().toFile()).nodes(
                String.format(
                    "/elections/job[ @id ='%s']/election[ last()]", job
                )
            ).isEmpty();
        }
    }

    /**
     * Retrieve TRUE if the performer is elected for the job.
     * @param job The job
     * @return TRUE if it has a winner
     * @throws IOException If fails
     */
    public boolean elected(final String job) throws IOException {
        boolean elected = this.exists(job);
        if (elected) {
            try (final Item item = this.item()) {
                elected = Elections.STYLESHEET.transform(
                    new XMLDocument(item.path().toFile()).nodes(
                        String.format(
                            "/elections/job[@id ='%s']/election[ last()]", job
                        )
                    ).get(0)
                ).nodes("/summary/winner").iterator().hasNext();
            }
        }
        return elected;
    }

    /**
     * Retrieve the reason for the given job.
     * @param job The job
     * @return The reason
     * @throws IOException If fails
     */
    public String reason(final String job) throws IOException {
        try (final Item item = this.item()) {
            return Elections.STYLESHEET.transform(
                new XMLDocument(item.path().toFile()).nodes(
                    String.format(
                        "/elections/job[@id ='%s']/election[last()]", job
                    )
                ).get(0)
            ).xpath("/summary/reason/text()").get(0);
        }
    }

    /**
     * Retrieve the reason for the given job.
     * @param job The job
     * @return The reason
     * @throws IOException If fails
     */
    public String winner(final String job) throws IOException {
        try (final Item item = this.item()) {
            return Elections.STYLESHEET.transform(
                new XMLDocument(item.path().toFile()).nodes(
                    String.format(
                        "/elections/job[@id='%s']/election[last()]", job
                    )
                ).get(0)
            ).xpath("/summary/winner/text()").get(0);
        }
    }

    /**
     * Current state with this job.
     * @param job The job
     * @return State, encrypted
     * @throws IOException If fails
     */
    private String state(final String job) throws IOException {
        final StringBuilder state = new StringBuilder(0);
        state.append(this.exists(job)).append(' ').append(this.elected(job));
        if (this.elected(job)) {
            state.append(' ').append(this.winner(job));
        }
        return state.toString();
    }

    /**
     * The item.
     * @return Item
     * @throws IOException If fails
     */
    private Item item() throws IOException {
        return this.project.acq("elections.xml");
    }

}
