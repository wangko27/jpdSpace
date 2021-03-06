package com.jingpaidang.sku.controller.example;

import com.jingpaidang.sku.domain.example.Example;
import com.jingpaidang.sku.service.example.ExampleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: JackChan
 * Date: 8/6/13
 * Time: 10:42 AM
 */
@Controller
@RequestMapping("/example")
public class ExampleController {

    @Resource
    private ExampleService exampleService;

    @RequestMapping("example")
    public String example() {

        exampleService.findAllExamples();
        return "example/example";
    }

    @RequestMapping("insertPage")
    public String insertPage(){
        return "example/insert";
    }
    @RequestMapping(value="insert",method = RequestMethod.POST)
    public String insert(Example example){

        exampleService.insert(example);
        return "example/insert";
    }

    @RequestMapping("aopPage")
    public String aopPage(){
        return "example/testAop";
    }

    @RequestMapping(value="testAop",method = RequestMethod.POST)
    public String testAop(Example example){

        String s = exampleService.testAop("chen", 1, false, example);


        return "example/insert";
    }

}
