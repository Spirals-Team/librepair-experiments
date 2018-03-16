/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.esigate.aggregator;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.output.StringBuilderWriter;
import org.esigate.Driver;
import org.esigate.DriverFactory;
import org.esigate.HttpErrorPage;
import org.esigate.MockRequestExecutor;
import org.esigate.impl.DriverRequest;
import org.esigate.test.TestUtils;

public class AggregateRendererTest extends TestCase {
    private AggregateRenderer tested;
    private DriverRequest request;

    @Override
    protected void setUp() throws HttpErrorPage {
        MockRequestExecutor requestExecutor = MockRequestExecutor.createMockDriver("mock");
        Driver driver = DriverFactory.getInstance("mock");
        requestExecutor.addResource("/testInclude", "Test include");
        requestExecutor.addResource("/testBlock",
                "before <!--$beginblock$myblock$-->some text goes here<!--$endblock$myblock$--> after");
        requestExecutor.addResource("/testTemplate",
                "before <!--$begintemplate$mytemplate$-->some text goes here<!--$endtemplate$mytemplate$--> after");
        requestExecutor.addResource("/testTemplateParams", "before <!--$begintemplate$mytemplate$-->some text "
                + "<!--$beginparam$param1$-->To be replaced<!--$endparam$param1$-->"
                + " goes here<!--$endtemplate$mytemplate$--> after");
        requestExecutor.addResource("", "before " + "<!--$beginblock$myblock$-->some text goes here"
                + "<!--$endblock$myblock$--> after");
        requestExecutor.addResource("/testNested", "before <!--$beginblock$myblock$--> nested "
                + "<!--$includeblock$mock$/testInclude$--> some text <!--$endincludeblock$-->"
                + " /nested <!--$endblock$myblock$--> after");
        requestExecutor.addResource("/testNestedTemplate", "before <!--$begintemplate$myblock$--> nested "
                + "<!--$includeblock$mock$/testInclude$--> some text <!--$endincludeblock$-->"
                + " /nested <!--$endtemplate$myblock$--> after");
        request = TestUtils.createDriverRequest(driver);
        tested = new AggregateRenderer();
    }

    public void testIncludeBlockNoBlockName() throws IOException, HttpErrorPage {
        String page = "content <!--$includeblock$mock$/testInclude$--> some text <!--$endincludeblock$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content Test include end", out.toString());
    }

    public void testIncludeBlock() throws IOException, HttpErrorPage {
        String page = "content <!--$includeblock$mock$/testBlock$myblock$--> some text <!--$endincludeblock$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text goes here end", out.toString());

        page = "content <!--$includeblock$mock$$(vartestBlock)$myblock$--> some text <!--$endincludeblock$--> end";
        out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text goes here end", out.toString());

    }

    public void testIncludeBlockNested() throws IOException, HttpErrorPage {
        String page = "content <!--$includeblock$mock$/testNested$myblock$--> some text <!--$endincludeblock$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content  nested Test include /nested  end", out.toString());
    }

    public void testIncludeTemplateNested() throws IOException, HttpErrorPage {
        String page =
                "content " + "<!--$includetemplate$mock$/testNestedTemplate$myblock$--> some text "
                        + "<!--$endincludetemplate$-->" + " end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content  nested Test include /nested  end", out.toString());
    }

    public void testIncludeBlockRoot() throws IOException, HttpErrorPage {
        String page = "content <!--$includeblock$mock$$myblock$--> some text <!--$endincludeblock$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text goes here end", out.toString());
    }

    public void testIncludeTemplateNoTemplateName() throws IOException, HttpErrorPage {
        String page = "content <!--$includetemplate$mock$/testInclude$--> some text <!--$endincludetemplate$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content Test include end", out.toString());
    }

    public void testIncludeTemplate() throws IOException, HttpErrorPage {
        String page =
                "content <!--$includetemplate$mock$/testTemplateParams$mytemplate$--> some text "
                        + "<!--$beginput$param1$-->Replacement<!--$endput$-->"
                        + "some other text<!--$endincludetemplate$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text Replacement goes here end", out.toString());
    }

    public void testIncludeTemplateWithVariables() throws IOException, HttpErrorPage {
        String page =
                "content <!--$includetemplate$mock$$(varTestTemplateParams)$mytemplate$--> some text "
                        + "<!--$beginput$param1$-->Replacement<!--$endput$-->"
                        + "some other text<!--$endincludetemplate$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text Replacement goes here end", out.toString());

        page =
                "content <!--$includetemplate$mock$/test$(varTemplate)Params$mytemplate$--> some text "
                        + "<!--$beginput$param1$-->Replacement<!--$endput$-->"
                        + "some other text<!--$endincludetemplate$--> end";
        out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text Replacement goes here end", out.toString());

        page =
                "content <!--$includetemplate$mock$/test$(varTemplate)$(varParams)$mytemplate$--> some text "
                        + "<!--$beginput$param1$-->Replacement<!--$endput$-->"
                        + "some other text<!--$endincludetemplate$--> end";
        out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text Replacement goes here end", out.toString());

    }

    public void testNestedTags() throws IOException, HttpErrorPage {
        String page =
                "content <!--$includetemplate$mock$/testTemplateParams$mytemplate$--> some text "
                        + "<!--$beginput$param1$-->aaa "
                        + "<!--$includeblock$mock$/testInclude$--> some text <!--$endincludeblock$-->"
                        + " bbb<!--$endput$-->" + "some other text<!--$endincludetemplate$--> end";
        StringBuilderWriter out = new StringBuilderWriter();
        tested.render(request, page, out);
        assertEquals("content some text aaa Test include bbb goes here end", out.toString());

    }

}
