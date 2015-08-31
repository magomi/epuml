package org.grnrt.epuml;

import net.sourceforge.plantuml.SourceStringReader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.swing.text.html.parser.Entity;
import java.io.*;

@Controller
@RequestMapping("/epuml")
public class EpumlController extends WebMvcConfigurerAdapter {

    @Value("${etherpad.base.url}")
    private String etherpadBaseUrl;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(Epuml epuml, BindingResult bindingResult) {
        epuml.setUrl(etherpadBaseUrl + "epuml-" + epuml.getName());
        epuml.setPicUrl("pic/?name=" + epuml.getName());
        return "epuml";
    }

    @RequestMapping(value = "pic", method = RequestMethod.GET, produces = "image/png")
    public ResponseEntity pic(Epuml epuml, BindingResult bindingResult) {
        HttpGet httpget = new HttpGet(etherpadBaseUrl + "epuml-" + epuml.getName() + "/export/txt");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        byte[] imageData = new byte[0];
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String umlTxt = EntityUtils.toString(entity);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            SourceStringReader reader = new SourceStringReader(umlTxt);

            String desc = reader.generateImage(baos);

            imageData = baos.toByteArray();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity(imageData, HttpStatus.OK);
    }
}
