package com.twb.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

/**
 * @author shrisowdhaman
 * Dec 18, 2017
 */
public class EmailTemplateUpdated {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	private TranslateUtils translateUtils;

	private String templateName;

	private String template;

	private Map<String, String> replacementParams;

	public EmailTemplateUpdated(TranslateUtils translateUtils, String templateName, String messageCode) {
		this.templateName = templateName;
		try {
			this.template = loadTemplate(templateName);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.template = translateUtils.translate(messageCode);
			}catch (Exception ex){
				ex.printStackTrace();
                this.template = "Empty";
			}

		}
	}
 
	private String loadTemplate(String templateName) throws Exception {
//		ClassLoader classLoader = getClass().getClassLoader();
//		File file = new File(classLoader.getResource(templateFile).getFile());
		String content = "Empty";
		try {
		    if(!StringUtils.isEmpty(templateName)){
                File file = new File(templateName);
                content = new String(Files.readAllBytes(file.toPath()));
            }else
                throw new Exception("Template file Name is empty");
		} catch (IOException e) {
		    logger.error(e.getMessage());
			throw new Exception("Could not read template with Name = " + templateName);
		}
		return content;
	}
 
	public String getTemplate(Map<String, String> replacements) {
		String cTemplate = this.template;
 
		//Replace the String 
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
		}
		return cTemplate;
	}
}
