package org.springframework.samples.mvc.mapping;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Map;

@Controller
public class MappingController {


	@RequestMapping("/mapping/path")
	public @ResponseBody String byPath() {
		return "Mapped by path!";
	}

	@RequestMapping(value="/mapping/path/*", method=RequestMethod.GET)
	public @ResponseBody String byPathPattern(HttpServletRequest request) {
		return "Mapped by path pattern ('" + request.getRequestURI() + "')";
	}

	@RequestMapping(value="/mapping/method", method=RequestMethod.GET)
	public @ResponseBody String byMethod() {
		return "Mapped by path + method";
	}

	@RequestMapping(value="/mapping/parameter", method=RequestMethod.GET, params="foo")
	public @ResponseBody String byParameter() {
		return "Mapped by path + method + presence of query parameter!";
	}

	@RequestMapping(value="/mapping/parameter", method=RequestMethod.GET, params="!foo")
	public @ResponseBody String byParameterNegation() {
		return "Mapped by path + method + not presence of query parameter!";
	}

	@RequestMapping(value="/mapping/header", method=RequestMethod.GET, headers="FooHeader=foo")
	public @ResponseBody String byHeader() {
		return "Mapped by path + method + presence of header!";
	}

	@RequestMapping(value="/mapping/header", method=RequestMethod.GET, headers="!FooHeader")
	public @ResponseBody String byHeaderNegation() {
		return "Mapped by path + method + absence of header!";
	}

	@RequestMapping(value="/mapping/consumes", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String byConsumes(@RequestBody JavaBean javaBean) {
		return "Mapped by path + method + consumable media type (javaBean '" + javaBean + "')";
	}

	@RequestMapping(value="/mapping/produces", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JavaBean byProducesJson() {
		return new JavaBean();
	}

	@RequestMapping(value="/mapping/produces", method=RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody JavaBean byProducesXml() {
		return new JavaBean();
	}

    @RequestMapping(value="/mapping/map", method=RequestMethod.GET)
    public @ResponseBody String map(@RequestParam Map<String, Object> model) {
        model.put("test","test");
        return "map";
    }

}
