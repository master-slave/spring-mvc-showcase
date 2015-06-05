# Checking daily for quality accepted answers, listing the interesting ones here

SO query: [spring-mvc] hasaccepted:yes
SO link: http://stackoverflow.com/search?tab=newest&q=%5bspring-mvc%5d%20hasaccepted%3ayes

# 2015-06-04

## Link
http://stackoverflow.com/questions/30639842/how-exactly-are-the-root-context-and-the-dispatcher-servlet-context-into-a-sprin

## Question
I am studying **Spring MVC** and I have some doubt related

So, I have this configuration class that configure my **DispatcherServlet** that handle the user requests:

    public class MyWebAppInitializer implements WebApplicationInitializer {

        @Override
        public void onStartup(ServletContext container) {

            // Create the 'root' Spring application context
            AnnotationConfigWebApplicationContext rootContext = ...
            // Create the dispatcher servlet's Spring application context
            AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();

           dispatcherContext.register(DispatcherConfig.class);

           // Register and map the dispatcher servlet
           ServletRegistration.Dynamic dispatcher = container.addServlet("main", new DispatcherServlet(dispatcherContext));
           dispatcher.setLoadOnStartup(1);
           dispatcher.addMapping("main/");
       }
    }

It is pretty clear for me how the **DispatcherServlet** works. My doubts are related to the **context** concept.

1) What exactly represent a **context**? I think that is something like a set of beans that have a specific pourpose and that works togheter into an environment. But I am absolutly not true about this assertion.

2) What is the difference between the **root context** and the **dispatcher servlet context**?

3) From what I have understand the beans defined in **dispatcherContext** have access to beans defined in **rootContext** (but the opposite is not true). Why?

Tnx

## Answer

Root Context
---
The root-context in a Spring application is the `ApplicationContext` that is loaded by the `ContextLoaderListener`. This context should have globally available resources like services, repositories, infrastructure beans (`DataSource`, `EntityManagerFactory`s etc.) etc.

The `ContextLoaderListener` registers this context in the `ServletContext` under the name `org.springframework.web.context.WebApplicationContext.ROOT`.

*If you load an `ApplicationContext` yourself and register it with the name above in the `ServletContext` that will then qualify as the root-context.*

Child Context
---
The child-context in a Spring application is the `ApplicationContext` that is loaded by a `DispatcherServlet` (or for instance a `MessageDispatcherServlet` in a Spring-WS application). This context should only contain beans relevant to that context, for Spring MVC that would be `ViewResolver`s, `HandlerMapping`s etc.

The servlet registers this context in the `ServletContext` under the name `org.springframework.web.servlet.FrameworkServlet.CONTEXT.<servlet-name>`.

Root <-Child Relation
---
Only child contexts have access to the parent context, because you could have multiple child contexts. For instance in an Spring MVC combined with Spring WS application. The parent-context is detect by the childs by finding it in the `ServletContext` with the well known name.

If the root context would have access to the child which one would it use to wire beans? Next to that if that would be the case you would also get surprising results when AOP is involved. AOP defined in the child context would suddenly influence beans configured in the root context.

# Link
http://stackoverflow.com/questions/30639554/fields-error-and-globalerrors-stay-empty-in-thymeleaf

# Question
I am currently using the following code in a project, the issue I have is that even when there are errors in the bindingresult (bindingResult.hasErrors() is true), it's rendered false in the thymeleaf result. This makes me think the bindingResult isn't "injected" correctly. Did I do anything wrong in the following code?

	<form action="blog.html" th:action="@{/fileUpload}" method="post"
			enctype="multipart/form-data" th:object="${form}">
			<input type="text" name="title" th:field="*{title}" /> <input
					type="text" name="content" th:field="*{content}" /> <input
					type="file" name="myFile" th:field="*{myFile}" /> <input
					type="submit" />

			<div id="errors" class="alert alert-error">
					<ul th:if="${#fields.hasErrors('*')}">
							<li th:each="err : ${#fields.errors('*')}" th:text="${err}"></li>
					</ul>

					<div th:if="${#fields.hasGlobalErrors()}">
							<p th:each="err : ${#fields.globalErrors()}" th:text="${err}">...</p>
					</div>
			</div>
	</form>

Controller


	@RequestMapping(value = "/blog", method = RequestMethod.GET)
	public String getIndex(Model model) {
			model.addAttribute("form", new AddBlogForm());
			return "blog";
	}

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String importParse(Model model, @Valid AddBlogForm form, BindingResult bindingResult) {
			model.addAttribute("form", form);
			try {
					if (!bindingResult.hasErrors()) {
							model.addAttribute("successmessage", "Succesfully added");
							blogSrv.addPost(form.getTitle(), form.getContent(), form.getMyFile());
							model.addAttribute("form", new AddBlogForm());
					}
					return "blog";
			} catch (IllegalStateException e) {
					bindingResult.addError(new ObjectError("image", "IllegalStateException occured " + e.getMessage()));
					return "blog";
			} catch (IOException e) {
					bindingResult.addError(new ObjectError("image", "IOException occured " + e.getMessage()));
					return "blog";
			}
	}

# Answer

You are trying to work around Springs data binding, work with the framework.

