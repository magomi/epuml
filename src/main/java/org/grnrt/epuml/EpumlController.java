package org.grnrt.epuml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@RequestMapping("/epuml")
public class EpumlController extends WebMvcConfigurerAdapter {

    @Value("${etherpad.base.url}")
    private String etherpadBaseUrl;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return "epuml";
    }
}
