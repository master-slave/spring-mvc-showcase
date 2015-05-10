package org.springframework.samples.mvc.data;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.samples.mvc.data.custom.CustomArgumentController;
import org.springframework.samples.mvc.data.custom.CustomArgumentResolver;
import org.springframework.samples.mvc.data.custom.RenamingProcessor;
import org.springframework.samples.mvc.data.custom.TestArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;

public class CustomArgumentControllerTests {
	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(new CustomArgumentController())
				.setCustomArgumentResolvers(new CustomArgumentResolver(),new TestArgumentResolver(), new RenamingProcessor(true)).build();
	}

	@Test
	public void param() throws Exception {
		this.mockMvc.perform(get("/data/custom"))
				.andExpect(content().string("Got 'foo' request attribute value 'bar'"));
	}

    @Test
    public void paramCustom() throws Exception {
        this.mockMvc.perform(get("/data/customTest"))
                .andExpect(content().string("Got 'foo' request attribute value 'foo'"));
    }

    @Ignore// http://stackoverflow.com/questions/8986593/how-to-customize-parameter-names-when-binding-spring-mvc-command-objects
    @Test
    public void paramRenaming() throws Exception {
        this.mockMvc.perform(get("/data/rename"))
                .andExpect(content().string("Got 'foo' request attribute value 'bar'"));
    }
}
