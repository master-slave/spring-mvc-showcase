package org.springframework.samples.mvc.mapping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@RequestMapping(value="/rest/consumes", method=RequestMethod.POST)
	public  void byConsumes(@RequestBody String javaBean) {
      System.out.println(javaBean);
    }


}