First remove the `Model` attribute from your method signature, second use a redirect after a successful save, and finally add `@ModelAttribute` to your `AddBlogForm` annotation.

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String importParse(@Valid @ModelAttribute AddBlogForm form, BindingResult bindingResult) {
		try {
			if (!bindingResult.hasErrors()) {
				blogSrv.addPost(form.getTitle(), form.getContent(), form.getMyFile());
				return "redirect:/blog";
			}
		} catch (IllegalStateException e) {
			bindingResult.addError(new ObjectError("image", "IllegalStateException occured " + e.getMessage()));
		} catch (IOException e) {
			bindingResult.addError(new ObjectError("image", "IOException occured " + e.getMessage()));
		}
		return "blog"
	}

If you really want to add a success message to the model, use `RedirectAttributes` instead and use a flash message so that it is available after the redirect.

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String importParse(@Valid @ModelAttribute AddBlogForm form, BindingResult bindingResult, RedirectAttributes attrs) {
		try {
			if (!bindingResult.hasErrors()) {
				atts.setFlashAttribute("successmessage", "Succesfully added");
				blogSrv.addPost(form.getTitle(), form.getContent(), form.getMyFile());
				return "redirect:/blog";
			}
		} catch (IllegalStateException e) {
			bindingResult.addError(new ObjectError("image", "IllegalStateException occured " + e.getMessage()));
		} catch (IOException e) {
			bindingResult.addError(new ObjectError("image", "IOException occured " + e.getMessage()));
		}
		return "blog"
	}

## Link
http://stackoverflow.com/questions/30627853/spring-boot-setting-up-message-properties-and-errors-properties-file-in-the-proj
## Question
I am new to spring boot. I want to add external properties files in project structure . Files are errors.properties, messages.propeties and sql.properties file which contains the all sql queries. I get it where to add it i.e \demo\src\main\resources\errors.properties file. Can Anyone of you give me insight how to read from these files to my java code .

## Answer
The easiest way would be to leverage what Spring Boot already give you automatically. Anything you put into application.properties (under \demo\src\main\resources\) is going to be added to your Environment. I would just take the keys from those three files and create unique entries in application.properites

    errors.key1=value1
    errors.key2=value2

    sql.key1=value1
    ....

Then you can use the @ConfigurationProperties annotation to map those configurations to a class that encapsulates each type.

    @Component
    @ConfigurationProperties(prefix="errors")
    public class ErrorSettings {

    private String key1;
     .....
    //getter and setters
    }

Now you have a Bean of type ErrorSettings that you can inject into any other Bean you declare and just call the getXXX() method of the configuration you want.

Reference doc:

http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

## Link
http://stackoverflow.com/questions/30616051/how-to-post-generic-objects-to-a-spring-controller
## Question
I want to create a website that displays a form.
The fields of the form depend on a request parameter (and also the form backing bean).
This is my controller that renders the different forms:

	@Controller
	public class TestController {

		@Autowired
		private MyBeanRegistry registry;

		@RequestMapping("/add/{name}")
		public String showForm(@PathVariable String name, Model model) {
			model.addAttribute("name", name);
			model.addAttribute("bean", registry.lookup(name));

			return "add";
		}

	}

The corresponding view looks like this:

	<!DOCTYPE html>
	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
	<head>
	</head>
	<body>
		<form method="post" th:action="@{|/add/${name}|}" th:object="${bean}">
			<th:block th:replace="|${name}::fields|"></th:block>
			<button type="submit">Submit</button>
		</form>
	</body>
	</html>

Following is an example fragment that displays the form fields:

	<!DOCTYPE html>
	<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
	<head>
	</head>
	<body>
		<th:block th:fragment="fields">
			<label for="firstName">First name</label><br />
			<input type="text" id="firstName" th:field="*{firstName}" /><br />
			<label for="lastName">Last name</label><br />
			<input type="text" id="lastName" th:field="*{lastName}" />
		</th:block>
	</body>
	</html>

The looked up bean would be like this:

	public class MyExampleBean {

		private String firstName;

		private String lastName;

		// Getters & setters

	}

The form is rendered correctly, but how can I receive the form back in the controller?
And how can I validate the submitted bean? I tried the following method, but obviously
it can not work:

	@RequestMapping(value = "/add/{name}", method = RequestMethod.POST)
	public String processForm(@PathVariable String name, @Valid Object bean) {
		System.out.println(bean);

		return "redirect:/add/" + name;
	}

Spring creates a new instance of `Object` but the submitted values are lost.
So how can I accomplish this task?

## Answer
If you only wanted to deal with a limited number of beans, you could have one `@RequestMapping` method for each bean, all delegating to a private method that would *do the job*. You can find an example [here](http://stackoverflow.com/a/30576659/3545273).

If you want to be able to accept bean dynamically, you will have to do *by hand* what Spring does automagically :

- only use the request and not a model attribute
- find the bean in registry by the `PathVariable` name
- do explicitely the binding

But hopefully Spring offers the subclasses of `WebDataBinder` as helpers :

    @RequestMapping(value = "/add/{name}", method = RequestMethod.POST)
    public String processForm(@PathVariable String name, WebRequest request) {
        //System.out.println(bean);

        Object myBean = registry.lookup(name);
        WebRequestDataBinder binder = new WebRequestDataBinder(myBean);
        // optionnaly configure the binder
        ...
        // trigger actual binding of request parameters
        binder.bind(request);
        // optionally validate
        binder.validate();
        // process binding results
        BindingResult result = binder.getBindingResult();
        ...

        return "redirect:/add/" + name;
    }


