package org.springframework.samples.mvc.mapping;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.samples.mvc.AbstractContextControllerTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class MappingControllerTests extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void byPath() throws Exception {
        this.mockMvc.perform(get("/mapping/path")).andExpect(content().string("Mapped by path!"));
    }

    @Test
    public void byPathPattern() throws Exception {
        this.mockMvc.perform(get("/mapping/path/wildcard"))
                .andExpect(content().string("Mapped by path pattern ('/mapping/path/wildcard')"));
    }

    // Optional path variable http://stackoverflow.com/a/29038120/1000933 quite nice
    @Test
    public void byPathPatternOptional() throws Exception {
        this.mockMvc.perform(get("/mapping/optional"))
                .andExpect(content().string("success"));

        this.mockMvc.perform(get("/mapping/optional/test"))
                .andExpect(content().string("success test"));

        this.mockMvc.perform(get("/mapping/optional/test/test2"))
                .andExpect(content().string("success test test2"));
    }

    @Test
    public void byMethod() throws Exception {
        this.mockMvc.perform(get("/mapping/method"))
                .andExpect(content().string("Mapped by path + method"));
    }

    @Test
    public void byParameter() throws Exception {
        this.mockMvc.perform(get("/mapping/parameter?foo=bar"))
                .andExpect(content().string("Mapped by path + method + presence of query parameter!"));
    }

    @Test
    public void byNotParameter() throws Exception {
        this.mockMvc.perform(get("/mapping/parameter"))
                .andExpect(content().string("Mapped by path + method + not presence of query parameter!"));
    }

    @Test
    public void byHeader() throws Exception {
        this.mockMvc.perform(get("/mapping/header").header("FooHeader", "foo"))
                .andExpect(content().string("Mapped by path + method + presence of header!"));
    }

    @Test
    public void byHeaderNegation() throws Exception {
        this.mockMvc.perform(get("/mapping/header"))
                .andExpect(content().string("Mapped by path + method + absence of header!"));
    }

    @Test
    public void byConsumes() throws Exception {
        this.mockMvc.perform(
                post("/mapping/consumes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"foo\": \"bar\", \"fruit\": \"apple\" }".getBytes()))
                .andExpect(content().string(startsWith("Mapped by path + method + consumable media type (javaBean")));
    }

    //http://stackoverflow.com/questions/29622390/passing-date-as-json-object-through-spring-hibernate/29622489#29622489
    // http://stackoverflow.com/questions/25646564/unable-to-convert-string-to-date-by-requestbody-in-spring
    @Test
    public void byDateConversions() throws Exception {
        this.mockMvc.perform(
                post("/mapping/dateConversion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"foo\": \"bar\", \"fruit\": \"apple\", \"date\": \"a20140202\" }".getBytes()))
                .andDo(print())
                .andExpect(jsonPath("$.date", is("a20140202")));
    }

    @Test
    public void byConsumesRest() throws Exception {
        this.mockMvc.perform(
                post("/rest/consumes").accept(MediaType.APPLICATION_JSON)
                          .content("\"{ foo: \"bar\", \"fruit\": \"apple\" }\"".getBytes()))
                .andDo(print());
    }

    @Test
    public void byProducesAcceptJson() throws Exception {
        this.mockMvc.perform(get("/mapping/produces").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.foo").value("bar"))
                .andExpect(jsonPath("$.fruit").value("apple"));
    }

    @Test
    public void byProducesAcceptXml() throws Exception {
        this.mockMvc.perform(get("/mapping/produces").accept(MediaType.APPLICATION_XML))
                .andExpect(xpath("/javaBean/foo").string("bar"))
                .andExpect(xpath("/javaBean/fruit").string("apple"));
    }

    @Test
    public void byProducesJsonExtension() throws Exception {
        this.mockMvc.perform(get("/mapping/produces.json"))
                .andExpect(jsonPath("$.foo").value("bar"))
                .andExpect(jsonPath("$.fruit").value("apple"));
    }

    @Test
    public void byProducesXmlExtension() throws Exception {
        this.mockMvc.perform(get("/mapping/produces.xml"))
                .andExpect(xpath("/javaBean/foo").string("bar"))
                .andExpect(xpath("/javaBean/fruit").string("apple"));
    }

}
